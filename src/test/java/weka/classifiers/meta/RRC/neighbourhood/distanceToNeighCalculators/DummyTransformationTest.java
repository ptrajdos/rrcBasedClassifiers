package weka.classifiers.meta.RRC.neighbourhood.distanceToNeighCalculators;

public class DummyTransformationTest extends Distance2NeighbourhoodTransformationTest {

	@Override
	public Distance2NeighbourhoodTransformation getTransformation() {
		return new DummyTransformation();
	}


}
