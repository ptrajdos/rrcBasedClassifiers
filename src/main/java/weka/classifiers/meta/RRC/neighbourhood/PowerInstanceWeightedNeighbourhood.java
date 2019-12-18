/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.UtilsPT;

/**
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 * 
 *
 */
public class PowerInstanceWeightedNeighbourhood extends InstanceWeightedNeighbourhood {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3188282022062599359L;
	
	private double alpha=1.0;
	

	/**
	 * 
	 */
	public PowerInstanceWeightedNeighbourhood() {
		super();
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
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.InstanceWeightedNeighbourhood#transformInstanceWeights()
	 */
	@Override
	protected double[] transformInstanceWeights() {
		double[] weights =super.transformInstanceWeights(); 
		for(int i=0;i<weights.length;i++)
			weights[i]= Math.pow(weights[i], this.alpha);
		return weights;  
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.InstanceWeightedNeighbourhood#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tThe power factor to be used "+
		          "(default:" +1.0  + ").\n",
			      "ALP", 1, "-ALP"));
		 
		 
		newVector.addAll(Collections.list(super.listOptions()));
		return newVector.elements();
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.InstanceWeightedNeighbourhood#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setAlpha(UtilsPT.parseDoubleOption(options, "ALP", 1.0));
		super.setOptions(options);
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.InstanceWeightedNeighbourhood#getOptions()
	 */
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		Collections.addAll(options, super.getOptions());
		return options.toArray(new String[0]);
	}
	
	

}
