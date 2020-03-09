/**
 * 
 */
package weka.classifiers.meta;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;

/**
 * @author pawel
 *
 */
public class MetaBayesClassifier2Test extends MetaBayesClassifierTest {

	/**
	 * @param name
	 */
	public MetaBayesClassifier2Test(String name) {
		super(name);
	}
	
	@Override
	public Classifier getClassifier() {
		return new MetaBayesClassifier(new NaiveBayes());
	}
	
	public static Test suite() {
	    return new TestSuite(MetaBayesClassifier2Test.class);
	  }

	  public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
	  }


}
