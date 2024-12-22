import React, {useRef, useState} from "react";
import {Button, message, Modal, Space, Tooltip} from "antd";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {history, useAccess, useIntl, useLocation} from "@umijs/max";
import {WORKSPACE_CONF} from "@/constants/constant";
import {PRIVILEGE_CODE} from "@/constants/privilegeCode";
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";
import {WsScheduleJobInstanceService} from "@/services/workspace/schedule/WsScheduleJobInstanceService";
import ScheduleJobForm from "@/pages/Project/Workspace/Schedule/Job/ScheduleJobForm";
import ScheduleJobInstanceForm from "@/pages/Project/Workspace/Schedule/Instance/ScheduleJobInstanceForm";

const ScheduleJobInstanceWeb: React.FC = () => {
    const urlParams = useLocation();
    const intl = useIntl();
    const access = useAccess();
    const actionRef = useRef<ActionType>();
    const formRef = useRef<ProFormInstance>();
    const [selectedRows, setSelectedRows] = useState<WorkspaceScheduleAPI.ScheduleJobInstance[]>([]);
    const [scheduleJobInstanceFormData, setScheduleJobInstanceFormData] = useState<{
        visiable: boolean;
        data: WorkspaceScheduleAPI.ScheduleJobInstance;
    }>({visiable: false, data: {}});
    const projectId = localStorage.getItem(WORKSPACE_CONF.projectId);
    const scheduleJob = urlParams.state as WorkspaceScheduleAPI.ScheduleJob;

    const tableColumns: ProColumns<WorkspaceScheduleAPI.ScheduleJobInstance>[] = [
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.instance.name'}),
            dataIndex: 'name'
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.instance.status'}),
            dataIndex: 'status',
            hideInSearch: true
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.instance.timezone'}),
            dataIndex: 'timezone',
            hideInSearch: true
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.instance.cron'}),
            dataIndex: 'cron',
            hideInSearch: true
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.instance.startTime'}),
            dataIndex: 'startTime',
            hideInSearch: true
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.instance.endTime'}),
            dataIndex: 'endTime',
            hideInSearch: true
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.instance.timeout'}),
            dataIndex: 'timeout',
            hideInSearch: true
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.instance.params'}),
            dataIndex: 'params',
            hideInSearch: true
        },
        {
            title: intl.formatMessage({id: 'app.common.data.remark'}),
            dataIndex: 'remark',
            hideInSearch: true,
        },
        {
            title: intl.formatMessage({id: 'app.common.data.createTime'}),
            dataIndex: 'createTime',
            hideInSearch: true,
            width: 180,
        },
        {
            title: intl.formatMessage({id: 'app.common.data.updateTime'}),
            dataIndex: 'updateTime',
            hideInSearch: true,
            width: 180,
        },
        {
            title: intl.formatMessage({id: 'app.common.operate.label'}),
            dataIndex: 'actions',
            align: 'center',
            width: 120,
            fixed: 'right',
            valueType: 'option',
            render: (_, record) => (
                <Space>
                    <Tooltip title={intl.formatMessage({id: 'app.common.operate.edit.label'})}>
                        <Button
                            shape="default"
                            type="link"
                            icon={<EditOutlined/>}
                            onClick={() => {
                                setScheduleJobInstanceFormData({visiable: true, data: record});
                            }}
                        />
                    </Tooltip>
                    <Tooltip title={intl.formatMessage({id: 'app.common.operate.delete.label'})}>
                        <Button
                            shape="default"
                            type="link"
                            danger
                            icon={<DeleteOutlined/>}
                            onClick={() => {
                                Modal.confirm({
                                    title: intl.formatMessage({id: 'app.common.operate.delete.confirm.title'}),
                                    content: intl.formatMessage({id: 'app.common.operate.delete.confirm.content'}),
                                    okText: intl.formatMessage({id: 'app.common.operate.confirm.label'}),
                                    okButtonProps: {danger: true},
                                    cancelText: intl.formatMessage({id: 'app.common.operate.cancel.label'}),
                                    onOk() {
                                        WsScheduleJobInstanceService.deleteOne(record).then((d) => {
                                            if (d.success) {
                                                message.success(intl.formatMessage({id: 'app.common.operate.delete.success'}));
                                                actionRef.current?.reload();
                                            }
                                        });
                                    },
                                });
                            }}
                        />
                    </Tooltip>
                </Space>
            ),
        },
    ];

    return (
        <PageContainer title={scheduleJob.name} onBack={() => history.back()}>
            <ProTable<WorkspaceScheduleAPI.ScheduleJobInstance>
                search={{
                    labelWidth: 'auto',
                    span: {xs: 24, sm: 12, md: 8, lg: 6, xl: 6, xxl: 4},
                }}
                rowKey="id"
                actionRef={actionRef}
                formRef={formRef}
                options={false}
                columns={tableColumns}
                request={(params, sorter, filter) => {
                    return WsScheduleJobInstanceService.list({...params, jobConfigId: scheduleJob.id})
                }}
                toolbar={{
                    actions: [
                        access.canAccess(PRIVILEGE_CODE.datadevResourceAdd) && (
                            <Button
                                key="new"
                                type="primary"
                                onClick={() => {
                                    setScheduleJobInstanceFormData({visiable: true, data: {}});
                                }}
                            >
                                {intl.formatMessage({id: 'app.common.operate.new.label'})}
                            </Button>
                        ),
                        access.canAccess(PRIVILEGE_CODE.datadevResourceDelete) && (
                            <Button
                                key="del"
                                type="default"
                                danger
                                disabled={selectedRows.length < 1}
                                onClick={() => {
                                    Modal.confirm({
                                        title: intl.formatMessage({id: 'app.common.operate.delete.confirm.title'}),
                                        content: intl.formatMessage({id: 'app.common.operate.delete.confirm.content'}),
                                        okText: intl.formatMessage({id: 'app.common.operate.confirm.label'}),
                                        okButtonProps: {danger: true},
                                        cancelText: intl.formatMessage({id: 'app.common.operate.cancel.label'}),
                                        onOk() {
                                            WsScheduleJobInstanceService.deleteBatch(selectedRows).then((d) => {
                                                if (d.success) {
                                                    message.success(intl.formatMessage({id: 'app.common.operate.delete.success'}));
                                                    actionRef.current?.reload();
                                                }
                                            });
                                        },
                                    });
                                }}
                            >
                                {intl.formatMessage({id: 'app.common.operate.delete.label'})}
                            </Button>
                        )
                    ],
                }}
                pagination={{showQuickJumper: true, showSizeChanger: true, defaultPageSize: 10}}
                rowSelection={{
                    fixed: true,
                    onChange(selectedRowKeys, selectedRows, info) {
                        setSelectedRows(selectedRows);
                    },
                }}
                tableAlertRender={false}
                tableAlertOptionRender={false}
            />

            {scheduleJobInstanceFormData.visiable && (
                <ScheduleJobInstanceForm
                    visible={scheduleJobInstanceFormData.visiable}
                    data={{scheduleJob: scheduleJob, jobInstance: scheduleJobInstanceFormData.data}}
                    onCancel={() => {
                        setScheduleJobInstanceFormData({visiable: false, data: {}});
                    }}
                    onVisibleChange={(visible) => {
                        setScheduleJobInstanceFormData({visiable: visible, data: {}});
                        actionRef.current?.reload();
                    }}
                />
            )}
        </PageContainer>);
}

export default ScheduleJobInstanceWeb;
