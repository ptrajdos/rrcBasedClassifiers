/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.util.Random;

import junit.framework.TestCase;
import weka.core.CheckOptionHandler;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.tools.SerialCopier;
import weka.tools.data.RandomDataGenerator;
import weka.tools.tests.WekaGOEChecker;

/**
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public abstract class NeighbourhoodCalculatorTest extends TestCase {

	/**
	 * 
	 */
	public NeighbourhoodCalculatorTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 */
	public NeighbourhoodCalculatorTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public abstract NeighbourhoodCalculator getNeighbourhoodCalculator();
	
	public void testSerialization() {
		try {
			NeighbourhoodCalculator copy = (NeighbourhoodCalculator) SerialCopier.makeCopy(this.getNeighbourhoodCalculator());
		} catch (Exception e) {
			fail("Serialization faiure: " + e.toString());
		}
	}
	
	public void testOptions() {
		NeighbourhoodCalculator neighCalc = this.getNeighbourhoodCalculator();
		if(neighCalc instanceof OptionHandler) {
			CheckOptionHandler optCheck = new CheckOptionHandler();
			optCheck.setOptionHandler((OptionHandler) neighCalc);
			optCheck.doTests();
		}
		
	}
	
	public void testNeighbourhoodCoefficients() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNominalAttributes(0);
		
		
		int numReps =20;
		
		Random rnd = new Random(0);
		NeighbourhoodCalculator calc = this.getNeighbourhoodCalculator();
		int[] possibleSetSizes= {100,101,111};
		try {
		for(int i=0;i<numReps;i++) {
			gen.setNumObjects(possibleSetSizes[i%possibleSetSizes.length]);
			Instances data = gen.generateData();
			int numInstances = data.numInstances();
			Instance testInstance = data.get(rnd.nextInt(numInstances));
			double[] neighs = calc.getNeighbourhoodCoeffs(data, testInstance);
			assertTrue("Neigh Coeffs check", this.checkCoeffs(neighs));
		}
		}catch(Exception e) {
			fail("Neighbourhood coefficients checking. An Exception has been caught: " + e.toString());
		}
	}
	
	public boolean checkCoeffs(double[] coeffs) {
		if(coeffs == null)
			return false;
		for (double d : coeffs) {
			if(Double.isInfinite(d))
				return false;
			if(d <0 )
				return false;
		}
		
		return true;
	}
	
	public void testTipTexts() {
		WekaGOEChecker goe = new WekaGOEChecker();
		goe.setObject(this.getNeighbourhoodCalculator());
		assertTrue("Call Tip Texts", goe.checkToolTipsCall());
	}
	
	public void testNoInstances() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNominalAttributes(0);
		
		Instances initData = gen.generateData();
		Instance testInstance = initData.get(0);
		
		Instances emptyInstanceSet = new Instances(initData,0);
		
		NeighbourhoodCalculator neiCalc = this.getNeighbourhoodCalculator();
		
		try {
			double[] coeffs = neiCalc.getNeighbourhoodCoeffs(emptyInstanceSet, testInstance);
			assertTrue("Neigh Coeffs check", this.checkCoeffs(coeffs));
		} catch (Exception e) {
			fail("Neighbourhood coefficients checking. An Exception has been caught: " + e.toString());
		}
	}
		
	

}
