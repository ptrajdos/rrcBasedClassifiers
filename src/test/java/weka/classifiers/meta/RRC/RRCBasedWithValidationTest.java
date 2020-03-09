package weka.classifiers.meta.RRC;

import weka.core.Instance;
import weka.core.Instances;
import weka.tools.data.RandomDataGenerator;
import weka.tools.tests.DistributionChecker;

public abstract class RRCBasedWithValidationTest extends RRCWrapperTest {

	public RRCBasedWithValidationTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void testCV() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		
		
		RRCBasedWithValidation rrcB = (RRCBasedWithValidation) this.getClassifier();
		rrcB.setCrossvalidate(true);
		String[] options = rrcB.getOptions();
		
		try {
			rrcB.buildClassifier(data);
			for (Instance instance : data) {
				double[] distrib = rrcB.distributionForInstance(instance);
				assertTrue("Crossvalidation. Distribution", DistributionChecker.checkDistribution(distrib) );
			}
		} catch (Exception e) {
			fail("RRCBasedWithValidation. Test CV. Exception has been caught: " + e.toString());
		}
		
	}
	
	public void testKeepValidationInstances() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		
		
		RRCBasedWithValidation rrcB = (RRCBasedWithValidation) this.getClassifier();
		rrcB.setCrossvalidate(true);
		rrcB.setKeepOldValidationInstances(true);
		String[] options = rrcB.getOptions();
		
		try {
			rrcB.buildClassifier(data);
			for (Instance instance: data) {
				rrcB.updateClassifier(instance);
				double[] respones = rrcB.distributionForInstance(instance);
				assertTrue("Keep validation Instances. Distribution check", DistributionChecker.checkDistribution(respones));
			}
		} catch (Exception e) {
			fail("Update Keep instances" + e.toString());
		}
		
		
		
	}
	

	

}
