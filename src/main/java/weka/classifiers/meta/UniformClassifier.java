/**
 * 
 */
package weka.classifiers.meta;

import java.util.Arrays;

import weka.classifiers.SingleClassifierEnhancer;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author pawel
 *
 */
public class UniformClassifier extends SingleClassifierEnhancer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7369536425985799360L;
	
	protected int numClasses=0;

	/**
	 * 
	 */
	public UniformClassifier() {
		super();
	}
	
	

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RandomClassifier#buildClassifier(weka.core.Instances)
	 */
	@Override
	public void buildClassifier(Instances data) throws Exception {
		this.m_Classifier.buildClassifier(data);
		this.numClasses = data.numClasses();
	}



	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RandomClassifier#distributionForInstance(weka.core.Instance)
	 */
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		double randomresp = 1.0/this.numClasses;
		double[] response = new double[numClasses];
		Arrays.fill(response, randomresp);
		return response;	
	}
	
	
	public String globalInfo() {
		return new String("Classifier that responses with uniform distribution 1/number_of_classes");
	}
}
