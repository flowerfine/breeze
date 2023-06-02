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

package cn.sliew.scaleph.engine.flink.kubernetes.factory;

import cn.sliew.scaleph.system.util.BeanUtil;
import cn.sliew.scaleph.engine.flink.kubernetes.operator.spec.FlinkSessionClusterSpec;
import cn.sliew.scaleph.engine.flink.kubernetes.resource.sessioncluster.FlinkSessionCluster;
import cn.sliew.scaleph.engine.flink.kubernetes.resource.template.FlinkTemplate;
import cn.sliew.scaleph.engine.flink.kubernetes.resource.template.FlinkTemplateSpec;

public enum FlinkSessionClusterFactory {
    ;

    public static FlinkSessionCluster create(FlinkTemplate template) {
        FlinkSessionCluster sessionCluster = new FlinkSessionCluster();
        sessionCluster.setMetadata(template.getMetadata());
        FlinkTemplateSpec templateSpec = template.getSpec();
        FlinkSessionClusterSpec sessionClusterSpec = BeanUtil.copy(templateSpec, new FlinkSessionClusterSpec());
        sessionCluster.setSpec(sessionClusterSpec);
        return sessionCluster;
    }
}
