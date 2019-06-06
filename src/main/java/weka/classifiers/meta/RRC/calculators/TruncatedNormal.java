/**
 * 
 */
package weka.classifiers.meta.RRC.calculators;

import java.io.Serializable;

import net.sourceforge.jdistlib.Normal;
import net.sourceforge.jdistlib.generic.GenericDistribution;

/**
 * The class implements the Truncated Gaussian distribution.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class TruncatedNormal extends GenericDistribution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6518630979621333436L;
	
	protected double mu=0;
	protected double sd=1;
	protected double lower = Double.NEGATIVE_INFINITY;
	protected double upper = Double.POSITIVE_INFINITY;
	
	protected double alpha,beta,Z;
	

	/**
	 * 
	 * @param mu -- expected value
	 * @param sd -- standard deviation
	 * @param lower -- upper bound
	 * @param upper -- lower bound
	 */
	public TruncatedNormal(double mu, double sd, double lower, double upper) {
		this.mu = mu;
		this.sd = sd;
		this.lower = lower;
		this.upper = upper;
		
		
		this.alpha = (this.lower - this.mu)/this.sd;
		this.beta = (this.upper - this.mu)/this.sd;
		Normal norm = new Normal();
		
		this.Z = norm.cumulative(this.beta) - norm.cumulative(this.alpha);
		
		
	}
	/**
	 * Constructor with default values
	 * mu -- 0
	 * sd -- 1
	 * lower -- 0
	 * upper -- 1
	 */
	public TruncatedNormal() {
		this(0,1,0,1);
	}
	
	

	/* (non-Javadoc)
	 * @see net.sourceforge.jdistlib.generic.GenericDistribution#density(double, boolean)
	 */
	@Override
	public double density(double x, boolean log) {
		if (x < this.lower || x > this.upper)
			return log? Double.NEGATIVE_INFINITY:0;
		
		double eps = (x-this.mu)/this.sd;
		Normal norm = new Normal();
		double p = norm.density(eps, false)/(this.sd * this.Z);
		return log? Math.log(p):p;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.jdistlib.generic.GenericDistribution#cumulative(double, boolean, boolean)
	 */
	@Override
	public double cumulative(double p, boolean lower_tail, boolean log_p) {
		if(p<this.lower) return log_p? Double.NEGATIVE_INFINITY:0;
		if(p>this.upper) return log_p? 0:1;
		
		double eps = (p-this.mu)/this.sd;
		Normal norm = new Normal();
		double val = (norm.cumulative(eps) - norm.cumulative(this.alpha))/this.Z;
		
		return log_p? Math.log(val):val;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.jdistlib.generic.GenericDistribution#quantile(double, boolean, boolean)
	 */
	@Override
	public double quantile(double q, boolean lower_tail, boolean log_p) {
		if(q<0 || q>1)return Double.NaN;
		Normal norm = new Normal();
		
		double val = q*this.Z + norm.cumulative(this.alpha);
		double res = this.mu + this.sd + norm.quantile(val);
		return res;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.jdistlib.generic.GenericDistribution#random()
	 */
	@Override
	public double random() {
		Normal norm = new Normal();
		double val = super.random.nextDouble()*this.Z + norm.cumulative(this.alpha);
		double res = this.mu + this.sd * norm.quantile(val);
		return res;
	}

}
