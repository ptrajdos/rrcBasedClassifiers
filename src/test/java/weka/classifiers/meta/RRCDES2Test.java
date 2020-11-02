package weka.classifiers.meta;

import weka.classifiers.Classifier;
import weka.classifiers.SingleClassifierEnhancer;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.RRC.neighbourhood.GaussianNeighbourhood;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.tools.data.RandomDataGenerator;

public class RRCDES2Test extends RRCDESTest {

	public RRCDES2Test(String name) {
		super(name);
	}

	@Override
	public Classifier getClassifier() {
		RRCDES2 des = new RRCDES2();
		des.setNumFolds(3);
		Bagging bag = new Bagging();
		bag.setNumIterations(3);
		bag.setClassifier(new J48());
		Bagging bag2 = new Bagging();
		bag2.setNumIterations(3);
		bag2.setClassifier(new NaiveBayes());
		Bagging bag3 = new Bagging();
		bag3.setNumIterations(3);
		bag3.setClassifier(new IBk());
		
		des.setClassifiers(new Classifier[] { bag,bag2,bag3,new J48(), new NaiveBayes() });
		des.setSeed(0);
		
		GaussianNeighbourhood gaussNeigh = new GaussianNeighbourhood();
		gaussNeigh.setAlpha(10.0);
		des.setNeighCalc(gaussNeigh);
		return des;
	}
	
	public void testBackupClassifiers() {
		RRCDES2 des = new RRCDES2();
		des.setNumFolds(3);
		Bagging bag = new Bagging();
		bag.setNumIterations(3);
		bag.setClassifier(new J48());
		Bagging bag2 = new Bagging();
		bag2.setNumIterations(3);
		bag2.setClassifier(new NaiveBayes());
		Bagging bag3 = new Bagging();
		bag3.setClassifier(new IBk());
		
		des.setClassifiers(new Classifier[] { bag,bag2,bag3 });
		des.setSeed(0);
		
		assertTrue("Not Null backup at the beginning", des.classifiersBackup != null);
		assertTrue("Check SingleClassifierEnhancer", this.checkEnchanced(des.getClassifiers()));
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		try {
			des.buildClassifier(data);
			assertFalse("Check SingleClassifierEnhancer afet built", this.checkEnchanced(des.getClassifiers()));
			
			
			des.restoreBackupClassifiers();
			assertTrue("Check SingleClassifierEnhancer after restore", this.checkEnchanced(des.getClassifiers()));
			assertTrue("Not Null backup after restore", des.classifiersBackup != null);
		} catch (Exception e) {
			fail("BackupClassifiers, Exception has been catch" + e.toString());
		}
		
	}
	
	public boolean checkEnchanced(Classifier[] classifiers) {
		for(int i=0;i<classifiers.length;i++) {
			if(!(classifiers[i] instanceof SingleClassifierEnhancer))
				return false;
		}
		
		return true;
	}
	


}
