import * as React from 'react';
import { useMemo, useRef } from 'react';
import useRoles from './useRoles';
import useGetAPIs from '../../components/GetApisService/GetAPIs';
import MUIModal from '../../components/UiComponents/Modal';
import Stepper from '../../components/UiComponents/Stepper';
import { Box, Card, FormLabel, OutlinedInput } from '@mui/material';
import Grid from '@mui/material/Grid2';
import { FormGrid } from '../../components/UiComponents/StyledComponents';
import AgGrid from '../../components/UiComponents/AgGridReact';

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
  const { createRoles, updateRoles } = useRoles();
  const selectedRowsRef = useRef([]);
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
    const data = {
      ...formData,
      add_modules: selectedRowsRef.current.map((item) => ({
        id: item.id,
        module_order: item?.module_order,
      })),
    };

    if (isEdit) {
      data.id = formData.id;
      updateRoles(data).then(() => {
        onModulesUpdate();
      });
    } else {
      createRoles(data).then(() => {
        onModulesUpdate();
      });
    }
    onClose();
    handleClear();
  };

  const children = [
    <AddEditRoleForm
      formData={formData}
      handleInputChange={handleInputChange}
    />,
    <AddEditModuleForm
      data={modules}
      onSelectedRowsChange={(rows) => {
        selectedRowsRef.current = rows;
      }}
    />,
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

const AddEditModuleForm = ({ data, onSelectedRowsChange }) => {
  const gridRef = useRef();

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
        rowNode.data
          ? rowNode.data.module_url !== undefined ||
            rowNode.data?.resources.length !== 0
          : false,
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

  const handleRowSelection = () => {
    if (gridRef.current && gridRef.current.api) {
      const selectedRows = gridRef.current.api.getSelectedRows();
      onSelectedRowsChange(selectedRows);
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
        onSelectionChanged={handleRowSelection}
        onGridReady={(params) => (gridRef.current = params)}
      />
    </Box>
  );
};

export default RoleModuleStepper;
