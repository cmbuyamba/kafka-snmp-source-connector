package com.ugovia.kafka.connect.snmp.source;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.HashMap;
import java.util.Map;

public class GetSourceConnectorConfig extends AbstractConfig {

  public static final String TOPIC_CONFIG = "sensor.events.topic";
  private static final String TOPIC_DEFAULT = "sensor-events";
  private static final String TOPIC_DOC = "Topic to publish sensor data to.";

  public GetSourceConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
    super(config, parsedConfig);
  }

  public GetSourceConnectorConfig(Map<String, String> props) {
    this(conf(), props);
  }

  public static ConfigDef conf() {
    return new ConfigDef()
        .define(TOPIC_CONFIG, ConfigDef.Type.STRING, TOPIC_DEFAULT, new MyNonEmptyStringWithoutControlChars(),
            ConfigDef.Importance.HIGH, TOPIC_DOC);
  }

  Map<String, String> returnPropertiesWithDefaultsValuesIfMissing() {
    Map<String, ?> uncastProperties = this.values();
    Map<String, String> config = new HashMap<>(uncastProperties.size());
    uncastProperties.forEach((key, valueToBeCast) -> config.put(key, valueToBeCast.toString()));

    return config;
  }
}

final class MyNonEmptyStringWithoutControlChars extends ConfigDef.NonEmptyStringWithoutControlChars {
  // Only here to create nice human readable for exporting to documentation.
  @Override
  public String toString() {
    return "non-empty string and no ISO control characters";
  }
}
