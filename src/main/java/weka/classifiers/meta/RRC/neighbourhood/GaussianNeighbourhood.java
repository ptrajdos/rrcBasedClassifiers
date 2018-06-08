/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Utils;

/**
 * @author pawel
 *
 */
public class GaussianNeighbourhood extends DistanceBasedNeighbourhood implements OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3874847101238233840L;
	
	protected double alpha=1.0;



	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator#getNeighbourhoodCoeffs(weka.core.Instances, weka.core.Instance)
	 */
	@Override
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
		
		double[] coeffs = this.calculateDistances(dataset, instance);
		for(int i=0;i<coeffs.length;i++)
			coeffs[i] =  Math.exp(-this.alpha * coeffs[i]*coeffs[i]);
		return coeffs;
	}
	
	public String alphaTipText() {
		return "Alpha factor";
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

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\t Alpha factor to be used"+
		          "(default:" + 1.0  + ").\n",
			      "AL", 1, "-AL"));
			 
		 newVector.addAll(Collections.list(super.listOptions()));
		    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		super.setOptions(options);
		
		String alphaString = Utils.getOption("AL", options);
		if(alphaString.length()!=0) {
			this.alpha = Double.parseDouble(alphaString);
		}else {
			this.alpha=1.0;
		}
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();

	    options.add("-AL");
	    options.add(""+this.alpha);
	    
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
	}
	
	

}
