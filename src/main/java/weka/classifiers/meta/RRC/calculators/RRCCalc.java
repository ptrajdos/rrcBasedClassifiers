/**
 * 
 */
package weka.classifiers.meta.RRC.calculators;

/**
 * The interface for varrious types of the RRC classifier.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public interface RRCCalc {
	/**
	 * Calculates RRC probabilities for binary classifier
	 * @param oneProb - probability of class "1"
	 * @return RRC probability related to class "1"
	 */
	public double calculateRRCBinary(double oneProb);
	
	/**
	 * Calculates RRC probabilities for binary classifier
	 * @param oneProbs - probabilities of class "1"
	 * @return RRC probabilities related to class "1"
	 */
	public double[] calculateRRCBinaryML(double[] oneProbs);
	
	/**
	 * Calculates RRC probabilities for multi-class prediction
	 * @param predictions -- vector of probabilities
	 * @return Probabilities calculates by the RRC model.
	 */
	public double[] calculateRRC(double[] predictions);
	
	

}
