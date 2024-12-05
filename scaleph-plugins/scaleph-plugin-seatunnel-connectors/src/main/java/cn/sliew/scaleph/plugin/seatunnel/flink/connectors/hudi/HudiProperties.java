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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.hudi;

import cn.sliew.scaleph.plugin.framework.property.*;

public enum HudiProperties {
    ;

    public static final PropertyDescriptor<String> TABLE_DFS_PATH = new PropertyDescriptor.Builder<String>()
            .name("table_dfs_path")
            .description("The dfs root path of hudi table")
            .type(PropertyType.STRING)
            .parser(Parsers.STRING_PARSER)
            .properties(Property.Required)
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<String> CONF_FILES_PATH = new PropertyDescriptor.Builder<String>()
            .name("conf_files_path")
            .description("The environment conf file path list(local path), which used to init hdfs client to read hudi table file.")
            .type(PropertyType.STRING)
            .parser(Parsers.STRING_PARSER)
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<String> TABLE_NAME = new PropertyDescriptor.Builder<String>()
            .name("table_name")
            .description("The name of hudi table.")
            .type(PropertyType.STRING)
            .parser(Parsers.STRING_PARSER)
            .properties(Property.Required)
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<String> DATABASE = new PropertyDescriptor.Builder<String>()
            .name("database")
            .description("The database of hudi table.")
            .type(PropertyType.STRING)
            .parser(Parsers.STRING_PARSER)
            .properties(Property.Required)
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<String> TABLE_TYPE = new PropertyDescriptor.Builder<String>()
            .name("table.type")
            .description("The type of hudi table. Now we only support 'cow', 'mor' is not support yet.")
            .type(PropertyType.STRING)
            .parser(Parsers.STRING_PARSER)
            .properties(Property.Required)
            .allowableValues("cow")
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();
}
