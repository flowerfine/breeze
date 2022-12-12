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

package cn.sliew.scaleph.core.di.service.impl;

import cn.sliew.scaleph.core.di.service.DiJobAttrService;
import cn.sliew.scaleph.core.di.service.convert.DiJobAttrConvert;
import cn.sliew.scaleph.core.di.service.dto.DiJobAttrDTO;
import cn.sliew.scaleph.dao.entity.master.ws.WsDiJobAttr;
import cn.sliew.scaleph.dao.mapper.master.ws.WsDiJobAttrMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author gleiyu
 */
@Service
public class DiJobAttrServiceImpl implements DiJobAttrService {

    @Autowired
    private WsDiJobAttrMapper diJobAttrMapper;

    @Override
    public List<DiJobAttrDTO> listJobAttr(Long jobId) {
        List<WsDiJobAttr> list = diJobAttrMapper.selectList(new LambdaQueryWrapper<WsDiJobAttr>()
                .eq(WsDiJobAttr::getJobId, jobId)
        );
        return DiJobAttrConvert.INSTANCE.toDto(list);
    }

    @Override
    public int upsert(DiJobAttrDTO jobAttrDTO) {
        WsDiJobAttr jobAttr = DiJobAttrConvert.INSTANCE.toDo(jobAttrDTO);
        WsDiJobAttr attr = diJobAttrMapper.selectOne(
                new LambdaQueryWrapper<WsDiJobAttr>()
                        .eq(WsDiJobAttr::getJobId, jobAttr.getJobId())
                        .eq(WsDiJobAttr::getJobAttrType, jobAttr.getJobAttrType())
                        .eq(WsDiJobAttr::getJobAttrKey, jobAttr.getJobAttrKey())
        );
        if (attr == null) {
            return diJobAttrMapper.insert(jobAttr);
        }

        return diJobAttrMapper.update(jobAttr,
                new LambdaUpdateWrapper<WsDiJobAttr>()
                        .eq(WsDiJobAttr::getJobId, jobAttr.getJobId())
                        .eq(WsDiJobAttr::getJobAttrType, jobAttr.getJobAttrType())
                        .eq(WsDiJobAttr::getJobAttrKey, jobAttr.getJobAttrKey())
        );
    }

    @Override
    public int deleteByProjectId(Collection<? extends Serializable> projectIds) {
        return diJobAttrMapper.deleteByProjectId(projectIds);
    }

    @Override
    public int deleteByJobId(Collection<? extends Serializable> jobIds) {
        return diJobAttrMapper.deleteByJobId(jobIds);
    }

    @Override
    public int clone(Long sourceJobId, Long targetJobId) {
        return diJobAttrMapper.clone(sourceJobId, targetJobId);
    }
}
