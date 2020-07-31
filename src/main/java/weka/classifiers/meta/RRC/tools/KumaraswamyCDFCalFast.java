/**
 * 
 */
package weka.classifiers.meta.RRC.tools;

import java.io.Serializable;

/**
 * Class implements fast computation of Kumaraswamy distribution CDF
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class KumaraswamyCDFCalFast implements MultiCumulativeCalc, Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = 8963830272020664013L;
	
	protected double alpha;
	
	protected double beta;

	/**
	 * 
	 * @param alpha -- parameter
	 * @param beta -- parameter
	 */
	public KumaraswamyCDFCalFast(double alpha, double beta) {
		this.alpha = alpha;
		this.beta = beta;
		
	}

	/* (non-Javadoc)
	 * @see mLWorkhorse.Tools.stats.MultiCumulativeCalc#cumulative(double[])
	 */
	@Override
	public double[] cumulative(double[] x) {
		double[] result = new double[x.length];
		
		for(int i=0;i<x.length;i++)
			result[i] = this.cumulative(x[i]);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see mLWorkhorse.Tools.stats.MultiCumulativeCalc#cumulative(double)
	 */
	@Override
	public double cumulative(double x) {
		double tmp = this.beta * Math.log(1-Math.pow(x, this.alpha)) ;
		double cdf= 1-Math.exp(tmp) ; 
		return cdf;
	}

}
