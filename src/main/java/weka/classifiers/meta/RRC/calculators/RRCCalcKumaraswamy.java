/**
 * 
 */
package weka.classifiers.meta.RRC.calculators;

import java.io.Serializable;


import cern.jet.stat.Gamma;
import weka.classifiers.meta.RRC.tools.KumaraswamyCDFCalFast;
import weka.classifiers.meta.RRC.tools.Linspace;
import weka.core.Utils;

/**
 * The class implements the RRC classifier using Kumaraswamy distribution. 
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class RRCCalcKumaraswamy implements RRCCalc, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6508607518098700281L;
	
	/**
	 * 
	 */
	
	public static final int intLen = 51;
	
	protected int integrLen  = RRCCalcKumaraswamy.intLen;
	
	public static final double EPS=1E-6;
	
	protected boolean fastComp = true;
	

	/**
	 * 
	 */
	public RRCCalcKumaraswamy() {
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
		final double eps = RRCCalcKumaraswamy.EPS;
		double[] integrSeq = Linspace.genLinspaceSym(0.0, 1.0, intSeqLen+1);
		double[] cdfs = new double[intSeqLen];
		double[] pdfs = new double[intSeqLen+1];
		double[] ppdfs = new double[intSeqLen];
		KumaraswamyCDFCalFast k1;
		KumaraswamyCDFCalFast k2;
		double integrationRes;
		double integrationRes2;
		
		double[] rrcProbs = new double[oneProbs.length];
		double[] paramPred1 = null;
		double[] paramPred2 = null;
		
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
				rrcProbs[pro]=eps;
				continue;
			}
			
			paramPred1 = this.findKumaraswamy4Mean(oneProbs[pro], 2);
			paramPred2 = this.findKumaraswamy4Mean(1-oneProbs[pro], 2);
			
			k1 = new KumaraswamyCDFCalFast(paramPred1[0], paramPred1[1]);
			k2 = new KumaraswamyCDFCalFast(paramPred2[0], paramPred2[1]);
			
			ppdfs = k1.cumulative(integrSeq);
			cdfs = k2.cumulative(integrSeq);
						
			integrationRes=0.0;
			integrationRes2=0.0;
			for(int i=0;i<intSeqLen;i++){
				pdfs[i] = (ppdfs[i+1] -ppdfs[i]);
				integrationRes+=pdfs[i]*cdfs[i];
				integrationRes2+= (cdfs[i+1] - cdfs[i])*ppdfs[i];
			}
		
			rrcProbs[pro]=integrationRes/(integrationRes + integrationRes2);
			
		}
		
		return rrcProbs;
	}
	protected double[] findKumaraswamy4Mean(double supportMean, double constSum){
		double[] result = new double[2];
		double betAlpha = constSum*supportMean;
		double del = 0.1;
		double begin,end;
		
		if(Utils.eq(constSum, 2.0)){
			begin = Math.max(EPS, betAlpha-0.1);
			end = Math.min(constSum-EPS,betAlpha+0.1);
		}else{
			begin = Math.max(EPS, betAlpha-del*constSum);
			end = Math.min(constSum-EPS,betAlpha+del*constSum);
		}
		
		
		
				
		
		double alpha = this.bisectionIterative(begin, end, supportMean, constSum);
		result[0]=alpha;
		result[1] = constSum - alpha;
		
		//System.out.println("Alpha: " + alpha+  "\tBalpha: " + betAlpha);
		
		return result;
	}
	
	protected double bisection(double begin, double end, double supportMean, double constSum){
		double mid = (begin+end)/2;
		
		double midVal = this.kumaraswamySingleMean(mid, constSum, supportMean);
		if(Math.abs(midVal)<EPS)
			return mid;
		
		double begVal = this.kumaraswamySingleMean(begin, constSum, supportMean);
		if(begVal*midVal <0)
			return this.bisection(begin, mid, supportMean, constSum);
		return  this.bisection(mid, end, supportMean, constSum);
	}
	
	protected double bisectionIterative(double begin, double end, double supportMean, double constSum){
		double result =0 ;
		double mid=0;
		double midVal =Double.MAX_VALUE;
		double begVal =0;
		double endVal =0;
		double ibegin=begin;
		double iend = end;
		boolean reCalcBeg = true;
		boolean reCalcEnd = true;
		boolean calcMid = true;
		int numIters = 100;
		double intervalWidth;
		double tolerance  =10*EPS;
		do{
			if(calcMid){
				mid = (ibegin+iend)/2.0;
			}
			intervalWidth = (iend - ibegin);
			midVal = this.kumaraswamySingleMean(mid, constSum, supportMean);
			if(Math.abs(midVal)<EPS || intervalWidth<tolerance){
				result = mid;
				break;
			}
			if(reCalcBeg){
				begVal= this.kumaraswamySingleMean(ibegin, constSum, supportMean);
			}
			if(reCalcEnd){
				endVal= this.kumaraswamySingleMean(iend, constSum, supportMean);
			}
			
			if(begVal*midVal<0){
				iend=mid;
				reCalcBeg=false;
				reCalcEnd=true;
				
				mid = mid - ((mid-ibegin)/(midVal - begVal))*midVal;
				calcMid=false;
				continue;
			}
			if(endVal*midVal<0){
				ibegin=mid;
				reCalcBeg=true;
				reCalcEnd=false;
				mid = iend - ((iend - mid)/(endVal - midVal))*endVal;
				calcMid=false;
				
				continue;
				
			}
			
			System.err.println("No ACC RES: ");
			result = mid;
			break;
			
		}while(--numIters>0);
		
		//System.out.println("STEPS: " + (100 - numIters));
		return result;
	}
	
	
	
	/**
	 * 
	 * @param a -- alpha parameter
	 * @param M -- sum of parameters  (similari way as in the original RRC)
	 * @return mean
	 */
	protected double kumaraswamySingleMean(double a, double M, double mean2Find){
		double b = M-a; //const sum of parameter
		double res;
		double f1 = Gamma.logGamma(1 + 1/a);
		double f2 = Gamma.logGamma(b);
		double f3 = Gamma.logGamma(1 + 1/a +b);
		
		res = b*Math.exp(f1-f3+f2)-mean2Find;
		return res;
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
		KumaraswamyCDFCalFast[] kumObjs= new KumaraswamyCDFCalFast[nClass];
		double correctedPrediction=0.0;
		boolean breakInstanceComputation=false;
		final double eps = RRCCalcKumaraswamy.EPS;
		double[] paramPred = null;
			for(int cl=0;cl<nClass;cl++){
				correctedPrediction = predictions[cl];
				if(1.0-correctedPrediction<eps){
					finalPredictions[cl]=1.0;
					breakInstanceComputation = true;
				}
				if(correctedPrediction<eps){
					correctedPrediction=eps;
				}
				
				paramPred = this.findKumaraswamy4Mean(correctedPrediction, nClass);
				kumObjs[cl] = new KumaraswamyCDFCalFast(paramPred[0], paramPred[1]);
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
				betaCumulatives[i] = kumObjs[i].cumulative(integrationSequence);
			}
			
			double h = 1.0/integrationSequence.length;
			double cumSum=0.0;
			double predSum=0;
			for(int cla=0;cla<nClass;cla++){
				integrationSum=0.0;
				for(int intS=0;intS <integrationSequence.length-1;intS++){
					pdf = (kumObjs[cla].cumulative(integrationSequence[intS+1])-kumObjs[cla].cumulative(integrationSequence[intS]))/h;
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
	 * @return the integrLen
	 */
	public int getIntegrLen() {
		return this.integrLen;
	}
	/**
	 * @param integrLen the integrLen to set
	 */
	public void setIntegrLen(int integrLen) {
		this.integrLen = integrLen;
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
	

}

