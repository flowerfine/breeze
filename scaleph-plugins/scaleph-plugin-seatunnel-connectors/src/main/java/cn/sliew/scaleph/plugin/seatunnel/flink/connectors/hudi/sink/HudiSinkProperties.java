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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.hudi.sink;

import cn.sliew.scaleph.plugin.framework.property.Parsers;
import cn.sliew.scaleph.plugin.framework.property.PropertyDescriptor;
import cn.sliew.scaleph.plugin.framework.property.PropertyType;
import cn.sliew.scaleph.plugin.framework.property.Validators;
import com.fasterxml.jackson.databind.JsonNode;

public enum HudiSinkProperties {
    ;

    public static final PropertyDescriptor<JsonNode> TABLE_LIST = new PropertyDescriptor.Builder()
            .name("table_list")
            .description("Table list.")
            .type(PropertyType.OBJECT)
            .parser(Parsers.JSON_PARSER)
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<Boolean> AUTO_COMMIT = new PropertyDescriptor.Builder()
            .name("auto_commit")
            .description("Automatic transaction commit is enabled by default.")
            .type(PropertyType.BOOLEAN)
            .parser(Parsers.BOOLEAN_PARSER)
            .addValidator(Validators.BOOLEAN_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<String> RECORD_KEY_FIELDS = new PropertyDescriptor.Builder<String>()
            .name("record_key_fields")
            .description("The record key fields of hudi table, its are used to generate record key")
            .type(PropertyType.STRING)
            .parser(Parsers.STRING_PARSER)
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<String> PARTITION_FIELDS = new PropertyDescriptor.Builder()
            .name("partition_fields")
            .description("The partition key fields of hudi table, its are used to generate partition")
            .type(PropertyType.BOOLEAN)
            .parser(Parsers.BOOLEAN_PARSER)
            .defaultValue("false")
            .addValidator(Validators.BOOLEAN_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<String> INDEX_TYPE = new PropertyDescriptor.Builder<String>()
            .name("index_type")
            .description("The index type of hudi table.")
            .type(PropertyType.STRING)
            .parser(Parsers.STRING_PARSER)
            .allowableValues("BLOOM", "SIMPLE", "GLOBAL SIMPLE")
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<String> INDEX_CLASS_NAME = new PropertyDescriptor.Builder<String>()
            .name("index_class_name")
            .description("The customized index classpath of hudi table")
            .type(PropertyType.STRING)
            .parser(Parsers.STRING_PARSER)
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<Integer> RECORD_BYTE_SIZE = new PropertyDescriptor.Builder()
            .name("record_byte_size")
            .description("The byte size of each record")
            .type(PropertyType.INT)
            .parser(Parsers.INTEGER_PARSER)
            .addValidator(Validators.POSITIVE_INTEGER_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<String> OP_TYPE = new PropertyDescriptor.Builder()
            .name("op_type")
            .description("The operation type of hudi table")
            .type(PropertyType.STRING)
            .parser(Parsers.STRING_PARSER)
            .allowableValues("insert", "upsert", "bulk_insert")
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<Integer> BATCH_INTERVAL_MS = new PropertyDescriptor.Builder()
            .name("batch_interval_ms")
            .description("The interval time of batch write to hudi table.")
            .type(PropertyType.INT)
            .parser(Parsers.INTEGER_PARSER)
            .addValidator(Validators.POSITIVE_INTEGER_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<Integer> BATCH_SIZE = new PropertyDescriptor.Builder()
            .name("batch_size")
            .description("The size of batch write to hudi table.")
            .type(PropertyType.INT)
            .parser(Parsers.INTEGER_PARSER)
            .addValidator(Validators.POSITIVE_INTEGER_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<Integer> INSERT_SHUFFLE_PARALLELISM = new PropertyDescriptor.Builder()
            .name("insert_shuffle_parallelism")
            .description("The parallelism of insert data to hudi table.")
            .type(PropertyType.INT)
            .parser(Parsers.INTEGER_PARSER)
            .addValidator(Validators.POSITIVE_INTEGER_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<Integer> UPSERT_SHUFFLE_PARALLELISM = new PropertyDescriptor.Builder()
            .name("upsert_shuffle_parallelism")
            .description("The parallelism of upsert data to hudi table.")
            .type(PropertyType.INT)
            .parser(Parsers.INTEGER_PARSER)
            .addValidator(Validators.POSITIVE_INTEGER_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<Integer> MIN_COMMITS_TO_KEEP = new PropertyDescriptor.Builder()
            .name("min_commits_to_keep")
            .description("The min commits to keep of hudi table.")
            .type(PropertyType.INT)
            .parser(Parsers.INTEGER_PARSER)
            .addValidator(Validators.POSITIVE_INTEGER_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<Integer> MAX_COMMITS_TO_KEEP = new PropertyDescriptor.Builder()
            .name("max_commits_to_keep")
            .description("The max commits to keep of hudi table.")
            .type(PropertyType.INT)
            .parser(Parsers.INTEGER_PARSER)
            .addValidator(Validators.POSITIVE_INTEGER_VALIDATOR)
            .validateAndBuild();


}
