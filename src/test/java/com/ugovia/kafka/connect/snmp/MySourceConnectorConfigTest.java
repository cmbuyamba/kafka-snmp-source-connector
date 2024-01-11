package com.ugovia.kafka.connect.snmp;

import org.junit.Test;

import com.ugovia.kafka.connect.snmp.source.GetSourceConnectorConfig;

public class MySourceConnectorConfigTest {
  @Test
  public void doc() {
    System.out.println(GetSourceConnectorConfig.conf().toRst());
  }
}