package com.dev.share.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dev.share.kafka.KafkaRecordInfo;
import com.google.common.collect.Maps;

public class CollectionTest {
    public static void test() {
        String json ="{\"mark\":\"201908\",\"tags\":[{\"tag\":\"ENTER_INDEX::FGRQDFSR\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDBDHZCLF\",\"value\":9.0},{\"tag\":\"ENTER_INDEX::YDBDHZCLF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDBDMCB\",\"value\":9.0},{\"tag\":\"ENTER_INDEX::YDBDMCB\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDBDPWF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDBDYCB\",\"value\":9.0},{\"tag\":\"ENTER_INDEX::YDBDYCB\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDCQDKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDDLYWLR\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDDQJKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDGDCBCLF\",\"value\":14.0},{\"tag\":\"ENTER_INDEX::YDGDCBCLF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDGDCBCWFY\",\"value\":12.0},{\"tag\":\"ENTER_INDEX::YDGDCBCWFY\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDGDCBQTFY\",\"value\":445.0},{\"tag\":\"ENTER_INDEX::YDGDCBQTFY\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDGDCBSFJSZYF\",\"value\":13.0},{\"tag\":\"ENTER_INDEX::YDGDCBSFJSZYF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDGDCBXLF\",\"value\":14.0},{\"tag\":\"ENTER_INDEX::YDGDCBXLF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDGDCBZGXC\",\"value\":15.0},{\"tag\":\"ENTER_INDEX::YDGDCBZGXC\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDGDCBZJ\",\"value\":4.0},{\"tag\":\"ENTER_INDEX::YDGDCBZJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDGDZCJZ\",\"value\":34.0},{\"tag\":\"ENTER_INDEX::YDGDZCJZ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDGDZCYE\",\"value\":332.0},{\"tag\":\"ENTER_INDEX::YDGDZCYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDHZZB\",\"value\":34.0},{\"tag\":\"ENTER_INDEX::YDHZZB\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDLJZJYE\",\"value\":43.0},{\"tag\":\"ENTER_INDEX::YDLJZJYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDQTYFKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDWXZCJZ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDYFZKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDYSPJYE\",\"value\":34.0},{\"tag\":\"ENTER_INDEX::YDYSPJYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDYSZKYE\",\"value\":231.0},{\"tag\":\"ENTER_INDEX::YDYSZKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDYXCKYE\",\"value\":341.0},{\"tag\":\"ENTER_INDEX::YDYXCKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDYYSJJFJ\",\"value\":25.0},{\"tag\":\"ENTER_INDEX::YDYYSJJFJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDYYWSZ\",\"value\":9.0},{\"tag\":\"ENTER_INDEX::YDYYWSZ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YDZJGCYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHBDCB\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHBGYYQRJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHCLF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHCQDKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHCWFY\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHDBSRJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHDQJKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHFGRQDFSR\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHFKRJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHGDZCJZ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHGDZCYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHHZCL\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHLJZJYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHM\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHPWF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHQTFY\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHQTYFKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHRJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHRLCB\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHSFJSZYF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHWXZCJZ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHXLF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHY\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHYCRJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHYFZKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHYSPJYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHYSZKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHYXCKYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHYYSJJFJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHYYWSZ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHZGXC\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHZJF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YJHZJGCYE\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YLJBDCB\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YLJCLF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YLJQTFY\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YLJSFJSZYF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YLJXHRLCB\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YLJXLF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YLJYYSJJFJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YLJZGXC\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YLJZJF\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YPJMJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YSJBGYYQRJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YSJDBSRJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YSJFKRJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YSJRJ\",\"value\":8.0},{\"tag\":\"ENTER_INDEX::YSJYCRJ\",\"value\":8.0}]}";
        JSONArray array = JSON.parseObject(json).getJSONArray("tags");
        Map<String,Object> map = Maps.newConcurrentMap();
        for (Object object : array) {
            JSONObject obj = (JSONObject)object;
            String tag = obj.getString("tag");
            Object value = obj.get("value");
            if(map.containsKey(tag)) {
                System.out.println("---1.tag:"+tag+",value:"+map.get(tag));
                System.out.println("---2.tag:"+tag+",value:"+value);
            }else {
                map.put(tag, value); 
            }
        }
    }
    public static void test1() {
        Map<KafkaRecordInfo, Integer> map = new HashMap<KafkaRecordInfo, Integer>();
        KafkaRecordInfo obj = new KafkaRecordInfo();
        System.out.println("count:" + map.get(obj));

        List<String> list = new ArrayList<String>(50000);
        for (int i = 0; i < 50000; i++) {
            list.add("" + i);
        }

        int size = list.size();
        int core = Runtime.getRuntime().availableProcessors() * 2 + 1;
        int pageSize = size / core;
        System.out.println("size:" + size + ",core:" + core + ",pageSize:" + pageSize);
        for (int i = 0; i < core; i++) {
            int from = i * pageSize;
            int to = i + 1 == core ? size : (i + 1) * pageSize - 1;

            System.out.println((i - 0 + 1) + "----" + from + "----" + to);
        }
    }
	public static void main(String[] args) {
	    test();
	}

}
