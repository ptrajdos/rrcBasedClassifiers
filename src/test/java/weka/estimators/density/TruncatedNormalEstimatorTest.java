package weka.estimators.density;

public class TruncatedNormalEstimatorTest extends DensEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new TruncatedNormalEstimator();
	}

	

}
