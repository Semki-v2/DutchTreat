package org.semki.dutchtreat.utils;

import java.util.List;

public class ObjectsSupport {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> safeCastList(List rawList) {
		return (List<T>) rawList;
	}
}
