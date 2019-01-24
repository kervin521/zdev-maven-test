package com.dev.share.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) throws ParseException {
//		while(true) {
//			String date = new SimpleDateFormat("MMddHHmmss.SSS").format(new Date());
//			
//			System.out.println("date:"+date);
//		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime = format.parse("2019-12-31 23:59:59");
		System.out.println(datetime);
		System.out.println(datetime.getTime());
		String date = format.format(datetime);
		System.out.println(date);
	}
}
