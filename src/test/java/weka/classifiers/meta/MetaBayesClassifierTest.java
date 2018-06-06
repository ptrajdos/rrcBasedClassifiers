package weka.classifiers.meta;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;

public class MetaBayesClassifierTest extends AbstractClassifierTest{

	 public MetaBayesClassifierTest(String name) {
			super(name);
		}

		/** Creates a default Classifier */
		  public Classifier getClassifier() {
		    return new MetaBayesClassifier();
		  }

		  public static Test suite() {
		    return new TestSuite(MetaBayesClassifierTest.class);
		  }

		  public static void main(String[] args){
		    junit.textui.TestRunner.run(suite());
		  }

}
