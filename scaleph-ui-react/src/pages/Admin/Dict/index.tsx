import {useIntl} from '@umijs/max';
import {useRef} from 'react';
import {Col, Row} from 'antd';
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from '@ant-design/pro-components';
import {SysDictDefinition, SysDictInstance} from '@/services/admin/typings';
import {SysDictService} from "@/services/admin/system/sysDict.service";

const Dict: React.FC = () => {
  const intl = useIntl();
  const dictTypeActionRef = useRef<ActionType>();
  const dictDataActionRef = useRef<ActionType>();
  const dictTypeFormRef = useRef<ProFormInstance>();
  const dictDataFormRef = useRef<ProFormInstance>();

  const dictTypeTableColumns: ProColumns<SysDictDefinition>[] = [
    {
      title: intl.formatMessage({id: 'pages.admin.dict.code'}),
      dataIndex: 'code',
      width: 180,
      fixed: 'left',
    },
    {
      title: intl.formatMessage({id: 'pages.admin.dict.name'}),
      dataIndex: 'name',
      width: 300,
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({id: 'app.common.data.remark'}),
      dataIndex: 'remark',
      width: 150,
      hideInSearch: true,
    }
  ];

  const dictDataTableColumns: ProColumns<SysDictInstance>[] = [
    {
      title: intl.formatMessage({id: 'pages.admin.dict.dictType'}),
      dataIndex: 'dictDefinitionCode',
      width: 240,
      fixed: 'left',
      // render: (_, record) => {
      //   return <span>{record.dictType?.code + '-' + record.dictType?.name}</span>;
      // },
      hideInTable: true
    },
    {
      title: intl.formatMessage({id: 'pages.admin.dict.value'}),
      dataIndex: 'value',
      width: 180,
    },
    {
      title: intl.formatMessage({id: 'pages.admin.dict.label'}),
      dataIndex: 'label',
      width: 180,
    },
    {
      title: intl.formatMessage({id: 'app.common.data.remark'}),
      dataIndex: 'remark',
      width: 260,
      hideInSearch: true,
    }
  ];

  return (
    <PageContainer title={false}>
      <Row gutter={[12, 12]}>
        <Col span={8}>
          <ProTable<SysDictDefinition>
            headerTitle={intl.formatMessage({id: 'pages.admin.dict.dictType'})}
            search={{filterType: 'light'}}
            rowKey="code"
            actionRef={dictTypeActionRef}
            formRef={dictTypeFormRef}
            options={false}
            columns={dictTypeTableColumns}
            request={(params, sorter, filter) => {
              return SysDictService.listDefinition(params);
            }}
            pagination={{showQuickJumper: true, showSizeChanger: true, defaultPageSize: 10}}
            onRow={(record) => {
              return {
                onClick: (event) => {
                  dictDataFormRef.current?.setFieldsValue({
                    dictDefinitionCode: record.code,
                  });
                  dictDataFormRef.current?.submit();
                },
              };
            }}
            tableAlertRender={false}
            tableAlertOptionRender={false}
          />
        </Col>
        <Col span={16}>
          <ProTable<SysDictInstance>
            headerTitle={intl.formatMessage({id: 'pages.admin.dict.dictData'})}
            search={{filterType: 'light'}}
            rowKey="key"
            actionRef={dictDataActionRef}
            formRef={dictDataFormRef}
            options={false}
            columns={dictDataTableColumns}
            request={(params, sorter, filter) => {
              return SysDictService.listInstance(params);
            }}
            pagination={{showQuickJumper: true, showSizeChanger: true, defaultPageSize: 10}}
            tableAlertRender={false}
            tableAlertOptionRender={false}
          />
        </Col>
      </Row>
    </PageContainer>
  );
};

export default Dict;
