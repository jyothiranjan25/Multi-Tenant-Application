import * as React from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  IconButton,
  Button,
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

function MUIModal({
  header,
  children,
  openModal = false,
  onClose,
  onSubmit,
  submitButton = 'Save',
}) {
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
        {header}
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
      <DialogContent dividers>{children}</DialogContent>
      {onSubmit && (
        <DialogActions>
          <Button autoFocus onClick={onSubmit}>
            {submitButton}
          </Button>
        </DialogActions>
      )}
    </Dialog>
  );
}

export default MUIModal;
