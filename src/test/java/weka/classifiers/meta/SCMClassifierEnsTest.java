package weka.classifiers.meta;



import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;

public class SCMClassifierEnsTest extends AbstractClassifierTest {

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
