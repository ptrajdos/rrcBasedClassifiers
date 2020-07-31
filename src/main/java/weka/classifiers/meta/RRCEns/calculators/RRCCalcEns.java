package weka.classifiers.meta.RRCEns.calculators;

/**
 * An interface for objects that calculate RRC probabilities
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 * 
 *
 */
public interface RRCCalcEns {
	
	/**
	 * Calculates RRC value for the ensemble prediction
	 * @param ensemblePrediction -- prediction of the ensmeble [classifier number] x [class supports]
	 * @return -- array of class-specific winning probability
	 * @throws an exception When something goes wrong
	 */
	public double[] calculateRRC(double[][] ensemblePrediction)throws Exception;

}
