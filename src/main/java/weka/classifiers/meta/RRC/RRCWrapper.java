/**
 * 
 */
package weka.classifiers.meta.RRC;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.RandomizableSingleClassifierEnhancer;
import weka.classifiers.UpdateableClassifier;
import weka.classifiers.meta.RRC.calculators.RRCCalc;
import weka.classifiers.meta.RRC.calculators.RRCCalcBeta;
import weka.classifiers.trees.J48;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;
import weka.core.UtilsPT;
import weka.tools.GlobalInfoHandler;

/**
 * @author pawel trajdos
 * @since 0.1.0
 * @version 2.0.0
 *
 */
public abstract class RRCWrapper extends RandomizableSingleClassifierEnhancer implements UpdateableClassifier,GlobalInfoHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4129082572575500420L;
	
	
	
	protected RRCCalc rrcCalc = new RRCCalcBeta();
	
	private boolean updateBaseClassifier=true;

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
			      "RRC", 1, "-RRC"));
		 
		 newVector.addElement(new Option(
			      "\tDetermines whether the base classifier should be updated"+
		          "(default:" + "TRUE"  + ").\n",
			      "UPD", 0, "-UPD"));
		 
		 newVector.addAll(Collections.list(super.listOptions()));
		    
		return newVector.elements();
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.SingleClassifierEnhancer#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setRrcCalc((RRCCalc) UtilsPT.parseObjectOptions(options, "RRC", new RRCCalcBeta(), RRCCalc.class));
		this.setUpdateBaseClassifier(Utils.getFlag("UPD", options));
		
		
		super.setOptions(options);
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.SingleClassifierEnhancer#getOptions()
	 */
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
	    

	    options.add("-RRC");
	    options.add(UtilsPT.getClassAndOptions(this.getRrcCalc()));
	    
	    if(this.isUpdateBaseClassifier())
	    	options.add("-UPD");
	    
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
	
	
	/* (non-Javadoc)
	 * @see weka.tools.GlobalInfoHandler#globalInfo()
	 */
	@Override
	public String globalInfo() {
		return "Wrapper for classifiers based on the RRC framework";
	}

	/**
	 * @return the updateBaseClassifier
	 */
	public boolean isUpdateBaseClassifier() {
		return this.updateBaseClassifier;
	}


	/**
	 * @param updateBaseClassifier the updateBaseClassifier to set
	 */
	public void setUpdateBaseClassifier(boolean updateBaseClassifier) {
		this.updateBaseClassifier = updateBaseClassifier;
	}
	
	public String updateBaseClassifierTipText() {
		return "Determines whether the base classifier should be updated.";
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.UpdateableClassifier#updateClassifier(weka.core.Instance)
	 */
	@Override
	public void updateClassifier(Instance instance) throws Exception {
		if(this.m_Classifier instanceof UpdateableClassifier & this.updateBaseClassifier) {
			((UpdateableClassifier) this.m_Classifier).updateClassifier(instance);
		}
		
	}



}
