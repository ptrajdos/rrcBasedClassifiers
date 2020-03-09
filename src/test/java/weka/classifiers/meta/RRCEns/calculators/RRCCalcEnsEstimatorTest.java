package weka.classifiers.meta.RRCEns.calculators;

import weka.tools.tests.WekaGOEChecker;

public class RRCCalcEnsEstimatorTest extends RRCCalcEnsTest{

	public RRCCalcEnsEstimatorTest(String name, String classname) {
		super(name, classname);
	}
	public RRCCalcEnsEstimatorTest(String name) {
		this(name,RRCCalcEnsEstimator.class.getCanonicalName());
	}
	
	public void testTipTexts() {
		WekaGOEChecker check = new WekaGOEChecker();
		check.setObject(this.getEstimator());
		assertTrue("Tip Text Checks", check.checkToolTipsCall());
	}

}
