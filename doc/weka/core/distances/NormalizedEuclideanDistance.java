/**
 * 
 */
package weka.core.distances;

import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.PerformanceStats;

/**
 * <p>NormalizedEuclideanDistance class.</p>
 *
 * @author pawel
 */
public class NormalizedEuclideanDistance extends EuclideanDistance {

	/**
	 * 
	 */
	private static final long serialVersionUID = -572810679027328671L;

	/**
	 * 
	 */
	public NormalizedEuclideanDistance() {
		super();
	}

	/**
	 * @param data
	 */
	public NormalizedEuclideanDistance(Instances data) {
		super(data);
	}

	/* (non-Javadoc)
	 * @see weka.core.NormalizableDistance#distance(weka.core.Instance, weka.core.Instance, double)
	 */
	@Override
	public double distance(Instance first, Instance second, double cutOffValue) {
		double tmpDistance = super.distance(first, second, cutOffValue);
		double numAttrs = this.m_Data.numAttributes();
		tmpDistance = tmpDistance/Math.sqrt(numAttrs);
		return tmpDistance;		
	}

	/* (non-Javadoc)
	 * @see weka.core.NormalizableDistance#distance(weka.core.Instance, weka.core.Instance, double, weka.core.neighboursearch.PerformanceStats)
	 */
	@Override
	public double distance(Instance first, Instance second, double cutOffValue, PerformanceStats stats) {
		double tmpDistance =super.distance(first, second, cutOffValue, stats);
		double numAttrs = this.m_Data.numAttributes();
		tmpDistance = tmpDistance/Math.sqrt(numAttrs);
		return tmpDistance;		
				
	}
	
	

}
