package weka.estimators.density;

import weka.core.Utils;
import weka.estimators.density.TruncatedNormalEstimator;

public class TruncatedNormalEstimatorTest extends DensEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new TruncatedNormalEstimator();
	}
	
	public void testEps() {
		TruncatedNormalEstimator tEst = (TruncatedNormalEstimator) this.getEstimator();
		assertTrue("Get Default Eps", Utils.eq(tEst.getEps(), 1E-3));
		double epsV = 0.1;
		tEst.setEps(epsV);
		assertTrue("Get Changed Eps", Utils.eq(tEst.getEps(), epsV));
		
	}

	
	//TODO problems with low variance
}
