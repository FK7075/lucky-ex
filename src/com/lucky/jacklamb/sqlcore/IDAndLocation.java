package com.lucky.jacklamb.sqlcore;

public class IDAndLocation {
	private Object id = null;
	private int location;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	/**
	 * �ҵ�ClassInfo������values�е�ID��ֵ��λ��
	 * @param info
	 *  ClassInfo����
	 */
	IDAndLocation(ClassInfo info) {
		for (int i = 0; i < info.getNames().size(); i++) {
			if (PojoManage.getIdString(info.getClzz()).equals(info.getNames().get(i))) {
				this.id = info.getValues().get(i);
				this.location = i;
			}
		}
		if (this.id == null) {
			throw new RuntimeException("û���ҵ�ID���ԣ��޷���ɸ��²�����");
		}
	}
}
