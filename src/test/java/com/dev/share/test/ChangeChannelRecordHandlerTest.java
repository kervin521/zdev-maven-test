package com.dev.share.test;

import java.io.Serializable;
import java.util.List;

import com.dev.share.serializable.SerializableConcurrentListMap;
import com.dev.share.serializable.ShutdownHookHandler;

public class ChangeChannelRecordHandlerTest {
	private volatile static SerializableConcurrentListMap<String,ChangeChannelTask> CHANGE_CHANNEL_MAP = new SerializableConcurrentListMap<String,ChangeChannelTask>();
	private static final String PREFIX = "channel_change_record";
	private static final String OBJECT_SAVE_BASE_DIR="e:/";
	
	public static void init() {
		CHANGE_CHANNEL_MAP.setFilePath(OBJECT_SAVE_BASE_DIR + "/" + ChangeChannelRecordHandlerTest.class.getName() + ".channel_change_record");
		ShutdownHookHandler.add(CHANGE_CHANNEL_MAP);
	}
	/**
	  * 描述:判断通道是否正在反向切换状态
	  * 作者:ZhangYi
	  * 时间:2019年1月4日 上午11:40:24
	  * 参数：(参数列表)
	  * @param fromChannelId
	  * @param toChannelId
	  * @return
	  */
	public static boolean judgeChannelStatus(long fromChannelId, long toChannelId) {
		String key = PREFIX+"_"+toChannelId+"_"+fromChannelId;
		boolean flag = CHANGE_CHANNEL_MAP.contains(key)?true:false;
		if(!flag) {
			put(fromChannelId, toChannelId);
		}
		return flag;
	}
	
	public static void put(long fromChannelId, long toChannelId) {
		String key = PREFIX+"_"+fromChannelId+"_"+toChannelId;
		CHANGE_CHANNEL_MAP.put(key, new ChangeChannelTask(key, fromChannelId, toChannelId));
	}
	public static void remove(long fromChannelId, long toChannelId) {
		String key = PREFIX+"_"+fromChannelId+"_"+toChannelId;
		List<ChangeChannelTask> objs = CHANGE_CHANNEL_MAP.get(key);
		if(objs!=null&&!objs.isEmpty()) {
			for (ChangeChannelTask obj : objs) {
				if(obj.getFromId()==fromChannelId&&obj.getToId()==toChannelId) {
					CHANGE_CHANNEL_MAP.remove(key, obj);
					break;
				}
			}
		}
	}
	public static class ChangeChannelTask implements Serializable{
		private static final long serialVersionUID = -8693206886464832291L;
		private String key;
		private long fromId;
		private long toId;
		public ChangeChannelTask(String key, long fromId, long toId){
			this.key = key;
			this.fromId = fromId;
			this.toId = toId;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public long getFromId() {
			return fromId;
		}
		public void setFromId(long fromId) {
			this.fromId = fromId;
		}
		public long getToId() {
			return toId;
		}
		public void setToId(long toId) {
			this.toId = toId;
		}
	}
	public static void main(String[] args) throws InterruptedException {
		init();
		remove(96, 97);
		System.out.println("96-->97:"+judgeChannelStatus(96, 97));
		System.out.println("97-->96:"+judgeChannelStatus(97, 96));
		System.out.println("96-->97:"+judgeChannelStatus(96, 97));
	}
}
