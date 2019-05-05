package com.dev.share.test;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.dev.share.ThreadPool.AbstractRunnable;

public class SystemTest {
	public static int core = Runtime.getRuntime().availableProcessors();
	public static ScheduledExecutorService shedule = Executors.newScheduledThreadPool(core * 2);

	public static void properties() {
		String enbale = System.getProperty("sys.enbale", "");
		String enbaled = System.getProperty("sys.enbaled", "false");
		System.out.println("[" + enbale + "]-->[" + Boolean.valueOf(enbale) + "]");
		System.out.println("[" + enbaled + "]-->[" + Boolean.valueOf(enbaled) + "]");
	}

	public static void exit() {
		System.out.println("-----------------------------[start]------------------------");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					while (true) {
						Thread.sleep(1);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();// 异常信息
				}
				System.out.println("-------[1]shutdown hook-----");
//				System.exit(0);
//				Runtime.getRuntime().halt(0);
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("-------[2]shutdown hook-----");
				System.exit(0);
//				Runtime.getRuntime().halt(0);
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("-------[3]shutdown hook-----");
				System.exit(0);
//				Runtime.getRuntime().halt(0);
			}
		});
		System.out.println("[]---正在退出.....");
		System.exit(1);
//		
		System.out.println("-----------------------------[end]------------------------");
	}

	public static void shutdown() {
		System.out.println("-----------------------------[start]------------------------");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("-------shutdown hook-----");
				shedule.shutdown();
				System.exit(0);
			}
		});
		int size = 10;
		for (int i = 0; i < size; i++) {
			final int n = i;
			shedule.execute(new AbstractRunnable(i) {
				@Override
				public void run() {
					int worker = this.getWorker();
					if (n == 4) {
						System.out.println("[" + worker + "(" + n + ")]---正在退出.....");
						System.exit(1);
//						Runtime.getRuntime().halt(0);
					} else {
						try {
							System.out.println("[" + worker + "(" + n + ")]---sleep.....");
							Thread.sleep(1);
							System.out.println("[" + worker + "(" + n + ")]---recycle.....");
						} catch (InterruptedException e) {
							e.printStackTrace();// 异常信息
						}
					}
				}
			});
		}
		System.out.println("-----------------------------[end]------------------------");
	}

	public static void env() {
		Properties properties = System.getProperties();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
		System.out.println("-----------------------------env------------------------");
		Map<String, String> env = System.getenv();
		for (Entry<String, String> entry : env.entrySet()) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}

	public static void main(String[] args) {
//		exit();
//		shutdown();
		env();
	}
}
