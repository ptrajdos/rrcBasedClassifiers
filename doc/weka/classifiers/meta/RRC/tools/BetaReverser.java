/**
 * 
 */
package weka.classifiers.meta.RRC.tools;

/**
 * <p>BetaReverser class.</p>
 *
 * @author pawel
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
