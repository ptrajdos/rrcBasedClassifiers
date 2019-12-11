/**
 * 
 */
package weka.classifiers.meta.RRCEns.calculators;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.core.UtilsPT;
import weka.estimators.density.BoundedEstimator;
import weka.estimators.density.DensityEstimator;
import weka.estimators.density.SimpleKernelEstimator;
import weka.estimators.density.bandwidthFinders.MaximalSmoothingPrincipleBandwidthSelectionKernel;
import weka.estimators.density.tools.ROIFinder;
import weka.tools.SerialCopier;
import weka.tools.numericIntegration.Function;
import weka.tools.numericIntegration.SimpsonsIntegrator;

/**
 * RRC probability estimator uese Kernel Density estimators
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class RRCCalcEnsEstimator implements RRCCalcEns, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1863996271170736592L;
	
	protected DensityEstimator estimator;
	
	protected int integrationSequenceLength =100;

	/**
	 * 
	 */
	public RRCCalcEnsEstimator() {
		BoundedEstimator bKernel = new BoundedEstimator();
		bKernel.setKernEstim(new MaximalSmoothingPrincipleBandwidthSelectionKernel());
		this.estimator = bKernel;
	}
	
	class DistrFunction implements Function{
		
		private int currClass =0;
		private DensityEstimator[] estimators;

		@Override
		public double value(double argument) {
			
			double pdf = this.estimators[this.currClass].getPDF(argument);
			double product =1;
			for(int i =0 ;i< this.estimators.length;i++) {
				if(i == this.currClass)continue;
				product*= this.estimators[i].getCDF(argument);
			}
			return pdf*product;
		}

		/**
		 * @return the currClass
		 */
		public int getCurrClass() {
			return this.currClass;
		}

		/**
		 * @param currClass the currClass to set
		 */
		public void setCurrClass(int currClass) {
			this.currClass = currClass;
		}

		/**
		 * @return the estimators
		 */
		public DensityEstimator[] getEstimators() {
			return this.estimators;
		}

		/**
		 * @param estimators the estimators to set
		 */
		public void setEstimators(DensityEstimator[] estimators) {
			this.estimators = estimators;
		}
		
		
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRCEns.calculators.RRCCalcEns#calculateRRC(double[][])
	 */
	@Override
	public double[] calculateRRC(double[][] ensemblePrediction)throws Exception {
		int numClassifiers = ensemblePrediction.length;
		int numClasses = ensemblePrediction[0].length;
		
		/**
		 * Build density estimators
		 */
		DensityEstimator[] estimators = new DensityEstimator[numClasses];
		for(int c=0;c<estimators.length;c++) {
			estimators[c]  = (DensityEstimator) SerialCopier.makeCopy(this.estimator);
			for(int i =0 ;i<numClassifiers;i++)
				estimators[c].addValue(ensemblePrediction[i][c], 1.0);
		}
		
		/**
		 * Calculate probabilities
		 */
		double[] distribution = new double[numClasses];
		DistrFunction distrFun = new DistrFunction();
		distrFun.setEstimators(estimators);
		
		SimpsonsIntegrator integr = new SimpsonsIntegrator();
		integr.setFunction(distrFun);
		integr.setLowerBound(0 + 1e-6);
		integr.setUpperBound(1 - 1e-6);
		integr.setSequenceLength(integrationSequenceLength);
		
		double[] roi;
		for(int c =0 ; c<numClasses;c++) {
			roi = ROIFinder.findRoi(estimators[c], 1E-6, 1-1E-6, integrationSequenceLength);
			distrFun.setCurrClass(c);
			integr.setLowerBound(roi[0]);
			integr.setUpperBound(roi[1]);
			distribution[c] = integr.integrate();
			
		}
		
		double sum = Utils.sum(distribution);
		//TODO in some datasets it is equal to zero thyroid  Truncated Normal and J48 classifier for example
		//Probably caused by low wariance in the classifier prediction
		Utils.normalize(distribution);
			
		
		return distribution;
	}

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tThe  DensityEstimator  to use "+
		          "(default: weka.estimators.density.BoundedKernelEstimator.BoundedKernelEstimator).\n",
			      "EST", 1, "-EST"));
		
		newVector.addElement(new Option(
			      "\tThe  lentgth of the sequence used to calculate probabilities"+
		          "(default: 100).\n",
			      "ILEN", 1, "-ILEN"));
		
		
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
		BoundedEstimator bKernel = new BoundedEstimator();
		bKernel.setKernEstim(new SimpleKernelEstimator());
		this.setEstimator((DensityEstimator) UtilsPT.parseObjectOptions(options, "EST", bKernel, DensityEstimator.class));
		
		this.setIntegrationSequenceLength(UtilsPT.parseIntegerOption(options, "ILEN", 100));
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
	    
	    options.add("-EST");
	    options.add(UtilsPT.getClassAndOptions(this.estimator));
	    
	    options.add("-ILEN");
	    options.add(""+this.integrationSequenceLength);
	    
	    return options.toArray(new String[0]);
	}

	/**
	 * @return the estimator
	 */
	public DensityEstimator getEstimator() {
		return this.estimator;
	}

	/**
	 * @param estimator the estimator to set
	 */
	public void setEstimator(DensityEstimator estimator) {
		this.estimator = estimator;
	}

	/**
	 * @return the integrationSequenceLength
	 */
	public int getIntegrationSequenceLength() {
		return this.integrationSequenceLength;
	}

	/**
	 * @param integrationSequenceLength the integrationSequenceLength to set
	 */
	public void setIntegrationSequenceLength(int integrationSequenceLength) {
		this.integrationSequenceLength = integrationSequenceLength;
	}
	
	public String integrationSequenceLengthTipText() {
		return "The length of integration sequence used to calculate the probability.";
	}

}
