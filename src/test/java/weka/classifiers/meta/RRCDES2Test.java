/**
 * 
 */
package weka.classifiers.meta;

import weka.classifiers.Classifier;

/**
 * @author pawel
 *
 */
public class RRCDES2Test extends RRCDESTest {

	/**
	 * @param name
	 */
	public RRCDES2Test(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Classifier getClassifier() {
		RRCDES des =(RRCDES) super.getClassifier();
		des.setCrossvalidate(true);
		return des; 
	}
	

}
