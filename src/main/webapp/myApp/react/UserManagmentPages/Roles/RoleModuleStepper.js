import * as React from 'react';
import useRoles from './useRoles';
import useGetAPIs from '../../components/GetApisService/GetAPIs';
import MUIModal from '../../components/UiComponents/Modal';
import Stepper from '../../components/UiComponents/Stepper';
import { Box, FormLabel, OutlinedInput, Card, TextField } from '@mui/material';
import Grid from '@mui/material/Grid2';
import { FormGrid } from '../../components/UiComponents/StyledComponents';
import AgGrid from '../../components/UiComponents/AgGridReact';
import { useMemo, useRef } from 'react';
import Button from '@mui/material/Button';

const steps = ['Create a Role', 'Select Modules'];

function RoleModuleStepper({
  isEdit,
  params,
  openModal,
  onClose,
  onModulesUpdate,
}) {
  const [formData, setFormData] = React.useState(isEdit ? params : {});
  const [modules, setModules] = React.useState([]);
  const { createModules, updateModules } = useRoles();
  const { getModules } = useGetAPIs();

  React.useEffect(() => {
    getModules().then((data) => {
      setModules(data.map((item) => item.data));
    });
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleClear = () => {
    setFormData({});
  };

  const handleFinish = () => {
    onClose();
    handleClear();
  };

  const children = [
    <AddEditRoleForm
      formData={formData}
      handleInputChange={handleInputChange}
    />,
    <AddEditModuleForm data={modules} />,
  ];

  return (
    <MUIModal
      openModal={openModal}
      header={isEdit ? 'Edit Role' : 'Add Role'}
      onClose={onClose}
    >
      <Stepper
        steps={steps}
        isEdit={isEdit}
        children={children}
        handleClear={handleClear}
        handleFinish={handleFinish}
      />
    </MUIModal>
  );
}

const AddEditRoleForm = ({ formData, handleInputChange }) => {
  return (
    <>
      <Card variant="outlined" sx={{ width: '100%', minHeight: 320 }}>
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
                id="name"
                name="role_name"
                type="text"
                placeholder="Name"
                autoComplete="off"
                required
                size="small"
                value={formData.role_name || ''}
                onChange={handleInputChange}
              />
            </FormGrid>
            <FormGrid size={{ xs: 6 }}>
              <FormLabel>Description</FormLabel>
              <OutlinedInput
                id="description"
                name="role_description"
                type="text"
                placeholder="Description"
                autoComplete="off"
                size="small"
                value={formData.role_description || ''}
                onChange={handleInputChange}
              />
            </FormGrid>
          </Grid>
        </Box>
      </Card>
    </>
  );
};

const AddEditModuleForm = ({ data }) => {
  const gridRef = useRef(null);

  const columns = [
    {
      field: 'serial',
      headerName: 'SNo',
      filter: false,
      flex: 0.1,
      valueGetter: (params) => params.node.rowIndex + 1,
    },
    { field: 'module_name', headerName: 'Name' },
    { field: 'module_description', headerName: 'Description' },
    {
      field: 'module_order',
      headerName: 'Order',
      editable: true,
      valueParser: (params) => Number(params.newValue),
    },
  ];

  const rowSelection = useMemo(() => {
    return {
      mode: 'multiRow',
      isRowSelectable: (rowNode) =>
        rowNode.data.resources ? rowNode.data.resources.length !== 0 : false,
      hideDisabledCheckboxes: true,
    };
  }, []);

  const selectionColumnDef = useMemo(() => {
    return {
      sortable: true,
      width: 120,
      maxWidth: 120,
      suppressHeaderMenuButton: false,
      pinned: 'left',
    };
  }, []);

  // Function to get selected rows
  const handleGetSelectedRows = () => {
    if (gridRef.current && gridRef.current.api) {
      const selectedRows = gridRef.current.api.getSelectedRows();
      console.log('Selected Rows:', selectedRows);
    }
  };

  return (
    <Box
      sx={{
        height: 320,
        width: '100%',
        '& .actions': {
          color: 'text.secondary',
        },
        '& .textPrimary': {
          color: 'text.primary',
        },
      }}
    >
      <AgGrid
        ref={gridRef}
        rowData={data}
        columnDefs={columns}
        rowSelection={rowSelection}
        selectionColumnDef={selectionColumnDef}
        onGridReady={(params) => (gridRef.current = params)}
      />
      <Button onClick={handleGetSelectedRows}>Get Selected Rows</Button>
    </Box>
  );
};

export default RoleModuleStepper;
