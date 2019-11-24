package weka.classifiers.meta;

import static org.junit.Assert.*;

import org.junit.Test;

import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;

public class RandomClassifierRandomizedTest extends AbstractClassifierTest {

	public RandomClassifierRandomizedTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		return new RandomClassifierRandomized();
	}


}
