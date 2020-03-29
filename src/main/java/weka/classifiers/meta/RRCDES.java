/**
 * 
 */
package weka.classifiers.meta;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.classifiers.meta.RRC.neighbourhood.DummyNeighbourhood;
import weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator;
import weka.classifiers.meta.generalOutputCombiners.MeanCombiner;
import weka.classifiers.meta.generalOutputCombiners.OutputCombiner;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;
import weka.core.UtilsPT;
import weka.core.Capabilities.Capability;

/**
 * DES system using RRC classifier
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class RRCDES extends MultipleClassifiersCombinerWithValidationSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 71081687600572612L;
	
	protected OutputCombiner outCombiner;
	protected NeighbourhoodCalculator neighCalc;
	
	protected double[][] correctClassificationProbs;

	/**
	 * 
	 */
	public RRCDES() {
		this.outCombiner = new MeanCombiner();
		this.neighCalc = new DummyNeighbourhood();
		
	}
	
	@Override
	public void buildClassifier(Instances data) throws Exception {
		super.buildClassifier(data);
		
		int numInstances = this.validationSet.numInstances();
		int numClassifiers = this.m_Classifiers.length;
		this.correctClassificationProbs = new double[numInstances][numClassifiers];
		
		
		for(int i=0;i<numInstances;i++) {
			Instance tmpInstance = this.validationSet.get(i);
			for(int c =0;c<numClassifiers;c++) {
				double prob = this.validationResponses[c].get(i)[(int) tmpInstance.classValue()];
				this.correctClassificationProbs[i][c] = prob;
			}
		}
		
	}
	
	
	

	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		
		double[] combinedProbs = new double[this.m_Classifiers.length];
		double[] neighCoeffsSum = new double[this.m_Classifiers.length];
		
		double[] neighCoeffs = this.neighCalc.getNeighbourhoodCoeffs(this.validationSet, instance);
		
		int numInstances = this.validationSet.numInstances();
		
		for(int c=0;c<this.m_Classifiers.length;c++) {
			for(int i=0;i<numInstances;i++) {
				combinedProbs[c]+= neighCoeffs[i]*this.correctClassificationProbs[i][c];
				neighCoeffsSum[c] += neighCoeffs[i];
			}
			if(Utils.gr(neighCoeffsSum[c], 0))
				combinedProbs[c]/=neighCoeffsSum[c];
		}
		
		double[] weights = this.probsThreshold(combinedProbs);
		return this.outCombiner.getCombinedDistributionForInstance(this.m_Classifiers, instance,weights);
	}
	
	protected double[] probsThreshold(double[] probs) {
		double thresh =1.0/this.validationSet.numClasses();
		double[] resultProbs = new double[probs.length];
		boolean oneAbove=false;
		for(int i=0;i<probs.length;i++) {
			if(probs[i]<thresh)
				resultProbs[i]=0;
			else {//TODO change it to make it NaN proof
				resultProbs[i] = probs[i];
				oneAbove = true;
			}
		}
		
		if(!oneAbove) {
			int maxIdx = Utils.maxIndex(probs);
			if(!Utils.eq(probs[maxIdx], 0))
				resultProbs[maxIdx]=1.0;
			return resultProbs;
		}
		
		Utils.normalize(resultProbs);
		
		return resultProbs;
	}

	/**
	 * @return the outCombiner
	 */
	public OutputCombiner getOutCombiner() {
		return this.outCombiner;
	}

	/**
	 * @param outCombiner the outCombiner to set
	 */
	public void setOutCombiner(OutputCombiner outCombiner) {
		this.outCombiner = outCombiner;
	}
	
	public static String outCombinerTipText() {
		return "Output combiner that is used to combine multiple classifiers";
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
	
	public String neighCalcTipText() {
		return "Neighbourhood calculator to be used";
	}

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tOutput Combiner to use"+
		          "(default:" +  MeanCombiner.class.getCanonicalName()  + ").\n",
			      "OC", 1, "-OC"));
		
		newVector.addElement(new Option(
			      "\tNeighbourhood Calculator to use"+
		          "(default:" +  DummyNeighbourhood.class.getCanonicalName()  + ").\n",
			      "NC", 1, "-NC"));
		
		
		 newVector.addAll(Collections.list(super.listOptions()));   
		 return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		super.setOptions(options);
		
		this.setOutCombiner((OutputCombiner) UtilsPT.parseObjectOptions(options, "OC", new MeanCombiner(), OutputCombiner.class));
		this.setNeighCalc((NeighbourhoodCalculator) UtilsPT.parseObjectOptions(options, "NC", new DummyNeighbourhood(), NeighbourhoodCalculator.class));
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-OC");
		options.add(UtilsPT.getClassAndOptions(this.getOutCombiner()));
		
		options.add("-NC");
		options.add(UtilsPT.getClassAndOptions(this.getNeighCalc()));
		
		
		Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
	}

	@Override
	public String globalInfo() {
		return "DES classifier built using RRC approach";
	}

	@Override
	public Capabilities getCapabilities() {
		Capabilities baseCaps = super.getCapabilities();
		baseCaps.disable(Capability.MISSING_VALUES);
		return baseCaps; 
	}

}

