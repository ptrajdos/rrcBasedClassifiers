/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;
import weka.core.distances.MahalanobisDistance;

/**
 * @author pawel
 * @since 0.2.0
 * @version 0.2.0
 *
 */
public class DynamicMahalanobisNeighbourhood implements NeighbourhoodCalculator, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8227764259065298547L;
	
	protected GaussianNeighbourhood initWeightsCalc;
	protected GaussianNeighbourhood distNeigh;
	protected double alpha=1.0;
	protected boolean normalize=false;
	MahalanobisDistance mahDist;
	

	

	/**
	 * 
	 */
	public DynamicMahalanobisNeighbourhood() {
		this.initWeightsCalc = new GaussianNeighbourhood();
		this.mahDist = new MahalanobisDistance();
		this.distNeigh = new GaussianNeighbourhood();
		this.distNeigh.setDistFun(this.mahDist);
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator#getNeighbourhoodCoeffs(weka.core.Instances, weka.core.Instance)
	 * Warning -- reweights dataset, no thread safe
	 */
	@Override
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
		int numInst = dataset.numInstances();
		double[] weights = this.initWeightsCalc.getNeighbourhoodCoeffs(dataset, instance);
		for(int i=0;i<numInst;i++)
			dataset.get(i).setWeight(weights[i]);
		
		double[] neighCoeffs = this.distNeigh.getNeighbourhoodCoeffs(dataset, instance);
		
		return neighCoeffs;
	}

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tAlpha factor for gaussian neighbourhood"+
		          "(default: 1.0 ).\n",
			      "A", 1, "-A"));
		    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setAlpha(UtilsPT.parseDoubleOption(options, "A", 1.0));
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
	    

	    options.add("-A");
	    options.add(""+this.alpha);
	    
	    return options.toArray(new String[0]);
	}

	/**
	 * @return the alpha
	 */
	public double getAlpha() {
		return this.alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
		this.initWeightsCalc.setAlpha(alpha);
		this.distNeigh.setAlpha(alpha);
	}
	
	/**
	 * @return the normalize
	 */
	public boolean isNormalize() {
		return this.normalize;
	}

	/**
	 * @param normalize the normalize to set
	 */
	public void setNormalize(boolean normalize) {
		this.normalize = normalize;
		this.mahDist.setNormalize(normalize);
	}

	/**
	 * @return the initWeightsCalc
	 */
	public GaussianNeighbourhood getInitWeightsCalc() {
		return this.initWeightsCalc;
	}

	/**
	 * @param initWeightsCalc the initWeightsCalc to set
	 */
	public void setInitWeightsCalc(GaussianNeighbourhood initWeightsCalc) {
		this.initWeightsCalc = initWeightsCalc;
	}
	
	
	

}
