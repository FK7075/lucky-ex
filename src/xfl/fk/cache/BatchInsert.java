package xfl.fk.cache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import xfl.fk.annotation.Lucky;
import xfl.fk.sqldao.ClassInfoFactory;
import xfl.fk.utils.LuckyUtils;

public class BatchInsert {
	
	private String insertSql;
	
	private Object[] insertObject;

	public String getInsertSql() {
		return insertSql;
	}

	public Object[] getInsertObject() {
		return insertObject;
	}
	
	public <T> BatchInsert(List<T> list) {
		if(!list.isEmpty()) {
			Class<?> pojoClass=list.get(0).getClass();
			insertSql=createInsertSql(pojoClass,list.size());
			insertObject=createInsertObject(list);
		}
	}
	
	public static String createInsertSql(Class<?> clzz,int size) {
		Lucky lucy=clzz.getAnnotation(Lucky.class);
		Field[] fields=clzz.getDeclaredFields();
		String prefix="INSERT INTO "+LuckyUtils.getTableName(clzz);
		String suffix=" VALUES ";
		if(lucy.auto()) {
			List<Field> list=new ArrayList<>();
			String id=lucy.id();
			Stream.of(fields).filter(field->!id.equals(ClassInfoFactory.getTableField(field))
					&&field.getType().getClassLoader()==null
					&&!(field.getType()).isAssignableFrom(List.class)).forEach(list::add);
			String fk="";
			for(int i=0;i<list.size();i++) {
				if(i==0) {
					prefix+="("+ClassInfoFactory.getTableField(list.get(i))+",";
					fk+="(?,";
				}else if(i==list.size()-1) {
					prefix+=ClassInfoFactory.getTableField(list.get(i))+")";
					fk+="?)";
				}else {
					prefix+=ClassInfoFactory.getTableField(list.get(i))+",";
					fk+="?,";
				}
			}
			for(int j=0;j<size;j++) {
				if(j==size-1) {
					suffix+=fk;
				}else {
					suffix+=fk+",";
				}
			}
		}else {
			List<Field> list=new ArrayList<>();
			Stream.of(fields).filter(field->field.getType().getClassLoader()==null
					&&!(field.getType()).isAssignableFrom(List.class)).forEach(list::add);
			String fk="";
			for(int i=0;i<list.size();i++) {
				if(i==0) {
					prefix+="("+ClassInfoFactory.getTableField(list.get(i))+",";
					fk+="(?,";
				}else if(i==list.size()-1) {
					prefix+=ClassInfoFactory.getTableField(list.get(i))+")";
					fk+="?)";
				}else {
					prefix+=ClassInfoFactory.getTableField(list.get(i))+",";
					fk+="?,";
				}
			}
			for(int j=0;j<size;j++) {
				if(j==size-1) {
					suffix+=fk;
				}else {
					suffix+=fk+",";
				}
			}
		}
		return prefix+suffix;
	}
	
	private <T> Object[] createInsertObject(List<T> list) {
		List<Object> po=new ArrayList<>();
		for(T t:list) {
			Class<?> clzz=t.getClass();
			Lucky lucy=clzz.getAnnotation(Lucky.class);
			String id=lucy.id();
			Field[] fields=clzz.getDeclaredFields();
			if(lucy.auto()) {
				for(Field fie:fields) {
					if(fie.getType().getClassLoader()==null
							&&!(fie.getType()).isAssignableFrom(List.class)
							&&!id.equals(ClassInfoFactory.getTableField(fie))) {
						fie.setAccessible(true);
						try {
							Object object=fie.get(t);
							po.add(object);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}else {
				for(Field fie:fields) {
					if(fie.getType().getClassLoader()==null&&!(fie.getType()).isAssignableFrom(List.class)) {
						fie.setAccessible(true);
						try {
							Object object=fie.get(t);
							po.add(object);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return po.toArray();
	}
	
}
