package com.dev.share.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
	public static void main(String[] args) throws IOException {
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
//		String[] ret = {"1","3","2","6"};
//		System.out.println("------"+StringUtils.join(ret));
//		Arrays.sort(ret, new Comparator<String>() {
//			public int compare(String arg0, String arg1) {
//				return arg0.compareTo(arg1);
//			}
//		});
//		System.out.println("------"+StringUtils.join(ret));
//		int size = 100*10000;
//		List<String> phones = new ArrayList<>();
//		for(int i=0;i<size;i++) {
//			int offset = 100*10000+i;
//			String phone = "138"+offset+""+new Random().nextInt(10);
//			phones.add(phone);
//		}
//		long start = System.currentTimeMillis();
//		String target = StringUtils.join(phones, ",");
//		byte[] data = StringUtils.gzip(target);
//	    String hash = new String(data);
//	    long delay = System.currentTimeMillis();
//	    double tm = 11.4*phones.size()/1000000;
//	    double hm = tm * data.length/target.getBytes().length;
//		System.out.println("------------------------------------{use:"+StringUtils.time(start, delay)+",target:"+target.length()+"["+String.format("%.2f", tm)+"MB],result:"+hash.length()+"["+String.format("%.2f", hm)+"MB]");
//	    String source = StringUtils.unGzip(data);
//	    long end = System.currentTimeMillis();
//	    System.out.println("------------------------------------{use:"+StringUtils.time(delay, end)+",result:"+source.length());
		
//		for(int i=0;i<20;i++) {
//			System.out.println(String.format("%02d", 5));
//		}
	}

}
