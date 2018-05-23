/**
 * 
 */
package weka.classifiers.meta.RRC.tools;

/**
 * @author pawel
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
