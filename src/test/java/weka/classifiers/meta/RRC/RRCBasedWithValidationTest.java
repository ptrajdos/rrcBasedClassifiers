package weka.classifiers.meta.RRC;

import java.util.ArrayList;
import java.util.List;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
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
		gen.setNumObjects(100);
		Instances data = gen.generateData();
		
		
		RRCBasedWithValidation rrcB = (RRCBasedWithValidation) this.getClassifier();
		rrcB.setCrossvalidate(true);
		rrcB.setKeepOldValidationInstances(true);
		String[] options = rrcB.getOptions();
		
		try {
			rrcB.buildClassifier(data);
			int valSize = rrcB.validationSet.size();
			assertTrue("Val size", valSize == rrcB.validationResponses.size());
			for (Instance instance: data) {
				rrcB.updateClassifier(instance);
				double[] respones = rrcB.distributionForInstance(instance);
				assertTrue("Keep validation Instances. Distribution check", DistributionChecker.checkDistribution(respones));
				assertTrue("Number of validation instances", rrcB.validationSet.size() == rrcB.validationResponses.size());
				assertTrue("Updated validation", rrcB.validationResponses.size() == ++valSize);
				weightsChecker(rrcB);
			}
		} catch (Exception e) {
			fail("Update Keep instances" + e.toString());
		}
		
		
		
	}
	
	public void testValidationInstances() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(100);
		Instances data = gen.generateData();
		
		
		RRCBasedWithValidation rrcB = (RRCBasedWithValidation) this.getClassifier();
		rrcB.setCrossvalidate(true);
		rrcB.setKeepOldValidationInstances(false);
		String[] options = rrcB.getOptions();
		
		try {
			rrcB.buildClassifier(data);
			int valSize = rrcB.validationSet.size();
			assertTrue("Val size", valSize == rrcB.validationResponses.size());
			for (Instance instance: data) {
				rrcB.updateClassifier(instance);
				double[] respones = rrcB.distributionForInstance(instance);
				assertTrue(" validation Instances. Distribution check", DistributionChecker.checkDistribution(respones));
				assertTrue("Number of validation instances", rrcB.validationSet.size() == rrcB.validationResponses.size());
				//assertTrue("Updated validation", rrcB.validationResponses.size() == valSize);
				weightsChecker(rrcB);
			}
		} catch (Exception e) {
			fail("Update Keep instances" + e.toString());
		}
		
		
		
	}
	
	protected List<Double> extractInstanceWeights(RRCBasedWithValidation rrc){
		int numValInstances = rrc.validationSet.size();
		List<Double> weightList = new ArrayList<Double>(numValInstances);
		for(int i=0;i<numValInstances;i++)
			weightList.add(rrc.validationSet.get(i).weight());
		
		return weightList;
	}
	
	protected void weightsChecker(RRCBasedWithValidation rrc) {
		List<Double> weights = this.extractInstanceWeights(rrc);
		int size = weights.size();
		double endWeight = weights.get(size-1);
		// last weight should be equall one
		assertTrue("Last weight", Utils.eq(endWeight, 1.0) );
		
		//older instances should get lower weights
		for(int i=0;i<size-1;i++) {
			if(weights.get(i) > weights.get(i+1))
				fail("Weights are not decreasing");
		}
		
	
	}
	

	

}
