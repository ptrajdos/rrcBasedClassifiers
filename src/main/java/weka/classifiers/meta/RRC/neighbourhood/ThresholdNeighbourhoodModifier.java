/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;

/**
 * Class converts neighbourhood to the crisp ones using the threshold
 * @author pawel trajdos
 * @since 0.2.0
 * @version 0.2.0
 *
 */
public abstract class ThresholdNeighbourhoodModifier implements NeighbourhoodCalculator, Serializable, OptionHandler {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7829599076507736692L;
	protected double threshold=0.5;
	protected NeighbourhoodCalculator neighCalc;

	/**
	 * 
	 */
	public ThresholdNeighbourhoodModifier() {
		this.neighCalc  = new DummyNeighbourhood();
	}

	/**
	 * @return the threshold
	 */
	public double getThreshold() {
		return this.threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	public String thresholdTipText() {
		return "Threshold to use";
	}

	/**
	 * @return the neighCalc
	 */
	public NeighbourhoodCalculator getNeighCalc() {
		return this.neighCalc;
	}

	/**
	 * @param neighCalc the neighCalc to set
	 */
	public void setNeighCalc(NeighbourhoodCalculator neighCalc) {
		this.neighCalc = neighCalc;
	}
	
	public String neighCalcTipText(){
		return "Neighbourhood calculator to use";
	}

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tThe threshold value to use "+
		          "(default: 0.5).\n",
			      "T", 1, "-T"));
		
		newVector.addElement(new Option(
			      "\tThe Neighbourhood calculator to use"+
		          "(default: weka.classifiers.meta.RRC.neighbourhood.DummyNeighbourhood).\n",
			      "NC", 1, "-NC"));
				
	    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setThreshold(UtilsPT.parseDoubleOption(options, "T", 0.5));
		this.setNeighCalc((NeighbourhoodCalculator) UtilsPT.parseObjectOptions(options, "NC", new DummyNeighbourhood(), NeighbourhoodCalculator.class));
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-T");
		options.add(""+this.getThreshold());
		
		options.add("-NC");
		options.add(UtilsPT.getClassAndOptions(this.neighCalc));
	
	    
	    return options.toArray(new String[0]);
	}
	
	

}
