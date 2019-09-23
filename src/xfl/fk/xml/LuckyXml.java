package xfl.fk.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LuckyXml {
	
	private String id;
	private String cpath;
	private String no_parameter;
//	private String parameter;
//	private List<String> paramTypes=new ArrayList<>();
//	private List<String> paramClass=new ArrayList<>();
//	private boolean typeisclass=false;
	private Map<String,String> nv=new HashMap<>();
	private Map<String,String> nr=new HashMap<>();
	private Map<String,List<Object>> n_array=new HashMap<>();
	private Map<String,List<String>> n_array_ref=new HashMap<>();
	private Map<String,List<Object>> n_list=new HashMap<>();
	private Map<String,List<String>> n_list_ref=new HashMap<>();
	private Map<String,Set<Object>> n_set=new HashMap<>();
	private Map<String,Set<String>> n_set_ref=new HashMap<>();
	private Map<String,Map<Object,Object>> n_map=new HashMap<>();
	private Map<String,Map<String,Object>> n_ref_map=new HashMap<>();
	private Map<String,Map<Object,String>> n_map_ref=new HashMap<>();
	private Map<String,Map<String,String>> n_ref_map_ref=new HashMap<>();
	private List<Object> array=new ArrayList<>();
	private List<String> array_ref=new ArrayList<>();
	private List<Object> list=new ArrayList<>();
	private List<String> list_ref=new ArrayList<>();
	private Set<Object> set=new HashSet<>();
	private Set<String> set_ref=new HashSet<>();
	private Map<Object,Object> map=new HashMap<>();
	private Map<String,Object> ref_map=new HashMap<>();
	private Map<Object,String> map_ref=new HashMap<>();
	private Map<String,String> ref_map_ref=new HashMap<>();
	

	public String getNo_parameter() {
		return no_parameter;
	}

	public void setNo_parameter(String no_parameter) {
		this.no_parameter = no_parameter;
	}

	public void addToArray(Object obj) {
		array.add(obj);
	}
	
	public void addToArrayRef(String obj) {
		array_ref.add(obj);
	}
	
	
	public Map<String, List<Object>> getN_array() {
		return n_array;
	}

	public void setN_array(Map<String, List<Object>> n_array) {
		this.n_array = n_array;
	}

	public Map<String, List<String>> getN_array_ref() {
		return n_array_ref;
	}

	public void setN_array_ref(Map<String, List<String>> n_array_ref) {
		this.n_array_ref = n_array_ref;
	}

	public void addToList(Object obj) {
		list.add(obj);
	}
	
	public void addToListRef(String obj) {
		list_ref.add(obj);
	}
	
	public void addToSet(Object obj) {
		set.add(obj);
	}
	
	public void addToSetRef(String obj) {
		set_ref.add(obj);
	}
	
	public void addToMap(Object entry_key,Object entry_value) {
		map.put(entry_key, entry_value);
	}
	
	public void addToMapRef(Object entry_key,String entry_value) {
		map_ref.put(entry_key, entry_value);
	}
	
	public void addToRefMap(String entry_key,Object entry_value) {
		ref_map.put(entry_key, entry_value);
	}
	
	public void addToRefMapRef(String entry_key,String entry_value) {
		ref_map_ref.put(entry_key, entry_value);
	}
	public Map<String, List<Object>> getN_list() {
		return n_list;
	}
	public void setN_list(Map<String, List<Object>> n_list) {
		this.n_list = n_list;
	}

	public Map<String, Set<Object>> getN_set() {
		return n_set;
	}
	public void setN_set(Map<String, Set<Object>> n_set) {
		this.n_set = n_set;
	}
	public Map<String, Map<Object, Object>> getN_map() {
		return n_map;
	}
	public void setN_map(Map<String, Map<Object, Object>> n_map) {
		this.n_map = n_map;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCpath() {
		return cpath;
	}
	public void setCpath(String cpath) {
		this.cpath = cpath;
	}
	public Map<String, String> getNv() {
		return nv;
	}
	public void setNv(Map<String, String> nv) {
		this.nv = nv;
	}
	public Map<String, String> getNr() {
		return nr;
	}
	public void setNr(Map<String, String> nr) {
		this.nr = nr;
	}


	public Map<String, List<String>> getN_list_ref() {
		return n_list_ref;
	}

	public void setN_list_ref(Map<String, List<String>> n_list_ref) {
		this.n_list_ref = n_list_ref;
	}

	public Map<String, Set<String>> getN_set_ref() {
		return n_set_ref;
	}

	public void setN_set_ref(Map<String, Set<String>> n_set_ref) {
		this.n_set_ref = n_set_ref;
	}

	public Map<String, Map<String, Object>> getN_ref_map() {
		return n_ref_map;
	}

	public void setN_ref_map(Map<String, Map<String, Object>> n_ref_map) {
		this.n_ref_map = n_ref_map;
	}

	public Map<String, Map<Object, String>> getN_map_ref() {
		return n_map_ref;
	}

	public void setN_map_ref(Map<String, Map<Object, String>> n_map_ref) {
		this.n_map_ref = n_map_ref;
	}

	public Map<String, Map<String, String>> getN_ref_map_ref() {
		return n_ref_map_ref;
	}

	public void setN_ref_map_ref(Map<String, Map<String, String>> n_ref_map_ref) {
		this.n_ref_map_ref = n_ref_map_ref;
	}

	public void push(String key) {
		if(!array.isEmpty())
			n_array.put(key, array);
		if(!array_ref.isEmpty())
			n_array_ref.put(key, array_ref);
		if(!list.isEmpty())
			n_list.put(key, list);
		if(!list_ref.isEmpty())
			n_list_ref.put(key, list_ref);
		if(!set.isEmpty())
			n_set.put(key, set);
		if(!set_ref.isEmpty())
			n_set_ref.put(key, set_ref);
		if(!map.isEmpty())
			n_map.put(key, map);
		if(!map_ref.isEmpty())
			n_map_ref.put(key, map_ref);
		if(!ref_map.isEmpty())
			n_ref_map.put(key, ref_map);
		if(!ref_map_ref.isEmpty())
			n_ref_map_ref.put(key, ref_map_ref);
		init();
	}
	private void init() {
		array=new ArrayList<>();
		array_ref=new ArrayList<>();
		list=new ArrayList<>();
		list_ref=new ArrayList<>();
		set=new HashSet<>();
		set_ref=new HashSet<>();
		map=new HashMap<>();
		ref_map=new HashMap<>();
		map_ref=new HashMap<>();
		ref_map_ref=new HashMap<>();
	}
}
