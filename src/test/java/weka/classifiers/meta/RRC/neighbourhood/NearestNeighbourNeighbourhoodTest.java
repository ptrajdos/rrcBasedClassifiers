package weka.classifiers.meta.RRC.neighbourhood;

public class NearestNeighbourNeighbourhoodTest extends DistanceBasedNeighbourhoodTest {

	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new NearestNeighbourNeighbourhood();
	}


}

