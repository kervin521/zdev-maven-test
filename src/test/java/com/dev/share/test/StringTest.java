package com.dev.share.test;

public class StringTest {

	public static String time(long start,long end) {
		String time = "";
		if(start>end) {
			long temp = start;
			start=end;
			end=temp;
		}
		long diff = (end-start);
		long millscond = diff%1000;
		if(millscond>0) {
			time = millscond+"ms";
		}
		long second = Long.valueOf(diff/1000)%60;
		if(second>0) {
			time = second+"s"+time;
		}
		long minutes = Long.valueOf(diff/(1000*60))%60;
		if(minutes>0) {
			time = minutes+"m"+time;
		}
		long hour = Long.valueOf(diff/(1000*60*60))%24;
		if(hour>0) {
			time = hour+"h"+time;
		}
		long day = Long.valueOf(diff/(1000*60*60*24));
		if(day>0) {
			time = day+"d"+time;
		}
		return time;
	}
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
			System.out.println("-------------------------------------------------切换通道服务[end]------------------------------------------------"+time(start, endTime));
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
		test();
	}

}
