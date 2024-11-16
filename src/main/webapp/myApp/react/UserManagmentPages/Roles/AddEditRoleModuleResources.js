import * as React from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  IconButton,
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import GetAPIs from '../../components/GetApisService/GetAPIs';
import { useState, useEffect } from 'react';
import { Tree } from 'antd';
import AppDesign from '../../components/UiComponents/AntDesign';

// ModalDialog Component
export default function AddEditRoleModuleResources({
  formData,
  openModal,
  onClose,
  onModulesUpdate,
}) {
  const { getModules } = GetAPIs();
  const [modules, setModules] = useState([]);
  const [selectedItems, setSelectedItems] = useState([]);
  const [checkedKeys, setCheckedKeys] = useState([]);

  useEffect(() => {
    getModules().then((data) => setModules(data.map((item) => item.data)));
  }, [getModules]);

  const onCheck = (checkedKeysValue) => {
    console.log('onCheck', checkedKeysValue);
    setCheckedKeys(checkedKeysValue);
  };

  const handleSaveClick = () => {};

  return (
    <Dialog
      fullWidth
      maxWidth="md"
      onClose={onClose}
      aria-labelledby="customized-dialog-title"
      open={openModal}
      scroll="paper"
    >
      <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
        Add/Edit Role Module Resources
        <IconButton
          aria-label="close"
          onClick={onClose}
          sx={{ position: 'absolute', right: 8, top: 8, color: 'grey.500' }}
        >
          <CloseIcon />
        </IconButton>
      </DialogTitle>
      <DialogContent dividers>
        <AppDesign>
          <Tree
            checkable
            showLine
            onCheck={onCheck}
            checkedKeys={checkedKeys}
            treeData={treeData}
            rootStyle={{
              background: 'none',
              backgroundColor: 'none',
            }}
          />
        </AppDesign>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleSaveClick} autoFocus>
          Save changes
        </Button>
      </DialogActions>
    </Dialog>
  );
}

const treeData = [
  {
    title: '0-0',
    key: '0-0',
    children: [
      {
        title: '0-0-0',
        key: '0-0-0',
        children: [
          {
            title: '0-0-0-0',
            key: '0-0-0-0',
          },
          {
            title: '0-0-0-1',
            key: '0-0-0-1',
          },
          {
            title: '0-0-0-2',
            key: '0-0-0-2',
          },
        ],
      },
      {
        title: '0-0-1',
        key: '0-0-1',
        children: [
          {
            title: '0-0-1-0',
            key: '0-0-1-0',
          },
          {
            title: '0-0-1-1',
            key: '0-0-1-1',
          },
          {
            title: '0-0-1-2',
            key: '0-0-1-2',
          },
        ],
      },
      {
        title: '0-0-2',
        key: '0-0-2',
      },
    ],
  },
  {
    title: '0-1',
    key: '0-1',
    children: [
      {
        title: '0-1-0-0',
        key: '0-1-0-0',
      },
      {
        title: '0-1-0-1',
        key: '0-1-0-1',
      },
      {
        title: '0-1-0-2',
        key: '0-1-0-2',
      },
    ],
  },
  {
    title: '0-2',
    key: '0-2',
  },
];
