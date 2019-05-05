package com.dev.share.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPTest {

	public static void main(String[] args) throws UnknownHostException {
		System.out.println("local_host_address:" + InetAddress.getLocalHost().getHostAddress());
		System.out.println("local_host_name:" + InetAddress.getLocalHost().getHostName());
		System.out.println("local_host_canonical:" + InetAddress.getLocalHost().getCanonicalHostName());
		System.out.println("Loop_back_address:" + InetAddress.getLoopbackAddress().getHostAddress());
		System.out.println("Loop_back_name:" + InetAddress.getLoopbackAddress().getHostName());
		System.out.println("Loop_back_canonical:" + InetAddress.getLoopbackAddress().getCanonicalHostName());

	}

}
