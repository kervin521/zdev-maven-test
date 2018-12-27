package com.dev.share.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class FileTest {
	public static void netstat() throws IOException {
		System.out.println("=========================================");
		String path = "E:\\home\\remote_debug.text";
		File file = new File(path);
//		file.deleteOnExit();
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		List<String> list = new ArrayList<>();
		for(int i=20899;i<44899;i+=1000) {
			String phone = "netstat -ano|grep "+i;
			list.add(phone);
		}
		writer.write(StringUtils.join(list, "\n"));
		writer.flush();
		writer.close();
		System.out.println("========================================="+list.size());
	}
	public static List<String> read() throws IOException {
		System.out.println("=========================================");
		String dpath = "E:\\home\\game";
		File direct = new File(dpath);
		File[] files = direct.listFiles();
		List<String> list = new ArrayList<>();
		for (File file  : files) {
			String fileName = file.getName();
			if("phone1.txt".equals(fileName)) {
				continue;
			}
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				List<String> sub = new ArrayList<String>(Arrays.asList(temp.split(",")));
				System.out.println("-----------------file:"+fileName+",size:"+sub.size());
				list.addAll(sub);
			}
			reader.close();
		}
		System.out.println("========================================="+list.size());
		return list;
	}
	public static void write(List<String> list) throws IOException {
		if(list==null) {
			list = new ArrayList<>();
		}
		int total = list.size();
		int mode = total%10000;
		String subfix = Double.valueOf((total/10000)).intValue()+"w"+(mode>0?"_"+mode:"");
		System.out.println("=========================================");
		String path = "E:\\home\\phone_"+subfix+".txt";
		File file = new File(path);
//		file.deleteOnExit();
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
//		writer.write(StringUtils.join(list, "\n"));
		writer.write(StringUtils.join(list, ","));
		writer.flush();
		writer.close();
		System.out.println("========================================="+list.size());
	}
	public static void main(String[] args) throws IOException {
//		String path = "E:\\home\\game";
//		File direct = new File(path);
//		File[] files = direct.listFiles();
//		String str = "";
//		for (File file  : files) {
//			str +=","+file.getName();
//		}
//		System.out.println(str);
		System.out.println("=========================================");
		
		List<String> list = new ArrayList<>();
		for(int i=1000000;i<1001000;i++) {
			String phone = "138"+i+""+new Random().nextInt(10);
			list.add(phone);
		}
//		List<String> list = read();
//		for(int i=1000000;i<2800000;i++) {
//			String phone = "138"+i+""+new Random().nextInt(10);
//			list.add(phone);
//		}
		write(list);
		System.out.println("=========================================");
	}

}
