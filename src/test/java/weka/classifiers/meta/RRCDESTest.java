package weka.classifiers.meta;

import java.util.Arrays;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.tools.data.RandomDataGenerator;

public class RRCDESTest extends MultipleClassifiersCombinerWithValidationSetTest {

	public RRCDESTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		RRCDES des = new RRCDES();
		des.setClassifiers(new Classifier[]{new J48(),new NaiveBayes(),new ZeroR(), new IBk()});
		return des;
	}
	
 public void testThresholds() {
	 RandomDataGenerator gen = new RandomDataGenerator();
	 Instances data = gen.generateData();
	 RRCDES des = (RRCDES) this.getClassifier();
	 int numClasses = data.numClasses();
	 int numClassifiers = des.getClassifiers().length;
	 
	 try {
		des.buildClassifier(data);
		double[] res = des.probsThreshold(new double[numClassifiers]);
		assertTrue("Zero probs", Utils.eq(Utils.sum(res), 0.0));
		
		double[] probs = new double[numClassifiers];
		double checkVal = 1.0/numClasses - 1E-3;
		Arrays.fill(probs, checkVal );
		res = des.probsThreshold(probs);
		double resVal = res[Utils.maxIndex(res)];
		assertTrue("Non zero weights under threshold", Utils.eq(resVal, 1.0)  );
		
		
		
		
	} catch (Exception e) {
		fail("Threshold test. Exception has been caught" + e.toString());
	}
	 
 }	

	

}
