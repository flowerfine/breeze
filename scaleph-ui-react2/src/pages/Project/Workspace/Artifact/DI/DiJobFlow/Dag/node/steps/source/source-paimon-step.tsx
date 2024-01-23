import React, {useEffect} from 'react';
import {Form} from 'antd';
import {DrawerForm, ProFormText,} from '@ant-design/pro-components';
import {getIntl, getLocale} from "@umijs/max";
import {Node, XFlow} from '@antv/xflow';
import {ModalFormProps} from '@/typings';
import {PaimonParams, STEP_ATTR_TYPE} from '../constant';

const SourcePaimonStepForm: React.FC<ModalFormProps<Node>> = ({data, visible, onVisibleChange, onOK}) => {
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
        />
        <ProFormText
          name={PaimonParams.warehouse}
          label={intl.formatMessage({id: 'pages.project.di.step.paimon.warehouse'})}
          rules={[{required: true}]}
        />
        <ProFormText
          name={PaimonParams.database}
          label={intl.formatMessage({id: 'pages.project.di.step.paimon.database'})}
          rules={[{required: true}]}
        />
        <ProFormText
          name={PaimonParams.table}
          label={intl.formatMessage({id: 'pages.project.di.step.paimon.table'})}
          rules={[{required: true}]}
        />
        <ProFormText
          name={PaimonParams.hdfsSitePath}
          label={intl.formatMessage({id: 'pages.project.di.step.paimon.hdfsSitePath'})}
          placeholder={intl.formatMessage({id: 'pages.project.di.step.paimon.hdfsSitePath.placeholder'})}
        />
      </DrawerForm>
    </XFlow>
  );
};

export default SourcePaimonStepForm;
