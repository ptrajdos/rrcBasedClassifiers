/**
 * 
 */
package weka.classifiers.meta;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;

/**
 * @author pawel
 *
 */
public class RRCWrapperStaticP1 extends RRCWrapper {

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
	 * @param baseClassifier
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
	 * @param args
	 */
	public static void main(String[] args) {
		runClassifier(new RRCWrapperStaticP1(), args);

	}

}
