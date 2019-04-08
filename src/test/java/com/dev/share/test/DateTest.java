package com.dev.share.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) throws ParseException {
//		while(true) {
//			String date = new SimpleDateFormat("MMddHHmmss.SSS").format(new Date());
//			
//			System.out.println("date:"+date);
//		}
	    
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date datetime = format.parse("2019-12-31 23:59:59");
//		System.out.println(datetime);
//		System.out.println(datetime.getTime());
//		System.out.println(datetime.getYear());
//		String date = format.format(datetime);
//		System.out.println(date);
	    LocalDateTime now = LocalDateTime.now();
        int quarter = 4;
        LocalDateTime end = YearMonth.of(now.getYear(), (quarter-1)*3).atEndOfMonth().atStartOfDay();
        System.out.println(end);
	}
}
