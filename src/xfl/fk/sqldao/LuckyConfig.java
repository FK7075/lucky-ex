package xfl.fk.sqldao;

import java.util.ResourceBundle;


/**
 * 读取配置文件的类(DL)
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
	 * 读取配置文件中的ID配置信息
	 * @param name 配置文件的 key
	 * @return 配置文件的 value
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
	 * 读取配置文件中的普通的配置信息
	 * @param name 配置文件的 key
	 * @return 配置文件的 value
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
	 * 读取配置文件中关于表字段长度的配置信息
	 * @param name 配置文件的 key
	 * @return 配置文件中的FieldLength的value
	 */
	public String fieldLength(String name) {
		try {
			return rb.getString(name);
		}catch(Exception e){
			return "35";
		}
	}

}
