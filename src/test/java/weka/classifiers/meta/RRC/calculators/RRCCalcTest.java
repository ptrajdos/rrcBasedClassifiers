package weka.classifiers.meta.RRC.calculators;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

import cern.colt.Arrays;
import weka.core.CheckOptionHandler;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.tools.Linspace;
import weka.tools.SerialCopier;
import weka.tools.tests.DistributionChecker;
import weka.tools.tests.WekaGOEChecker;

public abstract class RRCCalcTest  {

	
	public abstract RRCCalc getRRCCalc();
	
	@Test
	public void testSerialization() {
		try {
			RRCCalc calc = (RRCCalc) SerialCopier.makeCopy(this.getRRCCalc());
		} catch (Exception e) {
			fail("Serialization failed");
		}
	}
	
	@Test
	public void testOptions() {
		RRCCalc calc = this.getRRCCalc();
		if(calc instanceof OptionHandler) {
			CheckOptionHandler check = new CheckOptionHandler();
			check.setOptionHandler((OptionHandler) calc);
			check.doTests();
		}
	
	}
	
	@Test
	public void testGOE() {
		WekaGOEChecker check = new WekaGOEChecker();
		check.setObject(getRRCCalc());
		if(check.checkGlobalInfo())
			assertTrue("Global Info", check.checkCallGlobalInfo());
		
		if(check.checkToolTips())
			assertTrue("Tool Tips", check.checkToolTipsCall());
		
	}
	
	@Test
	public void testOneProb() {
		RRCCalc calc = this.getRRCCalc();
		double[] vals = Linspace.genLinspace(0, 1, 1000);
		for (double d : vals) {
			double output = calc.calculateRRCBinary(d);
			assertTrue("Is finite value:" + d +" ", Double.isFinite(output));
			assertTrue("Check One prob"+ d +" ", this.checkOneProb(output));
		}
	}
	
	@Test
	public void testOneProbExtremeVals() {
		RRCCalc calc = this.getRRCCalc();
		double[] vals = {0.0,Double.MIN_VALUE,1E-10,1E-6,1.0};
		for (double d : vals) {
			double output = calc.calculateRRCBinary(d);
			assertTrue("Is finite value" + d +" ", Double.isFinite(output));
			assertTrue("Check One prob"+ d +" ", this.checkOneProb(output));
		}
	}
	
	@Test
	public void testInvalidValues() {
		RRCCalc calc = this.getRRCCalc();
		double[] vals = {Double.NEGATIVE_INFINITY,-Double.MAX_VALUE,-1.0,-Double.MIN_VALUE,1.1, 1+Double.MIN_VALUE, Double.MAX_VALUE, Double.POSITIVE_INFINITY};
		for (double d : vals) {
			double output = calc.calculateRRCBinary(d);
			assertTrue("Is finite value"+ d +" ", Double.isFinite(output));
			assertTrue("Check One prob"+ d +" ", this.checkOneProb(output));
		}
	}
	
	@Test
	public void testNaNs() {
		RRCCalc calc = this.getRRCCalc();
		double[] vals = {Double.NaN};
		for (double d : vals) {
			double output = calc.calculateRRCBinary(d);
			assertTrue("Is finite value", Double.isNaN(output));
		}
	}
	
	
	@Test
	public void testOneProbs() {
		RRCCalc calc = this.getRRCCalc();
		int[] lengths = {1,2,3,10,100};
		for (int i : lengths) {
			double[] inputs = this.generateInputData(i, i);
			double[] results = calc.calculateRRCBinaryML(inputs);
			assertTrue("Check One probs ML: " + Arrays.toString(inputs), this.checkOneProbs(results));
			
		}
	}
	
	@Test
	public void testDistributions() {
		RRCCalc calc = this.getRRCCalc();
		int[] lengths = {1,2,3,5,10,20,100};
		for (int i : lengths) {
			double[] input = this.generateInputData(i, i);
			double[] outDist = calc.calculateRRC(input);
			assertTrue("Check Distributions: "+ Arrays.toString(input), DistributionChecker.checkDistribution(outDist));
		}
	}
	
	@Test
	public void testDegeneratedDistributions() {
		RRCCalc calc = this.getRRCCalc();
		double[][] distrs = {{0,1},{0,1},{0,0,0,0,1},{0,0},{0,0,0,0},{1,1}};
		for (double[] ds : distrs) {
			double[] outDist = calc.calculateRRC(ds);
			assertTrue("Check Distributions: " + Arrays.toString(ds), DistributionChecker.checkDistribution(outDist));
		}
		
	}
	
	@Test
	public void testUnnormalisedDistributions() {
		RRCCalc calc = this.getRRCCalc();
		int[] lengths = {1,2,3,5,10,20,100};
		for (int i : lengths) {
			double[] input = this.generateUnnormalisedData(i, i);
			double[] outDist = calc.calculateRRC(input);
			assertTrue("Check Distributions: " + Arrays.toString(input), DistributionChecker.checkDistribution(outDist));
		}
	}
	
	
	
	
	
	public boolean checkOneProb(double prob) {
		if(!Double.isFinite(prob))
			return false;
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
		double[] result = generateUnnormalisedData(len, seed);
		Utils.normalize(result);
		return result;
	}
	
	public double[] generateUnnormalisedData(int len, int seed) {
		double[] result = new double[len];
		
		Random rnd = new Random(seed);
		for(int i=0;i<result.length;i++) {
			result[i] = rnd.nextDouble();
		}
		return result;
	}

}
