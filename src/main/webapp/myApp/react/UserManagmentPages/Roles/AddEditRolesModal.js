import * as React from 'react';
import {
  Box,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  FormLabel,
  OutlinedInput,
  Button,
  IconButton,
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import Grid from '@mui/material/Grid2';
import { FormGrid } from '../../components/UiComponents/StyledComponents';
import useRoles from './useRoles';

// ModalDialog Component
export default function AddEditRolesModalDialog({
  isEdit,
  formData,
  openModal,
  onClose,
  onModulesUpdate,
}) {
  const { createModules, updateModules } = useRoles();
  const [formValues, setFormValues] = React.useState(formData || {});

  React.useEffect(() => {
    setFormValues(formData);
  }, [formData]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSaveClick = () => {
    const saveFunction = isEdit ? updateModules : createModules;
    saveFunction(formValues).then(onModulesUpdate).finally(onClose);
  };

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
        {isEdit ? 'Edit Module' : 'Add Module'}
        <IconButton
          aria-label="close"
          onClick={onClose}
          sx={{ position: 'absolute', right: 8, top: 8, color: 'grey.500' }}
        >
          <CloseIcon />
        </IconButton>
      </DialogTitle>
      <DialogContent dividers>
        <Box
          component="form"
          sx={{
            display: 'flex',
            flexDirection: 'column',
            width: '100%',
            gap: 2,
          }}
        >
          <Grid container spacing={3}>
            <FormGrid size={{ xs: 6 }}>
              <FormLabel required>Role Name</FormLabel>
              <OutlinedInput
                id="role_name"
                name="role_name"
                type="text"
                placeholder="Name"
                autoComplete="off"
                required
                size="small"
                onChange={handleInputChange}
                value={formValues.role_name || ''}
              />
            </FormGrid>
            <FormGrid size={{ xs: 6 }}>
              <FormLabel>Description</FormLabel>
              <OutlinedInput
                id="role_description"
                name="role_description"
                type="text"
                placeholder="Description"
                autoComplete="off"
                size="small"
                onChange={handleInputChange}
                value={formValues.role_description || ''}
              />
            </FormGrid>
          </Grid>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleSaveClick} autoFocus>
          Save changes
        </Button>
      </DialogActions>
    </Dialog>
  );
}
