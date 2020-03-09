/**
 * 
 */
package weka.classifiers.meta.RRC.calculators;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import net.sourceforge.jdistlib.Beta;
import weka.classifiers.meta.RRC.tools.BetaReverser;
import weka.core.Option;
import weka.core.Utils;
import weka.tools.Linspace;

/**
 * The class implements the RRC classifier usiung The Beta distribution.
 * @author pawel trajdos
 * @since 0.1.0
 * @version 1.0.0
 *
 */
public class RRCCalcBeta extends RRCCalcAbstract implements Serializable, RRCCalc{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1336409035171991035L;
	
	
	protected boolean fastComp = true;


	/**
	 * 
	 */
	public RRCCalcBeta() {
	}
	/**
	 * Calculates RRC probabilities for binary classifier
	 * @param oneProb - probability of class "1"
	 * @param intSeqLen - length of integration sequence
	 * @return RRC probability related to class "1"
	 */
	public double calculateRRCBinary(double oneProb,int intSeqLen){
		double[] oneProbs = new double[1];
		oneProbs[0]=oneProb;
		double[] RRCProbs = this.calculateRRCBinaryML(oneProbs, intSeqLen);
		return RRCProbs[0];
	}
	/**
	 * Calculates RRC probabilities for binary classifier
	 * @param oneProb - probability of class "1"
	 * @return RRC probability related to class "1"
	 */
	public double calculateRRCBinary(double oneProb){
		return this.calculateRRCBinary(oneProb, this.integrLen);
	}
	/**
	 * Calculates RRC probabilities for binary classifier
	 * @param oneProbs - probabilities of class "1"
	 * @param intSeqLen - length of integration sequence
	 * @return RRC probabilities related to class "1"
	 */
	@SuppressWarnings("static-method")
	public double[] calculateRRCBinaryML(double[] oneProbs,int intSeqLen){
		final double eps = RRCCalcBeta.EPS;
		double[] integrSeq = Linspace.genLinspaceSym(0.0, 1.0, intSeqLen+1);
		double[] cdfs = new double[intSeqLen];
		double[] pdfs = new double[intSeqLen+1];
		double[] ppdfs = new double[intSeqLen];
		double alpha;
		double beta;
		Beta pdf;
		//BetaCDFCalc betCdf;
		//BetaCDFCalc2 betCdf;
		double integrationRes;
		double integrationRes2;
		
		double[] rrcProbs = new double[oneProbs.length];
		
		for(int pro=0;pro<oneProbs.length;pro++){
			if(oneProbs[pro]>=1){
				rrcProbs[pro]=1.0;
				continue;
			}
			if(1-oneProbs[pro] <eps){
				rrcProbs[pro]=1.0;
				continue;
			}
			if(oneProbs[pro]<eps){
				rrcProbs[pro]=0;
				continue;
			}
			alpha = 2.0*oneProbs[pro];//A0
			beta = 2.0 - alpha;//B0
			
			//generate integration sequence
			pdf = new Beta(alpha, beta);//A0,B0
			//betCdf = new BetaCDFCalc(alpha, beta);
			//betCdf = new BetaCDFCalc2(alpha, beta);
			//betCdf.setEpsilon(1E-5);
			//betCdf.setMaxIter(50);
			
			
			ppdfs = pdf.cumulative(integrSeq);
			//ppdfs = betCdf.cumulative(integrSeq);
			cdfs = BetaReverser.reverseBetaCdf(ppdfs);
			
			
			integrationRes=0.0;
			integrationRes2=0.0;
			for(int i=0;i<intSeqLen;i++){
				pdfs[i] = (ppdfs[i+1] -ppdfs[i]);
				integrationRes+=pdfs[i]*cdfs[i];
				integrationRes2+= (cdfs[i+1] - cdfs[i])*ppdfs[i];
			}
			
			//System.out.println("IntRes:"+integrationRes);
			rrcProbs[pro]=integrationRes/(integrationRes + integrationRes2);
			
		}
		
		return rrcProbs;
	}
	/**
	 * Calculates RRC probabilities for binary classifier
	 * @param oneProbs - probabilities of class "1"
	 * @return RRC probabilities related to class "1"
	 */
	public double[] calculateRRCBinaryML(double[] oneProbs){
		return this.calculateRRCBinaryML(oneProbs, this.integrLen);
	}
	
	public double[] calculateRRC(double[] predictions){
		return this.calculateRRC(predictions,this.integrLen);
	}
	
	
	public double[] calculateRRC(double[] predictions,int intSeqLen){
		double[] finalPred = null;
		
		if(predictions.length == 2){
			if(this.fastComp)
				finalPred = calculateRRCMCBin(predictions, intSeqLen);
			else
				finalPred = this.calculateRRCMC(predictions, intSeqLen);
		}else{
			finalPred = this.calculateRRCMC(predictions, intSeqLen);
		}
		
		return finalPred;
				
	}
	protected double[] calculateRRCMCBin(double[] predictions,int intSeqLen){
		double[] oneProb = new double[1];
		oneProb[0] = predictions[0];
		double[] correctedPred = this.calculateRRCBinaryML(oneProb, intSeqLen);
		double[] finalPred = new double[]{correctedPred[0],1-correctedPred[0]}; 
		return finalPred;
	}
	
	protected double[] calculateRRCMC(double[] predictions,int intSeqLen){
		double[] integrationSequence = Linspace.genLinspaceSym(0.0, 1.0, intSeqLen+1);
		int nClass = predictions.length;
		double[] finalPredictions = new double[nClass];
		double alpha = 0;
		double beta = 0;
		Beta[] betaObjs= new Beta[nClass];
		double correctedPrediction=0.0;
		boolean breakInstanceComputation=false;
		final double eps = RRCCalcBeta.EPS;
			for(int cl=0;cl<nClass;cl++){
				correctedPrediction = predictions[cl];
				if(1.0-correctedPrediction<eps){
					finalPredictions[cl]=1.0;
					breakInstanceComputation = true;
				}
				if(correctedPrediction<eps){
					correctedPrediction=eps;
				}
				alpha = nClass*correctedPrediction;
				beta = nClass - alpha;
				betaObjs[cl] = new Beta(alpha,beta);
			}
			if(breakInstanceComputation){
				double finPredSum =0;
				for(int i=0;i<finalPredictions.length;i++){
					finPredSum+=finalPredictions[i];
				}
				if(! Utils.eq(0, finPredSum)){
					for(int i=0;i<finalPredictions.length;i++)
						finalPredictions[i]/=finPredSum;
				}
				
				return finalPredictions;
			}
			// integration code for each class
			double pdf=0;
			double integrationSum;
			double product;
			
			double[][] betaCumulatives = new double[nClass][];
			
			for(int i=0;i<nClass;i++){
				betaCumulatives[i] = betaObjs[i].cumulative(integrationSequence);
			}
			
			double h = 1.0/integrationSequence.length;
			double cumSum=0.0;
			double predSum=0;
			for(int cla=0;cla<nClass;cla++){
				integrationSum=0.0;
				for(int intS=0;intS <integrationSequence.length-1;intS++){
					pdf = (betaObjs[cla].cumulative(integrationSequence[intS+1])-betaObjs[cla].cumulative(integrationSequence[intS]))/h;
					product=pdf;
					for(int nCl=0;nCl<nClass;nCl++){
						if(cla==nCl)continue;
						product*=betaCumulatives[nCl][intS];
					}
					integrationSum+=product;
				}
				integrationSum/=integrationSequence.length;
				if(integrationSum>1)integrationSum=1.0;
				finalPredictions[cla]=integrationSum;
				predSum+=finalPredictions[cla];
				cumSum+=integrationSum;
			}
			for(int i=0;i<finalPredictions.length;i++){
				if(!Utils.eq(0, predSum))
					finalPredictions[i]/=predSum;
			}
			


return finalPredictions;
	}
	
	/**
	 * @return the fastComp
	 */
	public boolean isFastComp() {
		return this.fastComp;
	}
	/**
	 * @param fastComp the fastComp to set
	 */
	public void setFastComp(boolean fastComp) {
		this.fastComp = fastComp;
	}
	
	public String fastCompTipText() {
		return "Indicates whether the fast computing scheme is used";
	}
	
	@Override
	public Enumeration<Option> listOptions() {
		
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tIndicates whether fast computation scheme is used"+
		          "(default:" + false  + ").\n",
			      "FC", 0, "-FC"));
		 
		 newVector.addAll(Collections.list(super.listOptions()));
		
		    
		return super.listOptions();
		
	}
	@Override
	public void setOptions(String[] options) throws Exception {
		
		super.setOptions(options);
		
		this.setFastComp(Utils.getFlag("FC", options));
		
		
	}
	@Override
	public String[] getOptions() {
		
		Vector<String> options = new Vector<String>();
		
		if(this.isFastComp())
			options.add("-FC");
		
		  
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
	    
	}
	
	

}
