/**
 * 
 */
package weka.classifiers.meta.RRC.calculators;

import java.io.Serializable;

import weka.classifiers.meta.RRC.tools.Linspace;
import weka.core.Utils;

/**
 * RRC classifier using the Truncated Normal distribution.
 * 
 * @author pawel trajdos
 * @since 0.1.1
 * @version 0.1.1
 *
 */
public class RRCCalcTruncNormal implements RRCCalc, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3249080836097724381L;

	
	public static final int intLen = 51;
	
	protected int integrLen  = RRCCalcTruncNormal.intLen;
	
	public static final double EPS=1E-6;
	
	protected double sdPower=0.5;
	

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.calculators.RRCCalc#calculateRRCBinary(double)
	 */
	@Override
	public double calculateRRCBinary(double oneProb) {
		if(oneProb<EPS) {
			return 0;
		}
		if(1-oneProb<EPS) {
			return 1;
		}
		double sd = Math.pow(((oneProb*(1-oneProb))/(3.0)),this.sdPower);
		
		
		TruncatedNormal t1 = new TruncatedNormal(oneProb, sd, 0, 1);
		TruncatedNormal t2 = new TruncatedNormal(1-oneProb, sd, 0, 1);
		
		double[] integrSeq  = Linspace.genLinspace(0, 1, this.integrLen);
		double[] pdfs = t1.density(integrSeq, false);
		double[] cdfs = t2.cumulative(integrSeq);
		
		double[] pdfs2 = t2.density(integrSeq);
		double[]cdfs2 = t1.cumulative(integrSeq);
		double sum=0;
		double sum2=0;
		for(int i=1;i<integrSeq.length-1;i++) {
			sum+= pdfs[i]*cdfs[i];
			sum2+= pdfs2[i]*cdfs2[i];
		}
		sum+=(pdfs[0]*cdfs[0] + pdfs[integrSeq.length-1]*cdfs[integrSeq.length-1])/2;
		sum2+=(pdfs2[0]*cdfs2[0] + pdfs2[integrSeq.length-1]*cdfs2[integrSeq.length-1])/2; 
		
		double val = sum/(sum+sum2);
		
		return val;
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.calculators.RRCCalc#calculateRRCBinaryML(double[])
	 */
	@Override
	public double[] calculateRRCBinaryML(double[] oneProbs) {
		double[] integrSeq  = Linspace.genLinspace(0, 1, this.integrLen);
		double[] results = new double[oneProbs.length];
		double oneProb;
		for(int i=0;i<oneProbs.length;i++) {
			oneProb = oneProbs[i];
			if(oneProb<EPS) {
				results[i]=0;
				continue;
			}
			if(1-oneProb < EPS) {
				results[i]=1;
				continue;
			}
			double sd = Math.pow(((oneProb*(1-oneProb))/(3.0)),this.sdPower);
			
			TruncatedNormal t1 = new TruncatedNormal(oneProb, sd, 0, 1);
			TruncatedNormal t2 = new TruncatedNormal(1-oneProb, sd, 0, 1);
			
			double[] pdfs = t1.density(integrSeq, false);
			double[] cdfs = t2.cumulative(integrSeq);
			
			double[] pdfs2 = t2.density(integrSeq);
			double[]cdfs2 = t1.cumulative(integrSeq);
			double sum=0;
			double sum2=0;
			for(int j=1;j<integrSeq.length-1;j++) {
				sum+= pdfs[j]*cdfs[j];
				sum2+= pdfs2[j]*cdfs2[j];
			}
			sum+=(pdfs[0]*cdfs[0] + pdfs[integrSeq.length-1]*cdfs[integrSeq.length-1])/2;
			sum2+=(pdfs2[0]*cdfs2[0] + pdfs2[integrSeq.length-1]*cdfs2[integrSeq.length-1])/2; 
			results[i]=sum/(sum+sum2);
		}
		
		
		
		return results;
	}

	/* (non-Javadoc)
	 * @see weka.classifiers.meta.RRC.calculators.RRCCalc#calculateRRC(double[])
	 */
	@Override
	public double[] calculateRRC(double[] predictions) {
		
		/*Faster Way*/
		if(predictions.length==2) {
			double predO = this.calculateRRCBinary(predictions[0]);
			return new double[]{predO,1-predO};
		}
		
		return this.calculateRRCMC(predictions, integrLen);
	}

	
	protected double[] calculateRRCMC(double[] predictions,int intSeqLen){
		double[] integrationSequence = Linspace.genLinspaceSym(0.0, 1.0, intSeqLen+1);
		int nClass = predictions.length;
		double[] finalPredictions = new double[nClass];
		double sd=0;
		TruncatedNormal[] tNormObjs= new TruncatedNormal[nClass];
		double correctedPrediction=0.0;
		boolean breakInstanceComputation=false;
		final double eps = EPS;
		int oneIdx =0;
			for(int cl=0;cl<nClass;cl++){
				correctedPrediction = predictions[cl];
				if(1.0-correctedPrediction<eps){
					finalPredictions[cl]=1.0;
					breakInstanceComputation = true;
					oneIdx=cl;
					break;
					
				}
				
				
				sd = Math.pow( (correctedPrediction*(1-correctedPrediction)/(nClass+1)),this.sdPower);
				
				
				tNormObjs[cl] = new TruncatedNormal(correctedPrediction, sd, 0, 1);
				
			}
			if(breakInstanceComputation){
				
				for(int i=0;i<finalPredictions.length;i++){
					finalPredictions[i] = (i==oneIdx)? 1:0;
				}
				
				
				return finalPredictions;
			}
			// integration code for each class
			double pdf=0;
			double integrationSum;
			double product;
			
			double[][] tNormalCumulatives = new double[nClass][];
			
			for(int i=0;i<nClass;i++){
				tNormalCumulatives[i] = tNormObjs[i].cumulative(integrationSequence);
			}
			
			
			double predSum=0;
			for(int cla=0;cla<nClass;cla++){
				integrationSum=0.0;
				for(int intS=0;intS <integrationSequence.length;intS++){
					pdf = (tNormObjs[cla].density(integrationSequence[intS], false));
					product=pdf;
					for(int nCl=0;nCl<nClass;nCl++){
						if(cla==nCl)continue;
						product*=tNormalCumulatives[nCl][intS];
					}
					integrationSum+=product;
				}
				finalPredictions[cla]=integrationSum;
				predSum+=finalPredictions[cla];
				
			}
			if(!Utils.eq(0, predSum))
				for(int i=0;i<finalPredictions.length;i++){
						finalPredictions[i]/=predSum;
				}
			


return finalPredictions;
	}

	/**
	 * @return the sdPower
	 */
	public double getSdPower() {
		return this.sdPower;
	}

	/**
	 * @param sdPower the sdPower to set
	 */
	public void setSdPower(double sdPower) {
		this.sdPower = sdPower;
	}

	
	
}
