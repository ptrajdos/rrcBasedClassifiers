package weka.classifiers.meta;

import junit.framework.Test;
import junit.framework.TestSuite;
import moa.classifiers.core.driftdetection.ADWIN;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.tools.data.WellSeparatedSquares;
import weka.classifiers.meta.SCMClassifierADWIN;

public class SCMClassifierADWINTest extends SCMClassifierTest {

	public SCMClassifierADWINTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		return new SCMClassifierADWIN();
	}
	
	public void  testADWINSubObject() {
		SCMClassifierADWIN adwClass = (SCMClassifierADWIN) this.getClassifier();
		ADWIN adw = adwClass.adwin;
		int numElements=100;
		boolean isChanged=false;
		for(int i=0;i<numElements;i++) {
			isChanged = adw.setInput(0);
			if(isChanged)
				fail("No distribution change should have appeared here!");
		}
		
		boolean anyChangeReported=false;
		for(int i=0;i<numElements;i++) {
			isChanged = adw.setInput(1);
			if(isChanged)
				anyChangeReported=true;
		}
		
		assertTrue("A change should be reported here", anyChangeReported);
	}
	
	public void testAdwinClassifier() {
		WellSeparatedSquares gen = new WellSeparatedSquares();
		gen.setNumClasses(2);
		gen.setNumObjects(100);
		int numInstances = gen.getNumObjects();
		Instances data = gen.generateData();
		Instances classRevData = this.changeClassAssignment(data);
		SCMClassifierADWIN adwClassifier = (SCMClassifierADWIN) this.getClassifier();
		int preInstNum, postInstNum;
		boolean anyChangeRecorded=false;
		try {
			adwClassifier.buildClassifier(data);
			for(int i=0;i<numInstances;i++) {
				preInstNum = adwClassifier.adwin.getWidth();
				adwClassifier.updateClassifier(classRevData.get(i));
				postInstNum = adwClassifier.adwin.getWidth();
				if(preInstNum > postInstNum)
					anyChangeRecorded=true;
			}
				
			assertTrue("Change should have been recorded", anyChangeRecorded);
			
		} catch (Exception e) {
			fail("An exception has been caught");
		}
	}
	
	protected Instances changeClassAssignment(Instances instances) {
		Instances newInsts = new Instances(instances);
		int insNum = newInsts.numInstances();
		Instance tmpInstance;
		double classVal;
		for(int i=0;i<insNum;i++) {
			tmpInstance =newInsts.get(i);
			classVal = tmpInstance.classValue();
			classVal = classVal==0? 1:0;
			tmpInstance.setClassValue(classVal);
		}
		
		return newInsts;
	}
	
	public static Test suite() {
	    return new TestSuite(SCMClassifierADWINTest.class);
	  }

	  public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
	  }
	
}
