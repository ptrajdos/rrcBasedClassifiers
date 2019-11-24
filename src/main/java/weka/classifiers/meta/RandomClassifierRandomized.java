/**
 * 
 */
package weka.classifiers.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math3.util.Pair;

import weka.core.Instance;

/**
 * @author pawel
 *
 */
public class RandomClassifierRandomized extends RandomClassifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = 861225269072905102L;

	/**
	 * 
	 */
	public RandomClassifierRandomized() {
		super();
	}
	
	
	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifier#distributionForInstance(weka.core.Instance)
	 */
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		
		double[] baseResponse = this.m_Classifier.distributionForInstance(instance);
		if(!this.randomizeResponse)
			return baseResponse;
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

}
