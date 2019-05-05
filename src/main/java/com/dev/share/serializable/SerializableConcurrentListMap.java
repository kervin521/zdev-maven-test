package com.dev.share.serializable;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @company 空间畅想
 * 
 */
public class SerializableConcurrentListMap<K extends Serializable, T extends Serializable> extends ShutdownHookHandler {
	//
	private ConcurrentHashMap<K, List<T>> map = new ConcurrentHashMap<K, List<T>>();
	// 队列序列化保存路径
	private String filePath;

	public ConcurrentHashMap<K, List<T>> get() {
		return map;
	}

	public boolean contains(K key) {
		return map.containsKey(key);
	}

	public void set(K key, List<T> objs) {
		map.put(key, objs);
	}

	public void put(K key, List<T> objs) {
		if (!map.containsKey(key)) {
			map.put(key, new ArrayList<T>());
		}
		map.get(key).addAll(objs);
	}

	public void put(K key, T obj) {
		if (!map.containsKey(key)) {
			map.put(key, new ArrayList<T>());
		}
		map.get(key).add(obj);
	}

	public List<T> get(K key) {
		return map.get(key);
	}

	public void remove(K key) {
		map.remove(key);
	}

	public void remove(K key, T obj) {
		List<T> objs = map.get(key);
		if (objs != null && objs.contains(obj)) {
			objs.remove(obj);
		}
		if (objs == null || objs.isEmpty()) {
			remove(key);
		}
	}

	/**
	 * 系统退出时调用，将队列序列化到文件
	 */
	public synchronized void shutdown() {
		if (map != null && !map.isEmpty()) {
			if (filePath != null) {
				File f = new File(filePath);
				if (f.exists()) {
					f.delete();
				}
				f = null;
				try {
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
					try {
						out.writeObject(map);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 队列初始化时调用，从文件反序列化
	 * 
	 * @param filePath
	 *                 文件路径
	 */
	@SuppressWarnings("unchecked")
	public synchronized void setFilePath(String filePath) {
		this.filePath = filePath;
		if (filePath != null) {
			File f = new File(filePath);
			if (f.exists()) {
				try {
					ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
					try {
						ConcurrentHashMap<K, List<T>> res = (ConcurrentHashMap<K, List<T>>) in.readObject();
						if (res != null) {
							map = res;
						}
					} catch (EOFException ex) {
					}
					in.close();
					f.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
