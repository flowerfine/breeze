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

package cn.sliew.scaleph.workflow.api.dto;

import cn.sliew.scaleph.common.dto.BaseDTO;

public class WorkflowDefinitionDTO extends BaseDTO {

    /**
     * 名称
     */
    private String name;

    /**
     * 状态。启动，暂停，运行中
     */
    private Integer status;

    /**
     * 类型。java（pipeline，DAG，job），http，shell 等
     */
    private Integer type;

    /**
     * 参数
     */
    private String param;

    /**
     * 版本
     */
    private Integer version;









}
