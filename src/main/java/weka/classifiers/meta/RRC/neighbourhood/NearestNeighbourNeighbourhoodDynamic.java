/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;

/**
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class NearestNeighbourNeighbourhoodDynamic implements NeighbourhoodCalculator, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4996777917478928858L;
	
	protected NearestNeighbourNeighbourhood nnNeighCalc = new NearestNeighbourNeighbourhood();



	@Override
	public double[] getNeighbourhoodCoeffs(Instances dataset, Instance instance) throws Exception {
		int nn = this.determineNNNumber(dataset, instance);
		this.nnNeighCalc.setNeighbours(nn);
		return this.nnNeighCalc.calculateDistances(dataset, instance);
	}
	
	protected int determineNNNumber(Instances dataset, Instance instance) {
		int numInstances = dataset.numInstances();
		int numClasses = dataset.numClasses();
		
		int neighbours = (int) Math.ceil(Math.sqrt(numInstances));
		if(neighbours%numClasses == 0)
			neighbours++;
		return neighbours;
	}

	/**
	 * @return the nnNeighCalc
	 */
	public NearestNeighbourNeighbourhood getNnNeighCalc() {
		return this.nnNeighCalc;
	}

	/**
	 * @param nnNeighCalc the nnNeighCalc to set
	 */
	public void setNnNeighCalc(NearestNeighbourNeighbourhood nnNeighCalc) {
		this.nnNeighCalc = nnNeighCalc;
	}
	
	public String nnNeighCalcTipText() {
		return "Nearest Neighbour Neighbourhood calculator to use";
	}

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\t The NN Neighbourhood calculator to use"+
		          "(default:" + "weka.classifiers.meta.RRC.neighbourhood.NearestNeighbourNeighbourhood.NearestNeighbourNeighbourhood()"  + ").\n",
			      "NNC", 1, "-NNC"));
			
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		this.setNnNeighCalc((NearestNeighbourNeighbourhood) UtilsPT.parseObjectOptions(options, "NNC", new NearestNeighbourNeighbourhood(), NearestNeighbourNeighbourhood.class));
	}

	@Override
	public String[] getOptions() {
		
		Vector<String> options = new Vector<String>();

	    options.add("-NNC");
	    options.add(UtilsPT.getClassAndOptions(this.getNnNeighCalc()));
	   
	    
	    return options.toArray(new String[0]);
	}
	
	

}
