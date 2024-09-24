package org.formation;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.formation.model.Coursier;
import org.formation.model.Statut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaProducerThread implements Runnable {

	public static String TOPIC ="statutCoursier";
	public static int BATCH=10;
	KafkaProducer<String, Coursier> producer;
	private long nbMessages,sleep;
	private ProducerCallback callback = new ProducerCallback();
	
	private Coursier coursier;

	public static String [] FIRST_NAMES = {"Antoine", "Myrna", "Léopold", "Louise", "Michel", "Gaston", "Samson", "Annie", "Nathalie", "Fabienne"};
	public static String [] LAST_NAMES = {"Dupont", "Durand", "Gauthier", "Thibau", "Jean pierre", "Dardaine", "Dabout", "Potier", "Creach", "Leveque"};


	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerThread.class);


	public KafkaProducerThread(String id, long nbMessages, long sleep) {
		this.nbMessages = nbMessages;
		this.sleep = sleep;
		int randomName = new Random().nextInt(10);
		this.coursier = new Coursier(id, Statut.LIBRE, FIRST_NAMES[Integer.parseInt(id)%10], LAST_NAMES[randomName]);
		
		_initProducer();
		
	}

	@Override
	public void run() {

		int nbBatch=0;
		// Send ten by ten
		for (int i =0; i< nbMessages; i++) {

			if ( nbBatch%10 == 0) {
				Statut[] statuts = Statut.values();
				int randomIndex = new Random().nextInt(statuts.length);

				// Retourner la valeur correspondante à l'index
				coursier.setStatut(statuts[randomIndex]);
			}
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
