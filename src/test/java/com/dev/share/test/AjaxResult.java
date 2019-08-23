package com.dev.share.test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Ajax返回结果
 *
 * @author wuxiaowei
 */
public class AjaxResult {
	public static final int AJAX_STATUS_SUCCESS = 0;
	public static final int AJAX_STATUS_ERROR = -1;
	public static final String AJAX_MESSAGE_SUCCESS = "success";
	public static AjaxResult ERROR = new AjaxResult(AJAX_STATUS_ERROR);
	public static AjaxResult SUCCESS = new AjaxResult(AJAX_STATUS_SUCCESS);
	public static PAjaxResult PSUCCESS = new PAjaxResult(AJAX_STATUS_SUCCESS);
	/**
	 * 返回的中文消息
	 */
	private String message;
	/**
	 * 成功时携带的数据
	 */
	private Object results;
	/**
	 * 返回状态码
	 */
	private int statuscode;

	public AjaxResult() {
	}

	public AjaxResult(int statuscode) {
		this.statuscode = statuscode;
	}

	public AjaxResult(String message) {
		this.message = message;
	}

	public Object getResults() {
		return results;
	}

	public AjaxResult setResults(Object results) {
		this.results = results;
		return this;
	}

	public int getStatuscode() {
		return statuscode;
	}

	public AjaxResult setStatuscode(int statuscode) {
		this.statuscode = statuscode;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public AjaxResult setMessage(String message) {
		this.message = message;
		return this;
	}

	public AjaxResult addSuccess(String message) {
		this.statuscode = AJAX_STATUS_SUCCESS;
		this.message = message;
		return this;
	}

	public AjaxResult addError(String message) {
		this.statuscode = AJAX_STATUS_ERROR;
		this.message = message;
		this.results = null;
		return this;
	}

	public AjaxResult addFail(String message) {
		this.statuscode = AJAX_STATUS_ERROR;
		this.message = message;
		this.results = null;
		return this;
	}

	public AjaxResult addWarn(String message) {
		this.statuscode = AJAX_STATUS_ERROR;
		this.message = message;
		this.results = null;
		return this;
	}

	public AjaxResult success(Object data) {
		this.message = AJAX_MESSAGE_SUCCESS;
		this.results = data;
		return this;
	}

	/**
	 * @project SF_Common
	 * @description ajax响应重构
	 * 
	 * @author ZhangYi
	 *         时间: 2019年3月13日 下午3:22:41
	 *         版本: v1.0
	 *         JDK: 1.8
	 */
	public static class PAjaxResult extends AjaxResult {
		private int pageNum;
		private int pageSize;
		private long total;

		public PAjaxResult() {
			super(AJAX_STATUS_SUCCESS);
		}

		public PAjaxResult(int status) {
			super(status);
		}

		public PAjaxResult(int pageNum, int pageSize, long total, Object results) {
			this();
			this.pageNum = pageNum;
			this.pageSize = pageSize;
			this.total = total;
			super.results = results;
		}

		public int getPageNum() {
			return pageNum;
		}

		public PAjaxResult setPageNum(int pageNum) {
			this.pageNum = pageNum;
			return this;
		}

		public int getPageSize() {
			return pageSize;
		}

		public PAjaxResult setPageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public long getTotal() {
			return total;
		}

		public PAjaxResult setTotal(long total) {
			this.total = total;
			return this;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ConcurrentLinkedQueue<AjaxResult> queue = new ConcurrentLinkedQueue<AjaxResult>();
		CountDownLatch latch = new CountDownLatch(200);
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(4);
		for (int i = 0; i < 200; i++) {
			schedule.execute(new Runnable() {
				@Override
				public void run() {
					queue.add(SUCCESS);
					latch.countDown();
				}
			});
		}
		latch.await();
		ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<Integer, String>();
		System.out.println("==============" + queue.size());
		queue.stream().forEach(obj -> {
			map.put(obj.hashCode(), obj.toString());
		});
		System.out.println("------------------" + map.size());
	}
}
