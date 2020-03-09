package weka.classifiers.meta.RRC.neighbourhood;

public class NeighbourhoodTrimmerTest extends ThresholdNeighbourhoodModifierTest {

	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new NeighbourhoodTrimmer();
	}

	

}
