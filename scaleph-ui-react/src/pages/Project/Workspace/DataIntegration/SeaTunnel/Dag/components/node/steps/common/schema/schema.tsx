import React from 'react';
import {InfoCircleOutlined} from "@ant-design/icons";
import {
    ProFormDependency,
    ProFormDigit,
    ProFormGroup,
    ProFormList,
    ProFormSelect,
    ProFormText
} from "@ant-design/pro-components";
import {getIntl, getLocale} from "@umijs/max";
import {BaseFileParams, SchemaParams} from "../../constant";

const SchemaItem: React.FC = () => {
    const intl = getIntl(getLocale());

    const compressCodec = (
        <ProFormSelect
            name={BaseFileParams.compressCodec}
            label={intl.formatMessage({id: 'pages.project.di.step.baseFile.compressCodec'})}
            options={["lzo", "none"]}
        />
    )

    return (
        <ProFormGroup>
            <ProFormSelect
                name={'file_format_type'}
                label={intl.formatMessage({id: 'pages.project.di.step.baseFile.fileFormatType'})}
                rules={[{required: true}]}
                options={['json', 'parquet', 'orc', 'text', 'csv', 'excel']}
            />
            <ProFormDependency name={['file_format_type']}>
                {({file_format_type}) => {
                    if (file_format_type == 'json') {
                        return (
                            <ProFormGroup>
                                {compressCodec}
                                <ProFormGroup
                                    title={intl.formatMessage({id: 'pages.project.di.step.schema'})}
                                    tooltip={{
                                        title: intl.formatMessage({id: 'pages.project.di.step.schema.tooltip'}),
                                        icon: <InfoCircleOutlined/>,
                                    }}
                                >

                                    <ProFormList
                                        name={SchemaParams.fieldArray}
                                        copyIconProps={false}
                                        creatorButtonProps={{
                                            creatorButtonText: intl.formatMessage({id: 'pages.project.di.step.schema.fields'}),
                                            type: 'text',
                                        }}
                                    >
                                        <ProFormGroup>
                                            <ProFormText
                                                name={SchemaParams.field}
                                                label={intl.formatMessage({id: 'pages.project.di.step.schema.fields.field'})}
                                                colProps={{span: 10, offset: 1}}
                                            />
                                            <ProFormText
                                                name={SchemaParams.type}
                                                label={intl.formatMessage({id: 'pages.project.di.step.schema.fields.type'})}
                                                colProps={{span: 10, offset: 1}}
                                            />
                                        </ProFormGroup>
                                    </ProFormList>
                                </ProFormGroup>
                            </ProFormGroup>
                        );
                    }
                    if (file_format_type == 'text') {
                        return <ProFormGroup>
                            {compressCodec}
                            <ProFormDigit
                                name={SchemaParams.skipHeaderRowNumber}
                                label={intl.formatMessage({id: 'pages.project.di.step.schema.skipHeaderRowNumber'})}
                                initialValue={0}
                                fieldProps={{
                                    min: 0
                                }}
                            />
                            <ProFormText
                                name={SchemaParams.delimiter}
                                label={intl.formatMessage({id: 'pages.project.di.step.schema.delimiter'})}
                                initialValue={'\\001'}
                            />
                        </ProFormGroup>;
                    }
                    if (file_format_type == 'csv') {
                        return <ProFormGroup>
                            {compressCodec}
                            <ProFormDigit
                                name={SchemaParams.skipHeaderRowNumber}
                                label={intl.formatMessage({id: 'pages.project.di.step.schema.skipHeaderRowNumber'})}
                                initialValue={0}
                                fieldProps={{
                                    min: 0
                                }}
                            />
                        </ProFormGroup>;
                    }
                    if (file_format_type == 'excel') {
                        return <ProFormGroup>
                            <ProFormDigit
                                name={SchemaParams.skipHeaderRowNumber}
                                label={intl.formatMessage({id: 'pages.project.di.step.schema.skipHeaderRowNumber'})}
                                initialValue={0}
                                fieldProps={{
                                    min: 0
                                }}
                            />
                            <ProFormText
                                name={SchemaParams.sheetName}
                                label={intl.formatMessage({id: 'pages.project.di.step.schema.sheetName'})}
                            />
                        </ProFormGroup>;
                    }
                    return <ProFormGroup/>;
                }}
            </ProFormDependency>
        </ProFormGroup>
    );
}

export default SchemaItem;
