package weka.classifiers.meta.RRC.neighbourhood;

public class NearestNeighbourNeighbourhoodDynamicTest extends NeighbourhoodCalculatorTest {


	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new NearestNeighbourNeighbourhoodDynamic();
	}

}
