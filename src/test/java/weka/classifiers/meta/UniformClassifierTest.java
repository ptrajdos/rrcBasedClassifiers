package weka.classifiers.meta;

import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;
import weka.tools.GlobalInfoHandler;

public class UniformClassifierTest extends AbstractClassifierTest {

	public UniformClassifierTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		return new UniformClassifier();
	}

	
	

	
}
