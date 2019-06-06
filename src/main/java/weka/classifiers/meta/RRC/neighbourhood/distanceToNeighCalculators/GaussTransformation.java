/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood.distanceToNeighCalculators;

import java.io.Serializable;

/**
 * The class implments transformation from distance value to Gauusian Potential function around distance equal to zero. 
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class GaussTransformation implements Distance2NeighbourhoodTransformation, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3882714695630091408L;
	
	protected double alpha =1.0;


	@Override
	public double getNeighbourhood(double distanceVal) {
		double value = Math.exp(-this.alpha*distanceVal*distanceVal);
		return value;
	}

	@Override
	public double[] getNeighbourhood(double[] distanceVals) {
		double[] neighCoeffs = new double[distanceVals.length];
		for(int i=0;i<neighCoeffs.length;i++) {
			neighCoeffs[i] = Math.exp(-this.alpha*distanceVals[i]*distanceVals[i]);
		}
		
		return neighCoeffs;
	}

	/**
	 * @return the alpha
	 */
	public double getAlpha() {
		return this.alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	

}
