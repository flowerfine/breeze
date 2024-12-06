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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.iotdb.sink;

import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.IoTDBDataSourceProperties;
import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.scaleph.common.dict.seatunnel.SeaTunnelPluginMapping;
import cn.sliew.scaleph.plugin.framework.core.PluginInfo;
import cn.sliew.scaleph.plugin.framework.property.PropertyDescriptor;
import cn.sliew.scaleph.plugin.seatunnel.flink.SeaTunnelConnectorPlugin;
import cn.sliew.scaleph.plugin.seatunnel.flink.env.CommonProperties;
import cn.sliew.scaleph.plugin.seatunnel.flink.resource.ResourceProperties;
import cn.sliew.scaleph.plugin.seatunnel.flink.resource.ResourceProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.iotdb.IoTDBProperties.*;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.iotdb.sink.IoTDBSinkProperties.*;

@AutoService(SeaTunnelConnectorPlugin.class)
public class IoTDBSinkPlugin extends SeaTunnelConnectorPlugin {

    public IoTDBSinkPlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "Used to write data to IoTDB.",
                IoTDBSinkPlugin.class.getName());

        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(KEY_DEVICE);
        props.add(KEY_TIMESTAMP);
        props.add(KEY_MEASUREMENT_FIELDS);
        props.add(STORAGE_GROUP);
        props.add(BATCH_SIZE);
        props.add(MAX_RETRIES);
        props.add(RETRY_BACKOFF_MULTIPLIER_MS);
        props.add(MAX_RETRY_BACKOFF_MS);
        props.add(DEFAULT_THRIFT_BUFFER_SIZE);
        props.add(MAX_THRIFT_FRAME_SIZE);
        props.add(ZONE_ID);
        props.add(ENABLE_RPC_COMPRESSION);
        props.add(CONNECTION_TIMEOUT_IN_MS);
        props.add(CommonProperties.PARALLELISM);
        props.add(CommonProperties.SOURCE_TABLE_NAME);
        supportedProperties = Collections.unmodifiableList(props);
    }

    @Override
    public List<ResourceProperty> getRequiredResources() {
        return Collections.singletonList(ResourceProperties.DATASOURCE_RESOURCE);
    }

    @Override
    public ObjectNode createConf() {
        ObjectNode conf = super.createConf();
        JsonNode jsonNode = properties.get(ResourceProperties.DATASOURCE);
        DataSourceInfo dataSourceInfo = JacksonUtil.toObject(jsonNode, DataSourceInfo.class);
        IoTDBDataSourceProperties props = (IoTDBDataSourceProperties) dataSourceInfo.getProps();

        conf.putPOJO(NODE_URLS.getName(), props.getNodeUrls());
        conf.putPOJO(USERNAME.getName(), props.getUsername());
        conf.putPOJO(PASSWORD.getName(), props.getPassword());
        return conf;
    }

    @Override
    protected SeaTunnelPluginMapping getPluginMapping() {
        return SeaTunnelPluginMapping.SINK_IOTDB;
    }
}
