/**
 * 
 */
package weka.classifiers.meta;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.tools.GlobalInfoHandler;
import weka.tools.data.InstancesOperator;

/**
 * @author Pawel Trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class MetaBayesClassifier extends RRCBasedWithValidation implements GlobalInfoHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8867299051220506468L;
	
	protected double[] priorProbs;
	
	public static final double EPS = 1E-6;

	/**
	 * 
	 */
	public MetaBayesClassifier() {
	}

	/**
	 * @param baseClassifier
	 */
	public MetaBayesClassifier(Classifier baseClassifier) {
		super(baseClassifier);
	}
	
	
	

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRCBasedWithValidation#buildClassifier(weka.core.Instances)
	 */
	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		super.buildClassifier(arg0);
		int numTrainInstances = arg0.numInstances();
		
		int[] counts =  InstancesOperator.objPerClass(arg0);
		this.priorProbs  =new double[counts.length];
		
		for(int c=0;c<counts.length;c++)
			this.priorProbs[c] = (double)counts[c]/numTrainInstances;
			
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.AbstractClassifier#distributionForInstance(weka.core.Instance)
	 */
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		int numClasses = this.validationSet.numClasses();
		double[] distribution = new double[numClasses];
		double[] neighCoeffs =this.neighCalc.getNeighbourhoodCoeffs(validationSet, instance);
		double classifierPrediction = this.m_Classifier.classifyInstance(instance);
		
		int classIdx = this.validationSet.classIndex();
		double neigSum=0;
		int valClass;
		
		double[] RRCProbs = new double[numClasses];
		for(int i=0;i<neighCoeffs.length;i++) {
			neigSum += neighCoeffs[i];
			valClass = (int) this.validationSet.get(i).classValue();
			RRCProbs[valClass]+= this.validationResponses.get(i)[(int)classifierPrediction] * neighCoeffs[i];
		}
		
		if(Utils.eq(neigSum, 0)) {
			return this.m_Classifier.distributionForInstance(instance);
		}
		
		double normalizer =EPS ;
		double delta = EPS/numClasses;
		for(int c =0;c<RRCProbs.length;c++) {
			RRCProbs[c]/=neigSum;
			distribution[c] = this.priorProbs[c]*RRCProbs[c];
			normalizer+=distribution[c];
		}
		
		for(int c=0;c<numClasses;c++) {
			distribution[c]+=delta;
			distribution[c]/=normalizer;
		}
		
		return distribution;
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		runClassifier(new MetaBayesClassifier(), args);

	}

	@Override
	public String globalInfo() {
		
		return "The class Implements Meta Bayes Classifier";
	}

}
