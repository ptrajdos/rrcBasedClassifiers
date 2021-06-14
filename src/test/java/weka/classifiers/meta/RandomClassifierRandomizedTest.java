package weka.classifiers.meta;

import weka.classifiers.Classifier;

public class RandomClassifierRandomizedTest extends RandomClassifierTest {

	public RandomClassifierRandomizedTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		return new RandomClassifierRandomized();
	}


}
