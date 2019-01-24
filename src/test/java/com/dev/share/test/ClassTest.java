package com.dev.share.test;

public class ClassTest {
	public static void main(String[] args) {
		String path = ClassTest.class.getClassLoader().getSystemResource( "./" ).getPath();
		String path1 = ClassTest.class.getResource("/").getPath();
		String path2 = ClassTest.class.getClassLoader().getResource("").getPath();
		String path3 = ClassTest.class.getClassLoader().getSystemResource( "" ).getPath();
		String path4 = ClassTest.class.getResource("").getPath();
		String path5 = ClassTest.class.getResource("./").getPath();
		System.out.println(path);
		System.out.println(path1);
		System.out.println(path2);
		System.out.println(path3);
		System.out.println(path4);
		System.out.println(path5);
	}
}
