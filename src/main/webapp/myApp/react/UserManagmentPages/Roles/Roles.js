import * as React from 'react';
import ReactDOM from 'react-dom/client';
import { Box, CardHeader, CardContent, Tooltip, Button } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import AppLayout from '../../components/AppLayout';
import useRoles from './useRoles';
import GetAPIs from '../../components/GetApisService/GetAPIs';
import AgGrid from '../../components/UiComponents/AgGridReact';
import ActionCellRenderer from '../../components/UiComponents/ActionCell';
import AddEditRolesModalDialog from './AddEditRolesModal';
import AddEditRoleModuleResources from './AddEditRoleModuleResources';

// Roles Component
const Roles = () => {
  const { getRoles } = GetAPIs();
  const { deleteModules } = useRoles();

  const [roles, setRoles] = React.useState([]);
  const [openModal, setOpenModal] = React.useState(false);
  const [isEdit, setIsEdit] = React.useState(false);
  const [formData, setFormData] = React.useState({});
  const [roleModulesData, setRoleModulesData] = React.useState([]);
  const [openRoleModulesModal, setOpenRoleModulesModal] = React.useState(false);
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

  const openAddRoleModuleModal = (params) => {
    setRoleModulesData(params);
    setOpenRoleModulesModal(true);
  };

  const openEditModal = (params) => {
    setIsEdit(true);
    setFormData(params);
    setOpenModal(true);
  };

  const openViewModal = (params) => {
    setResTreeData(params);
    setOpenResViewModal(true);
  };

  const handleDeleteClick = (params) => {
    deleteModules(params).then(handleModulesUpdate);
  };

  const closeModals = () => {
    setOpenModal(false);
    setIsEdit(false);
    setOpenResViewModal(false);
    setOpenRoleModulesModal(false);
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
          onAddClick={() => openAddRoleModuleModal(params.data)}
          onViewClick={() => openViewModal(params.data)}
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
        <AddEditRolesModalDialog
          isEdit={isEdit}
          formData={formData}
          openModal={openModal}
          onClose={closeModals}
          onModulesUpdate={handleModulesUpdate}
        />
      )}
      {openRoleModulesModal && (
        <AddEditRoleModuleResources
          formData={roleModulesData}
          openModal={openRoleModulesModal}
          onClose={closeModals}
          onModulesUpdate={handleModulesUpdate}
        />
      )}
    </AppLayout>
  );
};

// Render Component
const root = ReactDOM.createRoot(document.getElementById('Roles'));
root.render(<Roles />);
