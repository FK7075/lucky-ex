package xfl.fk.join;

import java.util.List;

public interface PageLimit<T> {
	
	/**
	 * �õ���ǰҳ����������
	 * @param objs ִ�з�������Ҫ�Ĳ����б�ʹ�õ�ǰҳ��currPage����limit��index������
	 * @return
	 */
	List<T> limit(int currPage,int size);

}
