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

package cn.sliew.scaleph.application.oam.service.impl;

import cn.sliew.scaleph.application.oam.model.definition.ComponentDefinition;
import cn.sliew.scaleph.application.oam.service.OamComponentDefinitionService;
import cn.sliew.scaleph.application.oam.service.convert.OamComponentDefinitionConvert;
import cn.sliew.scaleph.dao.entity.master.oam.OamComponentDefinition;
import cn.sliew.scaleph.dao.mapper.master.oam.OamComponentDefinitionMapper;
import cn.sliew.scaleph.system.model.PaginationParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OamComponentDefinitionServiceImpl implements OamComponentDefinitionService {

    @Autowired
    private OamComponentDefinitionMapper oamComponentDefinitionMapper;

    @Override
    public Page<ComponentDefinition> listByPage(PaginationParam param) {
        Page<OamComponentDefinition> list = oamComponentDefinitionMapper.selectPage(
                new Page<>(param.getCurrent(), param.getPageSize()),
                Wrappers.lambdaQuery(OamComponentDefinition.class).orderByAsc(OamComponentDefinition::getId)
        );
        Page<ComponentDefinition> result = new Page<>(list.getCurrent(), list.getSize(), list.getTotal());
        result.setRecords(OamComponentDefinitionConvert.INSTANCE.toDto(list.getRecords()));
        return result;
    }

    @Override
    public List<ComponentDefinition> listAll() {
        LambdaQueryWrapper<OamComponentDefinition> queryWrapper = Wrappers.lambdaQuery(OamComponentDefinition.class)
                .orderByAsc(OamComponentDefinition::getId);
        List<OamComponentDefinition> oamComponentDefinitions = oamComponentDefinitionMapper.selectList(queryWrapper);
        return OamComponentDefinitionConvert.INSTANCE.toDto(oamComponentDefinitions);
    }

    @Override
    public ComponentDefinition selectOne(Long id) {
        OamComponentDefinition record = oamComponentDefinitionMapper.selectById(id);
        return OamComponentDefinitionConvert.INSTANCE.toDto(record);
    }
}