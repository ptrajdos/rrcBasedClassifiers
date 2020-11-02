/**
 * 
 */
package weka.classifiers.meta;

import moa.classifiers.core.driftdetection.ADWIN;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

/**
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class SCMClassifierADWIN extends SCMClassifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4898707752755073928L;
	
	protected ADWIN adwin = new  ADWIN();

	/**
	 * 
	 */
	public SCMClassifierADWIN() {
		super();
		this.initialiseParentFields();
	}

	/**
	 * @param baseClassifier
	 */
	public SCMClassifierADWIN(Classifier baseClassifier) {
		super(baseClassifier);
		this.initialiseParentFields();
		
	}
	private void initialiseParentFields() {
		this.keepOldValidationInstances=true;
		this.decayFactor=0.0;
		this.crossvalidate=true;

	}

	@Override
	public void setDecayFactor(double decayFactor) {
		super.setDecayFactor(0.0);
	}

	@Override
	public void setKeepOldValidationInstances(boolean keepOldValidationInstances) {
		super.setKeepOldValidationInstances(true);
	}

	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		super.buildClassifier(arg0);
		this.initialiseAdwin();
	}
	
	protected void initialiseAdwin() throws Exception {
		Instances tmpValSet = new Instances(this.validationSet);
		int tmpValSize = tmpValSet.size();
		for(int i=0;i<tmpValSize;i++) 
			this.adwinInstanceCheck(tmpValSet.get(i));
			
		
	}
	
	protected void adwinInstanceCheck(Instance instance) throws Exception {
		boolean isPredictionCorrect = Utils.eq(instance.classValue(), this.classifyInstance(instance));
		if(this.adwin.setInput(isPredictionCorrect? 0:1)) {
			this.trimValidationSetADWIN();
		}
		
	}
	
	protected void trimValidationSetADWIN() {
		int adwinSize = this.adwin.getWidth();
		int valSetSize =this.validationSet.size();
		if(adwinSize< valSetSize) {
			for(int i=0;i< valSetSize - adwinSize;i++) {
				this.validationSet.remove(0);
				this.validationResponses.remove(0);
			}
		}
		
	}

	@Override
	public void updateClassifier(Instance instance) throws Exception {
		this.adwinInstanceCheck(instance);
		super.updateClassifier(instance);
	}
	
	

}
