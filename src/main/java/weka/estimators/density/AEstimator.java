/**
 * 
 */
package weka.estimators.density;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getValues()
	 */
	@Override
	public double[] getValues() {
		int numSamples = this.samples.size();
		double[] values = new double[numSamples];
		for(int i=0;i<numSamples;i++)
			values[i] = this.samples.get(i);
		
		return values;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getWeights()
	 */
	@Override
	public double[] getWeights() {
		int numSamples = this.samples.size();
		double[] weights = new double[numSamples];
		Arrays.fill(weights, 1.0);
		return weights;
	}
	
	

}
