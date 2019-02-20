package com.company.appraisal.highcharts;

public class SeriesData {
	
	String name;
	long y;
	long drilldown;
	
	public SeriesData() {
		super();
	}
	public SeriesData(String name, long y, long drilldown) {
		super();
		this.name = name;
		this.y = y;
		this.drilldown = drilldown;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getY() {
		return y;
	}
	public void setY(long y) {
		this.y = y;
	}
	public long getDrilldown() {
		return drilldown;
	}
	public void setDrilldown(long drilldown) {
		this.drilldown = drilldown;
	}
}
