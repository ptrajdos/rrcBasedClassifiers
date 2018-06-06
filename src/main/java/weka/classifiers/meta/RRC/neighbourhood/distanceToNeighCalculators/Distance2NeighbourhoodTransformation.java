/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood.distanceToNeighCalculators;

/**
 * @author Pawel Trajdos
 *
 */
public interface Distance2NeighbourhoodTransformation {
	
	/**
	 * Calculates the neighbourhood coefficnent for the distance value.
	 * @param distanceVal
	 * @return
	 */
	public double getNeighbourhood(double distanceVal);
	
	/**
	 * Calculates the neighbourhood coefficnents for the distance values.
	 * @param distanceVals
	 * @return
	 */
	public double[] getNeighbourhood(double[] distanceVals);

}
