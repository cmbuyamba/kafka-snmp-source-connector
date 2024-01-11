package com.ugovia.kafka.connect.snmp.source;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ugovia.kafka.connect.snmp.VersionUtil;
import com.ugovia.kafka.connect.snmp.client.SnmpGet;
import com.ugovia.kafka.connect.snmp.model.EventLog;

import static com.ugovia.kafka.connect.snmp.source.GetSourceConnectorConfig.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GetSourceTask extends SourceTask {
  static final Logger log = LoggerFactory.getLogger(GetSourceTask.class);

  private String topic;

  static boolean isNotNullOrBlank(String str) {
    return str != null && !str.trim().isEmpty();
  }

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  @Override
  public void start(Map<String, String> props) {
    topic = props.get(TOPIC_CONFIG);
  }

  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    List<EventLog> logs = SnmpGet.snmpGet("166.165.252.96", "public", "1.3.6.1.4.1.1206.4.2.3.9.8.1.0");
    ArrayList<SourceRecord> records = new ArrayList<>();
    logs.forEach(element -> records.add(new SourceRecord(null, null, topic, EventLog.snmpGetSchema(), element.toStruct())));
    TimeUnit.MINUTES.sleep(1);
    return records;
  }

  @Override
  public void stop() {
    log.info("We don’t have any resources to clean up.");
  }
}