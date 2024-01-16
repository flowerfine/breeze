import React, {useEffect} from 'react';
import {Button, Drawer, Form} from 'antd';
import {ProForm, ProFormSelect, ProFormText, ProFormTextArea,} from '@ant-design/pro-components';
import {getIntl, getLocale} from "@umijs/max";
import {Node, XFlow} from '@antv/xflow';
import {ModalFormProps} from '@/typings';
import {CassandraParams, STEP_ATTR_TYPE} from '../constant';
import {StepSchemaService} from '../helper';
import DataSourceItem from "@/pages/Project/Workspace/Artifact/DI/DiJobFlow/Dag/node/steps/dataSource";

const SourceCassandraStepForm: React.FC<ModalFormProps<Node>> = ({data, visible, onCancel, onOK}) => {
  const intl = getIntl(getLocale());
  const [form] = Form.useForm();

  useEffect(() => {
    form.setFieldValue(STEP_ATTR_TYPE.stepTitle, data.data.label);
  }, []);

  return (
    <XFlow>
      <Drawer
        open={visible}
        title={data.data.label}
        width={780}
        bodyStyle={{overflowY: 'scroll'}}
        destroyOnClose={true}
        onClose={onCancel}
        extra={
          <Button
            type="primary"
            onClick={() => {
              form.validateFields().then((values) => {
                StepSchemaService.formatSchema(values);
                if (onOK) {
                  onOK(values);
                }
              });
            }}
          >
            {intl.formatMessage({id: 'app.common.operate.confirm.label'})}
          </Button>
        }
      >
        <ProForm form={form} initialValues={data.data.attrs} grid={true} submitter={false}>
          <ProFormText
            name={STEP_ATTR_TYPE.stepTitle}
            label={intl.formatMessage({id: 'pages.project.di.step.stepTitle'})}
            rules={[{required: true}, {max: 120}]}
          />
          <DataSourceItem dataSource={'Cassandra'}/>
          <ProFormSelect
            name={CassandraParams.consistencyLevel}
            label={intl.formatMessage({id: 'pages.project.di.step.cassandra.consistencyLevel'})}
            initialValue={'LOCAL_ONE'}
            options={[
              'ANY',
              'ONE',
              'TWO',
              'THREE',
              'QUORUM',
              'ALL',
              'LOCAL_ONE',
              'LOCAL_QUORUM',
              'EACH_QUORUM',
              'SERIAL',
              'LOCAL_SERIAL',
            ]}
          />
          <ProFormTextArea
            name={CassandraParams.cql}
            label={intl.formatMessage({id: 'pages.project.di.step.cassandra.cql'})}
            rules={[{required: true}]}
          />
        </ProForm>
      </Drawer>
    </XFlow>
  );
};

export default SourceCassandraStepForm;
