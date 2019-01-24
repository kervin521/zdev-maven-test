package com.dev.share.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tools.bzip2.CBZip2InputStream;
import org.apache.tools.bzip2.CBZip2OutputStream;
import org.jboss.netty.util.internal.jzlib.JZlib;

import com.dev.share.util.StringUtils;

public class GzipTest {
	/***
	  * 压缩GZip
	  * 
	  * @param data
	  * @return
	  */
	 public static byte[] gZip(byte[] data) {
	  byte[] b = null;
	  try {
	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   GZIPOutputStream gzip = new GZIPOutputStream(bos);
	   gzip.write(data);
	   gzip.finish();
	   gzip.close();
	   b = bos.toByteArray();
	   bos.close();
	  } catch (Exception ex) {
	   ex.printStackTrace();
	  }
	  return b;
	 }

	/***
	  * 解压GZip
	  * 
	  * @param data
	  * @return
	  */
	 public static byte[] unGZip(byte[] data) {
	  byte[] b = null;
	  try {
	   ByteArrayInputStream bis = new ByteArrayInputStream(data);
	   GZIPInputStream gzip = new GZIPInputStream(bis);
	   byte[] buf = new byte[1024];
	   int num = -1;
	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   while ((num = gzip.read(buf, 0, buf.length)) != -1) {
	    baos.write(buf, 0, num);
	   }
	   b = baos.toByteArray();
	   baos.flush();
	   baos.close();
	   gzip.close();
	   bis.close();
	  } catch (Exception ex) {
	   ex.printStackTrace();
	  }
	  return b;
	 }



	/***
	  * 压缩Zip
	  * 
	  * @param data
	  * @return
	  */
	 public static byte[] zip(byte[] data) {
	  byte[] b = null;
	  try {
	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   ZipOutputStream zip = new ZipOutputStream(bos);
	   ZipEntry entry = new ZipEntry("zip");
	   entry.setSize(data.length);
	   zip.putNextEntry(entry);
	   zip.write(data);
	   zip.closeEntry();
	   zip.close();
	   b = bos.toByteArray();
	   bos.close();
	  } catch (Exception ex) {
	   ex.printStackTrace();
	  }
	  return b;
	 }

	/***
	  * 解压Zip
	  * 
	  * @param data
	  * @return
	  */
	 public static byte[] unZip(byte[] data) {
	  byte[] b = null;
	  try {
	   ByteArrayInputStream bis = new ByteArrayInputStream(data);
	   ZipInputStream zip = new ZipInputStream(bis);
	   while (zip.getNextEntry() != null) {
	    byte[] buf = new byte[1024];
	    int num = -1;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    while ((num = zip.read(buf, 0, buf.length)) != -1) {
	     baos.write(buf, 0, num);
	    }
	    b = baos.toByteArray();
	    baos.flush();
	    baos.close();
	   }
	   zip.close();
	   bis.close();
	  } catch (Exception ex) {
	   ex.printStackTrace();
	  }
	  return b;
	 }

	/***
	  * 压缩BZip2
	  * 
	  * @param data
	  * @return
	  */
	 public static byte[] bZip2(byte[] data) {
	  byte[] b = null;
	  try {
	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   CBZip2OutputStream bzip2 = new CBZip2OutputStream(bos);
	   bzip2.write(data);
	   bzip2.flush();
	   bzip2.close();
	   b = bos.toByteArray();
	   bos.close();
	  } catch (Exception ex) {
	   ex.printStackTrace();
	  }
	  return b;
	 }

	 /***
	  * 解压BZip2
	  * 
	  * @param data
	  * @return
	  */
	 public static byte[] unBZip2(byte[] data) {
	  byte[] b = null;
	  try {
	   ByteArrayInputStream bis = new ByteArrayInputStream(data);
	   CBZip2InputStream bzip2 = new CBZip2InputStream(bis);
	   byte[] buf = new byte[1024];
	   int num = -1;
	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   while ((num = bzip2.read(buf, 0, buf.length)) != -1) {
	    baos.write(buf, 0, num);
	   }
	   b = baos.toByteArray();
	   baos.flush();
	   baos.close();
	   bzip2.close();
	   bis.close();
	  } catch (Exception ex) {
	   ex.printStackTrace();
	  }
	  return b;
	 }

	 public static void main(String[] args) {
		 List<String> phones = new ArrayList<>();
			for(int i=1000000;i<2000000;i++) {
				String phone = "138"+i+""+new Random().nextInt(10);
				phones.add(phone);
			}
			long start = System.currentTimeMillis();
			String target = StringUtils.join(phones, ",");
	  
//	  byte[] b1 = zip(target.getBytes());
//	  System.out.println("zip:" + new String(b1));
//	  byte[] b2 = unZip(b1);
//	  System.out.println("unZip:" + new String(b2));
//	  byte[] b3 = bZip2(target.getBytes());
//	  System.out.println("bZip2:" + new String(b3));
//	  byte[] b4 = unBZip2(b3);
//	  System.out.println("unBZip2:" + new String(b4));
//	  byte[] b5 = gZip(target.getBytes());
	  byte[] b5 = StringUtils.gzip(target);
	  System.out.println("gZip:" + b5.length);
	  byte[] b6 = unGZip(b5);
	  System.out.println("unGZip:" + new String(b6));
	 }
}
