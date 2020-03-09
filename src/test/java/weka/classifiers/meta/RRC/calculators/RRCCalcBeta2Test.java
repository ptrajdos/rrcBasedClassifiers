package weka.classifiers.meta.RRC.calculators;

public class RRCCalcBeta2Test extends RRCCalcBetaTest {

	@Override
	public RRCCalc getRRCCalc() {
		RRCCalcBeta bet = (RRCCalcBeta) super.getRRCCalc();
		bet.setFastComp(false);
		return bet;
	}

	

}
