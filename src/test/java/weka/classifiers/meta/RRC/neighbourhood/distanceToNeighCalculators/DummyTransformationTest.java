package weka.classifiers.meta.RRC.neighbourhood.distanceToNeighCalculators;

import static org.junit.Assert.*;

import org.junit.Test;

public class DummyTransformationTest extends Distance2NeighbourhoodTransformationTest {

	@Override
	public Distance2NeighbourhoodTransformation getTransformation() {
		return new DummyTransformation();
	}


}
