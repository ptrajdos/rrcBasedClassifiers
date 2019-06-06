/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood.distanceToNeighCalculators;

/**
 * The interface for converting distance values into some kind of potential function.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
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
