/**
 * 
 */
package weka.classifiers.meta;

import weka.classifiers.Classifier;

/**
 * @author pawel trajdos
 *
 */
public class RRCDES2_Crossvalidate_Test extends RRCDES2Test {

	/**
	 * @param name
	 */
	public RRCDES2_Crossvalidate_Test(String name) {
		super(name);
	}
	
	public Classifier getClassifier() {
		RRCDES2 des =(RRCDES2) super.getClassifier();
		des.setCrossvalidate(true);
		des.setNumFolds(2);
		return des;
	}

}
