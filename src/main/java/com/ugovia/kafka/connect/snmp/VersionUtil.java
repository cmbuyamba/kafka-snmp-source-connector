package com.ugovia.kafka.connect.snmp;

/**
 * Created by Celestin on 1/8/24.
 */
public class VersionUtil {
  private VersionUtil(){}
  public static String getVersion() {
    try {
      return VersionUtil.class.getPackage().getImplementationVersion();
    } catch(Exception ex){
      return "0.0.0.0";
    }
  }
}
