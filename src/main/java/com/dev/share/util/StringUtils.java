package com.dev.share.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StringUtils extends org.apache.commons.lang.StringUtils {

	public static String capacity(double size, boolean isBit) {
		String capacity = "";
		double diff = isBit ? size / 8 : size;
		double kb = diff / 1024;
		if (kb < 1024) {
			if (kb < 1) {
				capacity = String.format("%.2f", diff) + "B";
			} else {
				capacity = String.format("%.2f", kb) + "KB";
			}
			return capacity;
		}
		double mb = diff / (1024 * 1024);
		if (mb < 1024) {
			capacity = String.format("%.2f", mb) + "MB";
			return capacity;
		}
		double gb = diff / (1024 * 1024 * 1024);
		if (gb < 1024) {
			capacity = String.format("%.2f", gb) + "GB";
			return capacity;
		} else {
			double tb = diff / (1024 * 1024 * 1024 * 1024);
			capacity = String.format("%.2f", tb) + "TB";
		}
		return capacity;
	}

	public static String time(long start, long end) {
		String time = "";
		if (start > end) {
			long temp = start;
			start = end;
			end = temp;
		}
		long diff = (end - start);
		long millscond = diff % 1000;
		if (millscond > 0) {
			time = millscond + "ms";
		}
		long second = Long.valueOf(diff / 1000) % 60;
		if (second > 0) {
			time = second + "s" + time;
		}
		long minutes = Long.valueOf(diff / (1000 * 60)) % 60;
		if (minutes > 0) {
			time = minutes + "m" + time;
		}
		long hour = Long.valueOf(diff / (1000 * 60 * 60)) % 24;
		if (hour > 0) {
			time = hour + "h" + time;
		}
		long day = Long.valueOf(diff / (1000 * 60 * 60 * 24));
		if (day > 0) {
			time = day + "d" + time;
		}
		if ("".equals(time)) {
			time = diff + "ms";
		}
		return time;
	}

	/**
	 * @description GZip压缩
	 * 作者: ZhangYi
	 * 时间: 2019年1月12日 上午9:17:15
	 * 参数: (参数列表)
	 * 
	 * @param target 压缩文本
	 * @return
	 */
	public static byte[] gzip(String target) {
		byte[] data = null;
		if (isEmpty(target)) {
			return data;
		}
		ByteArrayOutputStream out = null;
		GZIPOutputStream gout = null;
		try {
			out = new ByteArrayOutputStream();
			gout = new GZIPOutputStream(out);
			gout.write(target.getBytes());
			gout.finish();
			data = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();// 异常信息
		} finally {
			try {
				if (gout != null)
					gout.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();// 异常信息
			}
		}
		return data;
	}

	/**
	 * @description GZip压缩文本解压
	 * 作者: ZhangYi
	 * 时间: 2019年1月12日 上午9:17:15
	 * 参数: (参数列表)
	 * 
	 * @param data 压缩数据
	 * @return
	 */
	public static String unGzip(byte[] data) {
		String target = null;
		if (data == null || data.length <= 0) {
			return target;
		}
		ByteArrayInputStream in = null;
		GZIPInputStream gin = null;
		ByteArrayOutputStream out = null;
		try {
			in = new ByteArrayInputStream(data);
			gin = new GZIPInputStream(in);
			out = new ByteArrayOutputStream();
			byte[] buf = new byte[512];
			int num = -1;
			while ((num = gin.read(buf, 0, buf.length)) != -1) {
				out.write(buf, 0, num);
			}
			target = new String(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();// 异常信息
		} finally {
			try {
				if (out != null)
					out.close();
				if (gin != null)
					gin.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();// 异常信息
			}
		}
		return target;
	}

	public static void main(String[] args) {
		System.out.println(capacity(24144, false));
	}
}
