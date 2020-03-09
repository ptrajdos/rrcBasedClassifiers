package weka.classifiers.meta.RRC.neighbourhood;

public class InstanceWeightedNeighbourhoodTest extends NeighbourhoodCalculatorTest {

	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new InstanceWeightedNeighbourhood();
	}



}
