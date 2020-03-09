package weka.classifiers.meta.RRC.calculators;

import java.util.Random;

import junit.framework.TestCase;
import weka.core.CheckOptionHandler;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.tools.Linspace;
import weka.tools.SerialCopier;
import weka.tools.tests.DistributionChecker;
import weka.tools.tests.WekaGOEChecker;

public abstract class RRCCalcTest extends TestCase {

	
	public abstract RRCCalc getRRCCalc();
	
	
	public void testSerialization() {
		try {
			RRCCalc calc = (RRCCalc) SerialCopier.makeCopy(this.getRRCCalc());
		} catch (Exception e) {
			fail("Serialization failed");
		}
	}
	
	public void testOptions() {
		RRCCalc calc = this.getRRCCalc();
		if(calc instanceof OptionHandler) {
			CheckOptionHandler check = new CheckOptionHandler();
			check.setOptionHandler((OptionHandler) calc);
			check.doTests();
		}
	
	}
	
	public void testGOE() {
		WekaGOEChecker check = new WekaGOEChecker();
		check.setObject(getRRCCalc());
		if(check.checkGlobalInfo())
			assertTrue("Global Info", check.checkCallGlobalInfo());
		
		if(check.checkToolTips())
			assertTrue("Tool Tips", check.checkToolTipsCall());
		
	}
	
	public void testOneProb() {
		RRCCalc calc = this.getRRCCalc();
		double[] vals = Linspace.genLinspace(0, 1, 100);
		for (double d : vals) {
			double output = calc.calculateRRCBinary(d);
			assertTrue("Check One prob", this.checkOneProb(output));
		}
	}
	
	public void testOneProbs() {
		RRCCalc calc = this.getRRCCalc();
		int[] lengths = {1,2,3,10,100};
		for (int i : lengths) {
			double[] inputs = this.generateInputData(i, i);
			double[] results = calc.calculateRRCBinaryML(inputs);
			assertTrue("Check One probs ML", this.checkOneProbs(results));
			
		}
	}
	
	public void testDistributions() {
		RRCCalc calc = this.getRRCCalc();
		int[] lengths = {1,2,3,5,10,20,100};
		for (int i : lengths) {
			double[] input = this.generateInputData(i, i);
			double[] outDist = calc.calculateRRC(input);
			assertTrue("Check Distributions", DistributionChecker.checkDistribution(outDist));
		}
	}
	
	
	
	public boolean checkOneProb(double prob) {
		if(prob<0 | prob >1)
			return false;
		
		return true;
	}
	
	public boolean checkOneProbs(double[] probs) {
		if(probs == null)
			return false;
		
		for (double d : probs) {
			if(! this.checkOneProb(d))
				return false;
		}
		
		return true;
	}
	
	public double[] generateInputData(int len, int seed) {
		double[] result = new double[len];
		
		Random rnd = new Random(seed);
		for(int i=0;i<result.length;i++) {
			result[i] = rnd.nextDouble();
		}
		Utils.normalize(result);
		
		return result;
	}

}
