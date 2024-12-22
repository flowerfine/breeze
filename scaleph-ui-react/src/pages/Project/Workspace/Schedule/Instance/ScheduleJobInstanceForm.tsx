import {Form, message, Modal} from 'antd';
import {ProForm, ProFormDateRangePicker, ProFormDigit, ProFormText, ProFormTextArea} from "@ant-design/pro-components";
import {useIntl} from '@umijs/max';
import {ModalFormProps} from '@/typings';
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";
import {WsScheduleJobInstanceService} from "@/services/workspace/schedule/WsScheduleJobInstanceService";

const ScheduleJobInstanceForm: React.FC<ModalFormProps<{
    scheduleJob: WorkspaceScheduleAPI.ScheduleJob,
    jobInstance?: WorkspaceScheduleAPI.ScheduleJobInstance
}>> = (props) => {
    const intl = useIntl();
    const [form] = Form.useForm();
    const {data, visible, onVisibleChange, onCancel} = props

    return (
        <Modal
            open={visible}
            title={
                data?.jobInstance?.id
                    ? intl.formatMessage({id: 'app.common.operate.edit.label'}) + ' ' +
                    intl.formatMessage({id: 'pages.project.schedule.job.instance'})
                    : intl.formatMessage({id: 'app.common.operate.new.label'}) + ' ' +
                    intl.formatMessage({id: 'pages.project.schedule.job.instance'})
            }
            width={580}
            destroyOnClose={true}
            onCancel={onCancel}
            onOk={() => {
                form.validateFields().then((values) => {
                    const params = {
                        id: values.id,
                        jobConfigId: values.jobConfigId,
                        name: values.name,
                        timezone: values.timezone,
                        cron: values.cron,
                        startTime: values.validTime[0].format('YYYY-MM-DD HH:mm:ss'),
                        endTime: values.validTime[1].format('YYYY-MM-DD HH:mm:ss'),
                        timeout: values.timeout,
                        params: values.params,
                        remark: values.remark,
                    }
                    data?.jobInstance?.id
                        ? WsScheduleJobInstanceService.update({...params}).then((response) => {
                            if (response.success) {
                                message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}));
                                if (onVisibleChange) {
                                    onVisibleChange(false);
                                }
                            }
                        })
                        : WsScheduleJobInstanceService.add({...params}).then((response) => {
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
                    id: data?.jobInstance?.id,
                    jobConfigId: data?.scheduleJob.id,
                    name: data?.jobInstance?.name,
                    timezone: data?.jobInstance?.timezone,
                    cron: data?.jobInstance?.cron,
                    validTime: [data?.jobInstance?.startTime, data?.jobInstance?.endTime],
                    startTime: data?.jobInstance?.startTime,
                    endTime: data?.jobInstance?.endTime,
                    timeout: data?.jobInstance?.timeout,
                    params: data?.jobInstance?.params,
                    remark: data?.jobInstance?.remark,
                }}
            >
                <ProFormDigit name="id" hidden/>
                <ProFormDigit name="jobConfigId" hidden/>
                <ProFormText
                    name="name"
                    label={intl.formatMessage({id: 'pages.project.schedule.job.instance.name'})}
                    rules={[{required: true}, {max: 32}]}
                />
                <ProFormText
                    name="timezone"
                    label={intl.formatMessage({id: 'pages.project.schedule.job.instance.timezone'})}
                    rules={[{required: true}]}
                />
                <ProFormText
                    name="cron"
                    label={intl.formatMessage({id: 'pages.project.schedule.job.instance.cron'})}
                    rules={[{required: true}]}
                />
                <ProFormDateRangePicker
                    name={"validTime"}
                    label={intl.formatMessage({id: 'pages.project.schedule.job.instance.validTime'})}
                    rules={[{required: true}]}
                    fieldProps={{
                        showTime: true,
                        format: "YYYY-MM-DD HH:mm:ss",
                        id: {
                            start: "startTime",
                            end: "endTime"
                        }
                    }}
                />
                <ProFormText
                    name="timeout"
                    label={intl.formatMessage({id: 'pages.project.schedule.job.instance.timeout'})}
                />
                <ProFormTextArea
                    name="params"
                    label={intl.formatMessage({id: 'pages.project.schedule.job.instance.params'})}
                />
                <ProFormTextArea
                    name={"remark"}
                    label={intl.formatMessage({id: 'app.common.data.remark'})}
                />
            </ProForm>
        </Modal>
    );
};

export default ScheduleJobInstanceForm;
