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

package cn.sliew.scaleph.ds.modal.nosql;

import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.scaleph.common.codec.CodecUtil;
import cn.sliew.scaleph.common.dict.ds.RedisMode;
import cn.sliew.scaleph.common.dict.job.DataSourceType;
import cn.sliew.scaleph.common.util.BeanUtil;
import cn.sliew.scaleph.ds.modal.AbstractDataSource;
import cn.sliew.scaleph.ds.service.dto.DsInfoDTO;
import cn.sliew.scaleph.ds.service.dto.DsTypeDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class RedisDataSource extends AbstractDataSource {

    @NotBlank
    @Schema(description = "host")
    private String host;

    @NotNull
    @Schema(description = "port")
    private Integer port;

    @Schema(description = "user")
    private String user;

    @Schema(description = "password")
    private String password;

    @Schema(description = "redis mode, single or cluster")
    private RedisMode mode;

    @Schema(description = "redis nodes information, used in cluster mode")
    private List<Node> nodes;

    @Override
    public DataSourceType getType() {
        return DataSourceType.REDIS;
    }

    @Override
    public DsInfoDTO toDsInfo() {
        DsInfoDTO dto = BeanUtil.copy(this, new DsInfoDTO());
        DsTypeDTO dsType = new DsTypeDTO();
        dsType.setId(getDsTypeId());
        dsType.setType(getType());
        dto.setDsType(dsType);
        Map<String, Object> props = new HashMap<>();
        props.put("host", host);
        props.put("port", port);
        if (StringUtils.hasText(user)) {
            props.put("user", user);
        }
        if (StringUtils.hasText(password)) {
            props.put("password", CodecUtil.encrypt(password));
        }
        if (mode != null) {
            props.put("mode", mode.getValue());
        }
        if (CollectionUtils.isEmpty(nodes) == false) {
            props.put("nodes", JacksonUtil.toJsonString(nodes));
        }
        dto.setProps(props);
        return dto;
    }

    @Data
    public static class Node {

        private String node;
    }
}
