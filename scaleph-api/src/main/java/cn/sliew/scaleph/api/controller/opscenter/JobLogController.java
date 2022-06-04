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

package cn.sliew.scaleph.api.controller.opscenter;

import cn.sliew.scaleph.api.annotation.Logging;
import cn.sliew.scaleph.core.di.service.DiJobLogService;
import cn.sliew.scaleph.core.di.service.dto.DiJobLogDTO;
import cn.sliew.scaleph.core.di.service.param.DiJobLogParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "运维中心-任务日志")
@RestController
@RequestMapping(path = "/api/opscenter/log")
public class JobLogController {

    @Autowired
    private DiJobLogService diJobLogService;

    @Logging
    @PostMapping(path = "/batch")
    @ApiOperation(value = "查询周期任务日志", notes = "分页查询周期任务日志")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).OPSCENTER_BATCH_SELECT)")
    public ResponseEntity<Page<DiJobLogDTO>> listBatchJobLog(@RequestBody DiJobLogParam param) {
        Page<DiJobLogDTO> page = this.diJobLogService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/realtime")
    @ApiOperation(value = "查询实时任务日志", notes = "分页查询实时任务日志")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).OPSCENTER_REALTIME_SELECT)")
    public ResponseEntity<Page<DiJobLogDTO>> listRealtimeJobLog(@RequestBody DiJobLogParam param) {
        Page<DiJobLogDTO> page = this.diJobLogService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
