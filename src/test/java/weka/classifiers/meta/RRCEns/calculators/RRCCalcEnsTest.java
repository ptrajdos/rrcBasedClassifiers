package weka.classifiers.meta.RRCEns.calculators;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;
import weka.core.OptionHandler;
import weka.core.OptionHandlersTest.OptionHandlerTest;
import weka.core.Utils;

public abstract class RRCCalcEnsTest extends TestCase {

	protected String className;
	
	public RRCCalcEnsTest(String name, String className) {
		super(name);
		this.className  = className;
	}
	
	public RRCCalcEnsTest(String name) {
		this(name,"");
	}
	

	
	public void testEstimRnd() {
		int numClassifiers=10;
		int numClasses =3;
		double[][] vals = this.generateDistribution(numClassifiers, numClasses, 0);
		RRCCalcEns ensEstim = (RRCCalcEns) this.getEstimator();
		
		try {
			double[] distr = ensEstim.calculateRRC(vals);
			this.checkDistr(distr);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("An Exception has been caught");
		}
	}
	
	public void testEstimDeterministic() {
		double[][] vals = this.generateDeterministicDistribution(10);
		RRCCalcEns ensEstim = (RRCCalcEns) this.getEstimator();
		try {
			double[] distr = ensEstim.calculateRRC(vals);
			this.checkDistr(distr);
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
	}


	public RRCCalcEns getEstimator() {
		RRCCalcEns estim = null;
		
		try {
			estim = (RRCCalcEns) Class.forName(this.className).newInstance();
			//Set default options if possible
			if(estim instanceof OptionHandler) {
				String[] opts = ((OptionHandler) estim).getOptions();
				((OptionHandler) estim).setOptions(opts);
			}
		} catch (Exception e) {
			e.printStackTrace();
			estim=null;
		}
		
		return estim;
		
	}
	public double[][] generateDistribution(int a, int b,int seed){
		double[][] distr = new double[a][b];
		
		Random rnd = new Random();
		rnd.setSeed(seed);
		
		for(int i=0;i<a;i++)
			for(int j=0;j<b;j++)
				distr[i][j] = rnd.nextDouble();
		
		
		return distr;
	}
	
	public double[][] generateDeterministicDistribution(int a){
		double[][] distr = new double[a][];
		for(int i=0; i<a; i++) {
			distr[i] = new double[] {0.5,0.3,0.2};
		}
		
		return distr;
	}
	
	public void checkDistr(double[] distr) {
		assertTrue("Not Null", distr!=null);
		for(int i=0;i<distr.length;i++) {
			assertTrue("Is finite", Double.isFinite(distr[i]));
			assertTrue("Bounded within [0;1]", distr[i]>=0.0 && distr[i]<=1.0);
		}
		assertTrue("Summing up to 1", Utils.eq(Utils.sum(distr), 1.0));
		System.out.println("Distr: " + Arrays.toString(distr) );
	}

}
