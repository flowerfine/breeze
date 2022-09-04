import {ModalFormProps} from '@/app.d';
import {Form, Input, message, Modal, Upload, UploadFile, UploadProps} from 'antd';
import {useIntl} from 'umi';
import {useState} from "react";
import {InboxOutlined} from "@ant-design/icons";
import {uploadFiles} from "@/services/resource/clusterCredential.service";

const { Dragger } = Upload;

const CredentialFileForm: React.FC<ModalFormProps<number>> = ({
  data,
  visible,
  onVisibleChange,
  onCancel,
}) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const [fileList, setFileList] = useState<UploadFile[]>([]);
  const [uploading, setUploading] = useState(false);

  const props: UploadProps = {
    name: 'files',
    multiple: true,
    maxCount: 10,
    onRemove: file => {
      const index = fileList.indexOf(file);
      const newFileList = fileList.slice();
      newFileList.splice(index, 1);
      setFileList(newFileList);
    },
    beforeUpload: file => {
      setFileList([...fileList, file]);
      return false;
    },
    fileList,
  };

  return (
    <Modal
      title={
        intl.formatMessage({ id: 'app.common.operate.upload.label' }) +
        intl.formatMessage({ id: 'pages.resource.credentialFile' })
      }
      visible={visible}
      width={580}
      destroyOnClose={true}
      onCancel={onCancel}
      confirmLoading={uploading}
      okText={uploading ? intl.formatMessage({ id: 'app.common.operate.uploading.label' }) : intl.formatMessage({ id: 'app.common.operate.upload.label' })}
      onOk={() => {
        form.validateFields().then((values) => {
          setUploading(true);
          uploadFiles(data, fileList)
            .then(() => {
              setFileList([]);
              message.success(intl.formatMessage({ id: 'app.common.operate.upload.success' }));
            })
            .catch(() => {
              message.error(intl.formatMessage({ id: 'app.common.operate.upload.failure' }));
            })
            .finally(() => {
              setUploading(false);
              onVisibleChange(false);
            });
        });
      }}
    >
      <Form form={form} layout="horizontal">
        <Form.Item name="id" hidden>
          <Input></Input>
        </Form.Item>
        <Form.Item>
          <Dragger {...props}>
            <p className="ant-upload-drag-icon">
              <InboxOutlined />
            </p>
            <p className="ant-upload-text">
              {intl.formatMessage({ id: 'pages.resource.files.upload.tooltip' })}
            </p>
            <p className="ant-upload-hint">
              {intl.formatMessage({ id: 'pages.resource.files.upload.hint' })}
            </p>
          </Dragger>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default CredentialFileForm;
