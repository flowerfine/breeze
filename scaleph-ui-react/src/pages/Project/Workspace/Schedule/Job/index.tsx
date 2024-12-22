import React, {useEffect, useRef, useState} from "react";
import {Button, message, Modal, Space, Tooltip} from "antd";
import {DeleteOutlined, EditOutlined, FileSearchOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {useAccess, useIntl, history} from "@umijs/max";
import {WORKSPACE_CONF} from "@/constants/constant";
import {PRIVILEGE_CODE} from "@/constants/privilegeCode";
import {WsScheduleJobService} from "@/services/workspace/schedule/WsScheduleJobService";
import {WsScheduleGroupService} from "@/services/workspace/schedule/WsScheduleGroupService";
import ScheduleJobForm from "@/pages/Project/Workspace/Schedule/Job/ScheduleJobForm";
import {DICT_TYPE} from "@/constants/dictType";
import {SysDictService} from "@/services/admin/system/sysDict.service";
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";

const ScheduleJobWeb: React.FC = () => {
    const intl = useIntl();
    const access = useAccess();
    const actionRef = useRef<ActionType>();
    const formRef = useRef<ProFormInstance>();
    const [selectedRows, setSelectedRows] = useState<WorkspaceScheduleAPI.ScheduleJob[]>([]);
    const [scheduleGroups, setScheduleGroups] = useState<Array<WorkspaceScheduleAPI.ScheduleGroup>>();
    const [jobGroupId, setJobGroupId] = useState<number>();
    const [scheduleJobFormData, setScheduleJobFormData] = useState<{
        visiable: boolean;
        data: WorkspaceScheduleAPI.ScheduleJob;
    }>({visiable: false, data: {}});
    const projectId = localStorage.getItem(WORKSPACE_CONF.projectId);

    useEffect(() => {
        if (scheduleGroups) {
            setJobGroupId(scheduleGroups[0].id)
            formRef.current?.setFieldValue("jobGroupId", scheduleGroups[0].id)
            formRef.current?.submit()
        }
    }, [scheduleGroups]);

    const tableColumns: ProColumns<WorkspaceScheduleAPI.ScheduleJob>[] = [
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.jobGroupId'}),
            dataIndex: 'jobGroupId',
            hideInTable: true,
            request: (params, props) => {
                return WsScheduleGroupService.list({namespace: projectId}).then((result) => {
                    if (result.data) {
                        setScheduleGroups(result.data)
                    }
                    return result.data?.map((item) => {
                        return {
                            value: item.id,
                            label: item.name
                        }
                    })
                })
            },
            fieldProps: (form, config) => {
                return {
                    allowClear: false,
                    onChange: (value) => setJobGroupId(value)
                }
            }
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.type'}),
            dataIndex: 'type',
            render: (dom, record) => {
                return record.type?.label;
            },
            request: (params, props) => {
                return SysDictService.listDictByDefinition(DICT_TYPE.scheduleType)
            }
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.name'}),
            dataIndex: 'name'
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.engineType'}),
            dataIndex: 'engineType',
            render: (dom, record) => {
                return record.engineType?.label;
            },
            request: (params, props) => {
                return SysDictService.listDictByDefinition(DICT_TYPE.scheduleEngineType)
            }
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.jobType'}),
            dataIndex: 'jobType',
            render: (dom, record) => {
                return record.jobType?.label;
            },
            request: (params, props) => {
                return SysDictService.listDictByDefinition(DICT_TYPE.scheduleJobType)
            }
        },
        {
            title: intl.formatMessage({id: 'pages.project.schedule.job.handler'}),
            dataIndex: 'handler',
            hideInSearch: true,
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
                                setScheduleJobFormData({visiable: true, data: record});
                            }}
                        />
                    </Tooltip>
                    <Tooltip title={intl.formatMessage({ id: 'app.common.operate.more.label' })}>
                        <Button
                            shape="default"
                            type="link"
                            icon={<FileSearchOutlined />}
                            onClick={() => history.push('/workspace/schedule/instance', record)}
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
                                        WsScheduleJobService.deleteOne(record).then((d) => {
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

    return (<PageContainer title={false}>
        <ProTable<WorkspaceScheduleAPI.ScheduleJob>
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
                if (params["jobGroupId"]) {
                    return WsScheduleJobService.list({...params})
                }
                return Promise.reject()
            }}
            toolbar={{
                actions: [
                    access.canAccess(PRIVILEGE_CODE.datadevResourceAdd) && (
                        <Button
                            key="new"
                            type="primary"
                            onClick={() => {
                                setScheduleJobFormData({visiable: true, data: {}});
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
                                        WsScheduleJobService.deleteBatch(selectedRows).then((d) => {
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
        {scheduleJobFormData.visiable && (
            <ScheduleJobForm
                visible={scheduleJobFormData.visiable}
                data={{jobGroupIdData: jobGroupId, job: scheduleJobFormData.data}}
                onCancel={() => {
                    setScheduleJobFormData({visiable: false, data: {}});
                }}
                onVisibleChange={(visible) => {
                    setScheduleJobFormData({visiable: visible, data: {}});
                    actionRef.current?.reload();
                }}
            />
        )}
    </PageContainer>);
}

export default ScheduleJobWeb;
