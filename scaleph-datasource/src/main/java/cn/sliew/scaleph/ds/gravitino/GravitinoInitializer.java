/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *nch
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.scaleph.ds.gravitino;

import cn.sliew.carp.framework.common.dict.datasource.DataSourceType;
import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.jdbc.MySQLDataSourceProperties;
import cn.sliew.carp.module.datasource.service.CarpDsInfoService;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;
import cn.sliew.milky.common.util.JacksonUtil;
import com.datastrato.gravitino.Catalog;
import com.datastrato.gravitino.NameIdentifier;
import com.datastrato.gravitino.client.GravitinoAdminClient;
import com.datastrato.gravitino.client.GravitinoMetalake;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GravitinoInitializer implements InitializingBean {

    @Autowired
    private GravitinoProperties properties;
    @Autowired
    private GravitinoAdminClient adminClient;
    @Autowired
    private CarpDsInfoService dsInfoService;

    @Override
    public void afterPropertiesSet() throws Exception {
        initialize();
    }

    private void initialize() {
        NameIdentifier nameIdentifier = NameIdentifier.ofMetalake(properties.getMetalake());

        // 初始化 metalake
        initMetalake(nameIdentifier);
        // 初始化 catalog
        initDataSource(properties.getMetalake(), DataSourceType.MYSQL);
        initDataSource(properties.getMetalake(), DataSourceType.POSTGRESQL);
        initDataSource(properties.getMetalake(), DataSourceType.HIVE);
        initDataSource(properties.getMetalake(), DataSourceType.ICEBERG);
        initDataSource(properties.getMetalake(), DataSourceType.DORIS);
        initDataSource(properties.getMetalake(), DataSourceType.KAFKA);
        initDataSource(properties.getMetalake(), DataSourceType.HDFS);
    }

    private void initMetalake(NameIdentifier nameIdentifier) {
        if (adminClient.metalakeExists(nameIdentifier) == false) {
            adminClient.createMetalake(nameIdentifier, "scaleph created", Collections.emptyMap());
        }
    }

    private void initDataSource(String metalakeName, DataSourceType type) {
        List<DsInfoDTO> dsInfoDTOS = dsInfoService.listByType(type);
        for (DsInfoDTO dsInfoDTO : dsInfoDTOS) {
            doInitDataSource(metalakeName, type, dsInfoDTO);
        }
    }

    private void doInitDataSource(String metalakeName, DataSourceType type, DsInfoDTO dsInfoDTO) {
        GravitinoMetalake metalake = adminClient.loadMetalake(NameIdentifier.ofMetalake(metalakeName));
        NameIdentifier catalogName = NameIdentifier.ofCatalog(metalakeName, dsInfoDTO.getName());
        if (metalake.catalogExists(catalogName) == false) {
            switch (type) {
                case MYSQL:
                    initMySQL(metalake, catalogName, dsInfoDTO);
                    break;
                case POSTGRESQL:
                    break;
                case HIVE:
                    break;
                case ICEBERG:
                    break;
                case DORIS:
                    break;
                case KAFKA:
                    break;
                case HDFS:
                    break;
                default:
            }
        }
    }

    private void initMySQL(GravitinoMetalake metalake, NameIdentifier catalogName, DsInfoDTO dsInfoDTO) {
        DataSourceInfo dataSourceInfo = JacksonUtil.toObject(JacksonUtil.toJsonNode(dsInfoDTO), DataSourceInfo.class);
        MySQLDataSourceProperties props = (MySQLDataSourceProperties) dataSourceInfo.getProps();
        Map<String, String> properties = new HashMap<>();
        properties.put("jdbc-driver", props.getDriverClassName());
        properties.put("jdbc-url", props.getUrl());
        properties.put("jdbc-user", props.getUser());
        properties.put("jdbc-password", props.getPassword());
        metalake.createCatalog(catalogName, Catalog.Type.RELATIONAL, "jdbc-mysql", dataSourceInfo.getRemark(), properties);
    }
}
