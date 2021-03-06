/**
 * 
 */
package weka.classifiers.meta.RRCEns;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.classifiers.RandomizableSingleClassifierEnhancer;
import weka.classifiers.UpdateableClassifier;
import weka.classifiers.meta.CommitteeWrapper;
import weka.classifiers.meta.RRCEns.calculators.RRCCalcEns;
import weka.classifiers.meta.RRCEns.calculators.RRCCalcEnsEstimator;
import weka.classifiers.meta.committees.Committee;
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
 * @since 1.0.0
 * @version 2.0.0
 *
 */
public abstract class RRCWrapper extends RandomizableSingleClassifierEnhancer implements UpdateableClassifier,GlobalInfoHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4129082572575500420L;
	
	
	/**
	 * RRC calculator to use
	 */
	protected RRCCalcEns rrcCalc = new RRCCalcEnsEstimator();
	
	private boolean updateBaseClassifier=true;
	
	
	/**
	 * Committee wraper
	 */
	private CommitteeWrapper comWrapp; 
	
	public RRCWrapper() {
		this.comWrapp = new CommitteeWrapper();
		this.comWrapp.setClassifier(getClassifier());
	}

	

	/* (non-Javadoc)
	 * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
	 */
	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		if(!this.getDoNotCheckCapabilities())
			this.getCapabilities().testWithFail(arg0);
		
		this.m_Classifier.buildClassifier(arg0);
		this.comWrapp.setClassifier(this.m_Classifier);

	}
	
	
	/**
	 * @return the rrcCalc
	 */
	public RRCCalcEns getRrcCalc() {
		return this.rrcCalc;
	}
	
	public String rrcCalcTipText() {
		return "Object to calculate RRC distribution";
	}


	/**
	 * @param rrcCalc the rrcCalc to set
	 */
	public void setRrcCalc(RRCCalcEns rrcCalc) {
		this.rrcCalc = rrcCalc;
	}
	
	


	/* (non-Javadoc)
	 * @see weka.classifiers.SingleClassifierEnhancer#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tThe RRC calculator  to use "+
		          "(default: weka.classifiers.meta.RRCEns.calculators.RRCCalcEnsTransientAverage).\n",
			      "RRC", 1, "-RRC"));
		
		newVector.addElement(new Option(
			      "\tUpdate base classifier"+
		          "(default: true).\n",
			      "UB",0, "-UB"));
		 
		newVector.addAll(Collections.list(super.listOptions()));

		return newVector.elements();
	}


	/* (non-Javadoc)
	 * @see weka.classifiers.SingleClassifierEnhancer#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setRrcCalc((RRCCalcEns) UtilsPT.parseObjectOptions(options, "RRC", new RRCCalcEnsEstimator(), RRCCalcEns.class));
		
		
		this.setUpdateBaseClassifier(Utils.getFlag("UB", options));
		
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
	    	options.add("-UB");
	    
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
		baseCaps.disable(Capability.DATE_CLASS);
		baseCaps.disable(Capability.MISSING_CLASS_VALUES);
		baseCaps.disable(Capability.NO_CLASS);
		baseCaps.disable(Capability.UNARY_CLASS);
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


	protected Committee getCommittee() {
		this.comWrapp.setClassifier(m_Classifier);
		return this.comWrapp;
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
