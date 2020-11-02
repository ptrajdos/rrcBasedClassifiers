/**
 * 
 */
package weka.classifiers.meta.RRC;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.meta.RRC.neighbourhood.DummyNeighbourhood;
import weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator;
import weka.classifiers.trees.J48;
import weka.core.DenseInstanceFast;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;
import weka.core.UtilsPT;
import weka.tools.data.InstancesOperator;

/**
 * Base class for classifiers based on RRC.
 * The classifier uses validation and training sets generated using crossvalidation.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 1.1.0
 *
 */
public abstract class RRCBasedWithValidation extends RRCWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6254717501799840574L;
	
	/**
	 * Validation set
	 */
	protected Instances validationSet;
	/**
	 * The object is used to calculate neighbourhood coefficients during the prediction phase
	 */
	protected NeighbourhoodCalculator neighCalc = new DummyNeighbourhood();
	
	/**
	 * Indicates whether the crossvalidation or single split should be done
	 */
	protected boolean crossvalidate=false;
	
	/**
	 * The percentage of the training set if the single split is used.
	 */
	protected double splitFactor=0.6;
	
	/**
	 * The number of CV folds is the crossvalidation is used
	 */
	protected int kFolds=2;
	
	/**
	 * Classifier responses on validation examples
	 */
	protected List<double[]> validationResponses; 
	
	/**
	 * Determines whether old instances should be kept
	 */
	protected boolean keepOldValidationInstances = false;
	
	
	/**
	 * Decay factor for instances in the validation set
	 */
	protected double decayFactor=1E-3;
	
	
	protected boolean updateValidationSet=true;
	
	protected int zeroedCounter;
	

	/**
	 * Default constructor 
	 */
	public RRCBasedWithValidation() {
		this(new J48());
		this.zeroedCounter =0;
	}

	/**
	 * Constructs the object using given base classifer.
	 * @param baseClassifier -- base classifier to use
	 */
	public RRCBasedWithValidation(Classifier baseClassifier) {
		super(baseClassifier);
	}
	
	
	

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRCWrapper#buildClassifier(weka.core.Instances)
	 */
	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		this.getCapabilities().testWithFail(arg0);
		if(this.crossvalidate) {
			this.buildCV(arg0);
		}else {
			this.buildSplit(arg0);
		}
		
	}
	
	protected void buildSplit(Instances inst) throws Exception {
		Instances[] tmpSet = InstancesOperator.stratifiedSplitSet(inst, this.splitFactor, this.m_Seed);
		Instances train = tmpSet[0];
		this.validationSet = tmpSet[1];
		this.m_Classifier.buildClassifier(train);
		
		//validate
		int valInstNum = this.validationSet.numInstances(); 
		this.validationResponses = new LinkedList<double[]>();
		for(int i=0;i<valInstNum;i++ ) {
			this.validationResponses.add(this.rrcCalc.calculateRRC(this.m_Classifier.distributionForInstance(this.validationSet.get(i))));			
		}
		
	}
	
	protected void buildCV(Instances inst) throws Exception {
		this.validationSet = new Instances(inst, 0);
		Instances tmpSet = new Instances(inst);
		tmpSet.stratify(this.kFolds);
		this.validationResponses = new LinkedList<double[]>();
		
		//iteratr over folds
		Instances train, val;
		int valInstNum;
		Instance tmpInstance;
		
		int numInstances = inst.numInstances();
		if(kFolds>numInstances & numInstances>0)
			kFolds=numInstances;
		
		if(numInstances==0) {
			this.m_Classifier.buildClassifier(inst);
			return;
		}
		
		
		for(int f=0;f<this.kFolds;f++) {
			train = tmpSet.trainCV(this.kFolds, f);
			val = tmpSet.testCV(this.kFolds, f);
			this.m_Classifier.buildClassifier(train);
			valInstNum= val.numInstances();
			for(int i=0;i<valInstNum;i++) {
				tmpInstance  = new DenseInstanceFast(val.get(i));
				tmpInstance.setDataset(val);
				this.validationSet.add(tmpInstance);
				this.validationResponses.add(this.rrcCalc.calculateRRC(this.m_Classifier.distributionForInstance(tmpInstance)));
			}
			
			
		}
		
		this.m_Classifier.buildClassifier(inst);
		
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRCWrapper#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tIndicates whether the crossvalidation approach is used"+
		          "(default:" + false  + ").\n",
			      "CV", 0, "-CV"));
		 
		 newVector.addElement(new Option(
			      "\t Split Factor"+
		          "(default:" + 0.6  + ").\n",
			      "SF", 1, "-SF"));
		 
		 newVector.addElement(new Option(
			      "\t Number of Crossvalidation splits"+
		          "(default:" + 2  + ").\n",
			      "KF", 1, "-KF"));
		 
		 
		 newVector.addElement(new Option(
			      "\t Neighbourhood calculator to use"+
		          "(default: weka.classifiers.meta.RRC.neighbourhood.DummyNeighbourhood ).\n",
			      "NC", 1, "-NC"));
		 
		 newVector.addElement(new Option(
			      "\t Determines whether old instances in the validation set should be kept"+
		          "(default: false ).\n",
			      "KI", 0, "-KI"));
		 
		 newVector.addElement(new Option(
			      "\t Determines whether old instances in the validation set should be kept"+
		          "(default: 1E-3 ).\n",
			      "DF", 1, "-DF"));
		 
		 newVector.addElement(new Option(
			      "\t Determines whether the validation set should be updated"+
		          "(default: true ).\n",
			      "UVS", 0, "-UVS"));
		 
		 
		 
		 newVector.addAll(Collections.list(super.listOptions()));
		    
		return newVector.elements();
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRCWrapper#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		super.setOptions(options);
		
		this.setCrossvalidate(Utils.getFlag("CV", options));

		
		this.setSplitFactor(UtilsPT.parseDoubleOption(options, "SF", 0.6));
		
		this.setkFolds(UtilsPT.parseIntegerOption(options, "KF", 2));
		
		
		this.setNeighCalc((NeighbourhoodCalculator) UtilsPT.parseObjectOptions(options, "NC", new DummyNeighbourhood(), NeighbourhoodCalculator.class));
		
		this.setKeepOldValidationInstances(Utils.getFlag("KI", options));
		
		this.setDecayFactor(UtilsPT.parseDoubleOption(options, "DF", 1E-3));
		
		this.setUpdateValidationSet(Utils.getFlag("UVS", options));
		
		
		
		
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRCWrapper#getOptions()
	 */
	@Override
	public String[] getOptions() {
		
		Vector<String> options = new Vector<String>();
	    

		if(this.isCrossvalidate())
			options.add("-CV");
	    
	    
	    options.add("-SF");
	    options.add(""+this.getSplitFactor());
	    
	    options.add("-KF");
	    options.add(""+this.getkFolds());
	    
	    options.add("-NC");
	    options.add(UtilsPT.getClassAndOptions(this.getNeighCalc()));
	    
	    if(this.isKeepOldValidationInstances())
	    	options.add("-KI");
	    
	    options.add("-DF");
	    options.add(""+this.getDecayFactor());
	    
	    if(this.isUpdateValidationSet())
	    	options.add("-UVS");
	    
	    
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
		
		
	}
	



	public String neighCalcTipText() {
		return "The object to calculate the neighbourhood coefficients";
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
	
	public String crossvalidateTipText() {
		return "The number of CV folds";
	}

	/**
	 * @return the crossvalidate
	 */
	public boolean isCrossvalidate() {
		return this.crossvalidate;
	}

	/**
	 * @param crossvalidate the crossvalidate to set
	 */
	public void setCrossvalidate(boolean crossvalidate) {
		this.crossvalidate = crossvalidate;
	}
	
	public String splitFactorTipText() {
		return (" If the single split is used the factor determines the percentage of the training set. "+
				"splitfactor \\in [0,1]");
	}
	
	/**
	 * @return the splitFactor
	 */
	public double getSplitFactor() {
		return this.splitFactor;
	}

	/**
	 * @param splitFactor the splitFactor to set
	 */
	public void setSplitFactor(double splitFactor) {
		this.splitFactor = splitFactor;
	}

	public String kFoldsTipText() {
		return "If the crossvalidation approach is used, the factor determines the number of folds";
	}
	/**
	 * @return the kFolds
	 */
	public int getkFolds() {
		return this.kFolds;
	}

	/**
	 * @param kFolds the kFolds to set
	 */
	public void setkFolds(int kFolds) {
		this.kFolds = kFolds;
	}
	
	
	/**
	 * @return the decayFactor
	 */
	public double getDecayFactor() {
		return this.decayFactor;
	}

	/**
	 * @param decayFactor the decayFactor to set
	 */
	public void setDecayFactor(double decayFactor) {
		this.decayFactor = decayFactor;
	}
	
	public String decayFactorTipText() {
		return "Determines the decay factor for instances in validation set";
	}

	/**
	 * @return the keepOldValidationInstances
	 */
	public boolean isKeepOldValidationInstances() {
		return this.keepOldValidationInstances;
	}

	/**
	 * @param keepOldValidationInstances the keepOldValidationInstances to set
	 */
	public void setKeepOldValidationInstances(boolean keepOldValidationInstances) {
		this.keepOldValidationInstances = keepOldValidationInstances;
	}
	
	public String keepOldValidationInstancesTipText() {
		return "Determines whether old instances in the validation set should be kept";
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.RRCWrapper#updateClassifier(weka.core.Instance)
	 */
	@Override
	public void updateClassifier(Instance instance) throws Exception {
		this.updateValidationSet(instance);
		super.updateClassifier(instance);
	}
	
	
	protected void updateValidationSet(Instance instance) throws Exception {
		if(!this.updateValidationSet)
			return;
	
		//Remove the oldest instance from the set
		if(!this.keepOldValidationInstances) {
			//Seems to be alright -- if weights are zeroed, the results were the same
			this.validationSet.remove(0);
			this.validationResponses.remove(0);
		}
		reweightValidationInstances();
		
		//Add new validation instance
		Instance tmpInstance = instance.copy(instance.toDoubleArray());
		this.validationSet.add(tmpInstance);
		this.validationResponses.add(this.rrcCalc.calculateRRC(this.m_Classifier.distributionForInstance(instance)));
	 
	} 
	
	
	
	protected void reweightValidationInstances() {
		int valSetSize = this.validationSet.size();
		Instance tmpInstance;
		double tmpWeight;
		for(int i=0;i<valSetSize;i++) {
			tmpInstance = this.validationSet.get(i);
			tmpWeight = tmpInstance.weight() * (1-this.decayFactor); 
			tmpInstance.setWeight(tmpWeight);
		}
	}

	/**
	 * @return the updateValidationSet
	 */
	public boolean isUpdateValidationSet() {
		return this.updateValidationSet;
	}

	/**
	 * @param updateValidationSet the updateValidationSet to set
	 */
	public void setUpdateValidationSet(boolean updateValidationSet) {
		this.updateValidationSet = updateValidationSet;
	}
	
	public String updateValidationSetTipText() {
		return "Determines whether the validation set should be updated.";
	}
	

}
