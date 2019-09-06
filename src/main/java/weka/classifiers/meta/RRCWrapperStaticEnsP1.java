/**
 * 
 */
package weka.classifiers.meta;

import weka.classifiers.meta.RRCEns.RRCWrapper;
import weka.core.Instance;
import weka.tools.GlobalInfoHandler;

/**
 * Static RRC wrapper for other classifiers.
 * Changes the classifier responses into probabilistic ones based on the RRC model.
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 */
public class RRCWrapperStaticEnsP1 extends RRCWrapper implements GlobalInfoHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4524618567036348904L;


	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifier#distributionForInstance(weka.core.Instance)
	 */
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		double[][] ensBaseResp = this.getCommittee().distributionForInstanceCommittee(instance); 
		double[] corrDistr = this.rrcCalc.calculateRRC(ensBaseResp);
		return corrDistr;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		runClassifier(new RRCWrapperStaticEnsP1(), args);

	}


	@Override
	public String globalInfo() {
		return "Creates the model with the output probabilities modified using RRC approach";
	}

}
