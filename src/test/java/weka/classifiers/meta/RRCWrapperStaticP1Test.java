package weka.classifiers.meta;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;
import weka.classifiers.meta.RRC.RRCWrapperTest;

public class RRCWrapperStaticP1Test extends RRCWrapperTest {

	 public RRCWrapperStaticP1Test(String name) {
		super(name);
	}

	/** Creates a default Classifier */
	  public Classifier getClassifier() {
	    return new RRCWrapperStaticP1();
	  }

	  public static Test suite() {
	    return new TestSuite(RRCWrapperStaticP1Test.class);
	  }

	  public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
	  }

}
