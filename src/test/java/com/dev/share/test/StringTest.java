package com.dev.share.test;

import com.dev.share.util.StringUtils;

public class StringTest {

	public static void test() {
		while(true) {
			long start = System.currentTimeMillis();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long endTime = System.currentTimeMillis();
			System.out.println("-------------------------------------------------切换通道服务[end]------------------------------------------------"+StringUtils.time(start, endTime));
		}
	}
	public static void main(String[] args) {
//		String a = "abc";
//		String b = "abc";
//		String c = new String("abc");
//		String d = new String("abc");
//		System.out.println("a==b:"+(a==b)+",a~b:"+a.equals(b));
//		System.out.println("a==c:"+(a==c)+",a~c:"+a.equals(c));
//		System.out.println("c==d:"+(c==d)+",c~d:"+c.equals(d));
//		Date start = DateUtils.parseDate("2018-12-13 13:30:30", new String[] {"yyyy-MM-dd HH:mm:ss"});
//		Date end = DateUtils.parseDate("2018-12-13 13:30:30", new String[] {"yyyy-MM-dd HH:mm:ss"});
//		System.out.println(time(start.getTime(), end.getTime()));
//		test();
//		StringUtils.join(array, separator)
	}

}
