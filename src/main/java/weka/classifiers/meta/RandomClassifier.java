/**
 * 
 */
package weka.classifiers.meta;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import weka.classifiers.RandomizableSingleClassifierEnhancer;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;

/**
 * Class implements Random classifier
 * @author pawel trajdos
 * @since 0.1.0
 * @version 1.0.0
 *
 */
public abstract class RandomClassifier extends RandomizableSingleClassifierEnhancer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8015924482789305122L;
	
	protected Random random = new Random(this.getSeed());
	
	protected boolean randomizeResponse=false;
	
	

	/**
	 * 
	 */
	public RandomClassifier() {
		super();
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
	 */
	@Override
	public void buildClassifier(Instances data) throws Exception {
		if(!this.m_DoNotCheckCapabilities)
			this.getCapabilities().testWithFail(data);
		this.m_Classifier.buildClassifier(data);

	}




	/*public String seedTipText() {
		return "The seed for the random generator";
	}*/
	
	public String globalInfo(){
		return "Performs the randomized classification according to the soft outputs returned by the base classifier";
	}
	
	
	
	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifier#getCapabilities()
	 */
	@Override
	public Capabilities getCapabilities() {
		Capabilities capabilities = this.m_Classifier.getCapabilities();
		capabilities.disable(Capability.NUMERIC_CLASS);
		capabilities.setOwner(this);
		return  capabilities;
		 
	}
	
	public boolean isRandomizeResponse() {
		return this.randomizeResponse;
	}
	
	public void setRandomizeResponse(boolean randomizeResponse) {
		this.randomizeResponse= randomizeResponse;
	}
	public String randomizeResponseTipText() {
		return "Determines if the response is randomized";
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.RandomizableSingleClassifierEnhancer#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tDetermines whether the randomization is performed"+
		          "(default:" + 0  + ").\n",
			      "RA", 0, "-RA"));
		 
		 newVector.addAll(Collections.list(super.listOptions()));
		    
		return newVector.elements();
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.RandomizableSingleClassifierEnhancer#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		super.setOptions(options);
		
		String randomizeString = Utils.getOption("RA", options);
		if(randomizeString.length() !=0) {
			this.randomizeResponse = Integer.parseInt(randomizeString)>0? true:false;
		}else {
			this.randomizeResponse=false;
		}
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.RandomizableSingleClassifierEnhancer#getOptions()
	 */
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
	    

	    options.add("-RA");
	    options.add(""+(this.isRandomizeResponse()?1:0));
	    
	    
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
	}
	

}
