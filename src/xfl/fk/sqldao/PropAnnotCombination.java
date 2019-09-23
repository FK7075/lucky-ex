package xfl.fk.sqldao;

import xfl.fk.annotation.AnnotationCgf;

public class PropAnnotCombination {
	private AnnotationCgf ann=AnnotationCgf.getAnnCfg();
	
	/**
	  *  获得class对应表的主键名
	 * @param clzz
	 * @return
	 */
	public String tableID(Class<?> clzz) {
		return ann.getId(clzz);
	}

}
