/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.AbstractMap.SimpleEntry;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.UtilsPT;

/**
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class NNTunedGaussianNeighbourhood extends DistanceBasedNeighbourhood {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2847569680939213606L;
	
	protected int neighbours=5;
	
	protected double threshold =0.3;

	/**
	 * 
	 */
	public NNTunedGaussianNeighbourhood() {
		super();
	}

	@Override
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
		double[] coeffs = this.calculateDistances(dataset, instance);
		List<SimpleEntry<Integer, Double>> coefList = new ArrayList<SimpleEntry<Integer, Double>>(coeffs.length);
		for(int i=0;i<coeffs.length;i++) {
			coefList.add(new SimpleEntry<Integer, Double>(i, coeffs[i]));
		}
		Collections.sort(coefList,new Comparator<SimpleEntry<Integer, Double>>() {

			@Override
			public int compare(SimpleEntry<Integer, Double> o1, SimpleEntry<Integer, Double> o2) {
				if(o1.getValue()>o2.getValue())return 1;
				if(o1.getValue()<o2.getValue())return -1;
				return 0;
			}
		});
		double cutoff = coefList.get(this.neighbours-1).getValue();
		double alpha = -Math.log(this.threshold)/(cutoff*cutoff);
		
		double[] newWeights = new double[coeffs.length];
		for(int i=0;i<newWeights.length;i++)
			newWeights[i] = Math.exp(-alpha*coeffs[i]*coeffs[i]);
		
		
		return newWeights;
	}
	
	/**
	 * @return the neighbours
	 */
	public int getNeighbours() {
		return this.neighbours;
	}

	/**
	 * @param neighbours the neighbours to set
	 */
	public void setNeighbours(int neighbours) {
		this.neighbours = neighbours;
	}
	
	public String neighboursTipText() {
		return "Number of the nearest neighbours to use";
	}
	
	
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\t The numer of nearest neighbours to be used"+
		          "(default:" + 3.0  + ").\n",
			      "NN", 1, "-NN"));
		 
		 
		 newVector.addElement(new Option(
			      "\t The threshold to be used"+
		          "(default:" + 0.3  + ").\n",
			      "TH", 1, "-TH"));
			 
		 newVector.addAll(Collections.list(super.listOptions()));
		    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		super.setOptions(options);
		
		this.setNeighbours(UtilsPT.parseIntegerOption(options, "NN", 3));
		this.setThreshold(UtilsPT.parseDoubleOption(options, "TH", 0.3));
		
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();

	    options.add("-NN");
	    options.add(""+this.getNeighbours());
	    
	    options.add("-TH");
	    options.add(""+this.getThreshold());
	    
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
	}

	/**
	 * @return the threshold
	 */
	public double getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	public String thresholdTipText() {
		return "Used threshold";
	}

}
