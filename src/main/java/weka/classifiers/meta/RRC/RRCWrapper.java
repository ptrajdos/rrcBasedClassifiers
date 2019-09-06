/**
 * 
 */
package weka.classifiers.meta.RRC;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.RandomizableSingleClassifierEnhancer;
import weka.classifiers.meta.RRC.calculators.RRCCalc;
import weka.classifiers.meta.RRC.calculators.RRCCalcBeta;
import weka.classifiers.trees.J48;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Utils;

/**
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public abstract class RRCWrapper extends RandomizableSingleClassifierEnhancer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4129082572575500420L;
	
	
	
	protected RRCCalc rrcCalc = new RRCCalcBeta();

	/**
	 * 
	 */
	public RRCWrapper() {
		this(new J48());
	}
	
	
	public RRCWrapper(Classifier baseClassifier) {
		this.m_Classifier =baseClassifier;
	}
	

	/* (non-Javadoc)
	 * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
	 */
	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		this.getCapabilities().testWithFail(arg0);
		this.m_Classifier.buildClassifier(arg0);

	}
	
	
	/**
	 * @return the rrcCalc
	 */
	public RRCCalc getRrcCalc() {
		return this.rrcCalc;
	}
	
	public String rrcCalcTipText() {
		return "Object to calculate RRC distribution";
	}


	/**
	 * @param rrcCalc the rrcCalc to set
	 */
	public void setRrcCalc(RRCCalc rrcCalc) {
		this.rrcCalc = rrcCalc;
	}
	
	


	/* (non-Javadoc)
	 * @see weka.classifiers.SingleClassifierEnhancer#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tThe RRC-probabilities-calculator to use"+
		          "(default:" + RRCCalcBeta.class.toGenericString()  + ").\n",
			      "RRC", 0, "-RRC"));
		 
		 newVector.addAll(Collections.list(super.listOptions()));
		    
		return newVector.elements();
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.SingleClassifierEnhancer#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		String rrcCalcString = Utils.getOption("RRC", options);
	    if(rrcCalcString.length() != 0) {
	      String rrcCalcClassSpec[] = Utils.splitOptions(rrcCalcString);
	      if(rrcCalcClassSpec.length == 0) { 
	        throw new Exception("Invalid  RRC calculator String" ); 
	      }
	      String className = rrcCalcClassSpec[0];
	      rrcCalcClassSpec[0] = "";

	      this.setRrcCalc( (RRCCalc)
	                  Utils.forName( RRCCalc.class, 
	                                 className, 
	                                 rrcCalcClassSpec)
	                                        );
	    }
	    else 
	      this.setRrcCalc(new RRCCalcBeta());
		
		super.setOptions(options);
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.SingleClassifierEnhancer#getOptions()
	 */
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
	    

	    options.add("-RRC");
	    String rrcCalcOptions[] = new String[] {""};
	    if(this.rrcCalc instanceof OptionHandler) {
	    	rrcCalcOptions = ((OptionHandler) this.rrcCalc).getOptions();
	    }
	    options.add(this.rrcCalc.getClass().getName()+" "+Utils.joinOptions(rrcCalcOptions)); 
	    
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.SingleClassifierEnhancer#getCapabilities()
	 */
	@Override
	public Capabilities getCapabilities() {
		Capabilities baseCaps = super.getCapabilities();
		baseCaps.disable(Capability.NUMERIC_CLASS);
		baseCaps.enable(Capability.NOMINAL_CLASS);
		return baseCaps; 
	}



}
