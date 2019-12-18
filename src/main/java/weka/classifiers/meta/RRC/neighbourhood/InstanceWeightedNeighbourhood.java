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

/**
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 * 
 *
 */
public class InstanceWeightedNeighbourhood implements NeighbourhoodCalculator, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4927179250883218750L;
	
	protected NeighbourhoodCalculator baseNeighCalc;
	protected Instances dataset;

	/**
	 * 
	 */
	public InstanceWeightedNeighbourhood() {
		this.baseNeighCalc = new DummyNeighbourhood();
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator#getNeighbourhoodCoeffs(weka.core.Instances, weka.core.Instance)
	 */
	@Override
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
		this.dataset = dataset;
		double[] instanceWeights = this.transformInstanceWeights();
		double[] neighbourhoodCoeffs = this.baseNeighCalc.getNeighbourhoodCoeffs(dataset, instance);
		
		return this.combineNeighCoeefsWithWeights(neighbourhoodCoeffs, instanceWeights);
	}
	/**
	 * Generates weights of the instances
	 * @return
	 */
	protected double[] transformInstanceWeights() {
		int numInstances = this.dataset.numInstances();
		double[] result = new double[numInstances];
		for(int i=0;i<numInstances;i++)
			result[i] = this.dataset.get(i).weight();
		
		return result;
	}
	
	/**
	 * Combines neighbourhood coefficients with weights.
	 * Assumed that both arrays have the same length 
	 * @param neighCoeffs
	 * @param weights
	 * @return
	 */
	protected double[] combineNeighCoeefsWithWeights(double[] neighCoeffs, double[] weights) {
		int neighSize = neighCoeffs.length;
		double[] result = new double[neighSize];
		for(int i=0;i<neighSize;i++) 
			result[i] = neighCoeffs[i] * weights[i];
		
		return result;
	}

	/**
	 * @return the baseNeighCalc
	 */
	public NeighbourhoodCalculator getBaseNeighCalc() {
		return this.baseNeighCalc;
	}

	/**
	 * @param baseNeighCalc the baseNeighCalc to set
	 */
	public void setBaseNeighCalc(NeighbourhoodCalculator baseNeighCalc) {
		this.baseNeighCalc = baseNeighCalc;
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tThe NeighbourhoodCalculator to be used internally "+
		          "(default:" + DummyNeighbourhood.class.toGenericString()  + ").\n",
			      "NC", 1, "-NC"));
		 
		return newVector.elements();
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		this.setBaseNeighCalc((NeighbourhoodCalculator) UtilsPT.parseObjectOptions(options, "NC", new DummyNeighbourhood(), NeighbourhoodCalculator.class));
		
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#getOptions()
	 */
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
	    

	    options.add("-NC");
	    options.add(UtilsPT.getClassAndOptions(this.baseNeighCalc));
	    	    
	    
	    return options.toArray(new String[0]);
	}
	
	
	

}
