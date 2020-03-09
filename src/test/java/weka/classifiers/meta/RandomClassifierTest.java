package weka.classifiers.meta;

import weka.classifiers.AbstractClassifierTest;
import weka.core.Instance;
import weka.core.Instances;
import weka.tools.data.RandomDataGenerator;
import weka.tools.tests.DistributionChecker;
import weka.tools.tests.WekaGOEChecker;

public abstract class RandomClassifierTest extends AbstractClassifierTest {

	public RandomClassifierTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	
	public void testRandomizedOutput() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		Instance testInstance = data.get(0);
		
		RandomClassifier rndClass = (RandomClassifier) this.getClassifier();
		rndClass.setRandomizeResponse(true);
		
		int numTries = 100;
		
		try {
			rndClass.buildClassifier(data);
			for(int i=0;i<numTries;i++) {
				double[] response = rndClass.distributionForInstance(testInstance);
				assertTrue("Random Response ", DistributionChecker.checkDistribution(response));
			}
		} catch (Exception e) {
			fail("Random Classifier Response. Exception has been caught: " + e.toString() );
		}
		
		
	}
	
	public void testGlobalInfo() {
		WekaGOEChecker check = new WekaGOEChecker();
		check.setObject(this.getClassifier());
		assertTrue("Global Info check", check.checkCallGlobalInfo());
		assertTrue("Check Tip Texts", check.checkToolTipsCall());
		
	}
	
	

}
