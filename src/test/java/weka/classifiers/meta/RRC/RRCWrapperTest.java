package weka.classifiers.meta.RRC;

import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.tools.data.RandomDataGenerator;
import weka.tools.tests.DistributionChecker;
import weka.tools.tests.WekaGOEChecker;


public abstract class RRCWrapperTest extends AbstractClassifierTest{

	public RRCWrapperTest(String name) {
		super(name);
	}


	public void testGlobalInfor() {
		WekaGOEChecker check = new WekaGOEChecker();
		check.setObject(this.getClassifier());
		assertTrue("Global info Checker", check.checkCallGlobalInfo());
		assertTrue("TipText checker", check.checkToolTipsCall());
	}
	
	public void testUpdate() {
		RandomDataGenerator gen  = new RandomDataGenerator();
		Instances data = gen.generateData();
		Instance testInstance =data.get(0);
		
		RRCWrapper wrap = (RRCWrapper) this.getClassifier();
		wrap.setClassifier(new NaiveBayesUpdateable());
		
		try {
			wrap.buildClassifier(data);
			for (Instance instance : data) {
				wrap.updateClassifier(instance);
				double[] prediction = wrap.distributionForInstance(testInstance);
				assertTrue("Update classifier. Prediction check", DistributionChecker.checkDistribution(prediction));
			}
		} catch (Exception e) {
			fail("RRC Wrapper. Update base classifier. Exception has been caught: " + e.toString());
		}
	}

	

}
