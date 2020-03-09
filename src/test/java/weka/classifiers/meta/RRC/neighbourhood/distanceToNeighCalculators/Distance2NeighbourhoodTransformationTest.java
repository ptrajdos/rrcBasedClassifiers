/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood.distanceToNeighCalculators;

import junit.framework.TestCase;

/**
 * @author pawel trajdos
 *
 */
public abstract class Distance2NeighbourhoodTransformationTest extends TestCase {

	public abstract Distance2NeighbourhoodTransformation getTransformation();
	
	public void testNeighbourhoodSingle() {
		Distance2NeighbourhoodTransformation trans = this.getTransformation();
		double[] vals = {0,1,100,10000};
		for(int i=0;i<vals.length;i++) {
			double val = vals[i];
			double transVal = trans.getNeighbourhood(val);
			assertTrue("Check Neighbourhood from distance", Double.isFinite(transVal));
		}
		
	}
	
	public void testNeighbourhoodMultiple() {
		Distance2NeighbourhoodTransformation trans = this.getTransformation();
		double[] vals = {1,3,5};
		double[] transVals = trans.getNeighbourhood(vals);
		assertTrue("Not null trans", transVals !=null);
		assertTrue("Length of transformed", vals.length == transVals.length);
		assertTrue("Non increasing", transVals[0] >= transVals[2]);
	}

}
