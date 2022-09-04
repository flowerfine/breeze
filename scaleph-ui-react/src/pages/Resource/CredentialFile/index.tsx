import {PRIVILEGE_CODE} from '@/constant';
import {CredentialFile} from '@/services/resource/typings';
import {DeleteOutlined, DownloadOutlined} from '@ant-design/icons';
import {ActionType, ProColumns, ProFormInstance, ProTable} from '@ant-design/pro-components';
import {Button, message, Modal, Space, Tooltip} from 'antd';
import {useRef, useState} from 'react';
import {useAccess, useIntl} from 'umi';
import ClusterCredentialForm from './components/ClusterCredentialForm';
import {deleteFiles, downloadFile, listFiles} from "@/services/resource/clusterCredential.service";
import {history} from "@@/core/history";

const CredentialFileResource: React.FC = () => {
  const state = history.location.state as {id: number}
  const intl = useIntl();
  const access = useAccess();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<CredentialFile[]>([]);
  const [credentialFileFormData, setCredentialFileData] = useState<{
    visiable: boolean;
    data: CredentialFile;
  }>({visiable: false, data: {}});

  const tableColumns: ProColumns<CredentialFile>[] = [
    {
      title: intl.formatMessage({id: 'pages.resource.credentialFile.name'}),
      dataIndex: 'name',
    },
    {
      title: intl.formatMessage({id: 'pages.resource.credentialFile.len'}),
      dataIndex: 'len',
      width: 280,
    },
    {
      title: intl.formatMessage({id: 'pages.resource.credentialFile.blockSize'}),
      dataIndex: 'blockSize',
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({id: 'pages.resource.credentialFile.accessTime'}),
      dataIndex: 'accessTime',
      hideInSearch: true,
      width: 180,
    },
    {
      title: intl.formatMessage({id: 'pages.resource.credentialFile.modificationTime'}),
      dataIndex: 'modificationTime',
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
        <>
          <Space>
            {access.canAccess(PRIVILEGE_CODE.datadevResourceDownload) && (
              <Tooltip title={intl.formatMessage({id: 'app.common.operate.download.label'})}>
                <Button
                  shape="default"
                  type="link"
                  icon={<DownloadOutlined></DownloadOutlined>}
                  onClick={() => {
                    downloadFile(state.id, record)
                  }}
                ></Button>
              </Tooltip>
            )}
            {access.canAccess(PRIVILEGE_CODE.datadevResourceDelete) && (
              <Tooltip title={intl.formatMessage({id: 'app.common.operate.delete.label'})}>
                <Button
                  shape="default"
                  type="link"
                  icon={<DeleteOutlined/>}
                  onClick={() => {
                    Modal.confirm({
                      title: intl.formatMessage({id: 'app.common.operate.delete.confirm.title'}),
                      content: intl.formatMessage({
                        id: 'app.common.operate.delete.confirm.content',
                      }),
                      okText: intl.formatMessage({id: 'app.common.operate.confirm.label'}),
                      okButtonProps: {danger: true},
                      cancelText: intl.formatMessage({id: 'app.common.operate.cancel.label'}),
                      onOk() {
                        deleteFiles(state.id, [record]).then((d) => {
                          if (d.success) {
                            message.success(
                              intl.formatMessage({id: 'app.common.operate.delete.success'}),
                            );
                            actionRef.current?.reload();
                          }
                        });
                      },
                    });
                  }}
                ></Button>
              </Tooltip>
            )}
          </Space>
        </>
      ),
    },
  ];

  return (
    <div>
      <ProTable<CredentialFile>
        headerTitle={intl.formatMessage({id: 'pages.resource.credentialFile'})}
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
          return listFiles(state.id);
        }}
        toolbar={{
          actions: [
            access.canAccess(PRIVILEGE_CODE.datadevResourceAdd) && (
              <Button
                key="new"
                type="primary"
                onClick={() => {
                  setCredentialFileData({visiable: true, data: {}});
                }}
              >
                {intl.formatMessage({id: 'app.common.operate.upload.label'})}
              </Button>
            ),
            access.canAccess(PRIVILEGE_CODE.datadevResourceDelete) && (
              <Button
                key="del"
                type="default"
                disabled={selectedRows.length < 1}
                onClick={() => {
                  Modal.confirm({
                    title: intl.formatMessage({id: 'app.common.operate.delete.confirm.title'}),
                    content: intl.formatMessage({
                      id: 'app.common.operate.delete.confirm.content',
                    }),
                    okText: intl.formatMessage({id: 'app.common.operate.confirm.label'}),
                    okButtonProps: {danger: true},
                    cancelText: intl.formatMessage({id: 'app.common.operate.cancel.label'}),
                    onOk() {
                      deleteFiles(state.id, selectedRows).then((d) => {
                        if (d.success) {
                          message.success(
                            intl.formatMessage({id: 'app.common.operate.delete.success'}),
                          );
                          actionRef.current?.reload();
                        }
                      });
                    },
                  });
                }}
              >
                {intl.formatMessage({id: 'app.common.operate.delete.label'})}
              </Button>
            ),
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
      ></ProTable>
      {credentialFileFormData.visiable && (
        <ClusterCredentialForm
          visible={credentialFileFormData.visiable}
          onCancel={() => {
            setCredentialFileData({visiable: false, data: {}});
          }}
          onVisibleChange={(visiable) => {
            setCredentialFileData({visiable: visiable, data: {}});
            actionRef.current?.reload();
          }}
          data={state.id}
        />
      )}
    </div>
  );
};

export default CredentialFileResource;
