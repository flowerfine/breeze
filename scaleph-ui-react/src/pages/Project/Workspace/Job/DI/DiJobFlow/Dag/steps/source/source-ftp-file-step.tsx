import {NsGraph} from "@antv/xflow";
import {ModalFormProps} from '@/app.d';
import {JobService} from "@/services/project/job.service";
import {Form, message, Modal} from "antd";
import {DiJob} from "@/services/project/typings";
import {getIntl, getLocale} from "umi";
import {
  ProForm,
  ProFormDependency,
  ProFormDigit,
  ProFormGroup,
  ProFormList,
  ProFormSelect, ProFormSwitch,
  ProFormText
} from "@ant-design/pro-components";
import {useEffect} from "react";
import {InfoCircleOutlined} from "@ant-design/icons";
import { BaseFileParams, FtpFileParams, SchemaParams, STEP_ATTR_TYPE } from "../../constant";
import { StepSchemaService } from "../schema";

const SourceFtpFileStepForm: React.FC<ModalFormProps<{
  node: NsGraph.INodeConfig;
  graphData: NsGraph.IGraphData;
  graphMeta: NsGraph.IGraphMeta;
}>> = ({data, visible, onCancel, onOK}) => {
  const nodeInfo = data.node.data;
  const jobInfo = data.graphMeta.origin as DiJob;
  const jobGraph = data.graphData;
  const intl = getIntl(getLocale(), true);
  const [form] = Form.useForm();

  useEffect(() => {
    form.setFieldValue(STEP_ATTR_TYPE.stepTitle, nodeInfo.data.displayName);
  }, []);

  return (<Modal
    open={visible}
    title={nodeInfo.data.displayName}
    width={780}
    bodyStyle={{overflowY: 'scroll', maxHeight: '640px'}}
    destroyOnClose={true}
    onCancel={onCancel}
    onOk={() => {
      form.validateFields().then((values) => {
        let map: Map<string, any> = new Map();
        map.set(STEP_ATTR_TYPE.jobId, jobInfo.id);
        map.set(STEP_ATTR_TYPE.jobGraph, JSON.stringify(jobGraph));
        map.set(STEP_ATTR_TYPE.stepCode, nodeInfo.id);
        StepSchemaService.formatSchema(values)
        map.set(STEP_ATTR_TYPE.stepAttrs, values);
        JobService.saveStepAttr(map).then((resp) => {
          if (resp.success) {
            message.success(intl.formatMessage({id: 'app.common.operate.success'}));
            onCancel();
            onOK ? onOK() : null;
          }
        });
      });
    }}
  >
    <ProForm form={form} initialValues={nodeInfo.data.attrs} grid={true} submitter={false}>
      <ProFormText
        name={STEP_ATTR_TYPE.stepTitle}
        label={intl.formatMessage({id: 'pages.project.di.step.stepTitle'})}
        rules={[{required: true}, {max: 120}]}
      />
      <ProFormText
        name={FtpFileParams.host}
        label={intl.formatMessage({id: 'pages.project.di.step.ftpFile.host'})}
        rules={[{required: true}]}
        colProps={{span: 12}}
      />
      <ProFormDigit
        name={FtpFileParams.port}
        label={intl.formatMessage({id: 'pages.project.di.step.ftpFile.port'})}
        rules={[{required: true}]}
        colProps={{span: 12}}
        min={0}
        max={65535}
      />
      <ProFormText
        name={FtpFileParams.username}
        label={intl.formatMessage({id: 'pages.project.di.step.ftpFile.username'})}
        rules={[{required: true}]}
        colProps={{span: 12}}
      />
      <ProFormText
        name={FtpFileParams.password}
        label={intl.formatMessage({id: 'pages.project.di.step.ftpFile.password'})}
        rules={[{required: true}]}
        colProps={{span: 12}}
      />
      <ProFormText
        name={BaseFileParams.path}
        label={intl.formatMessage({id: 'pages.project.di.step.baseFile.path'})}
        rules={[{required: true}]}
      />
      <ProFormSelect
        name={"type"}
        label={intl.formatMessage({id: 'pages.project.di.step.baseFile.type'})}
        rules={[{required: true}]}
        valueEnum={{
          json: "json",
          parquet: "parquet",
          orc: "orc",
          text: "text",
          csv: "csv"
        }}
      />
      <ProFormDependency name={["type"]}>
        {({type}) => {
          if (type == "json") {
            return (
              <ProFormGroup
                label={intl.formatMessage({id: 'pages.project.di.step.schema'})}
                tooltip={{
                  title: intl.formatMessage({id: 'pages.project.di.step.schema.tooltip'}),
                  icon: <InfoCircleOutlined/>,
                }}
              >
                <ProFormList
                  name={SchemaParams.fields}
                  copyIconProps={false}
                  creatorButtonProps={{
                    creatorButtonText: intl.formatMessage({id: 'pages.project.di.step.schema.fields'}),
                    type: 'text',
                  }}>
                  <ProFormGroup>
                    <ProFormText
                      name={SchemaParams.field}
                      label={intl.formatMessage({id: 'pages.project.di.step.schema.fields.field'})}
                      colProps={{span: 10, offset: 1}}
                    />
                    <ProFormText
                      name={SchemaParams.type}
                      label={intl.formatMessage({id: 'pages.project.di.step.schema.fields.type'})}
                      colProps={{span: 10, offset: 1}}
                    />
                  </ProFormGroup>
                </ProFormList>
              </ProFormGroup>
            );
          }
          return <ProFormGroup/>;
        }}
      </ProFormDependency>
      <ProFormText
        name={BaseFileParams.delimiter}
        label={intl.formatMessage({id: 'pages.project.di.step.baseFile.delimiter'})}
        initialValue={"\\001"}
      />
      <ProFormSwitch
        name={BaseFileParams.parsePartitionFromPath}
        label={intl.formatMessage({id: 'pages.project.di.step.baseFile.parsePartitionFromPath'})}
        initialValue={true}
      />
      <ProFormSelect
        name={BaseFileParams.dateFormat}
        label={intl.formatMessage({id: 'pages.project.di.step.baseFile.dateFormat'})}
        initialValue={"yyyy-MM-dd"}
        options={["yyyy-MM-dd", "yyyy.MM.dd", "yyyy/MM/dd"]}
      />
      <ProFormSelect
        name={BaseFileParams.timeFormat}
        label={intl.formatMessage({id: 'pages.project.di.step.baseFile.timeFormat'})}
        initialValue={"HH:mm:ss"}
        options={["HH:mm:ss", "HH:mm:ss.SSS"]}
      />
      <ProFormSelect
        name={BaseFileParams.datetimeFormat}
        label={intl.formatMessage({id: 'pages.project.di.step.baseFile.datetimeFormat'})}
        initialValue={"yyyy-MM-dd HH:mm:ss"}
        options={["yyyy-MM-dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyyMMddHHmmss"]}
      />
    </ProForm>
  </Modal>);
}

export default SourceFtpFileStepForm;