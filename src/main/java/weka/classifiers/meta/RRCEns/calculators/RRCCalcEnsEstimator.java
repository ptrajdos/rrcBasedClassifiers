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
import weka.estimators.density.BoundedKernelEstimator;
import weka.estimators.density.DensityEstimator;
import weka.estimators.density.SimpleKernelEstimator;
import weka.tools.SerialCopier;
import weka.tools.numericIntegration.Function;
import weka.tools.numericIntegration.Integrator;
import weka.tools.numericIntegration.TrapezoidalIntegrator;

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

	/**
	 * 
	 */
	public RRCCalcEnsEstimator() {
		BoundedKernelEstimator bKernel = new BoundedKernelEstimator();
		bKernel.setKernEstim(new SimpleKernelEstimator());
		this.estimator = bKernel;
	}
	
	class DistrFunction implements Function{
		
		private int currClass =0;
		private DensityEstimator[] estimators;

		@Override
		public double getValue(double argument) {
			
			double pdf = this.estimators[currClass].getPDF(argument);
			double product =1;
			for(int i =0 ;i< estimators.length;i++) {
				if(i == currClass)continue;
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
		
		Integrator integr = new TrapezoidalIntegrator();
		integr.setFunction(distrFun);
		
		for(int c =0 ; c<numClasses;c++) {
			distrFun.setCurrClass(c);
			distribution[c] = integr.integrate();
			
		}
		
		
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
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		BoundedKernelEstimator bKernel = new BoundedKernelEstimator();
		bKernel.setKernEstim(new SimpleKernelEstimator());
		this.setEstimator((DensityEstimator) UtilsPT.parseObjectOptions(options, "EST", bKernel, DensityEstimator.class));
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
	    
	    options.add("-EST");
	    options.add(UtilsPT.getClassAndOptions(this.estimator));
	    
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
	
	

}
