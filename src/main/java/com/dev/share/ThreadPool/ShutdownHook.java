package com.dev.share.ThreadPool;

import java.util.concurrent.ExecutorService;

/**
 * @description 系统退出时的钩子程序
 * JDK: 1.8.192
 */
public abstract class ShutdownHook {
	public static void add(final ShutdownHook handler) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				handler.shutdown();
			}
		});
	}

	public abstract void shutdown();

	/**
	 * @description 线程池钩子
	 * 作者:ZhangYi
	 * 时间:2019年1月10日 上午9:50:43
	 * 参数: (参数列表)
	 * 
	 * @param handler JDK线程池服务
	 */
	public static void add(final ExecutorService handler) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				handler.shutdown();
			}
		});
	}

	/**
	 * @description 自动关闭钩子
	 * 作者:ZhangYi
	 * 时间:2019年1月10日 上午9:50:43
	 * 参数: (参数列表)
	 * 
	 * @param handler 自动关闭服务
	 */
	public static void add(final AutoCloseable handler) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					handler.close();
				} catch (Exception e) {
					e.printStackTrace();// 异常信息
				}
			}
		});
	}
}
