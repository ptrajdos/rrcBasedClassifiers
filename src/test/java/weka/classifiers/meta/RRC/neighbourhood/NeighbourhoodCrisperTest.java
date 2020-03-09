package weka.classifiers.meta.RRC.neighbourhood;

public class NeighbourhoodCrisperTest extends ThresholdNeighbourhoodModifierTest {

	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new NeighbourhoodCrisper();
	}

	

}
