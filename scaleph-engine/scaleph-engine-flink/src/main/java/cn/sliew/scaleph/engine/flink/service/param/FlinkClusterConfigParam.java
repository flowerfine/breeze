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

package cn.sliew.scaleph.engine.flink.service.param;

import cn.sliew.scaleph.common.dict.flink.FlinkDeploymentMode;
import cn.sliew.scaleph.common.dict.flink.FlinkResourceProvider;
import cn.sliew.scaleph.common.dict.flink.FlinkVersion;
import cn.sliew.scaleph.common.param.PaginationParam;
import cn.sliew.scaleph.dao.entity.master.ws.WsFlinkClusterConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

@Data
@EqualsAndHashCode(callSuper = true)
public class FlinkClusterConfigParam extends PaginationParam {

    @ApiModelProperty("项目ID")
    private Long projectId;

    @ApiModelProperty("名称。支持模糊搜索")
    private String name;

    @ApiModelProperty("集群版本")
    private FlinkVersion flinkVersion;

    @ApiModelProperty("Resource。0: Standalone, 1: Native Kubernetes, 2: YARN")
    private FlinkResourceProvider resourceProvider;

    @ApiModelProperty("flink 部署模式。0: Application, 1: Per-Job, 2: Session")
    private FlinkDeploymentMode deployMode;

    public WsFlinkClusterConfig toDo() {
        WsFlinkClusterConfig entity = new WsFlinkClusterConfig();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
