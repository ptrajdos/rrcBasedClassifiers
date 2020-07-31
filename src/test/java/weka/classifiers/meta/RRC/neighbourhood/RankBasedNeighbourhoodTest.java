package weka.classifiers.meta.RRC.neighbourhood;

public class RankBasedNeighbourhoodTest extends NeighbourhoodCalculatorTest {

	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new RankBasedNeighbourhood();
	}

	
}
