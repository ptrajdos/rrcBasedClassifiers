/**
 * 
 */
package weka.estimators.density;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import weka.estimators.density.DensityEstimator;

/**
 * @author pawel
 *
 */
public abstract class AEstimator implements DensityEstimator, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 867056555449424387L;
	
	protected List<Double> samples;
	protected boolean isInitialised = false;

	/**
	 * 
	 */
	public AEstimator() {
		this.samples = new LinkedList<Double>();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.IncrementalEstimator#addValue(double, double)
	 */
	@Override
	public void addValue(double data, double weight) {
		this.samples.add(data);//weights are ignored
		this.isInitialised = false;
	}


	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#addValues(double[], double[])
	 */
	@Override
	public void addValues(double[] data, double[] weight) {
		for(int i=0;i<data.length;i++)
			this.addValue(data[i], weight[i]);
	}
	
	protected double[] listToArray() {
		double[] array = new double[this.samples.size()];
		for(int i=0;i<array.length;i++)
			array[i] = this.samples.get(i).doubleValue();
		
		return array;
	}

}
