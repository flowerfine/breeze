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

package cn.sliew.scaleph.engine.flink.kubernetes.resource.definition;

import cn.sliew.scaleph.common.dict.image.ImagePullPolicy;
import cn.sliew.scaleph.dao.entity.master.ws.WsFlinkArtifactJar;
import cn.sliew.scaleph.engine.flink.kubernetes.operator.spec.JobManagerSpec;
import cn.sliew.scaleph.engine.flink.kubernetes.resource.job.FlinkDeploymentJob;
import cn.sliew.scaleph.engine.flink.kubernetes.service.dto.WsFlinkKubernetesJobDTO;
import cn.sliew.scaleph.kubernetes.resource.definition.ResourceCustomizer;
import io.fabric8.kubernetes.api.model.*;

import java.util.*;

public enum FileFetcherFactory implements ResourceCustomizer<WsFlinkKubernetesJobDTO, FlinkDeploymentJob> {
    INSTANCE;

    private static final String FILE_FETCHER_CONTAINER_NAME = "scaleph-file-fetcher";
    //    private static final String FILE_FETCHER_CONTAINER_IMAGE = "ghcr.io/flowerfine/scaleph/scaleph-file-fetcher:latest";
    private static final String FILE_FETCHER_CONTAINER_IMAGE = "scaleph-file-fetcher:dev";

    private static final Map<String, Quantity> FILE_FETCHER_CONTAINER_CPU = Map.of("cpu", Quantity.parse("250m"));
    private static final Map<String, Quantity> FILE_FETCHER_CONTAINER_MEMORY = Map.of("memory", Quantity.parse("512Mi"));

    private static final String FILE_FETCHER_VOLUME_NAME = "file-fetcher-volume";
    public static final String TARGET_DIRECTORY = "/flink/usrlib/";
    public static final String LOCAL_SCHEMA = "local://";
    public static final String LOCAL_PATH = LOCAL_SCHEMA + TARGET_DIRECTORY;

    @Override
    public void customize(WsFlinkKubernetesJobDTO jobDTO, FlinkDeploymentJob job) {
        JobManagerSpec jobManager = Optional.ofNullable(job.getSpec().getJobManager()).orElse(new JobManagerSpec());
        PodBuilder builder = Optional.of(jobManager).map(JobManagerSpec::getPodTemplate).map(pod -> new PodBuilder(pod)).orElse(new PodBuilder());
        doCustomize(jobDTO, builder);
        jobManager.setPodTemplate(builder.build());
        job.getSpec().setJobManager(jobManager);
    }

    private void doCustomize(WsFlinkKubernetesJobDTO jobDTO, PodBuilder builder) {
        addAdditionalJars(jobDTO, builder);
        addArtifactJar(jobDTO, builder);
    }

    private void addArtifactJar(WsFlinkKubernetesJobDTO jobDTO, PodBuilder builder) {
        switch (jobDTO.getDeploymentKind()) {
            case FLINK_DEPLOYMENT:
                doAddJars(jobDTO.getFlinkArtifactJar(), builder);
                return;
            case FLINK_SESSION_JOB:
            default:
        }
    }

    private void addAdditionalJars(WsFlinkKubernetesJobDTO jobDTO, PodBuilder builder) {
        switch (jobDTO.getDeploymentKind()) {
            case FLINK_DEPLOYMENT:
//                doAddJars(builder);
                return;
            case FLINK_SESSION_JOB:
            default:
        }
    }

    private void doAddJars(WsFlinkArtifactJar jarArtifact, PodBuilder builder) {
        PodFluent.SpecNested<PodBuilder> spec = builder.editOrNewSpec();
        spec.addToInitContainers(addJarArtifact(jarArtifact));
        builder.withSpec(spec.endSpec().buildSpec());
    }

    private Container addJarArtifact(WsFlinkArtifactJar jarArtifact) {
        ContainerBuilder builder = new ContainerBuilder();
        builder.withName(FILE_FETCHER_CONTAINER_NAME);
        builder.withImage(FILE_FETCHER_CONTAINER_IMAGE);
        builder.withImagePullPolicy(ImagePullPolicy.IF_NOT_PRESENT.getValue());
        builder.withArgs(buildFileFetcherArgs(jarArtifact));
        builder.withResources(buildResource());
        builder.withVolumeMounts(buildVolumeMount());
        builder.withTerminationMessagePath("/dev/termination-log");
        builder.withTerminationMessagePolicy("File");
        return builder.build();
    }

    private void addAdditionalJars(PodFluent.SpecNested<PodBuilder> spec, WsFlinkArtifactJar jarArtifact) {

    }

    private List<String> buildFileFetcherArgs(WsFlinkArtifactJar jarArtifact) {
        return Arrays.asList("-uri", jarArtifact.getPath(),
                "-path", TARGET_DIRECTORY + jarArtifact.getFileName());
    }

    private ResourceRequirements buildResource() {
        ResourceRequirementsBuilder resourceRequirementsBuilder = new ResourceRequirementsBuilder();
        Map resource = new HashMap();
        resource.putAll(FILE_FETCHER_CONTAINER_CPU);
        resource.putAll(FILE_FETCHER_CONTAINER_MEMORY);
        resourceRequirementsBuilder.addToRequests(resource);
        resourceRequirementsBuilder.addToLimits(resource);
        return resourceRequirementsBuilder.build();
    }

    private VolumeMount buildVolumeMount() {
        VolumeMountBuilder builder = new VolumeMountBuilder();
        builder.withName(FILE_FETCHER_VOLUME_NAME);
        builder.withMountPath(TARGET_DIRECTORY);
        return builder.build();
    }

}
