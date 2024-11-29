import {Form, message, Modal} from 'antd';
import {ProForm, ProFormDigit, ProFormSelect, ProFormText, ProFormTextArea} from "@ant-design/pro-components";
import {useIntl} from '@umijs/max';
import {ModalFormProps} from '@/typings';
import {WORKSPACE_CONF} from '@/constants/constant';
import {ScheduleJob} from '@/services/project/typings';
import {WsScheduleGroupService} from "@/services/project/WsScheduleGroupService";
import {WsScheduleJobService} from "@/services/project/WsScheduleJobService";

const ScheduleJobForm: React.FC<ModalFormProps<{ jobGroupIdData?: number, job?: ScheduleJob }>> = ({
                                                                                                     data,
                                                                                                     visible,
                                                                                                     onVisibleChange,
                                                                                                     onCancel
                                                                                                   }) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const projectId = localStorage.getItem(WORKSPACE_CONF.projectId);

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
          name: data?.job?.name,
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
        <ProFormText
          name="name"
          label={intl.formatMessage({id: 'pages.project.schedule.job.name'})}
          rules={[{required: true}, {max: 32}]}
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
