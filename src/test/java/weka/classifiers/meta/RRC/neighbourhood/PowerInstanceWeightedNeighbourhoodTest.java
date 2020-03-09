package weka.classifiers.meta.RRC.neighbourhood;

public class PowerInstanceWeightedNeighbourhoodTest extends InstanceWeightedNeighbourhoodTest {

	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new PowerInstanceWeightedNeighbourhood();
	}

}

