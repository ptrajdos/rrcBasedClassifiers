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
 * Nearest Neighbourhood with inverse weighting
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class NearestNeighbourInverseWeightNeighbourhood extends NearestNeighbourNeighbourhood {

	/**
	 * 
	 */
	private static final long serialVersionUID = 393902139849024888L;
	
	private double eps =1.0;

	/**
	 * 
	 */
	public NearestNeighbourInverseWeightNeighbourhood() {
		super();
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NearestNeighbourNeighbourhood#calculateWeight(double)
	 */
	@Override
	protected double calculateWeight(double distance) {
		double wei = 1.0/ (distance + this.eps);
		return wei;
	}

	/**
	 * @return the eps
	 */
	public double getEps() {
		return this.eps;
	}

	/**
	 * @param eps the eps to set
	 */
	public void setEps(double eps) {
		this.eps = eps;
	}
	
	
	public String epsTipText() {
		return "Epsilon factor used during calculations";
	}
	
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\t Epsilon to be used"+
		          "(default:" + 1.0  + ").\n",
			      "EPS", 1, "-EPS"));
			 
		 newVector.addAll(Collections.list(super.listOptions()));
		    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		super.setOptions(options);
		
		this.setEps(UtilsPT.parseDoubleOption(options, "EPS", 1.0));
		
		
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();

	    options.add("-EPS");
	    options.add(""+this.getEps());
	    
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
	}

}
