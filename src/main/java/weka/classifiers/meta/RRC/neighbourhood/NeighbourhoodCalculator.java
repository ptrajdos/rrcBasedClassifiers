/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import weka.core.Instance;
import weka.core.Instances;

/**
 * The interface for calculating neighbourhood of the given instance.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public interface NeighbourhoodCalculator {
	
	/**
	 * Returns neighbourhood coefficients for the instance.
	 * Each instance from dataset is assigned the neighbourhood coefficient.
	 * @param dataset -- neighbouring points
	 * @param instance -- the center of the neighbourhood
	 * @return an array of the neighbourhood coefficients in [0,1]
	 * @throws Exception -- an exception
	 */
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance)throws Exception;

}
