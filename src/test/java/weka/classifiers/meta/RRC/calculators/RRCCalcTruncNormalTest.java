package weka.classifiers.meta.RRC.calculators;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class RRCCalcTruncNormalTest extends RRCCalcTest {
	
	protected double sdPow=1.0;
	
	@Parameters
	 public static Collection<Object[]> setParameters() {
	        Collection<Object[]> params = new ArrayList<Object[]>();
	        // load the external params here
	        // this is an example
	        params.add(new Object[] {1E-6});
	        params.add(new Object[] {0.01});
	        params.add(new Object[] {0.1});
	        params.add(new Object[] {0.5});
	        params.add(new Object[] {0.6});
	        params.add(new Object[] {0.7});
	        params.add(new Object[] {0.8});
	        params.add(new Object[] {0.9});
	        params.add(new Object[] {0.99});
	        params.add(new Object[] {1.0});
	        params.add(new Object[] {1.5});
	        params.add(new Object[] {2.0});

	        return params;
	 }
	 
	 public RRCCalcTruncNormalTest(double sdPow) {
		this.sdPow = sdPow;
	}
	 
	

	

	@Override
	public RRCCalc getRRCCalc() {
		RRCCalcTruncNormal tNormal =new RRCCalcTruncNormal();
		tNormal.setSdPower(sdPow);
		return tNormal;
	}

}
