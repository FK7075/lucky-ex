package com.lucky.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.lucky.utils.LuckyUtils;
import com.lucky.utils.ProperConfig;

public class LuckyDomXml {

	private static Document getDoc() {
		URL resource = LuckyDomXml.class.getClassLoader().getResource("lucky.xml");
		if(resource==null)
			return null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder;
			documentBuilder = dbf.newDocumentBuilder();
			InputStream is = LuckyDomXml.class.getClassLoader().getResourceAsStream("lucky.xml");
			return documentBuilder.parse(is);
		} catch (ParserConfigurationException e) {
			System.err.println("JackLabm:)-> 无法创建DocumentBuilder对象.....");
		} catch (SAXException e) {
			System.err.println("JackLabm:)-> 无法加载xml文件......");
		} catch (IOException e) {
			System.err.println("JackLabm:)-> 在classpath下找不到lucky.xml......");
		}
		return null;
	}

	public static List<LuckyXml> getLuckyBeans() {
		List<LuckyXml> list = new ArrayList<>();
		Document doc = getDoc();
		if (doc != null) {
			NodeList beans = doc.getElementsByTagName("bean");
			for (int i = 0; i < beans.getLength(); i++) {
				LuckyXml bean = new LuckyXml();
				bean.setId(beans.item(i).getAttributes().getNamedItem("id").getNodeValue());
				bean.setCpath(beans.item(i).getAttributes().getNamedItem("class").getNodeValue());
				Node nop = beans.item(i).getAttributes().getNamedItem("no-param-method");
				// Node pr=beans.item(i).getAttributes().getNamedItem("param-method");
				// Node ptype=beans.item(i).getAttributes().getNamedItem("types");
				// Node params=beans.item(i).getAttributes().getNamedItem("params-class");
				// Node isclass=beans.item(i).getAttributes().getNamedItem("isclass");
				if (nop != null)
					bean.setNo_parameter(nop.getNodeValue());
				NodeList propertys = beans.item(i).getChildNodes();
				Map<String, String> nv = new HashMap<>();
				Map<String, String> nr = new HashMap<>();
				for (int j = 0; j < propertys.getLength(); j++) {
					Node node = propertys.item(j);
					if (node.getNodeName() == "property") {
						String fieldName = node.getAttributes().getNamedItem("name").getNodeValue();
						Node ref = node.getAttributes().getNamedItem("ref");
						Node val = node.getAttributes().getNamedItem("value");
						if (ref != null) {
							nr.put(fieldName, ref.getNodeValue());
						} else if (val != null) {
							nv.put(fieldName, val.getNodeValue());
						} else if (val == null && ref == null) {
							NodeList nolsma = node.getChildNodes();
							for (int fk = 0; fk < nolsma.getLength(); fk++) {
								Node nde = nolsma.item(fk);
								String nodeName = nde.getNodeName();
								if ("array".equals(nodeName)) {
									NodeList arrays = nde.getChildNodes();
									for (int p = 0; p < arrays.getLength(); p++) {
										Node value = arrays.item(p);
										String noName = value.getNodeName();
										if ("value".equals(noName)) {
											String type = nde.getAttributes().getNamedItem("type").getNodeValue();
											bean.addToArray(
													LuckyUtils.typeCast(value.getFirstChild().getNodeValue(), type));
										} else if ("value-ref".equals(noName)) {
											bean.addToArrayRef(value.getFirstChild().getNodeValue());
										}
									}
								} else if ("list".equals(nodeName)) {
									NodeList arrays = nde.getChildNodes();
									for (int p = 0; p < arrays.getLength(); p++) {
										Node value = arrays.item(p);
										String noName = value.getNodeName();
										if ("value".equals(noName)) {
											String type = nde.getAttributes().getNamedItem("type").getNodeValue();
											bean.addToList(
													LuckyUtils.typeCast(value.getFirstChild().getNodeValue(), type));
										} else if ("value-ref".equals(noName)) {
											bean.addToListRef(value.getFirstChild().getNodeValue());
										}
									}

								} else if ("set".equals(nodeName)) {
									NodeList arrays = nde.getChildNodes();
									for (int p = 0; p < arrays.getLength(); p++) {
										Node value = arrays.item(p);
										String noName = value.getNodeName();
										if ("value".equals(noName)) {
											String type = nde.getAttributes().getNamedItem("type").getNodeValue();
											bean.addToSet(
													LuckyUtils.typeCast(value.getFirstChild().getNodeValue(), type));
										} else if ("value-ref".equals(noName)) {
											bean.addToSetRef(value.getFirstChild().getNodeValue());
										}
									}

								} else if ("map".equals(nodeName)) {
									NodeList arrays = nde.getChildNodes();
									for (int p = 0; p < arrays.getLength(); p++) {
										Node value = arrays.item(p);
										String noName = value.getNodeName();
										if ("entry".equals(noName)) {
											Node k = value.getAttributes().getNamedItem("key");
											Node v = value.getAttributes().getNamedItem("value");
											Node k_f = value.getAttributes().getNamedItem("key-ref");
											Node v_f = value.getAttributes().getNamedItem("value-ref");
											if (k != null && v != null && k_f == null && v_f == null) {// k-v
												String key_type = nde.getAttributes().getNamedItem("key-type")
														.getNodeValue();
												String value_type = nde.getAttributes().getNamedItem("value-type")
														.getNodeValue();
												bean.addToMap(LuckyUtils.typeCast(k.getNodeValue(), key_type),
														LuckyUtils.typeCast(v.getNodeValue(), value_type));
											}
											if (k != null && v == null && k_f == null && v_f != null) {// k-v_f
												String key_type = nde.getAttributes().getNamedItem("key-type")
														.getNodeValue();
												bean.addToMapRef(LuckyUtils.typeCast(k.getNodeValue(), key_type),
														v_f.getNodeValue());

											}
											if (k == null && v != null && k_f != null && v_f == null) {// k_f-v
												String value_type = nde.getAttributes().getNamedItem("value-type")
														.getNodeValue();
												bean.addToRefMap(k_f.getNodeValue(),
														LuckyUtils.typeCast(v.getNodeValue(), value_type));

											}
											if (k == null && v == null && k_f != null && v_f != null) {// k_f-v_f
												bean.addToRefMapRef(k_f.getNodeValue(), v_f.getNodeValue());
											}
										}
									}

								} else {
									continue;
								}
							}
							bean.push(fieldName);
						}
						bean.setNv(nv);
						bean.setNr(nr);
					}
				}
				list.add(bean);
			}
		}
		return list;
	}

	public static MvcXmlModel getLuckyMappings() {
		MvcXmlModel mvcxml = new MvcXmlModel();
		List<List<LuckyMapping>> list = new ArrayList<>();
		Document doc = getDoc();
		if (doc != null) {
			NodeList ismvc = doc.getElementsByTagName("mvc");
			if (ismvc != null && ismvc.getLength() != 0) {
				Node mvc = doc.getElementsByTagName("mvc").item(0);
				NodeList mvc_child = mvc.getChildNodes();
				for (int i = 0; i < mvc_child.getLength(); i++) {
					Node mappings = mvc_child.item(i);
					if ("mapping".equals(mappings.getNodeName())) {
						List<LuckyMapping> mpalist = new ArrayList<>();
						String id = mappings.getAttributes().getNamedItem("controller-id").getNodeValue();
						Node prefix = mappings.getAttributes().getNamedItem("prefix");
						Node suffix = mappings.getAttributes().getNamedItem("suffix");
						if (prefix != null && suffix != null) {
							mvcxml.setControllerstyle(id, prefix.getNodeValue() + "," + suffix.getNodeValue());
						}
						NodeList methods = mappings.getChildNodes();
						for (int j = 0; j < methods.getLength(); j++) {
							LuckyMapping mapping = new LuckyMapping();
							mapping.setId(id);
							Node n1 = methods.item(j);
							if ("method".equals(n1.getNodeName())) {
								mapping.setMethodname(n1.getAttributes().getNamedItem("method-name").getNodeValue());
								mapping.setMethodid(
										id + "/" + n1.getAttributes().getNamedItem("request-mapping").getNodeValue());
								List<String> params = new ArrayList<>();
								NodeList pars = n1.getChildNodes();
								for (int p = 0; p < pars.getLength(); p++) {
									Node p1 = pars.item(p);
									if ("param".equals(p1.getNodeName())) {
										params.add(p1.getFirstChild().getNodeValue());
									}
								}
								mapping.setParamlist(params);
								mpalist.add(mapping);
							}
						}
						list.add(mpalist);
						mvcxml.setMapping(list);
					} else if ("setting".equals(mappings.getNodeName())) {
						NodeList setter = mappings.getChildNodes();
						for (int l = 0; l < setter.getLength(); l++) {
							Node style = setter.item(l);
							if ("style".equals(style.getNodeName())) {
								mvcxml.setSetterStylePrefix(style.getAttributes().getNamedItem("prefix").getNodeValue());
								mvcxml.setSetterStyleSuffix(style.getAttributes().getNamedItem("suffix").getNodeValue());
							} else if ("url-path".equals(style.getNodeName())) {
								mvcxml.setUrl_paths(style.getAttributes().getNamedItem("url").getNodeValue(),
										style.getAttributes().getNamedItem("path").getNodeValue());
							} else if ("uri-encoding".equals(style.getNodeName())) {
								mvcxml.setEncoding(style.getFirstChild().getNodeValue());
							}
						}
					}
				}
			}
		}
		return mvcxml;
	}

	public static ProperConfig getProperties() {
		ProperConfig proper = new ProperConfig();
		Document doc = getDoc();
		if(doc!=null) {
			NodeList orm = doc.getElementsByTagName("orm");
			for (int i = 0; i < orm.getLength(); i++) {
				NodeList n1 = orm.item(i).getChildNodes();
				for (int j = 0; j < n1.getLength(); j++) {
					Node n2 = n1.item(j);
					String n2Name = n2.getNodeName();
					if ("jdbc-driver".equals(n2Name)) {
						proper.setDriver(n2.getFirstChild().getNodeValue());
					} else if ("jdbc-url".equals(n2Name)) {
						proper.setUrl(n2.getFirstChild().getNodeValue());
					} else if ("jdbc-user".equals(n2Name)) {
						proper.setUsername(n2.getFirstChild().getNodeValue());
					} else if ("jdbc-pass".equals(n2Name)) {
						proper.setPassword(n2.getFirstChild().getNodeValue());
					} else if ("cache".equals(n2Name)) {
						if ("true".equals(n2.getFirstChild().getNodeValue()))
							proper.setCache(true);
					} else if ("debug".equals(n2Name)) {
						if ("true".equals(n2.getFirstChild().getNodeValue()))
							proper.setLog(true);
					} else if ("classpath".equals(n2Name)) {
						proper.setSrcPath(n2.getFirstChild().getNodeValue());
					} else if ("pool".equals(n2Name)) {
						Node min = n2.getAttributes().getNamedItem("min");
						Node max = n2.getAttributes().getNamedItem("max");
						proper.setPoolmin(Integer.parseInt(min.getNodeValue()));
						proper.setPoolmax(Integer.parseInt(max.getNodeValue()));
					} else if ("reverse-package".equals(n2Name)) {
						proper.setPackages(n2.getFirstChild().getNodeValue());
					} else if ("scan".equals(n2Name)) {
						proper.setScans(LuckyUtils.strToArray(n2.getFirstChild().getNodeValue()));
					} else if ("scan-mapper".equals(n2Name)) {
						proper.setScans_mapper(LuckyUtils.strToArray(n2.getFirstChild().getNodeValue()));
					} else if ("create-table".equals(n2Name)) {
						List<String> c_url = new ArrayList<>();
						NodeList ct = n2.getChildNodes();
						for (int p = 0; p < ct.getLength(); p++) {
							Node ct1 = ct.item(p);
							if ("c-url".equals(ct1.getNodeName()))
								c_url.add(ct1.getFirstChild().getNodeValue());
						}
						proper.setClaurl(c_url);
					}
				}
			}
		}
		return proper;
	}

}
