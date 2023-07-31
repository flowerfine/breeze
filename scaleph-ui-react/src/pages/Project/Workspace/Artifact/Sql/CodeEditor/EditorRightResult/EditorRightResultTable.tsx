import { Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import React from 'react';
import styles from './index.less';

interface DataType {
  key: React.Key;
  name: string;
  age: number;
  address: string;
}

const columns: ColumnsType<DataType> = [
  {
    title: 'Name',
    dataIndex: 'name',
    width: 130,
    render: (value: any, row: any, rowIndex: number) => {
      return (
        <div className={styles.tableItem}>
          <div>{value}</div>
          <div className={styles.tableHoverBox}>
            <img
              src="https://s.xinc818.com/files/webcilklz16y4pxm3zv/位图 (1).svg"
              alt="查看"
              onClick={() => {
                console.log('查看');
              }}
            />
            <img
              src="https://s.xinc818.com/files/webcilklz19gz7rldus/复制_o.svg"
              alt="复制"
              onClick={() => {
                console.log('复制');
              }}
            />
          </div>
        </div>
      );
    },
    sorter: (a, b) => a.name.length - b.name.length,
  },
  {
    title: 'Age',
    dataIndex: 'age',
    width: 130,   
    render: (value: any, row: any, rowIndex: number) => {
        return (
          <div className={styles.tableItem}>
            <div>{value}</div>
            <div className={styles.tableHoverBox}>
              <img
                src="https://s.xinc818.com/files/webcilklz16y4pxm3zv/位图 (1).svg"
                alt="查看"
                onClick={() => {
                  console.log('查看');
                }}
              />
              <img
                src="https://s.xinc818.com/files/webcilklz19gz7rldus/复制_o.svg"
                alt="复制"
                onClick={() => {
                  console.log('复制');
                }}
              />
            </div>
          </div>
        );
      },
      sorter: (a, b) => a.age - b.age,
  },
  {
    title: 'Address',
    dataIndex: 'address',
  },
];

const data: DataType[] = [];
for (let i = 0; i < 100; i++) {
  data.push({
    key: i,
    name: `Edward King ${i}`,
    age: 32,
    address: `London, Park Lane no. ${i}`,
  });
}

const EditorRightResultTable: React.FC = () => (
  <div
    className={styles.tableBox}
  >
    <Table
      columns={columns}
      bordered={true}
      dataSource={data}
      pagination={false}
    />
    <div className={styles.statusBar}>Result：执行成功. Time Consuming：25ms</div>
  </div>
);
export default EditorRightResultTable;
