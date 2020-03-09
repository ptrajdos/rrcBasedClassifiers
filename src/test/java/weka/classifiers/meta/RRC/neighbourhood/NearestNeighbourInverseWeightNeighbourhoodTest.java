package weka.classifiers.meta.RRC.neighbourhood;

public class NearestNeighbourInverseWeightNeighbourhoodTest extends NearestNeighbourNeighbourhoodTest {

	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new NearestNeighbourInverseWeightNeighbourhood();
	}

}
