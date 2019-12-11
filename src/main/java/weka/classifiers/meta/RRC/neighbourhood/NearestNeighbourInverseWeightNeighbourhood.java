/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

/**
 * Nearest Neighbourhood with inverse weighting
 * @author pawel trajdos
 *
 */
public class NearestNeighbourInverseWeightNeighbourhood extends NearestNeighbourNeighbourhood {

	/**
	 * 
	 */
	private static final long serialVersionUID = 393902139849024888L;

	/**
	 * 
	 */
	public NearestNeighbourInverseWeightNeighbourhood() {
		super();
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NearestNeighbourNeighbourhood#calculateWeight(double)
	 */
	@Override
	protected double calculateWeight(double distance) {
		double wei = 1.0/ (distance + 1E-3);
		return wei;
	}
	
	

}
