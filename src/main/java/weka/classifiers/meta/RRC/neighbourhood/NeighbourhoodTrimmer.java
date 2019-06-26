/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import weka.core.Instance;
import weka.core.Instances;

/**
 * @author pawel trajdos
 * @since 0.2.0
 * @version 0.2.0
 *
 */
public class NeighbourhoodTrimmer extends ThresholdNeighbourhoodModifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6502220172488738529L;

	/**
	 * 
	 */
	public NeighbourhoodTrimmer() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator#getNeighbourhoodCoeffs(weka.core.Instances, weka.core.Instance)
	 */
	@Override
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
		double[] coeffs = this.neighCalc.getNeighbourhoodCoeffs(dataset, instance);
		for(int i=0;i<coeffs.length;i++)
			coeffs[i] = coeffs[i]>this.threshold? coeffs[i]:0.0;
		return coeffs;
	}

}
