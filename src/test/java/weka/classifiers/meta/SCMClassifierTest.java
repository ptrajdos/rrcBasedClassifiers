package weka.classifiers.meta;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;

public class SCMClassifierTest extends AbstractClassifierTest{

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
