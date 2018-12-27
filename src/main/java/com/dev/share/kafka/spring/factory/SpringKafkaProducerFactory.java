package com.dev.share.kafka.spring.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.dev.share.kafka.KafkaRecordInfo;
import com.dev.share.kafka.spring.KafkaProducerService;
import com.dev.share.kafka.spring.support.CommonKafkaConfig;
/**
 * @decription Kafka服务
 * @author yi.zhang
 * @time 2017年6月8日 下午2:39:42
 * @since 1.0
 * @jdk 1.8
 */
public class SpringKafkaProducerFactory extends CommonKafkaConfig{
	private static Logger logger = LogManager.getLogger(SpringKafkaProducerFactory.class);
	private KafkaProducerService producer = new KafkaProducerService();
	
	public SpringKafkaProducerFactory(){
		super();
	}
	
	public SpringKafkaProducerFactory(String servers,boolean isZookeeper,String zookeeper_servers,String acks){
		super(servers, isZookeeper, zookeeper_servers, acks);
	}

	/**
	 * @decription 初始化配置
	 * @author yi.zhang
	 * @time 2017年6月2日 下午2:15:57
	 */
	private void init() {
		try {
			Map<String, Object> productor_config = new HashMap<String, Object> ();
			if(isZookeeper&&zookeeper_servers!=null){
				productor_config.put("zk.connect", zookeeper_servers);
			}
			productor_config.put("bootstrap.servers", servers);
			// “所有”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。(The "all" setting we have specified will result in blocking on the full commit of the record, the slowest but most durable setting.)
			productor_config.put("acks", acks);
			// 如果请求失败，生产者也会自动重试，即使设置成０ the producer can automatically retry.
			productor_config.put("retries", 0);
			// The producer maintains buffers of unsent records for each partition.
			productor_config.put("batch.size", 16384);
			// 默认立即发送，这里这是延时毫秒数
			productor_config.put("linger.ms", 1);
			// 生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
			productor_config.put("buffer.memory", 33554432);
			// The key.serializer and value.serializer instruct how to turn the key and value objects the user provides with their ProducerRecord into bytes.
			productor_config.put("key.serializer", StringSerializer.class.getName());
			productor_config.put("value.serializer", StringSerializer.class.getName());
			// 创建kafka的生产者类
			DefaultKafkaProducerFactory<String,String> factory = new DefaultKafkaProducerFactory<String,String>(productor_config);
			KafkaTemplate<String, String> template = new KafkaTemplate<String,String>(factory);
			template.setProducerListener(producer.callback());
			producer.setTemplate(template);
			logger.setLevel(Level.WARN);
		} catch (Exception e) {
			logger.error("-----Kafka Config init Error-----", e);
		}
	}
	public void start() {
		init();
	}
	/**
	 * 关闭服务
	 */
	public void close(){
		if(producer!=null){
//			producer.close();
		}
	}
	/**
	 * @decription 生产者推送数据
	 * @author yi.zhang
	 * @time 2017年6月8日 下午2:24:05
	 * @param data
	 */
	public void send(KafkaRecordInfo obj){
		producer.sendMsg(obj);
//		ListenableFuture<SendResult<String, String>> furture = producer.send(new ProducerRecord<String,String>(KAFKA_TOPIC,JSON.toJSONString(obj)));
//		furture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//			@Override
//			public void onSuccess(SendResult<String, String> obj) {
//				String topic  = obj.getProducerRecord().topic();
//				String content = obj.getProducerRecord().value();
//				System.out.println("\n\n\n\n----------kafka producer success!topic:"+topic+",data:"+content+"\n\n\n\n");
//			}
//			@Override
//			public void onFailure(Throwable ex) {
//				System.out.println("\n\n\n\n----------kafka producer Error!"+ex.getMessage()+"\n\n\n\n");
//			}
//		});
	}
}
