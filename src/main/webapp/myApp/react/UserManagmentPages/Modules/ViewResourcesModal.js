import {
  Box,
  Dialog,
  DialogContent,
  DialogTitle,
  IconButton,
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import CustomRichTreeView from '../../components/UiComponents/CustomRichTreeView';
import * as React from 'react';

const ViewResourcesModal = ({ openModal, onClose, data }) => {
  const treeData = mapTreeData(data);
  return (
    <Dialog
      fullWidth
      maxWidth="md"
      onClose={onClose}
      aria-labelledby="customized-dialog-title"
      open={openModal}
      scroll={'paper'}
    >
      <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
        View Resources
        <IconButton
          aria-label="close"
          onClick={onClose}
          sx={(theme) => ({
            position: 'absolute',
            right: 8,
            top: 8,
            color: theme.palette.grey[500],
          })}
        >
          <CloseIcon />
        </IconButton>
      </DialogTitle>
      <DialogContent dividers>
        <Box sx={{ height: 300 }}>
          <CustomRichTreeView items={treeData} />
        </Box>
      </DialogContent>
    </Dialog>
  );
};

const mapTreeData = (params) => {
  if (!Array.isArray(params)) {
    return [];
  }
  return params
    .sort((a, b) => a.id - b.id)
    .map((x) => ({
      id: x.id.toString(),
      label: x.resource_name,
      children: x.child_resources ? mapTreeData(x.child_resources) : [],
    }));
};

export default ViewResourcesModal;
