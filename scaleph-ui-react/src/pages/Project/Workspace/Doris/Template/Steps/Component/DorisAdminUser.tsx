import {useIntl} from "umi";
import {ProCard, ProFormDigit, ProFormGroup, ProFormList, ProFormText} from "@ant-design/pro-components";

const DorisAdminUser: React.FC = () => {
  const intl = useIntl();

  return (<ProCard
    title={intl.formatMessage({id: 'pages.project.doris.template.steps.component.admin'})}
    headerBordered
    collapsible={true}>
    <ProFormText
      name="admin.name"
      label={intl.formatMessage({id: 'pages.project.doris.template.steps.component.admin.name'})}
      colProps={{span: 10, offset: 1}}
      rules={[{required: true}]}
      initialValue={"admin"}
    />
    <ProFormText
      name="admin.password"
      label={intl.formatMessage({id: 'pages.project.doris.template.steps.component.admin.password'})}
      colProps={{span: 10, offset: 1}}
      rules={[{required: true}]}
      initialValue={"Admin123"}
    />
  </ProCard>);
}

export default DorisAdminUser;