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

package cn.sliew.scaleph.engine.flink.service.impl;

import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.scaleph.common.util.BeanUtil;
import cn.sliew.scaleph.dao.entity.master.ws.WsFlinkClusterConfig;
import cn.sliew.scaleph.dao.mapper.master.ws.WsFlinkClusterConfigMapper;
import cn.sliew.scaleph.engine.flink.service.FlinkClusterConfigService;
import cn.sliew.scaleph.engine.flink.service.convert.FlinkClusterConfigConvert;
import cn.sliew.scaleph.engine.flink.service.dto.FlinkClusterConfigDTO;
import cn.sliew.scaleph.engine.flink.service.dto.KubernetesOptions;
import cn.sliew.scaleph.engine.flink.service.param.FlinkClusterConfigParam;
import cn.sliew.scaleph.resource.service.FlinkReleaseService;
import cn.sliew.scaleph.resource.service.dto.FlinkReleaseDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class FlinkClusterConfigServiceImpl implements FlinkClusterConfigService {

    @Autowired
    private WsFlinkClusterConfigMapper flinkClusterConfigMapper;

    @Autowired
    private FlinkReleaseService flinkReleaseService;

    @Override
    public int insert(FlinkClusterConfigDTO param) {
        FlinkReleaseDTO release = flinkReleaseService.selectOne(param.getFlinkRelease().getId());
        param.setFlinkRelease(release);
        param.setFlinkVersion(release.getVersion());
        final WsFlinkClusterConfig record = FlinkClusterConfigConvert.INSTANCE.toDo(param);

        return flinkClusterConfigMapper.insert(record);
    }

    @Override
    public int updateKubernetesOptions(Long id, KubernetesOptions options) {
        WsFlinkClusterConfig record = new WsFlinkClusterConfig();
        record.setId(id);
        record.setKubernetesOptions(JacksonUtil.toJsonString(options));
        return flinkClusterConfigMapper.updateById(record);
    }

    @Override
    public int updateFlinkConfig(Long id, Map<String, String> options) {
        if (CollectionUtils.isEmpty(options)) {
            return 0;
        }
        WsFlinkClusterConfig record = new WsFlinkClusterConfig();
        record.setId(id);
        record.setConfigOptions(JacksonUtil.toJsonString(options));
        return flinkClusterConfigMapper.updateById(record);
    }

    @Override
    public int update(FlinkClusterConfigDTO dto) {
        FlinkReleaseDTO release = flinkReleaseService.selectOne(dto.getFlinkRelease().getId());
        dto.setFlinkRelease(release);
        dto.setFlinkVersion(release.getVersion());
        final WsFlinkClusterConfig record = FlinkClusterConfigConvert.INSTANCE.toDo(dto);
        return flinkClusterConfigMapper.updateById(record);
    }

    @Override
    public int deleteById(Long id) {
        return flinkClusterConfigMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            deleteById(id);
        }
        return ids.size();
    }

    @Override
    public Page<FlinkClusterConfigDTO> listByPage(FlinkClusterConfigParam param) {
        final Page<WsFlinkClusterConfig> page = new Page<>(param.getCurrent(), param.getPageSize());
        WsFlinkClusterConfig wsFlinkClusterConfig = BeanUtil.copy(param, new WsFlinkClusterConfig());
        final Page<WsFlinkClusterConfig> clusterPage = flinkClusterConfigMapper.list(page, wsFlinkClusterConfig);

        Page<FlinkClusterConfigDTO> result =
                new Page<>(clusterPage.getCurrent(), clusterPage.getSize(), clusterPage.getTotal());
        List<FlinkClusterConfigDTO> dtoList = FlinkClusterConfigConvert.INSTANCE.toDto(clusterPage.getRecords());
        result.setRecords(dtoList);
        return result;
    }

    @Override
    public FlinkClusterConfigDTO selectOne(Long id) {
        WsFlinkClusterConfig record = flinkClusterConfigMapper.getById(id);
        return FlinkClusterConfigConvert.INSTANCE.toDto(record);
    }
}
