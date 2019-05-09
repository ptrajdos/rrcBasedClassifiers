/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;

/**
 * @author pawel
 *
 */
public class NearestNeighbourNeighbourhood extends DistanceBasedNeighbourhood {
	
	protected int neighbours=3;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4656882914788648210L;

	/**
	 * 
	 */
	public NearestNeighbourNeighbourhood() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.neighbourhood.NeighbourhoodCalculator#getNeighbourhoodCoeffs(weka.core.Instances, weka.core.Instance)
	 */
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
		double[] newCoeffs  =new double[coeffs.length];
		for(int i=0;i<this.neighbours;i++) {
			newCoeffs[coefList.get(i).getKey()]=1.0;
		}
		
		return newCoeffs;
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
	
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\t The numer of nearest neighbours to be used"+
		          "(default:" + 3.0  + ").\n",
			      "NN", 1, "-NN"));
			 
		 newVector.addAll(Collections.list(super.listOptions()));
		    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		super.setOptions(options);
		
		String neighString = Utils.getOption("NN", options);
		if(neighString.length()!=0) {
			this.neighbours = Integer.parseInt(neighString);
		}else {
			this.neighbours=3;
		}
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();

	    options.add("-NN");
	    options.add(""+this.neighbours);
	    
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
	}
	

}