/**
 * 
 */
package weka.classifiers.meta;

import weka.classifiers.Classifier;
import weka.classifiers.IteratedSingleClassifierEnhancer;
import weka.core.Instances;
import weka.tools.SerialCopier;

/**
 * @author pawel trajdos
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class RRCDES2 extends RRCDES {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9063842182861349657L;
	
	protected Classifier[] classifiersBackup;

	/**
	 * 
	 */
	public RRCDES2() {
		super();
		try {
			classifiersBackup = this.copyClassifierArray(m_Classifiers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@Override
	protected void trainBaseClassifiers(Instances data) throws Exception {
		if(this.finalLearning)
			for(int i=0;i<this.m_Classifiers.length;i++) {
				if(this.m_Classifiers[i] instanceof IteratedSingleClassifierEnhancer) {
					this.m_Classifiers[i] = (Classifier) SerialCopier.makeCopy( ((IteratedSingleClassifierEnhancer) this.m_Classifiers[i]).getClassifier() );
				}
			}
		
		
		super.trainBaseClassifiers(data);
	}



	@Override
	public void buildClassifier(Instances data) throws Exception {
		
		restoreBackupClassifiers();
			
		super.buildClassifier(data);
	}
	
	protected Classifier[] copyClassifierArray(Classifier[] classifiers) throws Exception {
		Classifier[] copied = new Classifier[classifiers.length];
		for(int i=0;i<copied.length;i++)
			copied[i] = (Classifier) SerialCopier.makeCopy(classifiers[i]);
		
		return copied;
	}
	
	protected void restoreBackupClassifiers() {
		if(this.classifiersBackup !=null)
			this.setClassifiers(this.classifiersBackup);
	}

	@Override
	public void setClassifiers(Classifier[] classifiers) {
		super.setClassifiers(classifiers);
		try {
			this.classifiersBackup = this.copyClassifierArray(m_Classifiers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
