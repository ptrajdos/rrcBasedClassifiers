/**
 * 
 */
package weka.core.distances;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.UtilsPT;
import weka.core.neighboursearch.PerformanceStats;
import weka.tools.data.InstancesOperator;

/**
 * Distance encounters class frequencies
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class ClassFreqModifiedDistance implements DistanceFunction, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2561251287726202262L;
	
	protected DistanceFunction baseDsitance;
	protected double[] freqs;


	/**
	 * 
	 */
	public ClassFreqModifiedDistance() {
		this.baseDsitance = new EuclideanDistance();
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tDistance function to use"+
		          "(default: weka.core.EuclideanDistance.EuclideanDistance ).\n",
			      "BDIS", 1, "-BDIS"));
		    
		return newVector.elements();
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		this.setBaseDsitance((DistanceFunction) UtilsPT.parseObjectOptions(options, "BDIS", new EuclideanDistance(), DistanceFunction.class));

	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#getOptions()
	 */
	@Override
	public String[] getOptions() {
		
		Vector<String> options = new Vector<String>();

	    options.add("-BDIS");
	    options.add(UtilsPT.getClassAndOptions(getBaseDsitance()));
	    
	    return options.toArray(new String[0]);
	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#setInstances(weka.core.Instances)
	 */
	@Override
	public void setInstances(Instances insts) {
		this.baseDsitance.setInstances(insts);
		try {
			this.freqs = InstancesOperator.classFreq(insts);
		} catch (Exception e) {
			e.printStackTrace();
			int numClasses = insts.numClasses();
			this.freqs = new double[numClasses];
			Arrays.fill(freqs, 1.0);
		}
	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#getInstances()
	 */
	@Override
	public Instances getInstances() {
		return this.baseDsitance.getInstances();
	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#setAttributeIndices(java.lang.String)
	 */
	@Override
	public void setAttributeIndices(String value) {
		this.baseDsitance.setAttributeIndices(value);

	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#getAttributeIndices()
	 */
	@Override
	public String getAttributeIndices() {
		return this.baseDsitance.getAttributeIndices();
	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#setInvertSelection(boolean)
	 */
	@Override
	public void setInvertSelection(boolean value) {
		this.baseDsitance.setInvertSelection(value);
	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#getInvertSelection()
	 */
	@Override
	public boolean getInvertSelection() {
		return this.baseDsitance.getInvertSelection();
	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#distance(weka.core.Instance, weka.core.Instance)
	 */
	@Override
	public double distance(Instance first, Instance second) {
		double dist =this.baseDsitance.distance(first, second); 
		return this.getModifiedDistance(first, second, dist);
	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#distance(weka.core.Instance, weka.core.Instance, weka.core.neighboursearch.PerformanceStats)
	 */
	@Override
	public double distance(Instance first, Instance second, PerformanceStats stats) throws Exception {
		double dist = this.baseDsitance.distance(first, second, stats); 
		return this.getModifiedDistance(first, second, dist); 
	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#distance(weka.core.Instance, weka.core.Instance, double)
	 */
	@Override
	public double distance(Instance first, Instance second, double cutOffValue) {
		double dist = this.baseDsitance.distance(first, second, cutOffValue); 
		return this.getModifiedDistance(first, second, dist);  
	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#distance(weka.core.Instance, weka.core.Instance, double, weka.core.neighboursearch.PerformanceStats)
	 */
	@Override
	public double distance(Instance first, Instance second, double cutOffValue, PerformanceStats stats) {
		double dist =this.baseDsitance.distance(first, second, cutOffValue, stats); 
		return this.getModifiedDistance(first, second, dist);  
	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#postProcessDistances(double[])
	 */
	@Override
	public void postProcessDistances(double[] distances) {
		this.baseDsitance.postProcessDistances(distances);

	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#update(weka.core.Instance)
	 */
	@Override
	public void update(Instance ins) {
		this.baseDsitance.update(ins);

	}

	/* (non-Javadoc)
	 * @see weka.core.DistanceFunction#clean()
	 */
	@Override
	public void clean() {
		this.baseDsitance.clean();

	}

	/**
	 * @return the baseDsitance
	 */
	public DistanceFunction getBaseDsitance() {
		return this.baseDsitance;
	}

	/**
	 * @param baseDsitance the baseDsitance to set
	 */
	public void setBaseDsitance(DistanceFunction baseDsitance) {
		this.baseDsitance = baseDsitance;
	}
	
	protected enum Case{
		BothNoClass,
		BothWithClass,
		FirstWithClass,
		SecondWithClass;
	}
	
	protected Case caseFinder(Instance first, Instance second) {
		if( first.classIsMissing() & second.classIsMissing())
			return Case.BothNoClass;
		if(!first.classIsMissing() & !second.classIsMissing())
			return Case.BothWithClass;
		if(!first.classIsMissing() & second.classIsMissing())
			return Case.FirstWithClass;
		
		return Case.SecondWithClass;
		
		
	}
	
	protected double getModifiedDistance(Instance first, Instance second, double origDist) {
		
		Case c = this.caseFinder(first, second);
		switch (c) {
		case FirstWithClass:
			
			return origDist * this.freqs[(int) first.classValue()];
			
		case SecondWithClass:
			
			return origDist * this.freqs[(int) second.classValue()];

		default:
			return origDist;
		}
	}
	
	

}
