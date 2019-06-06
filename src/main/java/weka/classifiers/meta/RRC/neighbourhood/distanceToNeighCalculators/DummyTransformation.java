/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood.distanceToNeighCalculators;

import java.io.Serializable;
import java.util.Arrays;

/**
 * The class implements dummy potential function. 
 * For each value of the distance the potential is zero.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class DummyTransformation implements Distance2NeighbourhoodTransformation, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7666295351552225438L;

	/**
	 * 
	 */
	public DummyTransformation() {
		
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.distanceToNeighCalculators.Distance2NeighbourhoodTransformation#getNeighbourhood(double)
	 */
	@Override
	public double getNeighbourhood(double distanceVal) {
		return 1;
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.distanceToNeighCalculators.Distance2NeighbourhoodTransformation#getNeighbourhood(double[])
	 */
	@Override
	public double[] getNeighbourhood(double[] distanceVals) {
		int numVals = distanceVals.length;
		double[] neighs = new double[numVals];
		Arrays.fill(neighs, 1.0);
		return neighs;
	}

}
