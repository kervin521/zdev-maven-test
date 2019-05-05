package com.dev.share.test;

public class NumberTest {
	public static void main(String[] args) {
		System.out.println("===============================================================================");
//		int i=0;
//		if(++i>0) {
//			System.out.println("---------------"+i);
//		}
//		int f = 0xffff;
//		for(int i=0;i<10;i++) {
//			int n = (i & f)+1;
//			System.out.println("f:"+f+"\ti:"+i+"\tn:"+n+"\t>:"+String.format("%05d", n));
//			if(i==9) {
//				i = 65536;
//				n = (i & f)+1;
//				System.out.println("f:"+f+"\ti:"+i+"\tn:"+n+"\t>:"+String.format("%05d", n));
//			}
//		}

//		‭    1111111111111111‬
//		00010000000000011000‬

//		double times = 3.56;
//		System.out.println(String.format("%.1f", times));
//		System.out.println(0%1);
//		System.out.println(1%1);
//		System.out.println(2%1);
//		System.out.println(Integer.MAX_VALUE);;
		System.out.println("===============================================================================");
		for (int i = 1; i <= 12; i++) {
			System.out.println(i + "----" + Double.valueOf((i - 1) / 3 + 1).intValue());
		}
	}
}
