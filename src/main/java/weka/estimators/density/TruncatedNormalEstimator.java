/**
 * 
 */
package weka.estimators.density;

import weka.classifiers.meta.RRC.calculators.TruncatedNormal;
import weka.core.Utils;
import weka.core.UtilsPT;

/**
 * Estimates the Truncated Normal distribution.
 * The distribution is truncated to the interval [0;1]
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class TruncatedNormalEstimator extends AEstimator {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1724395133482724624L;
	
	protected TruncatedNormal tNormalDistr;
	
	protected double eps =1e-6;

	/**
	 * @return the eps
	 */
	public double getEps() {
		return this.eps;
	}


	/**
	 * @param eps the eps to set
	 */
	public void setEps(double eps) {
		this.eps = eps;
	}


	/**
	 * 
	 */
	public TruncatedNormalEstimator() {
		super();
	}

	
	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getPDF(double)
	 */
	@Override
	public double getPDF(double x) {
		this.calculateDistribution();
		return this.tNormalDistr.density(x, false);
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getCDF(double)
	 */
	@Override
	public double getCDF(double x) {
		this.calculateDistribution();
		return this.tNormalDistr.cumulative(x);
	}
	
	protected void calculateDistribution() {
		if(this.isInitialised == true)return;
		double[] values = this.listToArray();
		double mean = Utils.mean(values);
		double sdev = UtilsPT.stdDev(values) + this.eps;
		this.tNormalDistr = new TruncatedNormal(mean, sdev, 0.0, 1.0);
		
		this.isInitialised=true;
		
	}


}
