package weka.classifiers.meta;

import weka.classifiers.Classifier;
import weka.tools.tests.RandomDataChecker;

public class RandomClassifierFullTest extends RandomClassifierTest {

	public RandomClassifierFullTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		return new RandomClassifierFull();
	}
	
	public void testAgainstRandomData() {
		 RandomClassifierFull des = (RandomClassifierFull) this.getClassifier();
		 des.setRandomizeResponse(true);
		 assertTrue("Total Random data", RandomDataChecker.checkAgainstRandomData(des, -0.2, 0.2));
		 assertTrue("Test, Well-separated data", RandomDataChecker.checkAgainstWellSeparatedData(des, -0.2,0.2));
	 }
	
	


}
