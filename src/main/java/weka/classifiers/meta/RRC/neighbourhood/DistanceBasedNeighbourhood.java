/**
 * 
 */
package weka.classifiers.meta.RRC.neighbourhood;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Utils;

/**
 * @author pawel
 *
 */
public abstract class DistanceBasedNeighbourhood implements NeighbourhoodCalculator, Serializable, OptionHandler {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3298340988235027614L;
	
	protected DistanceFunction distFun = new EuclideanDistance() ;

	/**
	 * 
	 */
	public DistanceBasedNeighbourhood() {
		
	}


	/**
	 * @return the distFun
	 */
	public DistanceFunction getDistFun() {
		return this.distFun;
	}
	
	public String distFunTipText() {
		return "Distance function to use.";
	}

	/**
	 * @param distFun the distFun to set
	 */
	public void setDistFun(DistanceFunction distFun) {
		this.distFun = distFun;
	}
	
	protected double[] calculateDistances(Instances data, Instance instance) {
		int numInstances = data.numInstances();
		double[] distances = new double[numInstances];
		this.distFun.setInstances(data);
		for(int i=0;i<numInstances;i++) {
			distances[i] = this.distFun.distance(data.get(i), instance);
		}
		return distances;
	}
	
	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tThe distance function to be used"+
		          "(default:" + EuclideanDistance.class.toGenericString()  + ").\n",
			      "DI", 1, "-DI"));
		 
		return newVector.elements();
	}


	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		String distanceString = Utils.getOption("DI", options);
	    if(distanceString.length() != 0) {
	      String distanceClassSpec[] = Utils.splitOptions(distanceString);
	      if(distanceClassSpec.length == 0) { 
	        throw new Exception("Invalid  distance function" ); 
	      }
	      String className = distanceClassSpec[0];
	      distanceClassSpec[0] = "";

	      this.setDistFun( (DistanceFunction)
	                  Utils.forName( DistanceFunction.class, 
	                                 className, 
	                                 distanceClassSpec)
	                                        );
	    }
	    else 
	      this.setDistFun(new EuclideanDistance());
		
		
		
	}


	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#getOptions()
	 */
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
	    

	    options.add("-DI");
	    String rrcCalcOptions[] = new String[] {""};
	    if(this.distFun instanceof OptionHandler) {
	    	rrcCalcOptions = ((OptionHandler) this.distFun).getOptions();
	    }
	    options.add(this.distFun.getClass().getName()+" "+Utils.joinOptions(rrcCalcOptions)); 
	    
	    
	    return options.toArray(new String[0]);
	}
	

}
