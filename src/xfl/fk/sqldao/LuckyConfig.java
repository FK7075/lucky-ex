package xfl.fk.sqldao;

import java.util.ResourceBundle;


/**
 * ��ȡ�����ļ�����(DL)
 * 
 * @author fk-7075
 *
 */
public class LuckyConfig {

	private ResourceBundle rb = ResourceBundle.getBundle("lucky");
	private static LuckyConfig cfg = null;

	private LuckyConfig() {
	};

	public static LuckyConfig getConfig() {
		if (cfg == null)
			cfg = new LuckyConfig();
		return cfg;
	}

	/**
	 * ��ȡ�����ļ��е�ID������Ϣ
	 * @param name �����ļ��� key
	 * @return �����ļ��� value
	 */
	public String nameToValueId(String name) {
		try {
			String value = rb.getString(name + ".id");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return "err";
		}
	}

	/**
	 * ��ȡ�����ļ��е���ͨ��������Ϣ
	 * @param name �����ļ��� key
	 * @return �����ļ��� value
	 */
	public String nameToValue(String name) {
		try {
			String value = rb.getString(name);
			return value;
		} catch (Exception e) {
			return "err";
		}
	}
	/**
	 * ��ȡ�����ļ��й��ڱ��ֶγ��ȵ�������Ϣ
	 * @param name �����ļ��� key
	 * @return �����ļ��е�FieldLength��value
	 */
	public String fieldLength(String name) {
		try {
			return rb.getString(name);
		}catch(Exception e){
			return "35";
		}
	}

}
