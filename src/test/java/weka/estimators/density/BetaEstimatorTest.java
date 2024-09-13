package weka.estimators.density;

import weka.core.Utils;
import weka.estimators.density.BetaEstimator;

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
	
	public void testEps() {
		BetaEstimator tEst = (BetaEstimator) this.getEstimator();
		assertTrue("Get Default Eps", Utils.eq(tEst.getEps(), 1E-6));
		double epsV = 0.1;
		tEst.setEps(epsV);
		assertTrue("Get Changed Eps", Utils.eq(tEst.getEps(), epsV));
		
	}

	//TODO fails when there is low variance
	
	

}
