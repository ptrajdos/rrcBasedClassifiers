package weka.classifiers.meta.RRC.neighbourhood;
/**
 * 
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class DummyNeighbourhoodTest extends NeighbourhoodCalculatorTest {

	
	@Override
	public NeighbourhoodCalculator getNeighbourhoodCalculator() {
		return new DummyNeighbourhood();
	}

}
