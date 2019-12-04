package weka.estimators.density;

public class BetaEstimatorTest extends DensEstimatorTest {


	@Override
	protected DensityEstimator getEstimator() {
		return new BetaEstimator();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensEstimatorTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.integrationEps=1E-6;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensEstimatorTest#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.integrationEps=1E-6;
	}
	
	

	//TODO fails when there is low variance
	
	

}
