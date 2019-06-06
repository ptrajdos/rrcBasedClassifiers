package weka.classifiers.meta.RRC.neighbourhood;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.OptionHandlersTest;

public class DistanceBasedNeighbourhoodTest extends OptionHandlersTest {

	public DistanceBasedNeighbourhoodTest(String name) {
		super(name);
	}

	protected OptionHandler getOptionHandler() {
		return new DistanceBasedNeighbourhood() {
			
			@Override
			public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	  public static Test suite() {
	    return new TestSuite(DistanceBasedNeighbourhoodTest.class);
	  }

	  public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
	  }

}
