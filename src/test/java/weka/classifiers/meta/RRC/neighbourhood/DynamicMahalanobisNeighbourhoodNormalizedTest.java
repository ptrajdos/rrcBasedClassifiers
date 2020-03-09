package weka.classifiers.meta.RRC.neighbourhood;

public class DynamicMahalanobisNeighbourhoodNormalizedTest extends DynamicMahalanobisNeighbourhoodTest {

	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		DynamicMahalanobisNeighbourhood nei = new DynamicMahalanobisNeighbourhood();
		nei.setNormalize(true);
		return nei;
	}

	

}
