/**
 * 
 */
package weka.classifiers.meta.RRC.tools;

/**
 * The class implements reversed Beta distribution CDF. 
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class BetaReverser {

	/**
	 * 
	 */
	
	public static double[] reverseBetaCdf(double[] cdfs){
		double[] reversed = new double[cdfs.length];
		
		for(int i=0;i<reversed.length;i++){
			reversed[i] = 1 - cdfs[cdfs.length -1 -i];
		}
		return reversed;
		
	}

}
