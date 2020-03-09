package weka.classifiers.meta;

import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;

public class RandomClassifierFullTest extends RandomClassifierTest {

	public RandomClassifierFullTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		return new RandomClassifierFull();
	}
	
	


}
