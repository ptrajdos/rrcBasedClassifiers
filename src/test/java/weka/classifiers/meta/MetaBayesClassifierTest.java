package weka.classifiers.meta;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.Classifier;
import weka.classifiers.meta.RRC.RRCBasedWithValidationTest;


public class MetaBayesClassifierTest extends RRCBasedWithValidationTest{

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
