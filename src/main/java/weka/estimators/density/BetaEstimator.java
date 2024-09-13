/**
 * 
 */
package weka.estimators.density;

import java.util.LinkedList;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.SimpleBounds;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math3.special.Gamma;
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
	// Log-likelihood function for the Beta distribution
    public static class BetaLogLikelihood implements MultivariateFunction {
        private final double[] data;

        public BetaLogLikelihood(double[] data) {
            this.data = data;
        }

        @Override
        public double value(double[] params) {
            double alpha = params[0];
            double beta = params[1];
            
            if (alpha <= 0 || beta <= 0) {
                return Double.POSITIVE_INFINITY;  // Invalid values
            }

            int n = data.length;
            double logXSum = 0;
            double logOneMinusXSum = 0;

            for (double x : data) {
                logXSum += Math.log(x);
                logOneMinusXSum += Math.log(1 - x);
            }

            double logLikelihood = (alpha - 1) * logXSum + (beta - 1) * logOneMinusXSum;
            logLikelihood -= n * (Gamma.logGamma(alpha) + Gamma.logGamma(beta) - Gamma.logGamma(alpha + beta));

            return -logLikelihood;  // Return negative log-likelihood for minimization
        }
    }


	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getPDF(double)
	 */
	@Override
	public double getPDF(double x) {
		this.calculateParameters();
		
		if(this.getValues().length == 0 ) {
			//For compatibility with Kernel estimators!
			return Double.NaN;
		}
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
		if(this.getValues().length == 0 ) {
			//For compatibility with Kernel estimators!
			return Double.NaN;
		}
		return this.betaDist.cumulative(x);
	}

	 // Function to estimate alpha and beta via MLE
    protected double[] estimateBetaParamsMLE(double[] data) {
        // Initial guess for alpha and beta
        double[] initialGuess = {1.0, 1.0};

        // Define optimization bounds (alpha > 0, beta > 0)
        SimpleBounds bounds = new SimpleBounds(new double[]{eps, eps}, new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY});

        // Define optimizer, here using BOBYQA or you can use CMAES or others
        BOBYQAOptimizer optimizer = new BOBYQAOptimizer(5);

        // Perform the optimization
        try {
            return optimizer.optimize(
                    new MaxEval(1000),
                    new MaxIter(1000),
                    new ObjectiveFunction(new BetaLogLikelihood(data)),
                    GoalType.MINIMIZE,
                    bounds,
                    new org.apache.commons.math3.optim.InitialGuess(initialGuess)
            ).getPoint();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Optimization failed");
        }
    }
    
    protected double[] estimateBetaParamsMomenst(double[] values) {
		double mean = Utils.mean(values);
		double var = UtilsPT.var(values);
		//TODO not so accurate for small sample [0,1]
		double alpha = mean * ( (mean*(1.0 - mean)+eps)/(var + eps) - 1.0);
		double beta = (1.0 - mean) * ( (mean*(1.0 - mean)+eps)/(var + eps) - 1.0);
		
		alpha = Math.max(alpha, eps);
		beta = Math.max(beta, eps);
		
    	return new double[] {alpha,beta};
    }
	
	private void calculateParameters() {
		if(this.isInitialised == true)return;
		double alpha;
		double beta;
		
		double[] vals = this.listToArray();
		if(vals.length == 0) {
			this.betaDist = new Beta(0, 0);
			this.isInitialised = true;
			return;
		}
		
		try {
			double[] estimatedParams = estimateBetaParamsMLE(vals);
			alpha = estimatedParams[0];
			beta = estimatedParams[1];
		}
		catch(RuntimeException e) {
			double[] estimatedParams = estimateBetaParamsMomenst(vals);
			alpha = estimatedParams[0];
			beta = estimatedParams[1];
		}
		
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
		this.samples = new LinkedList<>();
		
	}
	
	
	

}
