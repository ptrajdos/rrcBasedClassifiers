package weka.classifiers.meta;



import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.Classifier;
import weka.classifiers.meta.RRCEns.RRCBasedWithValidationTest;
import weka.classifiers.meta.SCMClassifierEns;

public class SCMClassifierEnsTest extends RRCBasedWithValidationTest {

	public SCMClassifierEnsTest(String name) {
		super(name);
	}


	@Override
	public Classifier getClassifier() {
		return new SCMClassifierEns();
	}
	
	public static Test suite() {
	    return new TestSuite(SCMClassifierEnsTest.class);
	  }

	  public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
	  }
	  
	  

}
