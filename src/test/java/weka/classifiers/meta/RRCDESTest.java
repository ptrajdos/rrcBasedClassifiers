package weka.classifiers.meta;

import java.util.Arrays;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.RRC.neighbourhood.GaussianNeighbourhood;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.tools.SerialCopier;
import weka.tools.data.RandomDataGenerator;
import weka.tools.tests.DistributionChecker;
import weka.tools.tests.RandomDataChecker;

public class RRCDESTest extends MultipleClassifiersCombinerWithValidationSetTest {

	public RRCDESTest(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		RRCDES des = new RRCDES();
		des.setClassifiers(new Classifier[]{new J48(),new NaiveBayes(),new ZeroR(), new IBk()});
		GaussianNeighbourhood gaussNeigh = new GaussianNeighbourhood();
		gaussNeigh.setAlpha(10.0);
		des.setNeighCalc(gaussNeigh);
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
		double checkVal = (1.0/numClasses) - 1E-3;
		Arrays.fill(probs, checkVal );
		res = des.probsThreshold(probs);
		double resVal = res[Utils.maxIndex(res)];
		assertTrue("Non zero weights under threshold", Utils.eq(resVal, 1.0)  );
		
		int numTrials=10;
		for(int i=0;i<numTrials;i++) {
			double[] vec = this.generateRandomVectors(numClasses, i);
			double[] thresholds = des.probsThreshold(vec);
			assertTrue("Threshold distribution check", DistributionChecker.checkDistribution(thresholds));
		}
		
	} catch (Exception e) {
		fail("Threshold test. Exception has been caught" + e.toString());
	}
 }	
 
 public void testAgainstRandomData() {
	 RRCDES des = (RRCDES) this.getClassifier();
	 assertTrue("Total Random data", RandomDataChecker.checkAgainstRandomData(des, -0.1, 0.1));
	 assertTrue("Test, Well-separated data", RandomDataChecker.checkAgainstWellSeparatedData(des, 0.9));
 }
 
 public void testClassifierSelection() {
	 RRCDES des = (RRCDES) this.getClassifier();
	 des.setClassifiers(new Classifier[] {new ZeroR(), new ZeroR(), new ZeroR(), new ZeroR(), new ZeroR(),new ZeroR(),new J48()});
	 assertTrue("Total Random data", RandomDataChecker.checkAgainstRandomData(des, -0.1, 0.1));
	 assertTrue("Test, Well-separated data, classifier selection", RandomDataChecker.checkAgainstWellSeparatedData(des, 0.9));
 }
 
 public void testClassifierSelection2() {
	 RRCDES des = (RRCDES) this.getClassifier();
	 RandomClassifierFull rndClassifier = new RandomClassifierFull();
	 rndClassifier.setRandomizeResponse(true);
	 int numClassifiers =11;
	 Classifier[] classifiers = new Classifier[numClassifiers];
	 try {
		 for(int i=0;i<numClassifiers-3;i++) {
			 rndClassifier.setSeed(i);
			 classifiers[i] = (Classifier) SerialCopier.makeCopy(rndClassifier);
		 }
		 classifiers[numClassifiers -1] = new IBk();
		 classifiers[numClassifiers -2] = new J48();
		 NaiveBayes nb = new NaiveBayes();
		 nb.setUseKernelEstimator(true);
		 classifiers[numClassifiers -3] = nb;
		 
		 des.setClassifiers(classifiers);
		 assertTrue("Total Random data", RandomDataChecker.checkAgainstRandomData(des, -0.2, 0.2));
		 assertTrue("Test, Well-separated data, classifier selection", RandomDataChecker.checkAgainstWellSeparatedData(des, 0.8));
	 }catch(Exception e) {
		 fail("Test Classifier selection, random classifiers, An exception: " + e.toString());
	 }
 }
 
 public void testClassifierSelectionFullRandom() {
	 RRCDES des = (RRCDES) this.getClassifier();
	 RandomClassifierFull rndClassifier = new RandomClassifierFull();
	 rndClassifier.setRandomizeResponse(true);
	 int numClassifiers =5;
	 Classifier[] classifiers = new Classifier[numClassifiers];
	 try {
		 for(int i=0;i<numClassifiers;i++) {
			 rndClassifier.setSeed(i);
			 classifiers[i] = (Classifier) SerialCopier.makeCopy(rndClassifier);
		 }
		 des.setClassifiers(classifiers);
		 assertTrue("Total Random data", RandomDataChecker.checkAgainstRandomData(des, -0.2, 0.2));
		 assertTrue("Test, Well-separated data, classifier selection", RandomDataChecker.checkAgainstWellSeparatedData(des,-0.2,0.2));
	 }catch(Exception e) {
		 fail("Test Classifier selection, random classifiers, An exception: " + e.toString());
	 }
 }
 
 
 
 public double[] generateRandomVectors(int numVals, int seed) {
	 double[] res = new double[numVals];
	 Random rnd = new Random(seed);
	 for(int i=0;i<res.length;i++) {
		 res[i] = rnd.nextDouble();
	 }
	 
	 return res;
 }

	

}
