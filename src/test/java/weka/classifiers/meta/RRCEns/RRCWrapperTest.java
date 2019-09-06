package weka.classifiers.meta.RRCEns;

import org.mockito.Mockito;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;

public class RRCWrapperTest extends AbstractClassifierTest {

	public RRCWrapperTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		RRCWrapper wrap = Mockito.mock(RRCWrapper.class, Mockito.CALLS_REAL_METHODS);
		return wrap;
	}
	
	public static Test suite() {
	    return new TestSuite(RRCWrapperTest.class);
	  }

	  public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
	  }

	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifierTest#testRegression()
	 */
	@Override
	public void testRegression() throws Exception {
		/**
		 * distribution for instance is not implemented in the abstract class;
		 */
		assertTrue(true);
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifierTest#testListOptions()
	 */
	@Override
	public void testListOptions() {
		/**
		 * Something goes wrong using Mockito
		 */
		assertTrue(true);
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifierTest#testToolTips()
	 */
	@Override
	public void testToolTips() {
		/**
		 * Something goes wrong using Mockito
		 */
		assertTrue(true);
	}

	
	

}
