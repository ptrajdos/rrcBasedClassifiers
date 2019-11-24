package weka.classifiers.meta.RRCEns.calculators;

import weka.core.OptionHandler;
import weka.core.OptionHandlersTest.OptionHandlerTest;

public class RRCCalcEnsEstimatorOptTest extends OptionHandlerTest {

	public RRCCalcEnsEstimatorOptTest(String name, String classname) {
		super(name, classname);
		// TODO Auto-generated constructor stub
	}

	public RRCCalcEnsEstimatorOptTest(String name) {
		this(name, RRCCalcEnsEstimator.class.getCanonicalName());
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandlersTest.OptionHandlerTest#getOptionHandler()
	 */
	@Override
	protected OptionHandler getOptionHandler() {
		return new RRCCalcEnsEstimator();
	}
	
	
	
}
