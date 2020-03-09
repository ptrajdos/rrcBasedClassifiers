package weka.classifiers.meta.RRC.neighbourhood;

public class GaussianNeighbourhoodTest extends NeighbourhoodCalculatorTest {

	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new GaussianNeighbourhood();
	}

	

}
