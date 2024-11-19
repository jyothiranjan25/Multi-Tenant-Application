import * as React from 'react';
import ReactDOM from 'react-dom/client';
import { Box, CardHeader, CardContent, Tooltip, Button } from '@mui/material';
import AppLayout from '../../components/AppLayout';
import useRoles from './useRoles';
import GetAPIs from '../../components/GetApisService/GetAPIs';
import AgGrid from '../../components/UiComponents/MUIDataTable';
import RoleModuleStepper from './RoleModuleStepper';
import { GridActionsCellItem } from '@mui/x-data-grid';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

// Roles Component
const Roles = () => {
  const { getRoles } = GetAPIs();
  const { deleteRoles } = useRoles();

  const [roles, setRoles] = React.useState([]);
  const [openModal, setOpenModal] = React.useState(false);
  const [isEdit, setIsEdit] = React.useState(false);
  const [formData, setFormData] = React.useState({});

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

  const openEditModal = (params) => {
    setIsEdit(true);
    setFormData(params);
    setOpenModal(true);
  };

  const openViewModal = (params) => {};

  const handleDeleteClick = (params) => {
    deleteRoles(params).then(handleModulesUpdate);
  };

  const closeModals = () => {
    setOpenModal(false);
    setIsEdit(false);
  };

  const columns = [
    { field: 'role_name', headerName: 'Name', flex: 0.5 },
    { field: 'role_description', headerName: 'Description', flex: 0.5 },
    {
      field: 'role_icon',
      headerName: 'Icon',
      flex: 0.5,
    },
    {
      field: 'actions',
      type: 'actions',
      headerName: 'Actions',
      flex: 0.5,
      cellClassName: 'actions',
      getActions: (params) => {
        return [
          <GridActionsCellItem
            icon={<EditIcon />}
            label="Edit"
            onClick={() => openEditModal(params.row)}
            color="inherit"
          />,
          <GridActionsCellItem
            icon={<DeleteIcon />}
            label="Delete"
            onClick={() => handleDeleteClick(params.row)}
            color="inherit"
          />,
        ];
      },
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
          <AgGrid rows={roles} columns={columns} />
        </Box>
      </CardContent>
      {openModal && (
        <RoleModuleStepper
          isEdit={isEdit}
          params={formData}
          openModal={openModal}
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
