/**
 * 
 */
package weka.classifiers.meta;

import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;

/**
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class RandomClassifierFull extends RandomClassifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7369536425985799360L;
	
	protected int numClasses=0;

	/**
	 * 
	 */
	public RandomClassifierFull() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RandomClassifier#buildClassifier(weka.core.Instances)
	 */
	@Override
	public void buildClassifier(Instances data) throws Exception {
		super.buildClassifier(data);
		this.numClasses = data.numClasses();
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifier#distributionForInstance(weka.core.Instance)
	 */
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		
		if(!isRandomizeResponse()) 
			return this.m_Classifier.distributionForInstance(instance);
		
		int selectedClass = this.random.nextInt(this.numClasses);
		double[] response = new double[this.numClasses];
		response[selectedClass]=1.0;
		return response;
	}
	
	

}
