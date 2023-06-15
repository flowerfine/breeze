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

package cn.sliew.scaleph.engine.flink.kubernetes.service.impl;

import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.scaleph.common.dict.flink.kubernetes.ResourceLifecycleState;
import cn.sliew.scaleph.common.util.UUIDUtil;
import cn.sliew.scaleph.dao.entity.master.ws.WsFlinkKubernetesSessionCluster;
import cn.sliew.scaleph.dao.mapper.master.ws.WsFlinkKubernetesSessionClusterMapper;
import cn.sliew.scaleph.engine.flink.kubernetes.operator.status.FlinkDeploymentStatus;
import cn.sliew.scaleph.engine.flink.kubernetes.resource.sessioncluster.FlinkSessionCluster;
import cn.sliew.scaleph.engine.flink.kubernetes.resource.sessioncluster.FlinkSessionClusterConverter;
import cn.sliew.scaleph.engine.flink.kubernetes.service.WsFlinkKubernetesSessionClusterService;
import cn.sliew.scaleph.engine.flink.kubernetes.service.WsFlinkKubernetesTemplateService;
import cn.sliew.scaleph.engine.flink.kubernetes.service.convert.WsFlinkKubernetesSessionClusterConvert;
import cn.sliew.scaleph.engine.flink.kubernetes.service.dto.WsFlinkKubernetesSessionClusterDTO;
import cn.sliew.scaleph.engine.flink.kubernetes.service.dto.WsFlinkKubernetesTemplateDTO;
import cn.sliew.scaleph.engine.flink.kubernetes.service.param.WsFlinkKubernetesSessionClusterListParam;
import cn.sliew.scaleph.engine.flink.kubernetes.service.param.WsFlinkKubernetesSessionClusterSelectListParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static cn.sliew.milky.common.check.Ensures.checkState;

@Service
public class WsFlinkKubernetesSessionClusterServiceImpl implements WsFlinkKubernetesSessionClusterService {

    @Autowired
    private WsFlinkKubernetesSessionClusterMapper wsFlinkKubernetesSessionClusterMapper;
    @Autowired
    private WsFlinkKubernetesTemplateService wsFlinkKubernetesTemplateService;

    @Override
    public Page<WsFlinkKubernetesSessionClusterDTO> list(WsFlinkKubernetesSessionClusterListParam param) {
        Page<WsFlinkKubernetesSessionCluster> page = wsFlinkKubernetesSessionClusterMapper.selectPage(
                new Page<>(param.getCurrent(), param.getPageSize()),
                Wrappers.lambdaQuery(WsFlinkKubernetesSessionCluster.class)
                        .eq(WsFlinkKubernetesSessionCluster::getProjectId, param.getProjectId())
                        .eq(param.getClusterCredentialId() != null, WsFlinkKubernetesSessionCluster::getClusterCredentialId, param.getClusterCredentialId())
                        .like(StringUtils.hasText(param.getName()), WsFlinkKubernetesSessionCluster::getName, param.getName()));
        Page<WsFlinkKubernetesSessionClusterDTO> result =
                new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<WsFlinkKubernetesSessionClusterDTO> dtoList = WsFlinkKubernetesSessionClusterConvert.INSTANCE.toDto(page.getRecords());
        result.setRecords(dtoList);
        return result;
    }

    @Override
    public List<Long> listAll() {
        LambdaQueryWrapper<WsFlinkKubernetesSessionCluster> queryWrapper = Wrappers.lambdaQuery(WsFlinkKubernetesSessionCluster.class)
                .select(WsFlinkKubernetesSessionCluster::getId);
        List<WsFlinkKubernetesSessionCluster> wsFlinkKubernetesSessionClusters = wsFlinkKubernetesSessionClusterMapper.selectList(queryWrapper);
        return wsFlinkKubernetesSessionClusters.stream().map(WsFlinkKubernetesSessionCluster::getId).collect(Collectors.toList());
    }

    @Override
    public List<WsFlinkKubernetesSessionClusterDTO> listAll(WsFlinkKubernetesSessionClusterSelectListParam param) {
        LambdaQueryWrapper<WsFlinkKubernetesSessionCluster> queryWrapper = Wrappers.lambdaQuery(WsFlinkKubernetesSessionCluster.class)
                .eq(param.getProjectId() != null, WsFlinkKubernetesSessionCluster::getProjectId, param.getProjectId())
                .like(StringUtils.hasText(param.getName()), WsFlinkKubernetesSessionCluster::getName, param.getName());
        List<WsFlinkKubernetesSessionCluster> wsFlinkKubernetesSessionClusters = wsFlinkKubernetesSessionClusterMapper.selectList(queryWrapper);
        return WsFlinkKubernetesSessionClusterConvert.INSTANCE.toDto(wsFlinkKubernetesSessionClusters);
    }

    @Override
    public WsFlinkKubernetesSessionClusterDTO selectOne(Long id) {
        WsFlinkKubernetesSessionCluster record = wsFlinkKubernetesSessionClusterMapper.selectById(id);
        checkState(record != null, () -> "flink kubernetes session cluster not exist for id = " + id);
        return WsFlinkKubernetesSessionClusterConvert.INSTANCE.toDto(record);
    }

    @Override
    public WsFlinkKubernetesSessionClusterDTO fromTemplate(Long templateId) {
        WsFlinkKubernetesTemplateDTO wsFlinkKubernetesTemplateDTO = wsFlinkKubernetesTemplateService.selectOne(templateId);
        WsFlinkKubernetesSessionClusterDTO wsFlinkKubernetesSessionClusterDTO = new WsFlinkKubernetesSessionClusterDTO();
        wsFlinkKubernetesSessionClusterDTO.setKubernetesOptions(wsFlinkKubernetesTemplateDTO.getKubernetesOptions());
        wsFlinkKubernetesSessionClusterDTO.setJobManager(wsFlinkKubernetesTemplateDTO.getJobManager());
        wsFlinkKubernetesSessionClusterDTO.setTaskManager(wsFlinkKubernetesTemplateDTO.getTaskManager());
        wsFlinkKubernetesSessionClusterDTO.setPodTemplate(wsFlinkKubernetesTemplateDTO.getPodTemplate());
        wsFlinkKubernetesSessionClusterDTO.setFlinkConfiguration(wsFlinkKubernetesTemplateDTO.getFlinkConfiguration());
        wsFlinkKubernetesSessionClusterDTO.setLogConfiguration(wsFlinkKubernetesTemplateDTO.getLogConfiguration());
        wsFlinkKubernetesSessionClusterDTO.setIngress(wsFlinkKubernetesTemplateDTO.getIngress());
        wsFlinkKubernetesSessionClusterDTO.setRemark("generated from template-" + wsFlinkKubernetesTemplateDTO.getName());
        return wsFlinkKubernetesSessionClusterDTO;
    }

    @Override
    public FlinkSessionCluster asYAML(WsFlinkKubernetesSessionClusterDTO dto) {
        return FlinkSessionClusterConverter.INSTANCE.convertTo(dto);
    }

    @Override
    public int insert(WsFlinkKubernetesSessionClusterDTO dto) {
        WsFlinkKubernetesSessionCluster record = WsFlinkKubernetesSessionClusterConvert.INSTANCE.toDo(dto);
        record.setSessionClusterId(UUIDUtil.randomUUId());
        return wsFlinkKubernetesSessionClusterMapper.insert(record);
    }

    @Override
    public int update(WsFlinkKubernetesSessionClusterDTO dto) {
        WsFlinkKubernetesSessionCluster record = WsFlinkKubernetesSessionClusterConvert.INSTANCE.toDo(dto);
        return wsFlinkKubernetesSessionClusterMapper.updateById(record);
    }

    @Override
    public int updateStatus(Long id, FlinkDeploymentStatus status) {
        if (status == null) {
            return -1;
        }
        WsFlinkKubernetesSessionCluster record = new WsFlinkKubernetesSessionCluster();
        record.setId(id);
        record.setState(EnumUtils.getEnum(ResourceLifecycleState.class, status.getLifecycleState().name()));
        record.setError(status.getError());
        if (CollectionUtils.isEmpty(status.getClusterInfo())) {
            record.setClusterInfo(null);
        } else {
            record.setClusterInfo(JacksonUtil.toJsonString(status.getClusterInfo()));
        }
        if (status.getTaskManager() == null) {
            record.setTaskManagerInfo(null);
        } else {
            record.setTaskManagerInfo(JacksonUtil.toJsonString(status.getTaskManager()));
        }
        return wsFlinkKubernetesSessionClusterMapper.updateById(record);
    }

    @Override
    public int clearStatus(Long id) {
        WsFlinkKubernetesSessionCluster record = new WsFlinkKubernetesSessionCluster();
        record.setId(id);
        record.setState(null);
        record.setError(null);
        record.setClusterInfo(null);
        record.setTaskManagerInfo(null);
        return wsFlinkKubernetesSessionClusterMapper.updateById(record);
    }

    @Override
    public int deleteById(Long id) {
        return wsFlinkKubernetesSessionClusterMapper.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return wsFlinkKubernetesSessionClusterMapper.deleteBatchIds(ids);
    }

}
