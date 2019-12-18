package weka.classifiers.meta.RRC.neighbourhood;

import static org.junit.Assert.*;

import org.junit.Test;

import weka.classifiers.meta.RRCEns.calculators.RRCCalcEnsEstimator;
import weka.core.OptionHandler;
import weka.core.OptionHandlersTest;
import weka.core.OptionHandlersTest.OptionHandlerTest;

public class InstanceWeightedNeighbourhoodTest extends OptionHandlerTest {

	public InstanceWeightedNeighbourhoodTest(String name, String classname) {
		super(name, classname);
	}
	
	public InstanceWeightedNeighbourhoodTest(String name) {
		this(name, InstanceWeightedNeighbourhood.class.getCanonicalName());
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandlersTest.OptionHandlerTest#getOptionHandler()
	 */
	@Override
	protected OptionHandler getOptionHandler() {
		return new InstanceWeightedNeighbourhood();
	}
	

	

	

}
