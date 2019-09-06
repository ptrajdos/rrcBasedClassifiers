/**
 * 
 */
package weka.classifiers.meta.RRCEns.calculators;

import java.io.Serializable;

import weka.core.Utils;

/**
 * Simple transient-like RRC probability estimator
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class RRCCalcEnsTransientAverage implements RRCCalcEns, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5416635199716466924L;
	
	public RRCCalcEnsTransientAverage() {
		// DO NOTHING
	}

	

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRCEns.calculators.RRCCalcEns#calculateRRC(double[][])
	 */
	@Override
	public double[] calculateRRC(double[][] ensemblePrediction)throws Exception {
		int numClasses = ensemblePrediction[0].length;
		double[] response =  new double[numClasses];
		
		for(int c =0 ;c<ensemblePrediction.length;c++) 
			for(int i=0;i<numClasses;i++) 
				response[i]+=ensemblePrediction[c][i]/ensemblePrediction.length;
		
		Utils.normalize(response);
	
		return response;
	}

}
