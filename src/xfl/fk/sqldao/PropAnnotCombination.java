package xfl.fk.sqldao;

import xfl.fk.annotation.AnnotationCgf;

public class PropAnnotCombination {
	private AnnotationCgf ann=AnnotationCgf.getAnnCfg();
	
	/**
	  *  ���class��Ӧ���������
	 * @param clzz
	 * @return
	 */
	public String tableID(Class<?> clzz) {
		return ann.getId(clzz);
	}

}
