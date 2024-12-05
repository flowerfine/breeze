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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.hudi.sink;

import cn.sliew.scaleph.common.dict.seatunnel.SeaTunnelPluginMapping;
import cn.sliew.scaleph.plugin.framework.core.PluginInfo;
import cn.sliew.scaleph.plugin.framework.property.PropertyDescriptor;
import cn.sliew.scaleph.plugin.seatunnel.flink.SeaTunnelConnectorPlugin;
import cn.sliew.scaleph.plugin.seatunnel.flink.env.CommonProperties;
import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.SaveModeProperties.SCHEMA_SAVE_MODE;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.hudi.HudiProperties.CONF_FILES_PATH;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.hudi.HudiProperties.TABLE_DFS_PATH;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.hudi.sink.HudiSinkProperties.AUTO_COMMIT;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.hudi.sink.HudiSinkProperties.TABLE_LIST;

@AutoService(SeaTunnelConnectorPlugin.class)
public class HudiSinkPlugin extends SeaTunnelConnectorPlugin {

    public HudiSinkPlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "Hudi sink connector",
                HudiSinkPlugin.class.getName());

        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(TABLE_DFS_PATH);
        props.add(CONF_FILES_PATH);
        props.add(AUTO_COMMIT);
        props.add(SCHEMA_SAVE_MODE);
        props.add(TABLE_LIST);
        props.add(CommonProperties.PARALLELISM);
        props.add(CommonProperties.RESULT_TABLE_NAME);
        supportedProperties = Collections.unmodifiableList(props);
    }

    @Override
    protected SeaTunnelPluginMapping getPluginMapping() {
        return SeaTunnelPluginMapping.SINK_HUDI;
    }
}
