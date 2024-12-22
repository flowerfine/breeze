import {Form, message, Modal} from 'antd';
import {ProForm, ProFormDigit, ProFormSelect, ProFormText, ProFormTextArea} from "@ant-design/pro-components";
import {useIntl} from '@umijs/max';
import {ModalFormProps} from '@/typings';
import {WORKSPACE_CONF} from '@/constants/constant';
import {DICT_TYPE} from "@/constants/dictType";
import {SysDictService} from "@/services/admin/system/sysDict.service";
import {WsScheduleGroupService} from "@/services/workspace/schedule/WsScheduleGroupService";
import {WsScheduleJobService} from "@/services/workspace/schedule/WsScheduleJobService";
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";

const ScheduleJobForm: React.FC<ModalFormProps<{
  jobGroupIdData?: number,
  job?: WorkspaceScheduleAPI.ScheduleJob
}>> = (props) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const projectId = localStorage.getItem(WORKSPACE_CONF.projectId);
  const {data, visible, onVisibleChange, onCancel} = props

  return (
    <Modal
      open={visible}
      title={
        data?.job?.id
          ? intl.formatMessage({id: 'app.common.operate.edit.label'}) + ' ' +
          intl.formatMessage({id: 'pages.project.schedule.job'})
          : intl.formatMessage({id: 'app.common.operate.new.label'}) + ' ' +
          intl.formatMessage({id: 'pages.project.schedule.job'})
      }
      width={580}
      destroyOnClose={true}
      onCancel={onCancel}
      onOk={() => {
        form.validateFields().then((values) => {
          data?.job?.id
            ? WsScheduleJobService.update({...values}).then((response) => {
              if (response.success) {
                message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}));
                if (onVisibleChange) {
                  onVisibleChange(false);
                }
              }
            })
            : WsScheduleJobService.add({...values}).then((response) => {
              if (response.success) {
                message.success(intl.formatMessage({id: 'app.common.operate.new.success'}));
                if (onVisibleChange) {
                  onVisibleChange(false);
                }
              }
            });
        });
      }}
    >
      <ProForm
        form={form}
        layout={"horizontal"}
        submitter={false}
        labelCol={{span: 6}}
        wrapperCol={{span: 16}}
        initialValues={{
          id: data?.job?.id,
          jobGroupId: data?.jobGroupIdData,
          type: data?.job?.type?.value ? data?.job?.type?.value : '0',
          name: data?.job?.name,
          engineType: data?.job?.engineType?.value,
          jobType: data?.job?.jobType?.value,
          handler: data?.job?.handler,
          remark: data?.job?.remark,
        }}
      >
        <ProFormDigit name="id" hidden/>
        <ProFormSelect
          name="jobGroupId"
          label={intl.formatMessage({id: 'pages.project.schedule.job.jobGroupId'})}
          rules={[{required: true}]}
          disabled={true}
          allowClear={false}
          request={(params, props) => {
            return WsScheduleGroupService.list({namespace: projectId}).then((result) => {
              if (result.data) {
                return result.data?.map((item) => {
                  return {
                    value: item.id,
                    label: item.name
                  }
                })
              }
              return []
            })
          }}
        />
        <ProFormSelect
          name="type"
          label={intl.formatMessage({id: 'pages.project.schedule.job.type'})}
          rules={[{required: true}]}
          disabled={true}
          request={() => SysDictService.listDictByDefinition(DICT_TYPE.scheduleType)}
        />
        <ProFormText
          name="name"
          label={intl.formatMessage({id: 'pages.project.schedule.job.name'})}
          rules={[{required: true}, {max: 32}]}
        />
        <ProFormSelect
          name="engineType"
          label={intl.formatMessage({id: 'pages.project.schedule.job.engineType'})}
          rules={[{required: true}]}
          disabled={data?.job?.id ? true : false}
          request={() => SysDictService.listDictByDefinition(DICT_TYPE.scheduleEngineType)}
        />
        <ProFormSelect
          name="jobType"
          label={intl.formatMessage({id: 'pages.project.schedule.job.jobType'})}
          rules={[{required: true}]}
          disabled={data?.job?.id ? true : false}
          request={() => SysDictService.listDictByDefinition(DICT_TYPE.scheduleJobType)}
        />
        <ProFormText
          name="handler"
          label={intl.formatMessage({id: 'pages.project.schedule.job.handler'})}
          rules={[{required: true}]}
        />
        <ProFormTextArea
          name={"remark"}
          label={intl.formatMessage({id: 'app.common.data.remark'})}
        />
      </ProForm>
    </Modal>
  );
};

export default ScheduleJobForm;
