import * as React from 'react';
import ReactDOM from 'react-dom/client';
import { Box, Button } from '@mui/material';
import AppLayout from '../../components/AppLayout';
import useRoles from './useRoles';
import GetAPIs from '../../components/GetApisService/GetAPIs';
import AgGrid from '../../components/UiComponents/AgGridReact';
import RoleModuleStepper from './RoleModuleStepper';
import AddIcon from '@mui/icons-material/Add';
import ActionCellRenderer from '../../components/UiComponents/ActionCell';
import ViewModuleResource from './ViewModuleResource';

// Roles Component
const Roles = () => {
  const { getRoles } = GetAPIs();
  const { deleteRoles } = useRoles();

  const [roles, setRoles] = React.useState([]);
  const [openModal, setOpenModal] = React.useState(false);
  const [isEdit, setIsEdit] = React.useState(false);
  const [formData, setFormData] = React.useState({});
  const [openViewModal, setOpenViewModal] = React.useState(false);
  const [viewModuleResData, setViewModuleResData] = React.useState({});
  const [pageSize, setPageSize] = React.useState(10);
  const [pageOffset, setPageOffset] = React.useState(0);
  const [totalRecords, setTotalRecords] = React.useState(0);

  const paginationFilter = {
    page_offset: pageOffset,
    page_size: pageSize,
  };

  React.useEffect(() => {
    handleModulesUpdate(paginationFilter);
  }, [pageSize, pageOffset]);

  const handleModulesUpdate = React.useCallback(
    (data) => {
      const filterData = {
        ...paginationFilter,
        ...data,
      };
      getRoles(filterData).then((data) => {
        setRoles(data.data);
        setTotalRecords(data.total_count);
      });
    },
    [getRoles]
  );

  const openAddModal = () => {
    setOpenModal(true);
    setFormData({});
  };

  const openEditModal = (params) => {
    return () => {
      setIsEdit(true);
      setFormData(params);
      setOpenModal(true);
    };
  };

  const openModuleResourcesViewModal = (params) => {
    return () => {
      setOpenViewModal(true);
      setViewModuleResData(params);
    };
  };

  const handleDeleteClick = (params) => {
    return () => {
      deleteRoles(params).then(() => {
        handleModulesUpdate(paginationFilter);
      });
    };
  };

  const closeModals = () => {
    setOpenModal(false);
    setOpenViewModal(false);
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
      headerName: 'Actions',
      filter: false,
      flex: 0.5,
      cellRenderer: (params) => (
        <ActionCellRenderer
          onViewClick={openModuleResourcesViewModal(params.data)}
          onEditClick={openEditModal(params.data)}
          onDeleteClick={handleDeleteClick(params.data)}
        />
      ),
    },
  ];

  return (
    <AppLayout
      headerTitle={'Roles'}
      Button={
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
      }
    >
      <Box
        sx={{
          height: 570,
          width: '100%',
        }}
      >
        <AgGrid
          rowData={roles}
          columnDefs={columns}
          pagination={true}
          totalRecords={totalRecords}
          pageSize={pageSize}
          setPageSize={setPageSize}
          pageOffset={pageOffset}
          setPageOffset={setPageOffset}
        />
      </Box>
      {openModal && (
        <RoleModuleStepper
          isEdit={isEdit}
          params={formData}
          openModal={openModal}
          onClose={closeModals}
          onModulesUpdate={handleModulesUpdate}
        />
      )}
      {openViewModal && (
        <ViewModuleResource
          openModal={openViewModal}
          onClose={closeModals}
          viewData={viewModuleResData}
        />
      )}
    </AppLayout>
  );
};

// Render Component
const root = ReactDOM.createRoot(document.getElementById('Roles'));
root.render(<Roles />);
