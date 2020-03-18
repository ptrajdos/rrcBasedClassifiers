/**
 * 
 */
package weka.classifiers.meta.RRC.calculators;

import java.io.Serializable;
import java.util.Arrays;

import weka.core.Utils;
import weka.core.UtilsPT;

/**
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 * 
 * This class implements a transient RRC calculator
 * This calculator does not modify the soft outcome of the classifier 
 * The soft outcomes are interpreted as probabilities of selecting the releated classes
 *
 */
public class RRCCalcTransient implements RRCCalc, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1777504791789058473L;

	/**
	 * 
	 */
	public RRCCalcTransient() {
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.calculators.RRCCalc#calculateRRCBinary(double)
	 */
	@Override
	public double calculateRRCBinary(double oneProb) {
		if(oneProb <0)
			return 0;
		if(oneProb>1)
			return 1;
		
		return oneProb;
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.calculators.RRCCalc#calculateRRCBinaryML(double[])
	 */
	@Override
	public double[] calculateRRCBinaryML(double[] oneProbs) {
		double[] result  =Arrays.copyOf(oneProbs, oneProbs.length);
		for(int i=0;i<result.length;i++) {
			if(result[i]<0)
				result[i]=0;
			
			if(result[i]>0)
				result[i]=1;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.calculators.RRCCalc#calculateRRC(double[])
	 */
	@Override
	public double[] calculateRRC(double[] predictions) {
		double[] result = Arrays.copyOf(predictions, predictions.length);
		double sum = Utils.sum(result);
		
		if(!Utils.eq(sum, 0.0))
			Utils.normalize(result,sum);
		else
			result = UtilsPT.softMax(result);
		
		return result;
	}

}
