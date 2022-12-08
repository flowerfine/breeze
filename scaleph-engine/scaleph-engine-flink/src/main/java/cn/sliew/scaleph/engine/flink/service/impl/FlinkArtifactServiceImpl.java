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

import cn.sliew.scaleph.common.exception.ScalephException;
import cn.sliew.scaleph.dao.entity.master.flink.FlinkArtifact;
import cn.sliew.scaleph.dao.mapper.master.flink.FlinkArtifactMapper;
import cn.sliew.scaleph.engine.flink.service.FlinkArtifactService;
import cn.sliew.scaleph.engine.flink.service.convert.FlinkArtifactConvert;
import cn.sliew.scaleph.engine.flink.service.dto.FlinkArtifactDTO;
import cn.sliew.scaleph.engine.flink.service.param.FlinkArtifactListParam;
import cn.sliew.scaleph.system.util.I18nUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class FlinkArtifactServiceImpl implements FlinkArtifactService {

    @Autowired
    private FlinkArtifactMapper flinkArtifactMapper;

    @Override
    public Page<FlinkArtifactDTO> list(FlinkArtifactListParam param) {
        final Page<FlinkArtifact> page = flinkArtifactMapper.selectPage(
                new Page<>(param.getCurrent(), param.getPageSize()),
                Wrappers.lambdaQuery(FlinkArtifact.class)
                        .like(StringUtils.hasText(param.getName()), FlinkArtifact::getName, param.getName())
                        .eq(param.getProjectId() != null, FlinkArtifact::getProjectId, param.getProjectId()));
        Page<FlinkArtifactDTO> result =
                new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<FlinkArtifactDTO> dtoList = FlinkArtifactConvert.INSTANCE.toDto(page.getRecords());
        result.setRecords(dtoList);
        return result;
    }

    @Override
    public FlinkArtifactDTO selectOne(Long id) {
        final FlinkArtifact record = flinkArtifactMapper.selectById(id);
        return FlinkArtifactConvert.INSTANCE.toDto(record);
    }

    @Override
    public int insert(FlinkArtifactDTO dto) {
        final FlinkArtifact record = FlinkArtifactConvert.INSTANCE.toDo(dto);
        return flinkArtifactMapper.insert(record);
    }

    @Override
    public int update(FlinkArtifactDTO dto) {
        final FlinkArtifact record = FlinkArtifactConvert.INSTANCE.toDo(dto);
        return flinkArtifactMapper.updateById(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) throws ScalephException {
        FlinkArtifact artifact = flinkArtifactMapper.isUsed(id);
        if (artifact != null) {
            throw new ScalephException(I18nUtil.get("response.error.job.artifact.jar"));
        }
        return flinkArtifactMapper.deleteById(id);
    }

}
