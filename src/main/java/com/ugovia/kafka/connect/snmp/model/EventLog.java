package com.ugovia.kafka.connect.snmp.model;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventLog {
    public static final String OID = "OID";
    public static final String VARIABLE = "VARIABLE";
    public static final String DEVICE_IP = "DEVICE_IP";

    public static final Schema SCHEMA = SchemaBuilder.struct()
            .name(EventLog.class.getSimpleName())
            .field(OID, Schema.OPTIONAL_STRING_SCHEMA)
            .field(VARIABLE, Schema.OPTIONAL_STRING_SCHEMA)
            .field(DEVICE_IP, Schema.OPTIONAL_STRING_SCHEMA)
            .build();

    private String oid;
    private String variable;
    private String deviceIp;

    public static Schema snmpGetSchema() {
        return SCHEMA;
    }

    public Struct toStruct() {
        return new Struct(snmpGetSchema()).put(DEVICE_IP, getDeviceIp())
                .put(OID, getOid()).put(VARIABLE, getVariable());
    }
}
