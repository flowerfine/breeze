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

package cn.sliew.scaleph.ds.service.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class DsInfoAddParam {

    @NotNull
    @ApiModelProperty("data source type id")
    private Long dsTypeId;

    @ApiModelProperty("版本")
    private String version;

    @NotBlank
    @ApiModelProperty("name")
    private String name;

    @NotEmpty
    @ApiModelProperty("props")
    private Map<String, Object> props;

    @ApiModelProperty("additional props")
    private Map<String, Object> additionalProps;

    @ApiModelProperty("remark")
    private String remark;
}