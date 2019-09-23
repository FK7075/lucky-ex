package xfl.fk.sqldao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.utils.LuckyManager;
import xfl.fk.utils.LuckyUtils;

/**
 * Connection���ӳع���
 * @author fk-7075
 *
 */
public class DBConnectionPool {
	private List<Connection> pool;//���ӳ�
	private final int POOL_MIN=LuckyManager.getPropCfg().getPoolmin();//��С��������
	private final int POOL_MAX=LuckyManager.getPropCfg().getPoolmax();//�����������
	
	public DBConnectionPool() {
		initPool();
		System.out.println("xflfk:�ɹ����ӵ����ݿ� #"+LuckyUtils.getDatabaseName()+"#,���ӳس�ʼ���ɹ�����ǰ���й���"+POOL_MIN+"��Connection����");
	}
	
	/**
	 * ��ʼ�����ӳأ�������С�������������Ӷ���
	 */
	public void initPool() {
		if(pool==null)
			pool=new ArrayList<Connection>();
		while(pool.size()<this.POOL_MIN) {
			pool.add(JdbcUtils.createConnection());
		}
	}
	/**
	 * �������ӳ��е����һ�����󣬲������ӳ����Ƴ��������
	 * @return ����һ�����Ӷ���
	 */
	public synchronized Connection getConnection() {
		int last_index=pool.size()-1;
		Connection conn=pool.get(last_index);
		pool.remove(last_index);
		return conn;
	}
	/**
	 * �ر����ӣ������ӳ��е����Ӷ��󳬹����ֵʱ�������Ĺر�
	 * @param conn ���Ӷ���
	 */
	public synchronized void closeConection(Connection conn) {
		if(pool.size()>this.POOL_MAX) {
				try {
					if(conn!=null) {
						conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}else {
			pool.add(conn);
		}
			
	}

}
