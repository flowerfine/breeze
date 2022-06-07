package cn.sliew.scaleph.plugin.seatunnel.flink.connector.druid.source;

import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.scaleph.common.enums.JobStepTypeEnum;
import cn.sliew.scaleph.plugin.framework.core.AbstractPlugin;
import cn.sliew.scaleph.plugin.framework.core.PluginInfo;
import cn.sliew.scaleph.plugin.framework.property.PropertyDescriptor;
import cn.sliew.scaleph.plugin.seatunnel.flink.SeatunnelNativeFlinkConnector;
import cn.sliew.scaleph.plugin.seatunnel.flink.common.CommonProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.sliew.scaleph.plugin.seatunnel.flink.connector.druid.source.DruidSourceProperties.*;


public class DruidSourceConnector extends AbstractPlugin implements SeatunnelNativeFlinkConnector {

    private static final List<PropertyDescriptor> supportedProperties;

    static {
        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(JDBC_URL);
        props.add(DATASOURCE);
        props.add(START_DATE);
        props.add(END_DATE);
        props.add(COLUMNS);
        props.add(PARALLELISM);

        props.add(CommonProperties.RESULT_TABLE_NAME);
        props.add(CommonProperties.FIELD_NAME);
        supportedProperties = Collections.unmodifiableList(props);
    }

    private final PluginInfo pluginInfo;

    public DruidSourceConnector() {
        this.pluginInfo = new PluginInfo("DruidSource", "druid source connector", "2.1.1", DruidSourceConnector.class.getName());
    }

    @Override
    public ObjectNode createConf() {
        ObjectNode objectNode = JacksonUtil.createObjectNode();
        for (PropertyDescriptor descriptor : getSupportedProperties()) {
            if (properties.contains(descriptor)) {
                objectNode.put(descriptor.getName(), properties.getValue(descriptor));
            }
        }
        return objectNode;
    }

    @Override
    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    @Override
    public JobStepTypeEnum getStepType() {
        return JobStepTypeEnum.SOURCE;
    }

    @Override
    public List<PropertyDescriptor> getSupportedProperties() {
        return supportedProperties;
    }

}
