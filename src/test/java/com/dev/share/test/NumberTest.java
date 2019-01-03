package com.dev.share.test;

public class NumberTest {

	public static void main(String[] args) {
		System.out.println("===============================================================================");
//		int i=0;
//		if(++i>0) {
//			System.out.println("---------------"+i);
//		}
		int f = 0xffff;
		int i = 65560;
		int n = i & f;
//		‭    1111111111111111‬
//		00010000000000011000‬
		System.out.println("f:"+f+"\ni:"+i+"\nn:"+n);
		System.out.println("===============================================================================");
	}

}
