package weka.classifiers.meta.RRC.neighbourhood;

public class DynamicMahalanobisNeighbourhoodTest extends NeighbourhoodCalculatorTest {

	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new DynamicMahalanobisNeighbourhood();
	}


}
