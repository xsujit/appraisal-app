package com.masteknet.appraisal.highcharts;

import java.util.HashMap;
import java.util.Map;

public class DrillDown {
	
	private long id; // employee
	private Map<Long, Integer> data = new HashMap<>(); // voter, total vote

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Map<Long, Integer> getData() {
		return data;
	}
	public void setData(Map<Long, Integer> data) {
		this.data = data;
	}

}
