import * as React from 'react';
import AppLayout from '../../components/AppLayout';
import useModules from './useModules';
import ModuleStepper from './ModuleStepper';
import ViewResourcesModalDialog from './ViewResourcesModalDialog';
import AgGrid from '../../components/UiComponents/AgGridReact';
import ActionCellRenderer from '../../components/UiComponents/ActionCell';
import useGetAPIs from '../../components/GetApisService/GetAPIs';
import {
  Box,
  CardHeader,
  CardContent,
  IconButton,
  Tooltip,
  Dialog,
  DialogTitle,
  DialogContent,
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import CloseIcon from '@mui/icons-material/Close';
import ReactDOM from 'react-dom/client';
import Button from '@mui/material/Button';

const Modules = (props) => {
  const { getModules } = useGetAPIs();
  const { deleteModules } = useModules();
  const [modules, setModules] = React.useState([]);
  const [isEdit, setIsEdit] = React.useState(false);
  const [editData, setEditData] = React.useState({});
  const [resTreeData, setResTreeData] = React.useState([]);
  const [openStepperModal, setOpenStepperModal] = React.useState(false);
  const [openResViewModal, setOpenResViewModal] = React.useState(false);

  React.useEffect(() => {
    handleModulesUpdate();
  }, []);

  const handleClickOpen = () => {
    setIsEdit(false);
    setOpenStepperModal(true);
  };

  const handleViewClick = (params) => {
    return () => {
      setResTreeData(params.resources);
      setOpenResViewModal(true);
    };
  };

  const handleEditClick = (params) => {
    return () => {
      setIsEdit(true);
      setEditData(params);
      setOpenStepperModal(true);
    };
  };

  const handleDeleteClick = (params) => {
    return () => {
      deleteModules(params).then(() => {
        handleModulesUpdate();
      });
    };
  };

  const handleClose = () => {
    setIsEdit(false);
    setOpenStepperModal(false);
    setOpenResViewModal(false);
  };

  const handleModulesUpdate = () => {
    getModules().then((data) => setModules(data.map((item) => item.data)));
  };

  const columns = [
    {
      field: 'serial',
      headerName: 'SNo',
      filter: false,
      valueGetter: (params) => params.node.rowIndex + 1,
    },
    { field: 'module_name', headerName: 'Name' },
    { field: 'module_description', headerName: 'Description' },
    {
      field: 'actions',
      headerName: 'Actions',
      filter: false,
      cellRenderer: (params) => (
        <ActionCellRenderer
          onViewClick={
            params.data?.resources.length > 0
              ? handleViewClick(params.data)
              : undefined
          }
          onEditClick={handleEditClick(params.data)}
          onDeleteClick={handleDeleteClick(params.data)}
        />
      ),
    },
  ];

  return (
    <AppLayout
      headerTitle={'Modules'}
      Button={
        <Button
          onClick={handleClickOpen}
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
      <>
        <Box
          sx={{
            height: 600,
            width: '100%',
            '& .actions': {
              color: 'text.secondary',
            },
            '& .textPrimary': {
              color: 'text.primary',
            },
          }}
        >
          <AgGrid rowData={modules} columnDefs={columns} />
        </Box>
      </>
      <ModalDialog
        isEdit={isEdit}
        params={editData}
        openModal={openStepperModal}
        onClose={handleClose}
        onModulesUpdate={handleModulesUpdate}
      />
      <ViewResourcesModalDialog
        openModal={openResViewModal}
        onClose={handleClose}
        data={resTreeData}
      />
    </AppLayout>
  );
};

const ModalDialog = ({
  isEdit,
  params,
  openModal,
  onClose,
  onModulesUpdate,
}) => (
  <Dialog
    fullWidth
    maxWidth="md"
    onClose={onClose}
    aria-labelledby="customized-dialog-title"
    open={openModal}
    scroll={'paper'}
  >
    <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
      {isEdit ? 'Edit Module' : 'Add Module'}
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
      <ModuleStepper
        isEdit={isEdit}
        params={params}
        onClose={onClose}
        onModulesUpdate={onModulesUpdate}
      />
    </DialogContent>
  </Dialog>
);

const root = ReactDOM.createRoot(document.getElementById('Modules'));
root.render(<Modules />);
