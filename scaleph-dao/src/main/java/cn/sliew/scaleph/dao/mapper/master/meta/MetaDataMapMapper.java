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

package cn.sliew.scaleph.dao.mapper.master.meta;

import cn.sliew.scaleph.dao.entity.master.meta.MetaDataMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 元数据-参考数据映射 Mapper 接口
 * </p>
 *
 * @author liyu
 * @since 2022-04-25
 */
@Repository
public interface MetaDataMapMapper extends BaseMapper<MetaDataMap> {

    Page<MetaDataMap> selectPage(IPage<?> page,
                                 @Param(value = "srcDataSetTypeCode") String srcDataSetTypeCode,
                                 @Param(value = "tgtDataSetTypeCode") String tgtDataSetTypeCode,
                                 @Param(value = "srcDataSetCode") String srcDataSetCode,
                                 @Param(value = "tgtDataSetCode") String tgtDataSetCode,
                                 @Param(value = "auto") boolean auto);
}
