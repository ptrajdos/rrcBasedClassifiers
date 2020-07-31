/**
 * 
 */
package weka.classifiers.meta;

import weka.classifiers.Classifier;
import weka.classifiers.meta.RRC.RRCWrapper;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.tools.GlobalInfoHandler;

/**
 * Static RRC wrapper for other classifiers.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 */
public class RRCWrapperStaticP1 extends RRCWrapper implements GlobalInfoHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4524618567036348904L;

	/**
	 * 
	 */
	public RRCWrapperStaticP1() {
		this(new J48());
	}
	
	
	/**
	 * @param baseClassifier -- base classifier to use
	 */
	public RRCWrapperStaticP1(Classifier baseClassifier) {
		super(baseClassifier);
	}
	
	

	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifier#distributionForInstance(weka.core.Instance)
	 */
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		double[] baseDistr = this.m_Classifier.distributionForInstance(instance); 
		double[] corrDistr = this.rrcCalc.calculateRRC(baseDistr);
		return corrDistr;
	}


	/**
	 * @param args arguments
	 */
	public static void main(String[] args) {
		runClassifier(new RRCWrapperStaticP1(), args);

	}


	@Override
	public String globalInfo() {
		return "Creates the model with the output probabilities modified using RRC approach";
	}

}
