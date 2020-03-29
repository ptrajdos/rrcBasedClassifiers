/**
 * 
 */
package weka.classifiers.meta;

import weka.classifiers.Classifier;

/**
 * @author pawel trajdos
 *
 */
public class RRCDES_Crossvalidate_Test extends RRCDESTest {

	/**
	 * @param name
	 */
	public RRCDES_Crossvalidate_Test(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Classifier getClassifier() {
		RRCDES des =(RRCDES) super.getClassifier();
		des.setCrossvalidate(true);
		des.setNumFolds(2);
		return des; 
	}
	

}
