package weka.classifiers.meta;

import static org.junit.Assert.*;

import org.junit.Test;

import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;

public class RandomClassifierFullTest extends AbstractClassifierTest {

	public RandomClassifierFullTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		return new RandomClassifierFull();
	}


}
