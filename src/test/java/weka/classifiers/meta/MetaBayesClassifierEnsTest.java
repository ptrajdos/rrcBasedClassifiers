package weka.classifiers.meta;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.Classifier;
import weka.classifiers.meta.RRCEns.RRCBasedWithValidationTest;

public class MetaBayesClassifierEnsTest  extends RRCBasedWithValidationTest{

	public MetaBayesClassifierEnsTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		return new MetaBayesClassifierEns();
	}
	
	public static Test suite() {
	    return new TestSuite(MetaBayesClassifierEnsTest.class);
	  }

	  public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
	  }

	
}
