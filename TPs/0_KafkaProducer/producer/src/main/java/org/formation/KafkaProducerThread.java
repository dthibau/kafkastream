package org.formation;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.formation.model.Coursier;
import org.formation.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaProducerThread implements Runnable {

	public static String TOPIC ="position";
	public static int BATCH=10;
	KafkaProducer<String, Coursier> producer;
	private long nbMessages,sleep;
	private ProducerCallback callback = new ProducerCallback();
	
	private Coursier coursier;


	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerThread.class);


	public KafkaProducerThread(String id, long nbMessages, long sleep) {
		this.nbMessages = nbMessages;
		this.sleep = sleep;
		this.coursier = new Coursier(id, new Position(Math.random() + 45, Math.random() + 2));
		
		_initProducer();
		
	}

	@Override
	public void run() {

		// Send ten by ten
		for (int i =0; i< nbMessages; i++) {

			coursier.move();
			ProducerRecord<String, Coursier> producerRecord = new ProducerRecord<String, Coursier>(TOPIC, coursier.getId(), coursier);
			producer.send(producerRecord,callback);

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.err.println("INTERRUPTED");
			}

		}
		
	}
	

	
	private void _initProducer() {
		Properties kafkaProps = new Properties();
		kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,KafkaProducerApplication.props.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
		kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,KafkaProducerApplication.props.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
		kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProducerApplication.props.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
		kafkaProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);
		kafkaProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,4);
		kafkaProps.put(ProducerConfig.RETRIES_CONFIG,Integer.MAX_VALUE);
		kafkaProps.put(ProducerConfig.ACKS_CONFIG,"all");


		producer = new KafkaProducer<String, Coursier>(kafkaProps);
	}
}
