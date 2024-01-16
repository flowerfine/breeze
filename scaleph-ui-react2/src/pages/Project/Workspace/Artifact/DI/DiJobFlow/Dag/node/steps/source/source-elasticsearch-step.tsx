import React, {useEffect} from 'react';
import {Button, Drawer, Form} from 'antd';
import {InfoCircleOutlined} from "@ant-design/icons";
import {
  ProForm,
  ProFormDigit,
  ProFormGroup,
  ProFormList,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-components';
import {getIntl, getLocale} from "@umijs/max";
import {Node, XFlow} from '@antv/xflow';
import {ModalFormProps} from '@/typings';
import {ElasticsearchParams, SchemaParams, STEP_ATTR_TYPE} from '../constant';
import {StepSchemaService} from '../helper';
import DataSourceItem from "@/pages/Project/Workspace/Artifact/DI/DiJobFlow/Dag/node/steps/dataSource";

const SourceElasticsearchStepForm: React.FC<ModalFormProps<Node>> = ({data, visible, onCancel, onOK}) => {
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
          <DataSourceItem dataSource={"Elasticsearch"}/>
          <ProFormText
            name={ElasticsearchParams.index}
            label={intl.formatMessage({id: 'pages.project.di.step.elasticsearch.index'})}
            rules={[{required: true}]}
          />
          <ProFormTextArea
            name={ElasticsearchParams.query}
            label={intl.formatMessage({id: 'pages.project.di.step.elasticsearch.query'})}
            initialValue={"{\"match_all\": {}}"}
          />
          <ProFormText
            name={ElasticsearchParams.scrollTime}
            label={intl.formatMessage({id: 'pages.project.di.step.elasticsearch.scrollTime'})}
            colProps={{span: 12}}
            initialValue={"1m"}
          />
          <ProFormDigit
            name={ElasticsearchParams.scrollSize}
            label={intl.formatMessage({id: 'pages.project.di.step.elasticsearch.scrollSize'})}
            colProps={{span: 12}}
            initialValue={100}
            fieldProps={{
              min: 1,
              step: 100
            }}
          />
          <ProFormGroup
            label={intl.formatMessage({id: 'pages.project.di.step.elasticsearch.source'})}
          >
            <ProFormList
              name={ElasticsearchParams.sourceArray}
              copyIconProps={false}
              creatorButtonProps={{
                creatorButtonText: intl.formatMessage({id: 'pages.project.di.step.elasticsearch.source.field'}),
                type: 'text',
              }}
            >
              <ProFormText
                name={ElasticsearchParams.sourceField}
                colProps={{span: 16, offset: 4}}
              />
            </ProFormList>
          </ProFormGroup>
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
              }}
            >
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
        </ProForm>
      </Drawer>
    </XFlow>
  );
};

export default SourceElasticsearchStepForm;
