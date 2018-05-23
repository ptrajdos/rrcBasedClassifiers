/**
 * 
 */
package weka.classifiers.meta.RRC.tools;

/**
 * @author pawel
 *
 */
public class Linspace {

	/**
	 * 
	 */
	public Linspace() {
		// TODO Auto-generated constructor stub
	}
	
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
