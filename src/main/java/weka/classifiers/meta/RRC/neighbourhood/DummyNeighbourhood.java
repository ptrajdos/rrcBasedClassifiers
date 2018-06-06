/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.io.Serializable;
import java.util.Arrays;

import weka.core.Instance;
import weka.core.Instances;

/**
 * @author Pawel Trajdos
 * 
 * The class implements dummy neighbourhood -- all points get membership degree equal to 1.
 *
 */
public class DummyNeighbourhood implements NeighbourhoodCalculator, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 989293575363989263L;

	/**
	 * 
	 */
	public DummyNeighbourhood() {
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator#getNeighbourhoodCoeffs(weka.core.Instances, weka.core.Instance)
	 */
	@Override
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
		int nInst = dataset.numInstances();
		double[] neigh = new double[nInst];
		Arrays.fill(neigh, 1.0);
		return neigh;
	}

}
