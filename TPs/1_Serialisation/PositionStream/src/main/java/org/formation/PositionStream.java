package org.formation;

import io.confluent.kafka.schemaregistry.avro.AvroSchema;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.avro.Schema;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Produced;
import org.formation.model.Coursier;
import org.formation.model.CoursierSerde;
import org.formation.model.Position;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class PositionStream {

    public static String APPLICATION_ID = "position-avro";
    public static String BOOTSTRAP_SERVERS = "localhost:19092";
    public static String REGISTRY_URL = "http://localhost:8081";

    public static String INPUT_TOPIC = "position";
    public static String OUTPUT_TOPIC = "position-avro";

    public static void main(String[] args) {


 
    }

    private static void registerSchema() throws IOException, RestClientException {

        // avro schema avsc file path.
        String schemaPath = "/Courier.avsc";
        // subject convention is "<topic-name>-value"
        String subject = OUTPUT_TOPIC + "-value";
        // avsc json string.
//		String schema = null;

        InputStream inputStream = PositionStream.class.getResourceAsStream(schemaPath);

        Schema avroSchema = new Schema.Parser().parse(inputStream);

        CachedSchemaRegistryClient client = new CachedSchemaRegistryClient(REGISTRY_URL, 20);

        client.register(subject, new AvroSchema(avroSchema));

    }
}
