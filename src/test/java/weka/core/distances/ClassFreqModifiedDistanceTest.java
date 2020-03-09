package weka.core.distances;

import weka.core.CheckOptionHandler;
import weka.core.DistanceFunction;
import weka.core.DistanceFunctionTest;
import weka.core.Instance;
import weka.core.Instances;
import weka.tools.data.RandomDataGenerator;

public class ClassFreqModifiedDistanceTest extends DistanceFunctionTest {

	@Override
	public DistanceFunction getDistanceFunction() {
		return new ClassFreqModifiedDistance();
	}
	
	public void testOptions(){
		CheckOptionHandler optHandlerCheck = new CheckOptionHandler();
		optHandlerCheck.setOptionHandler(this.getDistanceFunction());
		optHandlerCheck.doTests();
	}
	
	public void testCaseFinder() {
		ClassFreqModifiedDistance dFun = (ClassFreqModifiedDistance) this.getDistanceFunction();
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		Instance first = data.get(0);
		Instance second = data.get(1);
		dFun.setInstances(data);
		
		ClassFreqModifiedDistance.Case resCase; 
		double distance;
		double origDist=1.0;
		
		resCase = dFun.caseFinder(first, second);
		assertTrue("Both Class case", resCase.equals(ClassFreqModifiedDistance.Case.BothWithClass));
		distance = dFun.getModifiedDistance(first, second, origDist);
		assertTrue("Modified distance, Both classes present", Double.isFinite(distance));
		
		first.setClassMissing();
		resCase = dFun.caseFinder(first, second);
		assertTrue("First Class missing", resCase.equals(ClassFreqModifiedDistance.Case.SecondWithClass));
		distance = dFun.getModifiedDistance(first, second, origDist);
		assertTrue("Modified distance, First Class missing", Double.isFinite(distance));
		
		second.setClassMissing();
		resCase = dFun.caseFinder(first, second);
		assertTrue("BothClass missing", resCase.equals(ClassFreqModifiedDistance.Case.BothNoClass));
		distance = dFun.getModifiedDistance(first, second, origDist);
		assertTrue("Modified distance, Both Classes missing", Double.isFinite(distance));
		
		first=data.get(2);
		
		resCase = dFun.caseFinder(first, second);
		assertTrue("Second Class missing", resCase.equals(ClassFreqModifiedDistance.Case.FirstWithClass));
		distance = dFun.getModifiedDistance(first, second, origDist);
		assertTrue("Modified distance, Second Class missing", Double.isFinite(distance));
		
	}


}
