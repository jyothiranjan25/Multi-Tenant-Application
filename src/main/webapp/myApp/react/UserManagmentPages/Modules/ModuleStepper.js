import * as React from 'react';
import useModules from './useModules';
import useGetAPIs from '../../components/GetApisService/GetAPIs';
import {
  Box,
  Button,
  Card,
  FormLabel,
  OutlinedInput,
  Step,
  StepLabel,
  Stepper,
} from '@mui/material';
import Grid from '@mui/material/Grid2';
import { SimpleTreeView } from '@mui/x-tree-view/SimpleTreeView';
import {
  CollapseIcon,
  CustomTreeItem,
  EndIcon,
  ExpandIcon,
} from '../../components/UiComponents/CustomSimpleTreeView';
import { FormGrid } from '../../components/UiComponents/StyledComponents';

const steps = ['Create a Module', 'Select Resources'];

const CustomTreeComponent = ({ data }) => {
  return data
    .sort((a, b) => a.id - b.id)
    .map((node) => (
      <CustomTreeItem
        key={node.id}
        itemId={node.id.toString()}
        label={node.resource_name}
      >
        {node.child_resources && (
          <CustomTreeComponent data={node.child_resources} />
        )}
      </CustomTreeItem>
    ));
};

function ModuleStepper({ isEdit, params, onClose, onModulesUpdate }) {
  const [activeStep, setActiveStep] = React.useState(0);
  const [formData, setFormData] = React.useState(isEdit ? params : {});
  const [resources, setResources] = React.useState([]);
  const [selectedItems, setSelectedItems] = React.useState([]);
  const { createModules, updateModules } = useModules();
  const { getResources } = useGetAPIs();

  React.useEffect(() => {
    const filterParams = {
      show_in_menu: true,
    };
    getResources(filterParams).then((data) => {
      setResources(data.map((item) => item.data));
    });

    if (isEdit) {
      const extractIds = (resources) => {
        let ids = [];
        resources.forEach((resource) => {
          ids.push(resource.id.toString());
          if (resource.child_resources) {
            ids = ids.concat(extractIds(resource.child_resources));
          }
        });
        return ids;
      };

      const selectedIds = extractIds(params.resources);
      setSelectedItems(selectedIds);
    }
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleNext = () =>
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  const handleBack = () =>
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  const handleReset = () => {
    setActiveStep(0);
    setFormData({});
    setSelectedItems([]);
  };

  const handleFinish = () => {
    const data = {
      ...formData,
      resource_ids: selectedItems,
    };
    if (isEdit) {
      data.id = params.id;
      updateModules(data).then(() => {
        onModulesUpdate();
      });
    } else {
      createModules(data).then(() => {
        onModulesUpdate();
      });
    }
    onClose();
    handleReset();
  };

  const handleItemSelectionToggle = (event, itemId, isSelected) => {
    setSelectedItems((prevSelectedItems) => {
      if (isSelected) {
        return [...prevSelectedItems, itemId];
      } else {
        return prevSelectedItems.filter((id) => id !== itemId);
      }
    });
  };

  return (
    <Box sx={{ width: '100%' }}>
      <Stepper activeStep={activeStep}>
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
      <Card
        variant="outlined"
        sx={{ width: '100%', minHeight: 320, mt: 2, mb: 1 }}
      >
        {activeStep === 0 && (
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
                <FormLabel required>Module Name</FormLabel>
                <OutlinedInput
                  id="name"
                  name="module_name"
                  type="text"
                  placeholder="Name"
                  autoComplete="off"
                  required
                  size="small"
                  value={formData.module_name || ''}
                  onChange={handleInputChange}
                />
              </FormGrid>
              <FormGrid size={{ xs: 6 }}>
                <FormLabel>Description</FormLabel>
                <OutlinedInput
                  id="description"
                  name="module_description"
                  type="text"
                  placeholder="Description"
                  autoComplete="off"
                  size="small"
                  value={formData.module_description || ''}
                  onChange={handleInputChange}
                />
              </FormGrid>
              <FormGrid size={{ xs: 6 }}>
                <FormLabel>URL</FormLabel>
                <OutlinedInput
                  id="url"
                  name="module_url"
                  type="text"
                  placeholder="URL"
                  autoComplete="off"
                  size="small"
                  value={formData.module_url || ''}
                  onChange={handleInputChange}
                />
              </FormGrid>
              <FormGrid size={{ xs: 6 }}>
                <FormLabel>Icon</FormLabel>
                <OutlinedInput
                  id="module_icon"
                  name="module_icon"
                  type="text"
                  placeholder="Icon"
                  size="small"
                  value={formData.module_icon || ''}
                  onChange={handleInputChange}
                />
              </FormGrid>
            </Grid>
          </Box>
        )}
        {activeStep === 1 && (
          <Box>
            <SimpleTreeView
              aria-label="customized"
              slots={{
                expandIcon: ExpandIcon,
                collapseIcon: CollapseIcon,
                endIcon: EndIcon,
              }}
              sx={{
                overflowX: 'hidden',
                minHeight: 270,
                flexGrow: 1,
                maxWidth: 500,
              }}
              multiSelect
              checkboxSelection
              onItemSelectionToggle={handleItemSelectionToggle}
              defaultSelectedItems={selectedItems}
            >
              <CustomTreeComponent data={resources} />
            </SimpleTreeView>
          </Box>
        )}
      </Card>
      <Box sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
        <Button
          color="inherit"
          disabled={activeStep === 0}
          onClick={handleBack}
          sx={{ mr: 1 }}
        >
          Back
        </Button>
        <Box sx={{ flex: '1 1 auto' }} />
        {!isEdit && <Button onClick={handleReset}>Reset</Button>}
        {activeStep === steps.length - 1 ? (
          <Button onClick={handleFinish}>Save</Button>
        ) : (
          <Button onClick={handleNext}>Next</Button>
        )}
      </Box>
    </Box>
  );
}

export default ModuleStepper;
