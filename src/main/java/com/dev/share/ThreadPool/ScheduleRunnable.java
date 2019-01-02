package com.dev.share.ThreadPool;

public abstract class ScheduleRunnable implements Runnable{
	private int worker;
	public ScheduleRunnable(){
		super();
	}
	public ScheduleRunnable(int worker) {
		super();
		this.worker = worker;
	}
	public int getWorker() {
		return worker;
	}
	public void setWorker(int worker) {
		this.worker = worker;
	}
}
