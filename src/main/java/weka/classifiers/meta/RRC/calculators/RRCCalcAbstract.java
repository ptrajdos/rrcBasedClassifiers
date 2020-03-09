package weka.classifiers.meta.RRC.calculators;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;

/**
 * Abstract class for integration-based RRC calculators
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */

public abstract class RRCCalcAbstract implements RRCCalc, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6508607518098700281L;
	
	
	protected int integrLen  = 51;
	
	public static final double EPS=1E-6;

	/**
	 * @return the integrLen
	 */
	public int getIntegrLen() {
		return integrLen;
	}

	/**
	 * @param integrLen the integrLen to set
	 */
	public void setIntegrLen(int integrLen) {
		this.integrLen = integrLen;
	}
	
	
	public String integrLenTipText() {
		return "Integration length used";
	}

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tThe length of integration sequence"+
		          "(default:" + 51  + ").\n",
			      "IL", 1, "-IL"));
		 
		 
		return newVector.elements();
	}
		
	

	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setIntegrLen(UtilsPT.parseIntegerOption(options, "IL", 51));
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-IL");
		options.add(""+this.getIntegrLen());
		
		
	    return options.toArray(new String[0]);
	}
	
	
	
	
}
