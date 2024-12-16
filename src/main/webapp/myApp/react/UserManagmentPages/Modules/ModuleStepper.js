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
import { FormGrid } from '../../components/UiComponents/StyledComponents';
import { Tree } from 'antd';
import AntDesign from '../../components/UiComponents/AntDesign';
import { useState } from 'react';

const steps = ['Create a Module', 'Select Resources'];

const transformDataToTreeData = (data, ParentId) => {
  return data
    .filter((node) => node.child_resources || node.resource_url != '#')
    .sort((a, b) => a.id - b.id)
    .map((node) => {
      const key = ParentId ? `${ParentId}.${node.id}` : `${node.id}`;
      return {
        title: node.resource_name,
        key: key,
        children: node.child_resources
          ? transformDataToTreeData(node.child_resources, node.id)
          : [],
      };
    });
};

function ModuleStepper({ isEdit, params, onClose, onModulesUpdate }) {
  const [activeStep, setActiveStep] = React.useState(0);
  const [formData, setFormData] = React.useState(isEdit ? params : {});
  const [resources, setResources] = React.useState([]);
  const [selectedResources, setSelectedResources] = useState([]);
  const [checkedKeys, setCheckedKeys] = useState([]);
  const { createModules, updateModules } = useModules();
  const { getResources } = useGetAPIs();

  React.useEffect(() => {
    const filterParams = {
      show_in_menu: true,
    };
    getResources(filterParams).then((data) => {
      setResources(data.data);
    });

    if (isEdit) {
      const extractIds = (data, parentId = null) => {
        let ids = [];
        let checkedKeys = [];

        data.forEach((resource) => {
          const resourceId = resource.id.toString();
          ids.push(resourceId);
          // Include the parent ID in checkedKeys for this resource
          if (parentId) {
            checkedKeys.push(`${parentId}.${resourceId}`);
          }
          if (resource.child_resources) {
            const childData = extractIds(resource.child_resources, resourceId);
            ids = ids.concat(childData.ids);
            checkedKeys = checkedKeys.concat(childData.checkedKeys);
          }
        });
        return { ids, checkedKeys };
      };

      const { ids, checkedKeys } = extractIds(params.resources);
      setCheckedKeys(checkedKeys);
      setSelectedResources(ids);
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
  };

  const handleFinish = () => {
    const data = {
      ...formData,
      resource_ids: selectedResources,
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

  const treeData = transformDataToTreeData(resources);
  const onCheck = (checkedKeysValue) => {
    setCheckedKeys(checkedKeysValue);
    // Split by dot, flatten the array, convert to numbers, and filter unique values
    const uniqueValues = [
      ...new Set(
        checkedKeysValue.flatMap((value) => value.split('.').map(Number))
      ),
    ];
    setSelectedResources(uniqueValues);
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
          <AntDesign>
            <Tree
              showLine
              checkable
              onCheck={onCheck}
              checkedKeys={checkedKeys}
              treeData={treeData}
              rootStyle={{
                background: 'none',
                backgroundColor: 'none',
                overflowX: 'scroll',
                maxHeight: 280,
                flexGrow: 1,
              }}
            />
          </AntDesign>
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
