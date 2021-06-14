package weka.classifiers.meta;

import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;
import weka.tools.tests.WekaGOEChecker;

public class UniformClassifierTest extends AbstractClassifierTest {

	public UniformClassifierTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		return new UniformClassifier();
	}

	public void testGlobalInfo() {
		WekaGOEChecker check = new WekaGOEChecker();
		check.setObject(this.getClassifier());
		assertTrue("Global Info", check.checkCallGlobalInfo());
	}
	

	
}
