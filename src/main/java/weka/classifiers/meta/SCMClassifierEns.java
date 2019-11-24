/**
 * 
 */
package weka.classifiers.meta;

import java.util.Arrays;

import weka.classifiers.meta.RRCEns.RRCBasedWithValidation;
import weka.core.Instance;
import weka.tools.GlobalInfoHandler;

/**
 * The class implements the Soft Confusion Matrix classifier
 * 
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class SCMClassifierEns extends RRCBasedWithValidation implements GlobalInfoHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3470674240553011523L;
	
	protected static final double EPS=1E-6;

	
	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifier#distributionForInstance(weka.core.Instance)
	 */
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		int numClasses = this.validationSet.numClasses();
		
		double[] rrcResponse = this.rrcCalc.calculateRRC(this.getCommittee().distributionForInstanceCommittee(instance));
		
		double[] finalResponse = new double[numClasses];
		double[][] matrix = this.generateMatrix(instance);
		
		
		for(int c=0;c<numClasses;c++) {
			
			for(int d=0;d<numClasses;d++) {
				finalResponse[c]+= rrcResponse[d]*matrix[c][d];
			}
		}	
		return finalResponse;
	}
	
	
	protected double[][] generateMatrix(Instance instance) throws Exception {
		int numClasses = this.validationSet.numClasses();
		double[][] matrix = new double[numClasses][numClasses];
		
		double delta = EPS/(numClasses*numClasses);
		for(int c =0;c<numClasses;c++)
			Arrays.fill(matrix[c],delta);
				
		
		double[] neighCoeffs = this.neighCalc.getNeighbourhoodCoeffs(this.validationSet, instance);
		int numValInstances = this.validationSet.numInstances();
		int gtClass=0;
		delta = EPS/numClasses;
		double[] colSums = new double[numClasses];
		Arrays.fill(colSums, delta);
		double tmp=0;
		for(int i=0;i<numValInstances;i++) {
			gtClass = (int) this.validationSet.get(i).classValue();
			for(int c=0;c<numClasses;c++) {
				tmp = this.validationResponses.get(i)[c]*neighCoeffs[i];
				matrix[gtClass][c]+= tmp;
				colSums[c]+=tmp;
			}
			
		}
		//Normalize Matrix
		for(int c=0;c<numClasses;c++)
			for(int d=0;d<numClasses;d++) {
				matrix[d][c]/=colSums[c];
			}
				
			
		return matrix;
	}

	@Override
	public String globalInfo() {
		return "The SCM classifier";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		runClassifier(new SCMClassifierEns(), args);

	}
	

}
