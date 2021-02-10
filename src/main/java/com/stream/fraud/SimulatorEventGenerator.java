/**
 * 
 */
package com.stream.fraud;

import static com.stream.telecom.integration.LocalKafka.*;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonSerializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.fraud.model.TxnEvent;
import com.stream.telecom.model.TelecomCallRecord;

/**
 * @author bdutt
 *
 */
public class SimulatorEventGenerator {
	
	
	public static Producer createProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		return new KafkaProducer<>(props);
	}
	
	static void runProducer() {
		Producer producer = createProducer();
		ObjectMapper objectMapper = new ObjectMapper();

		for (int index = 0; index < MESSAGE_COUNT; index++) {
			ProducerRecord record =  null;

				TxnEvent txnEvent = new TxnEvent();
				txnEvent.setAmount(10.0);
				txnEvent.setUserId("123");
				txnEvent.setHoursFromLastTxn(100);
				txnEvent.setMilesFromLastTxn(100);
				JsonNode  jsonNode = objectMapper.valueToTree(txnEvent);
				
				record = new ProducerRecord<Long, JsonNode>(TOPIC_NAME,
						jsonNode);
				
			
			try {
				RecordMetadata metadata = (RecordMetadata) producer.send(record).get();
				System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
						+ " with offset " + metadata.offset());
				Thread.sleep(5000);
			} catch (ExecutionException e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			} catch (InterruptedException e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		runProducer();
	}
	
}