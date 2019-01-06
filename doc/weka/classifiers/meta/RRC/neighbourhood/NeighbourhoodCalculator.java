/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import weka.core.Instances;
import weka.core.Instance;

/**
 * <p>NeighbourhoodCalculator interface.</p>
 *
 * @author pawel
 */
public interface NeighbourhoodCalculator {
	
	/**
	 * Returns neighbourhood coefficients for the instance.
	 * Each instance from dataset is assigned the neighbourhood coefficient.
	 * @param dataset -- neighbouring points
	 * @param instance -- the center of the neighbourhood
	 * @return an array of the neighbourhood coefficients in [0,1]
	 * @throws Exception
	 */
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance)throws Exception;

}
