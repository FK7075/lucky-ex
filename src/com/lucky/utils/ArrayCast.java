package com.lucky.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ArrayCast {

	@SuppressWarnings("unchecked")
	public static <T> T[] strArrayChange(String[] strArr, Class<T> changTypeClass) {
		return (T[]) ArrayCast.strToList(strArr, changTypeClass.getSimpleName());
	}

	public static String[] getFieldGenericType(Field field) {
		Type genericType = field.getGenericType();
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			Class<?>[] actualTypeArguments = (Class<?>[]) pt.getActualTypeArguments();
			String[] gener = new String[actualTypeArguments.length];
			for (int i = 0; i < gener.length; i++) {
				gener[i]=actualTypeArguments[i].getSimpleName();
			}
			return gener;
		}
		return null;
	}

	public static Object[] strToList(String[] arr, String type) {
		if (arr == null)
			return null;
		switch (type) {
		case "int": {
			Integer[] ints = new Integer[arr.length];
			for (int i = 0; i < arr.length; i++) {
				ints[i] = Integer.parseInt(arr[i]);
			}
			return ints;
		}
		case "Integer": {
			Integer[] Ints = new Integer[arr.length];
			for (int i = 0; i < arr.length; i++) {
				Ints[i] = Integer.parseInt(arr[i]);
			}
			return Ints;
		}
		case "Double": {
			Double[] Doubs = new Double[arr.length];
			for (int i = 0; i < arr.length; i++) {
				Doubs[i] = Double.parseDouble(arr[i]);
			}
			return Doubs;

		}
		case "double": {
			Double[] doubs = new Double[arr.length];
			for (int i = 0; i < arr.length; i++) {
				doubs[i] = Double.parseDouble(arr[i]);
			}
			return doubs;
		}
		case "long": {
			Long[] Longs = new Long[arr.length];
			for (int i = 0; i < arr.length; i++) {
				Longs[i] = Long.parseLong(arr[i]);
			}
			return Longs;
		}
		case "Long": {
			Long[] Longs = new Long[arr.length];
			for (int i = 0; i < arr.length; i++) {
				Longs[i] = Long.parseLong(arr[i]);
			}
			return Longs;
		}
		case "float": {
			Float[] floats = new Float[arr.length];
			for (int i = 0; i < arr.length; i++) {
				floats[i] = Float.parseFloat(arr[i]);
			}
			return floats;
		}
		case "Float": {
			Float[] floats = new Float[arr.length];
			for (int i = 0; i < arr.length; i++) {
				floats[i] = Float.parseFloat(arr[i]);
			}
			return floats;
		}
		case "byte": {
			Byte[] bytes = new Byte[arr.length];
			for (int i = 0; i < arr.length; i++) {
				bytes[i] = Byte.parseByte(arr[i]);
			}
			return bytes;
		}
		case "Byte": {
			Byte[] bytes = new Byte[arr.length];
			for (int i = 0; i < arr.length; i++) {
				bytes[i] = Byte.parseByte(arr[i]);
			}
			return bytes;
		}
		case "boolean": {
			Boolean[] booleans = new Boolean[arr.length];
			for (int i = 0; i < arr.length; i++) {
				booleans[i] = Boolean.parseBoolean(arr[i]);
			}
			return booleans;
		}
		case "Boolean": {
			Boolean[] booleans = new Boolean[arr.length];
			for (int i = 0; i < arr.length; i++) {
				booleans[i] = Boolean.parseBoolean(arr[i]);
			}
			return booleans;
		}
		case "int[]": {
			Integer[] ints = new Integer[arr.length];
			for (int i = 0; i < arr.length; i++) {
				ints[i] = Integer.parseInt(arr[i]);
			}
			return ints;
		}
		case "Integer[]": {
			Integer[] Ints = new Integer[arr.length];
			for (int i = 0; i < arr.length; i++) {
				Ints[i] = Integer.parseInt(arr[i]);
			}
			return Ints;
		}
		case "Double[]": {
			Double[] Doubs = new Double[arr.length];
			for (int i = 0; i < arr.length; i++) {
				Doubs[i] = Double.parseDouble(arr[i]);
			}
			return Doubs;

		}
		case "double[]": {
			Double[] doubs = new Double[arr.length];
			for (int i = 0; i < arr.length; i++) {
				doubs[i] = Double.parseDouble(arr[i]);
			}
			return doubs;
		}
		case "long[]": {
			Long[] Longs = new Long[arr.length];
			for (int i = 0; i < arr.length; i++) {
				Longs[i] = Long.parseLong(arr[i]);
			}
			return Longs;
		}
		case "Long[]": {
			Long[] Longs = new Long[arr.length];
			for (int i = 0; i < arr.length; i++) {
				Longs[i] = Long.parseLong(arr[i]);
			}
			return Longs;
		}
		case "float[]": {
			Float[] floats = new Float[arr.length];
			for (int i = 0; i < arr.length; i++) {
				floats[i] = Float.parseFloat(arr[i]);
			}
			return floats;
		}
		case "Float[]": {
			Float[] floats = new Float[arr.length];
			for (int i = 0; i < arr.length; i++) {
				floats[i] = Float.parseFloat(arr[i]);
			}
			return floats;
		}
		case "byte[]": {
			Byte[] bytes = new Byte[arr.length];
			for (int i = 0; i < arr.length; i++) {
				bytes[i] = Byte.parseByte(arr[i]);
			}
			return bytes;
		}
		case "Byte[]": {
			Byte[] bytes = new Byte[arr.length];
			for (int i = 0; i < arr.length; i++) {
				bytes[i] = Byte.parseByte(arr[i]);
			}
			return bytes;
		}
		case "boolean[]": {
			Boolean[] booleans = new Boolean[arr.length];
			for (int i = 0; i < arr.length; i++) {
				booleans[i] = Boolean.parseBoolean(arr[i]);
			}
			return booleans;
		}
		case "Boolean[]": {
			Boolean[] booleans = new Boolean[arr.length];
			for (int i = 0; i < arr.length; i++) {
				booleans[i] = Boolean.parseBoolean(arr[i]);
			}
			return booleans;
		}
		default:
			return arr;
		}
	}
}
