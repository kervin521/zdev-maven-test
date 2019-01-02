package com.dev.share.util;

public class StringUtils extends org.apache.commons.lang.StringUtils {

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
		if("".equals(time)) {
			time = diff+"ms";
		}
		return time;
	}
}
