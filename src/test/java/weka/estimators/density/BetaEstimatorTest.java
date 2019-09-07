package weka.estimators.density;

public class BetaEstimatorTest extends DensEstimatorTest {


	@Override
	protected DensityEstimator getEstimator() {
		return new BetaEstimator();
	}

}
