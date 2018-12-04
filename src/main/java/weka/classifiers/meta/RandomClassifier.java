/**
 * 
 */
package weka.classifiers.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.util.Pair;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.RandomizableSingleClassifierEnhancer;
import weka.classifiers.trees.J48;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author pawel
 *
 */
public class RandomClassifier extends RandomizableSingleClassifierEnhancer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8015924482789305122L;
	
	private Random random = new Random(this.getSeed());
	
	

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
		this.m_Classifier.buildClassifier(data);

	}


	/**
	 * @return the baseClassifier
	 */
	
	

	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifier#distributionForInstance(weka.core.Instance)
	 */
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		double[] baseResponse = this.m_Classifier.distributionForInstance(instance);
		int numClasses = baseResponse.length;
		double[] finalResponse = new double[numClasses];
		
		
		List<Pair<Integer, Double>> probClassList  =new ArrayList<Pair<Integer, Double>>(numClasses);
		Pair<Integer, Double> tmp;
		for(int i=0;i<numClasses;i++) {
			tmp = new Pair<Integer, Double>(i, baseResponse[i]);
			probClassList.add(tmp);
		}
		
		Collections.sort(probClassList, new Comparator<Pair<Integer, Double>>() {

			@Override
			public int compare(Pair<Integer, Double> o1, Pair<Integer, Double> o2) {
				if( o2.getValue() > o1.getValue())
					return 1;
				if( o2.getValue() < o1.getValue())
					return -1;
				return 0;
			}
		});
		//Roulette 
		double rndVal = this.random.nextDouble();
		double cumSum =0;
		int winIndex =0;
		for (Pair<Integer, Double> pair : probClassList) {
			cumSum += pair.getValue();
			if(cumSum > rndVal) {
				winIndex = pair.getKey();
				break;
			}
		}
		finalResponse[winIndex]=1;
			
		
		
		return finalResponse;
	}

	public String seedTipText() {
		return "The seed for the random generator";
	}
	
	public String globalInfo(){
		return "Performs the randomized classification according to the soft outputs returned by the base classifier";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		runClassifier(new RandomClassifier(), args);

	}
	
	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifier#getCapabilities()
	 */
	@Override
	public Capabilities getCapabilities() {
		Capabilities capabilities =this.m_Classifier.getCapabilities();
		capabilities.disable(Capability.NUMERIC_CLASS);
		return  capabilities;
		 
	}

}
