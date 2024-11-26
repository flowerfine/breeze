import {Form, message} from 'antd';
import {ModalForm, ProFormDigit, ProFormText, ProFormTextArea} from "@ant-design/pro-components";
import {useIntl} from '@umijs/max';
import {ModalFormProps} from '@/typings';
import {WORKSPACE_CONF} from '@/constants/constant';
import {ScheduleJob} from '@/services/project/typings';
import {WsScheduleGroupService} from "@/services/project/WsScheduleGroupService";

const ScheduleJobForm: React.FC<ModalFormProps<ScheduleJob>> = ({
                                                                    data,
                                                                    visible,
                                                                    onVisibleChange
                                                                }) => {
    const intl = useIntl();
    const [form] = Form.useForm();
    const projectId = localStorage.getItem(WORKSPACE_CONF.projectId);

    return (
        <ModalForm
            title={
                data.id
                    ? intl.formatMessage({id: 'app.common.operate.edit.label'}) +
                    intl.formatMessage({id: 'pages.project.schedule.job'})
                    : intl.formatMessage({id: 'app.common.operate.new.label'}) +
                    intl.formatMessage({id: 'pages.project.schedule.job'})
            }
            form={form}
            initialValues={{
                id: data?.id,
                name: data?.name,
                remark: data?.remark,
            }}
            open={visible}
            onOpenChange={onVisibleChange}
            width={580}
            layout={"horizontal"}
            labelCol={{span: 6}}
            wrapperCol={{span: 16}}
            modalProps={{
                destroyOnClose: true,
                closeIcon: false
            }}
            onFinish={(values: Record<string, any>) => {
                const param = {
                    id: values.id,
                    namespace: projectId,
                    name: values.name,
                    remark: values.remark
                };
                return values.id
                    ? WsScheduleGroupService.update(param).then((response) => {
                        if (response.success) {
                            message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}));
                            if (onVisibleChange) {
                                onVisibleChange(false);
                            }
                        }
                    })
                    : WsScheduleGroupService.add(param).then((response) => {
                        if (response.success) {
                            message.success(intl.formatMessage({id: 'app.common.operate.new.success'}));
                            if (onVisibleChange) {
                                onVisibleChange(false);
                            }
                        }
                    })
            }}
        >
            <ProFormDigit name="id" hidden/>
            <ProFormText
                name="name"
                label={intl.formatMessage({id: 'pages.project.schedule.group.name'})}
                rules={[{required: true}, {max: 32}]}
            />
            <ProFormTextArea
                name="remark"
                label={intl.formatMessage({id: 'app.common.data.remark'})}
                rules={[{max: 200}]}
            />
        </ModalForm>
    );
};

export default ScheduleJobForm;
