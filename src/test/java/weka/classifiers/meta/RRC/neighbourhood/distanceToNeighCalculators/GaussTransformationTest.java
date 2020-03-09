package weka.classifiers.meta.RRC.neighbourhood.distanceToNeighCalculators;

import weka.core.Utils;

public class GaussTransformationTest extends Distance2NeighbourhoodTransformationTest {

	@Override
	public Distance2NeighbourhoodTransformation getTransformation() {
		return new GaussTransformation();
	}
	
	public void testAlpha() {
		GaussTransformation trans = (GaussTransformation) this.getTransformation();
		double alpha =1;
		trans.setAlpha(alpha);
		assertTrue("Alpha", Utils.eq(alpha, trans.getAlpha()));
	}

	

}
