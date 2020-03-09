package weka.core.distances;



import java.util.Random;

import weka.core.DistanceFunction;
import weka.core.DistanceFunctionTest;
import weka.core.Instance;
import weka.core.Instances;
import weka.tools.data.RandomDataGenerator;

public class NormalizedEuclideanDistanceTest extends DistanceFunctionTest {

	@Override
	public DistanceFunction getDistanceFunction() {
		return new NormalizedEuclideanDistance();
	}

	public void testNormalization() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		
		DistanceFunction dFun = this.getDistanceFunction();
		dFun.setInstances(data);
		
		int numTries=100;
		int numInstances = data.numInstances();
		Random rnd = new Random();
		for(int i=0;i<numTries;i++) {
			Instance inst1 = data.get(rnd.nextInt(numInstances));
			Instance inst2 = data.get(rnd.nextInt(numInstances));
			double distance = dFun.distance(inst1, inst2);
			assertTrue("DistanceRange", distance>=0 & distance<=1);
		}
	}

}
