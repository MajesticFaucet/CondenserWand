package com.majestic.condenserwand.util;

import java.util.List;

public final class MiscUtil {
	private MiscUtil() {}
	
	public static boolean sequentialComparison(final List<?> lista, final List<?> listb) {
		final int size = lista.size();
		if(size!=listb.size()) return false;
		for(int a=0; a<size; a++) {
			if(!lista.get(0).equals(listb.get(0))) return false;
		}
		return true;
	}
}
