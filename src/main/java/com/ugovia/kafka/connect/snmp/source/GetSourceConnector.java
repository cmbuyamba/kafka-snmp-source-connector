package com.ugovia.kafka.connect.snmp.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ugovia.kafka.connect.snmp.VersionUtil;

public class GetSourceConnector extends SourceConnector {
  private static Logger log = LoggerFactory.getLogger(GetSourceConnector.class);
  private Map<String, String> configProperties;

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  @Override
  public void start(Map<String, String> props) {
    log.info("Starting up SnmpGet Source connector");
    try {
      configProperties = setupSourcePropertiesWithDefaultsIfMissing(props);
    } catch (ConfigException e) {
      throw new ConnectException("Couldn't start GetSourceConnector due to configuration error", e);
    }
  }

  @Override
  public Class<? extends Task> taskClass() {
    return GetSourceTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int maxTasks) {
    if (maxTasks != 1) {
      log.info("Ignoring maxTasks as there can only be one.");
    }
    List<Map<String, String>> configs = new ArrayList<>(maxTasks);
    Map<String, String> taskConfig = new HashMap<>();
    taskConfig.putAll(configProperties);
    configs.add(taskConfig);
    return configs;
  }

  @Override
  public void stop() {
    log.info("We don’t have any resources to clean up.");
  }

  @Override
  public ConfigDef config() {
    return GetSourceConnectorConfig.conf();
  }

  private Map<String, String> setupSourcePropertiesWithDefaultsIfMissing(Map<String, String> props)
      throws ConfigException {
    return new GetSourceConnectorConfig(props).returnPropertiesWithDefaultsValuesIfMissing();
  }
}
