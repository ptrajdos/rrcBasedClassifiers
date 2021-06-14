/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import weka.core.Instance;
import weka.core.Instances;

/**
 * Nearest neighbour distance function.
 * The neighbourhood is created using the choosen number of nearest neighbours. 
 * The neighbourhood membership coefficient for the nearest instances is one. 
 * The neighbourhood membership coefficient for remaining instances is zero.
 * 
 * @author pawel trajdos
 * @since 0.1.0
 * @version 1.0.0
 *
 */
public class RankBasedNeighbourhood extends DistanceBasedNeighbourhood {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4656882914788648210L;


	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator#getNeighbourhoodCoeffs(weka.core.Instances, weka.core.Instance)
	 */
	@Override
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
		double[] coeffs = this.calculateDistances(dataset, instance);
		List<SimpleEntry<Integer, Double>> coefList = new ArrayList<SimpleEntry<Integer, Double>>(coeffs.length);
		for(int i=0;i<coeffs.length;i++) {
			coefList.add(new SimpleEntry<Integer, Double>(i, coeffs[i]));
		}
		Collections.sort(coefList,new Comparator<SimpleEntry<Integer, Double>>() {

			@Override
			public int compare(SimpleEntry<Integer, Double> o1, SimpleEntry<Integer, Double> o2) {
				if(o1.getValue()>o2.getValue())return 1;
				if(o1.getValue()<o2.getValue())return -1;
				return 0;
			}
		});
		double[] newCoeffs  =new double[coeffs.length];
		for(int i=0;i<newCoeffs.length;i++) {
			newCoeffs[coefList.get(i).getKey()]=this.calculateWeight(coeffs[coefList.get(i).getKey()]);
		}
		
		return newCoeffs;
	}

	
	
	protected double calculateWeight(double distance) {
		return 1.0/(distance+1.0);
	}
	

}
