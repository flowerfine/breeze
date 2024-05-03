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

package cn.sliew.scaleph.workflow.service;

import cn.sliew.scaleph.common.dict.workflow.WorkflowTaskInstanceStage;
import cn.sliew.scaleph.workflow.service.dto.WorkflowTaskDefinitionDTO;
import cn.sliew.scaleph.workflow.service.dto.WorkflowTaskInstanceDTO;
import cn.sliew.scaleph.workflow.service.param.WorkflowTaskInstanceListParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.graph.Graph;

import java.util.List;

public interface WorkflowTaskInstanceService {

    Page<WorkflowTaskInstanceDTO> list(WorkflowTaskInstanceListParam param);

    List<WorkflowTaskInstanceDTO> list(Long workflowInstanceId);

    Graph<WorkflowTaskInstanceDTO> getDag(Long workflowInstanceId, Graph<WorkflowTaskDefinitionDTO> dag);

    WorkflowTaskInstanceDTO get(Long id);

    void updateState(Long id, WorkflowTaskInstanceStage stage, WorkflowTaskInstanceStage nextStage, String message);

    void updateSuccess(Long id);

    void updateFailure(Long id, Throwable throwable);

    void updateTaskId(Long id, String taskId);

    Graph<WorkflowTaskInstanceDTO> initialize(Long workflowInstanceId, Graph<WorkflowTaskDefinitionDTO> graph);

    void deploy(Long id);

    void shutdown(Long id);

    void suspend(Long id);

    void resume(Long id);
}
