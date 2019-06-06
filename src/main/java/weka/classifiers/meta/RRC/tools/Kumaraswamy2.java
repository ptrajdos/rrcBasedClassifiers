/**
 * 
 */
package weka.classifiers.meta.RRC.tools;

import net.sourceforge.jdistlib.Kumaraswamy;

/**
 * The class implements Kumaraswamy distribution.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class Kumaraswamy2 extends Kumaraswamy {

	/**
	 * @param a
	 * @param b
	 */
	public Kumaraswamy2(double a, double b) {
		super(a, b);
	}
	
	@Override
	public double cumulative(double p, boolean lower_tail, boolean log_p) {
		return cumulative( p, a, b, lower_tail, log_p);
	}


	

}
