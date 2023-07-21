import {useIntl} from "umi";
import React from "react";
import {ProFormGroup, ProFormRadio} from "@ant-design/pro-components";
import {DictDataService} from "@/services/admin/dictData.service";
import {DICT_TYPE} from "@/constant";

const FlinkKubernetesJobDeployStateStepForm: React.FC = () => {
  const intl = useIntl();

  return (
    <ProFormGroup>
      <ProFormRadio.Group
        name={"upgradeMode"}
        label={intl.formatMessage({id: 'pages.project.flink.kubernetes.job.detail.deploy.state.upgradeMode'})}
        rules={[{required: true}]}
        request={() => {
          return DictDataService.listDictDataByType2(DICT_TYPE.upgradeMode)
        }}
      />

    </ProFormGroup>
  );
}

export default FlinkKubernetesJobDeployStateStepForm;
