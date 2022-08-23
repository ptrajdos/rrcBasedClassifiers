/**
 * 
 */
package weka.estimators.density;

import net.sourceforge.jdistlib.Beta;
import weka.core.Utils;
import weka.core.UtilsPT;

/**
 * Class that estimates Beta distribution PDF and CDF using moments method.
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */

//TODO Test failures. Problems with low wariance parameter estimation.
//TODO Problem with Inf values at the end of the [0,1] interval
public class BetaEstimator extends AEstimator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1167079678523837148L;
	
	
	protected Beta betaDist;
	
	protected double eps=1e-6;
	

	/**
	 * 
	 */
	public BetaEstimator() {
		super();
	}


	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getPDF(double)
	 */
	@Override
	public double getPDF(double x) {
		this.calculateParameters();
		double origDens =this.betaDist.density(x, false);
		double density = Double.isInfinite(origDens)? Double.MAX_VALUE:origDens;
		return density;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getCDF(double)
	 */
	@Override
	public double getCDF(double x) {
		this.calculateParameters();
		return this.betaDist.cumulative(x);
	}

	
	
	private void calculateParameters() {
		if(this.isInitialised == true)return;
		double alpha;
		double beta;
		
		double[] vals = this.listToArray();
		
		double mean = Utils.mean(vals);
		double var = UtilsPT.var(vals);
		
		alpha = mean * ( (mean*(1.0 - mean)+this.eps)/(var + this.eps) - 1.0);
		beta = (1.0 - mean) * ( (mean*(1.0 - mean)+this.eps)/(var + this.eps) - 1.0);
		
		alpha = Math.max(alpha, this.eps);
		beta = Math.max(beta, this.eps);
		
		this.betaDist = new Beta(alpha, beta);
		this.isInitialised = true;
	}


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


	@Override
	public void reset() {
		this.isInitialised = false;
		
	}
	
	
	

}
