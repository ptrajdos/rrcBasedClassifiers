/**
 * 
 */
package weka.classifiers.meta.RRC.tools;

import net.sourceforge.jdistlib.Kumaraswamy;

/**
 * <p>Kumaraswamy2 class.</p>
 *
 * @author pawel
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
