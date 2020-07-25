package weka.classifiers.meta.RRC.neighbourhood;

public class NNTunedGaussianNeighbourhoodTest extends NeighbourhoodCalculatorTest {


	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new NNTunedGaussianNeighbourhood();
	}

}
