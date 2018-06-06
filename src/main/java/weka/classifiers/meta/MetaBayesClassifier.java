/**
 * 
 */
package weka.classifiers.meta;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.tools.GlobalInfoHandler;

/**
 * @author pawel
 *
 */
public class MetaBayesClassifier extends RRCBasedWithValidation implements GlobalInfoHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8867299051220506468L;

	/**
	 * 
	 */
	public MetaBayesClassifier() {
	}

	/**
	 * @param baseClassifier
	 */
	public MetaBayesClassifier(Classifier baseClassifier) {
		super(baseClassifier);
	}
	
	

	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifier#distributionForInstance(weka.core.Instance)
	 */
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		// TODO Auto-generated method stub
		return this.m_Classifier.distributionForInstance(instance);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		runClassifier(new MetaBayesClassifier(), args);

	}

	@Override
	public String globalInfo() {
		
		return "The class Implements Meta Bayes Classifier";
	}

}
