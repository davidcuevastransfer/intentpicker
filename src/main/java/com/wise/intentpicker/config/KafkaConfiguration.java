package com.wise.intentpicker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
public class KafkaConfiguration {

  private static final long POLL_TIMEOUT = 30_000L;

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;

  @Autowired
  private ObjectMapper objectMapper;

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Bytes>> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Bytes> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.getContainerProperties().setPollTimeout(POLL_TIMEOUT);
    factory.setConsumerFactory(consumerFactory());
    factory.setRecordMessageConverter(new JsonMessageConverter(objectMapper));
    factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(500L, 2)));
    return factory;
  }

  @Bean
  public ConsumerFactory<String, Bytes> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    return props;
  }

  @Bean
  KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  ProducerFactory<String, Object> producerFactory(KafkaProperties kafkaProperties, ObjectMapper objectMapper) {
    DefaultKafkaProducerFactory<String, Object> producerFactory =
        new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());

    JsonSerializer<Object> jsonSerializer = new JsonSerializer<>(objectMapper);
    jsonSerializer.setAddTypeInfo(false);

    producerFactory.setValueSerializer(jsonSerializer);
    return producerFactory;
  }

  @Bean
  public ProducerListener<Object, Object> kafkaProducerListener() {
    LoggingProducerListener<Object, Object> producerListener = new LoggingProducerListener<>();
    producerListener.setIncludeContents(false);
    return producerListener;
  }
}
