package com.dev.share.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class GoogleTest {
	public static void write(List<String> list) throws IOException {
		if(list==null) {
			list = new ArrayList<>();
		}
		int total = list.size();
		int mode = total%10000;
		int num = Double.valueOf((total/10000)).intValue();
		String subfix = (num>0?num+"w":"")+(mode>0?"_"+mode:"");
		System.out.println("========================================="+subfix);
		String path = "E:\\Hosts\\google_hosts.txt";
		File file = new File(path);
//		file.deleteOnExit();
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write(StringUtils.join(list, "\n"));
//		writer.write(StringUtils.join(list, ","));
		writer.flush();
		writer.close();
		System.out.println("========================================="+list.size());
	}
	public static void main(String[] args) throws IOException {
		String google = "52.216.138.139,52.216.170.75,52.216.111.43,52.216.8.147,52.216.108.195,52.216.9.195,52.216.10.51,52.216.111.59";
		google += "	github-production-release-asset-2e65be.s3.amazonaws.com";
		List<String> list = Arrays.asList(google.replaceAll("127.1.0.1,", "").replaceAll(",", "	github-production-release-asset-2e65be.s3.amazonaws.com,").split(","));
		write(list);
	}
}
