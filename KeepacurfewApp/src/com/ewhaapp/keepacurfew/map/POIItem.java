package com.ewhaapp.keepacurfew.map;

import com.skp.Tmap.TMapPOIItem;

public class POIItem {
	TMapPOIItem poi;
	@Override
	public String toString() {
		return poi.getPOIName();
	}
}
