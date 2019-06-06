/**
 * 
 */
package weka.classifiers.meta.RRC.tools;

/**
 * The class contains static methods that calculates linspaces (like in Matlab or R).
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class Linspace {
	
	/**
	 * Generates linspace
	 * @param from -- starting point in the sequence
	 * @param to -- ending point in the sequence
	 * @param length -- the length of the output sequence
	 * @return linspace
	 */
	public static double[] genLinspace(double from,double to,int length){
		double[] sequence = new double[length];
		double step=0;
		if(length>1){
			step= (to-from)/(length-1);
		}
		
		double current=0.0;
		for(int i=0;i<length;i++){
			sequence[i]=from+current;
			current+=step;
		}
		return sequence;
	}
	
	/**
	 * Generates symetric linspace -- values are symmetric around the central value.
	 * 
	 * @param from -- starting point in the sequence
	 * @param to -- ending point in the sequence
	 * @param length -- the length of the output sequence
	 * 
	 * @return
	 */
	public static double[] genLinspaceSym(double from,double to,int length){
		double[] sequence = new double[length];
		double step=0;
		if(length>1){
			step= (to-from)/(length-1);
		}
		
		double current=0.0;
		int fillSet = (int) Math.floor((length-1)/2);
		for(int i=0;i<=fillSet;i++){
			sequence[i]=from+current;
			sequence[length-1-i] = to -sequence[i];
			current+=step;
		}
		return sequence;
	}

}
