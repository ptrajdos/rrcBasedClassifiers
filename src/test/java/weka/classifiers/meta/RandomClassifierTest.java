package weka.classifiers.meta;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;

public class RandomClassifierTest extends AbstractClassifierTest{

	public RandomClassifierTest(String name) {
		super(name);
		
	}


	@Override
	public Classifier getClassifier() {
		return new RandomClassifier();
	}
	
	public static Test suite() {
	    return new TestSuite(RandomClassifierTest.class);
	  }

	  public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
	  }

}
