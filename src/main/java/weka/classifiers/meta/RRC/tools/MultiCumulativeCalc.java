/**
 * 
 */
package weka.classifiers.meta.RRC.tools;

/**
 * Interface for objects that calculates the values of the CDF function.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public interface MultiCumulativeCalc {
	
	/**
	 * Returns cumulative values for given values
	 * @param x -- values
	 * @return
	 */
	public double[] cumulative(double[] x);
	
	public double cumulative(double x);

}
