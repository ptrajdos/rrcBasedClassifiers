package weka.classifiers.meta.RRC.tools;

import org.apache.commons.math3.random.RandomGenerator;

/**
 * The class implements Kumaraswamy Distribution CDF
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class KumaraswamyCDFCal extends KumaraswamyDistribution implements MultiCumulativeCalc {

	public KumaraswamyCDFCal(double alpha, double beta) {
		super(alpha, beta);
	}
	
	public KumaraswamyCDFCal(RandomGenerator rng,double alpha, double beta) {
		super(rng,alpha,beta);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6203140804101948747L;

	@Override
	public double[] cumulative(double[] x) {
		int xLen = x.length;
		double[] values = new double[xLen];
		
		for(int i=0;i<xLen;i++)
			values[i] = this.cumulativeProbability(x[i]);
		
		
		return values;
	}
	
	public double cumulative(double x){
		return this.cumulative(x);
	}

	

}
