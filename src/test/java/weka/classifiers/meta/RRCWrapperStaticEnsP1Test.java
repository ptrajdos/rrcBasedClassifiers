package weka.classifiers.meta;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.Classifier;
import weka.classifiers.meta.RRCEns.RRCWrapperTest;


public class RRCWrapperStaticEnsP1Test extends RRCWrapperTest {

	public RRCWrapperStaticEnsP1Test(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		return new RRCWrapperStaticEnsP1();
	}
	
	public static Test suite() {
	    return new TestSuite(RRCWrapperStaticEnsP1Test.class);
	  }

	  public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
	  }


	

}
