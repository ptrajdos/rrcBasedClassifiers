/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import weka.core.Instance;
import weka.core.Instances;

/**
 * Class converts neighbourhood to the crisp ones using the threshold
 * @author pawel trajdos
 * @since 0.2.0
 * @version 0.2.0
 *
 */
public class NeighbourhoodCrisper extends ThresholdNeighbourhoodModifier {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7011526982553018657L;

	/**
	 * 
	 */
	public NeighbourhoodCrisper() {
		super();
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator#getNeighbourhoodCoeffs(weka.core.Instances, weka.core.Instance)
	 */
	@Override
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
		double[] coeffs = this.neighCalc.getNeighbourhoodCoeffs(dataset, instance);
		for(int i=0;i<coeffs.length;i++)
			coeffs[i] = coeffs[i]>this.threshold? 1.0:0.0;
		return coeffs;
	}


}
