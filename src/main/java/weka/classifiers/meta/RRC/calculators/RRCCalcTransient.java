/**
 * 
 */
package weka.classifiers.meta.RRC.calculators;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author pawel trajdos
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
		return oneProb;
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.calculators.RRCCalc#calculateRRCBinaryML(double[])
	 */
	@Override
	public double[] calculateRRCBinaryML(double[] oneProbs) {
		double[] result  =Arrays.copyOf(oneProbs, oneProbs.length);
		return result;
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.calculators.RRCCalc#calculateRRC(double[])
	 */
	@Override
	public double[] calculateRRC(double[] predictions) {
		double[] result = Arrays.copyOf(predictions, predictions.length);
		return result;
	}

}
