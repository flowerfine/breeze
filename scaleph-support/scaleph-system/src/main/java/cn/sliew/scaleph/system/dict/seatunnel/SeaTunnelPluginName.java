/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.scaleph.system.dict.seatunnel;

import cn.sliew.milky.common.primitives.Enums;
import cn.sliew.scaleph.system.dict.DictDefinition;
import cn.sliew.scaleph.system.dict.DictInstance;
import cn.sliew.scaleph.system.dict.DictType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SeaTunnelPluginName implements DictInstance {

    FAKESOURCE("FakeSource", "Fake"),
    ASSERT("Assert", "Assert"),
    SOCKET("Socket", "Socket"),
    CONSOLE("Console", "Console"),
    EMAIL("Email", "Email"),
    HTTP("Http", "Http"),
    FEISHU("Feishu", "Feishu"),
    DINGTALK("DingTalk", "DingTalk"),

    LOCAL_FILE("LocalFile", "LocalFile"),
    FTP_FILE("FtpFile", "FtpFile"),
    HDFS_FILE("HdfsFile", "HdfsFile"),
    OSS_FILE("OssFile", "OssFile"),

    KAFKA("Kafka", "Kafka"),
    PULSAR("Pulsar", "Pulsar"),
    DATAHUB("DataHub", "DataHub"),

    JDBC("Jdbc", "Jdbc"),
    REDIS("Redis", "Redis"),
    ELASTICSEARCH("elasticsearch", "elasticsearch"),

    HIVE("Hive", "Hive"),
    CLICKHOUSE("Clickhouse", "Clickhouse"),
    CLICKHOUSE_FILE("ClickhouseFile", "ClickhouseFile"),
    HUDI("Hudi", "Hudi"),
    KUDU("Kudu", "Kudu"),
    IOTDB("IoTDB", "IoTDB"),
    NEO4J("Neo4j", "Neo4j"),
    ;

    @JsonCreator
    public static SeaTunnelPluginName of(@JsonProperty("code") String code) {
        return Enums.toEnum(code, SeaTunnelPluginName.class)
                .orElseThrow(() -> new EnumConstantNotPresentException(SeaTunnelPluginName.class, code));
    }

    private String code;
    private String value;

    SeaTunnelPluginName(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public DictDefinition getDefinition() {
        return DictType.SEATUNNEL_PLUGIN_NAME;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
