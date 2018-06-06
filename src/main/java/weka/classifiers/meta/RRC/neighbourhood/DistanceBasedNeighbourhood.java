/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.io.Serializable;

import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author pawel
 *
 */
public class DistanceBasedNeighbourhood implements NeighbourhoodCalculator, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3298340988235027614L;
	
	protected DistanceFunction distFun = new EuclideanDistance() ;

	/**
	 * 
	 */
	public DistanceBasedNeighbourhood() {
		
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator#getNeighbourhoodCoeffs(weka.core.Instances, weka.core.Instance)
	 */
	@Override
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the distFun
	 */
	public DistanceFunction getDistFun() {
		return this.distFun;
	}
	
	public String distFunTipText() {
		return "Distance function to use.";
	}

	/**
	 * @param distFun the distFun to set
	 */
	public void setDistFun(DistanceFunction distFun) {
		this.distFun = distFun;
	}
	
	

}
