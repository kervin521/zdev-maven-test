package com.dev.share.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dev.share.kafka.KafkaRecordInfo;

public class CollectionTest {

	public static void main(String[] args) {
		Map<KafkaRecordInfo,Integer> map = new HashMap<KafkaRecordInfo,Integer>();
		KafkaRecordInfo obj = new KafkaRecordInfo();
		System.out.println("count:"+map.get(obj));
		
		List<String> list = new ArrayList<String>(50000);
		for(int i=0;i<50000;i++) {
			list.add(""+i);
		}
			
		int size = list.size();
		int core = Runtime.getRuntime().availableProcessors()*2+1;
		int pageSize = size/core;
		System.out.println("size:"+size+",core:"+core+",pageSize:"+pageSize);
		for(int i=0;i<core;i++) {
			int from = i*pageSize;
			int to = i+1==core?size:(i+1)*pageSize-1;
			
			System.out.println((i-0+1)+"----"+from+"----"+to);
		}
	}

}
