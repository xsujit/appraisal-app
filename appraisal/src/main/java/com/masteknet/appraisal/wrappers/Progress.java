package com.masteknet.appraisal.wrappers;

public class Progress {
	
	private String stage;
	private boolean done;
	
	public Progress() {
		super();
	}
	public Progress(String stage) {
		super();
		this.stage = stage;
	}
	
	public Progress(String stage, boolean done) {
		super();
		this.stage = stage;
		this.done = done;
	}
	
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
}
