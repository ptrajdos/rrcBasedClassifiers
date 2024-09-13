package weka.classifiers.meta;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.Classifier;
import weka.classifiers.meta.RRC.RRCBasedWithValidationTest;
import weka.classifiers.meta.SCMClassifier;

public class SCMClassifierTest extends RRCBasedWithValidationTest {

	public SCMClassifierTest(String name) {
		super(name);
	}


	@Override
	public Classifier getClassifier() {
		return new SCMClassifier();
	}
	
	public static Test suite() {
	    return new TestSuite(SCMClassifierTest.class);
	  }

	  public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
	  }

}
