/**
 * 
 */
package weka.classifiers.meta.RRC.tools;

import org.apache.commons.math.special.Gamma;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937a;
import org.apache.commons.math3.util.FastMath;

/**
 * The class implements Kumaraswamy distribution.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class KumaraswamyDistribution extends AbstractRealDistribution  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4944611664747930687L;
	
	protected double alpha;
	
	protected double beta;
	

	/**
	 * @param rng -- random generator to use
	 * @param alpha -- distribution parameter
	 * @param beta -- distribution parameter
	 */
	public KumaraswamyDistribution(RandomGenerator rng,double alpha, double beta) {
		super(rng);
		this.alpha = alpha;
		this.beta = beta;
	}

	public KumaraswamyDistribution(double alpha, double beta) {
		this(new Well19937a(), alpha, beta);
	}
	/* (non-Javadoc)
	 * @see org.apache.commons.math3.distribution.RealDistribution#density(double)
	 */
	@Override
	public double density(double x) {
		
		double val = FastMath.log(this.alpha) + FastMath.log(this.beta) + (this.alpha - 1) * FastMath.log(x) 
				+ (this.beta - 1) * FastMath.log1p(-FastMath.pow(x, this.alpha));
		val = FastMath.exp(val);
		return val;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.math3.distribution.RealDistribution#cumulativeProbability(double)
	 */
	@Override
	public double cumulativeProbability(double x) {
		double tmp = this.beta * FastMath.log1p(-FastMath.pow(x, this.alpha)) ;
		double cdf= 1-FastMath.exp(tmp) ; 
		return cdf;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.math3.distribution.RealDistribution#getNumericalMean()
	 */
	@Override
	public double getNumericalMean() {
		double a = Gamma.logGamma(1 + 1.0/this.alpha);
		double b = Gamma.logGamma(this.beta);
		double c = Gamma.logGamma(1+ 1.0/this.alpha + this.beta);
		
		double res = this.beta * Math.exp(a+b-c);
		return res;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.math3.distribution.RealDistribution#getNumericalVariance()
	 */
	@Override
	public double getNumericalVariance() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.math3.distribution.RealDistribution#getSupportLowerBound()
	 */
	@Override
	public double getSupportLowerBound() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.math3.distribution.RealDistribution#getSupportUpperBound()
	 */
	@Override
	public double getSupportUpperBound() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.math3.distribution.RealDistribution#isSupportLowerBoundInclusive()
	 */
	@Override
	public boolean isSupportLowerBoundInclusive() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.math3.distribution.RealDistribution#isSupportUpperBoundInclusive()
	 */
	@Override
	public boolean isSupportUpperBoundInclusive() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.math3.distribution.RealDistribution#isSupportConnected()
	 */
	@Override
	public boolean isSupportConnected() {
		return true;
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

	/**
	 * @return the beta
	 */
	public double getBeta() {
		return this.beta;
	}

	/**
	 * @param beta the beta to set
	 */
	public void setBeta(double beta) {
		this.beta = beta;
	}
	
	

}
