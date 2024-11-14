import * as React from 'react';
import ReactDOM from 'react-dom/client';
import {
  Box,
  CardHeader,
  CardContent,
  Tooltip,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  FormLabel,
  OutlinedInput,
  Button,
  IconButton,
} from '@mui/material';
import Grid from '@mui/material/Grid2';
import AddIcon from '@mui/icons-material/Add';
import CloseIcon from '@mui/icons-material/Close';
import AppLayout from '../../components/AppLayout';
import useRoles from './useRoles';
import GetAPIs from '../../components/GetApisService/GetAPIs';
import AgGrid from '../../components/UiComponents/AgGridReact';
import ActionCellRenderer from '../../components/UiComponents/ActionCell';
import { FormGrid } from '../../components/UiComponents/StyledComponents';

// Roles Component
const Roles = () => {
  const { getRoles } = GetAPIs();
  const { deleteModules } = useRoles();

  const [roles, setRoles] = React.useState([]);
  const [openModal, setOpenModal] = React.useState(false);
  const [isEdit, setIsEdit] = React.useState(false);
  const [formData, setFormData] = React.useState({});
  const [resTreeData, setResTreeData] = React.useState([]);
  const [openResViewModal, setOpenResViewModal] = React.useState(false);

  React.useEffect(() => {
    handleModulesUpdate();
  }, []);

  const handleModulesUpdate = React.useCallback(() => {
    getRoles().then((data) => setRoles(data.map((item) => item.data)));
  }, [getRoles]);

  const openAddModal = () => {
    setOpenModal(true);
    setFormData({});
  };
  const closeModals = () => {
    setOpenModal(false);
    setIsEdit(false);
    setOpenResViewModal(false);
  };

  const openEditModal = (params) => {
    setIsEdit(true);
    setFormData(params);
    setOpenModal(true);
  };

  const handleDeleteClick = (params) => {
    deleteModules(params).then(handleModulesUpdate);
  };

  const columns = [
    {
      field: 'serial',
      headerName: 'SNo',
      filter: false,
      flex: 0.1,
      valueGetter: (params) => params.node.rowIndex + 1,
    },
    { field: 'role_name', headerName: 'Name' },
    { field: 'role_description', headerName: 'Description' },
    {
      field: 'actions',
      headerName: 'Actions',
      filter: false,
      cellRenderer: (params) => (
        <ActionCellRenderer
          onViewClick={() => {
            setResTreeData(params.data.resources);
            setOpenResViewModal(true);
          }}
          onEditClick={() => openEditModal(params.data)}
          onDeleteClick={() => handleDeleteClick(params.data)}
        />
      ),
    },
  ];

  return (
    <AppLayout>
      <CardHeader
        action={
          <Tooltip title="Add">
            <Button
              onClick={openAddModal}
              color="inherit"
              aria-label="Add"
              size="small"
              variant="outlined"
            >
              <AddIcon />
              Add
            </Button>
          </Tooltip>
        }
        title="Roles"
        titleTypographyProps={{ variant: 'h2' }}
        sx={{ padding: '10px' }}
      />
      <CardContent>
        <Box
          sx={{
            height: 590,
            width: '100%',
            '& .actions': { color: 'text.secondary' },
            '& .textPrimary': { color: 'text.primary' },
          }}
        >
          <AgGrid rowData={roles} columnDefs={columns} />
        </Box>
      </CardContent>
      {openModal && (
        <ModalDialog
          isEdit={isEdit}
          formData={formData}
          openModal={openModal}
          onClose={closeModals}
          onModulesUpdate={handleModulesUpdate}
        />
      )}
    </AppLayout>
  );
};

// ModalDialog Component
const ModalDialog = ({
  isEdit,
  formData,
  openModal,
  onClose,
  onModulesUpdate,
}) => {
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
};

// Render Component
const root = ReactDOM.createRoot(document.getElementById('Roles'));
root.render(<Roles />);
