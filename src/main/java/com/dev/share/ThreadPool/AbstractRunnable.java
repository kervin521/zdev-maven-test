package com.dev.share.ThreadPool;
/**
  * 项目: emgw-common
  * 描述: 多线程处理增加线程编号
  * 作者: ZhangYi
  * 时间: 2019年1月10日 上午10:29:31
  * 版本: v1.0
  * JDK: 1.8.192
  * @company 空间畅想
 */
public abstract class AbstractRunnable implements Runnable{
	private int worker;
	public AbstractRunnable(){
		super();
	}
	public AbstractRunnable(int worker) {
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
