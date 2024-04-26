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

package cn.sliew.scaleph.ds.gravitino;

import com.datastrato.gravitino.client.GravitinoAdminClient;
import com.datastrato.gravitino.client.GravitinoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableConfigurationProperties(GravitinoProperties.class)
public class GravitinoConfig {

    @Autowired
    private GravitinoProperties properties;

    @Bean
    public GravitinoAdminClient gravitinoAdminClient() {
        return GravitinoAdminClient.builder(properties.getUrl())
                .withSimpleAuth()
                .build();
    }

    /**
     * fixme 必须添加 metalakeName
     */
    public GravitinoClient gravitinoClient() {
        return GravitinoClient.builder(properties.getUrl())
                .withSimpleAuth()
                .build();
    }
}
