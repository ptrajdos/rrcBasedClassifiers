/**
 * 
 */
package weka.classifiers.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.math3.util.Pair;

import weka.classifiers.RandomizableSingleClassifierEnhancer;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;

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
	
	private boolean randomizeResponse=false;
	
	

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
		Capabilities capabilities = this.m_Classifier.getCapabilities();
		capabilities.disable(Capability.NUMERIC_CLASS);
		capabilities.setOwner(this);
		return  capabilities;
		 
	}
	
	public boolean isRandomizedResponse() {
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
	    options.add(""+(this.isRandomizedResponse()?1:0));
	    
	    
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
	}
	

}
