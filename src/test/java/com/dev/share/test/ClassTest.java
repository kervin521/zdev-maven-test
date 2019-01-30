package com.dev.share.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;

import redis.clients.jedis.Connection;


public class ClassTest {
	public enum IMode{
		IMODE_SERVER,
		IMODE_CLIENT_SEND,
		IMODE_CLIENT_RESP;
	}
	public class ChannelThread implements Runnable{
		ISession session;
		public ChannelThread(ISession session) {
			this.session= session;
		}
		@Override
		public void run() {
			while (true) {
				System.out.println("-----ChannelThread:"+session.mode);
				try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public class ListenThread extends Thread{
		ISession session;
		public ListenThread(ISession session) {
			this.session= session;
		}
		@Override
		public void run() {
			while (true) {
				System.out.println("-----ListenThread:"+session.mode);
				try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public class ISession{
		private Connection conn;
		private IMode mode = IMode.IMODE_CLIENT_SEND;
		public void setMode(IMode mode) {
			this.mode = mode;
		}
		public ISession(Connection conn) {
			this.conn=conn;
		}
		/**
		  * 描述: 上行监听服务
		  * 作者:ZhangYi
		  * 时间:2019年1月28日 下午5:03:28
		  * 参数：(参数列表)
		 */
		public void listener() {//回执侦听
			try {
				this.setMode(IMode.IMODE_SERVER);
				ListenThread listenThread = new ListenThread(this);
				listenThread.start();//BindResp//DeliverResp/ReportResp/UnbindResp
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		  * 描述: 消息回执
		  * 作者:ZhangYi
		  * 时间:2019年1月28日 下午6:27:24
		  * 参数：(参数列表)
		 */
		public void submitResp() {
			this.setMode(IMode.IMODE_CLIENT_RESP);
			heart();
			final ISession session = this;
			ExecutorService pool = Executors.newSingleThreadExecutor();
			pool.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						System.out.println("-----submitResp:"+session.mode);
						try {
							Thread.sleep(2*1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		/**
		  * 描述: 提交消息
		  * 作者:ZhangYi
		  * 时间:2019年1月28日 下午6:26:55
		  * 参数：(参数列表)
		 */
		public void submit() {
			this.setMode(IMode.IMODE_CLIENT_SEND);
			heart();
			ExecutorService pool = Executors.newSingleThreadExecutor();
			pool.execute(new ChannelThread(this));
		}
		/**
		  * 描述: SGIP客户端检查并绑定或重连
		  * 作者:ZhangYi
		  * 时间:2019年1月28日 下午5:01:45
		  * 参数：(参数列表)
		 */
		public void check()  {
			if(IMode.IMODE_SERVER.equals(mode)) {
				return;
			}
			System.out.println("-----check:"+mode);
		}
		/**
		  * 描述: SGIP客户端心跳
		  * 作者:ZhangYi
		  * 时间:2019年1月28日 下午5:01:45
		  * 参数：(参数列表)
		 */
		public void heart() {
			if(IMode.IMODE_SERVER.equals(mode)) {
				return;
			}
			ScheduledExecutorService schudle = Executors.newSingleThreadScheduledExecutor();
			schudle.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try {
						check();
					}catch (Exception e) {
					}
				}
			}, 1, 1, TimeUnit.SECONDS);
		}
	}
	public void session() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		Connection conn = new Connection("127.0.0.1", 6369);
		ISession session = new ISession(conn);
		session.listener();//上行监听[回执监听]
		for (int i = 0; i < 10; i++) {
			session = new ISession(conn);
			session.submitResp();
			session.submit();
		}
	}
	public void test() {
		String path = ClassTest.class.getClassLoader().getSystemResource( "./" ).getPath();
		String path1 = ClassTest.class.getResource("/").getPath();
		String path2 = ClassTest.class.getClassLoader().getResource("").getPath();
		String path3 = ClassTest.class.getClassLoader().getSystemResource( "" ).getPath();
		String path4 = ClassTest.class.getResource("").getPath();
		String path5 = ClassTest.class.getResource("./").getPath();
		System.out.println(path);
		System.out.println(path1);
		System.out.println(path2);
		System.out.println(path3);
		System.out.println(path4);
		System.out.println(path5);
	}
	public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		new ClassTest().session();
	}
}
