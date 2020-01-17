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
	
	private double exponent=1.0;
	private double expLowerBound=2E-3;
	

	/**
	 * 
	 */
	public PowerInstanceWeightedNeighbourhood() {
		super();
	}


	/**
	 * @return the alpha
	 */
	public double getExponent() {
		return this.exponent;
	}


	/**
	 * @param exponent the alpha to set
	 */
	public void setExponent(double exponent) {
		this.exponent = exponent;
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.InstanceWeightedNeighbourhood#transformInstanceWeights()
	 */
	@Override
	protected double[] transformInstanceWeights() {
		double[] weights =super.transformInstanceWeights(); 
		for(int i=0;i<weights.length;i++) 
			if(this.exponent>this.expLowerBound)
				weights[i]= Math.pow(weights[i], this.exponent);
			else
				weights[i]=1.0;
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
			      "EXP", 1, "-EXP"));
		 
		 
		 newVector.addElement(new Option(
			      "\tThe power factor Lower bound "+
		          "(default:" +1E-3  + ").\n",
			      "EXPL", 1, "-EXPL"));

		 
		newVector.addAll(Collections.list(super.listOptions()));
		return newVector.elements();
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.InstanceWeightedNeighbourhood#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setExponent(UtilsPT.parseDoubleOption(options, "EXP", 1.0));
		this.setExpLowerBound(UtilsPT.parseDoubleOption(options, "EXPL", 1E-3));
		
		super.setOptions(options);
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.InstanceWeightedNeighbourhood#getOptions()
	 */
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("EXP");
		options.add(""+this.exponent);
		
		options.add("EXPL");
		options.add(""+this.expLowerBound);
		
		
		Collections.addAll(options, super.getOptions());
		return options.toArray(new String[0]);
	}


	/**
	 * @return the expLowerBound
	 */
	public double getExpLowerBound() {
		return this.expLowerBound;
	}


	/**
	 * @param expLowerBound the expLowerBound to set
	 */
	public void setExpLowerBound(double expLowerBound) {
		this.expLowerBound = expLowerBound;
	}
	
	
	

}
