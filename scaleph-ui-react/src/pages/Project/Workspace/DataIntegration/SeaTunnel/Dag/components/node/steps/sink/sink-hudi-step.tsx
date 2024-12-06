import React, {useEffect} from 'react';
import {Form} from 'antd';
import {InfoCircleOutlined} from "@ant-design/icons";
import {
  DrawerForm,
  ProFormDigit,
  ProFormGroup,
  ProFormList,
  ProFormSelect,
  ProFormSwitch,
  ProFormText
} from '@ant-design/pro-components';
import {getIntl, getLocale} from "@umijs/max";
import {Node, XFlow} from '@antv/xflow';
import {ModalFormProps} from '@/typings';
import {CommonConfigParams, HttpParams, HudiParams, STEP_ATTR_TYPE} from '../constant';
import {StepSchemaService} from "../helper";
import SaveModeItem
  from "@/pages/Project/Workspace/DataIntegration/SeaTunnel/Dag/components/node/steps/common/saveMode";

const SinkHudiStepForm: React.FC<ModalFormProps<Node>> = ({data, visible, onVisibleChange, onOK}) => {
  const intl = getIntl(getLocale());
  const [form] = Form.useForm();

  useEffect(() => {
    form.setFieldValue(STEP_ATTR_TYPE.stepTitle, data.data.label);
  }, []);

  return (
    <XFlow>
      <DrawerForm
        title={data.data.label}
        form={form}
        initialValues={data.data.attrs}
        open={visible}
        onOpenChange={onVisibleChange}
        grid={true}
        width={780}
        drawerProps={{
          styles: {body: {overflowY: 'scroll'}},
          closeIcon: null,
          destroyOnClose: true
        }}
        onFinish={(values) => {
          if (onOK) {
            StepSchemaService.formatTableListConfig(values, HudiParams.tableList, HudiParams.tableList);
            onOK(values)
            return Promise.resolve(true)
          }
          return Promise.resolve(false)
        }}
      >
        <ProFormText
          name={STEP_ATTR_TYPE.stepTitle}
          label={intl.formatMessage({id: 'pages.project.di.step.stepTitle'})}
          rules={[{required: true}, {max: 120}]}
          colProps={{span: 24}}
        />

        <ProFormText
          name={HudiParams.tableDfsPath}
          label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableDfsPath'})}
          placeholder={intl.formatMessage({id: 'pages.project.di.step.hudi.tableDfsPath.placeholder'})}
          rules={[{required: true}]}
        />
        <ProFormText
          name={HudiParams.confFilesPath}
          label={intl.formatMessage({id: 'pages.project.di.step.hudi.confFilesPath'})}
          placeholder={intl.formatMessage({id: 'pages.project.di.step.hudi.confFilesPath.placeholder'})}
        />
        <ProFormSwitch
          name={HudiParams.autoCommit}
          label={intl.formatMessage({id: 'pages.project.di.step.hudi.autoCommit'})}
          initialValue={true}
        />

        <SaveModeItem/>

        <ProFormGroup
          title={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList'})}
          tooltip={{
            title: intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.tooltip'}),
            icon: <InfoCircleOutlined/>,
          }}
          collapsible={true}
          defaultCollapsed={true}
        >

          <ProFormList
            name={HudiParams.tableList + CommonConfigParams.commonConfig}
            creatorButtonProps={{
              creatorButtonText: intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.table'}),
              type: 'text',
            }}
          >
            <ProFormGroup
              title={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.table'})}
              collapsible={true}
              defaultCollapsed={false}
            >
              <ProFormText
                name={HudiParams.database}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.database'})}
                colProps={{span: 5, offset: 1}}
                initialValue={"default"}
              />
              <ProFormText
                name={HudiParams.tableName}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.tableName'})}
                rules={[{required: true}]}
                colProps={{span: 5, offset: 1}}
              />
              <ProFormSelect
                name={HudiParams.tableType}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.tableType'})}
                colProps={{span: 5, offset: 1}}
                allowClear={false}
                options={["COPY_ON_WRITE", "MERGE_ON_READ"]}
                initialValue={"COPY_ON_WRITE"}
              />
              <ProFormSelect
                name={HudiParams.opType}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.opType'})}
                colProps={{span: 5, offset: 1}}
                allowClear={false}
                options={["insert", "upsert", "bulk_insert"]}
                initialValue={"insert"}
              />

              <ProFormText
                name={HudiParams.recordKeyFields}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.recordKeyFields'})}
                colProps={{span: 23, offset: 1}}
              />
              <ProFormText
                name={HudiParams.partitionFields}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.partitionFields'})}
                colProps={{span: 23, offset: 1}}
              />
              <ProFormDigit
                name={HudiParams.recordByteSize}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.recordByteSize'})}
                colProps={{span: 7, offset: 1}}
                initialValue={1024}
                fieldProps={{
                  min: 1,
                  step: 1024
                }}
              />
              <ProFormDigit
                name={HudiParams.batchSize}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.batchSize'})}
                colProps={{span: 7, offset: 1}}
                initialValue={1000}
                fieldProps={{
                  min: 1,
                  step: 1000
                }}
              />
              <ProFormDigit
                name={HudiParams.batchIntervalMs}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.batchIntervalMs'})}
                colProps={{span: 7, offset: 1}}
                initialValue={1000}
                fieldProps={{
                  min: 1,
                  step: 1000
                }}
              />
              <ProFormDigit
                name={HudiParams.minCommitsToKeep}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.minCommitsToKeep'})}
                colProps={{span: 5, offset: 1}}
                initialValue={20}
                fieldProps={{
                  min: 1,
                  step: 1
                }}
              />
              <ProFormDigit
                name={HudiParams.maxCommitsToKeep}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.maxCommitsToKeep'})}
                colProps={{span: 5, offset: 1}}
                initialValue={30}
                fieldProps={{
                  min: 1,
                  step: 1
                }}
              />
              <ProFormDigit
                name={HudiParams.insertShuffleParallelism}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.insertShuffleParallelism'})}
                colProps={{span: 5, offset: 1}}
                initialValue={2}
                fieldProps={{
                  min: 1,
                  step: 1
                }}
              />
              <ProFormDigit
                name={HudiParams.upsertShuffleParallelism}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.upsertShuffleParallelism'})}
                colProps={{span: 5, offset: 1}}
                initialValue={2}
                fieldProps={{
                  min: 1,
                  step: 1
                }}
              />
              <ProFormSelect
                name={HudiParams.indexType}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.indexType'})}
                colProps={{span: 5, offset: 1}}
                options={["BLOOM", "SIMPLE", "GLOBAL SIMPLE"]}
                initialValue={"BLOOM"}
              />
              <ProFormText
                name={HudiParams.indexClassName}
                label={intl.formatMessage({id: 'pages.project.di.step.hudi.tableList.indexClassName'})}
                colProps={{span: 17, offset: 1}}
              />
            </ProFormGroup>
          </ProFormList>
        </ProFormGroup>

      </DrawerForm>
    </XFlow>
  );
};

export default SinkHudiStepForm;
