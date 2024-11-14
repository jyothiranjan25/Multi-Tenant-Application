import * as React from 'react';
import { useState } from 'react';
import AppLayout from '../../components/AppLayout';
import ReactDOM from 'react-dom/client';
import Grid from '@mui/material/Grid2';
import { FormGrid } from '../../components/UiComponents/StyledComponents';
import { Box, Card, FormLabel, OutlinedInput, Button } from '@mui/material';
import { SimpleTreeView } from '@mui/x-tree-view/SimpleTreeView';
import {
  ExpandIcon,
  CollapseIcon,
  EndIcon,
  CustomTreeItem,
} from '../../components/UiComponents/CustomSimpleTreeView';
import DeleteIcon from '@mui/icons-material/Delete';
import ClearOutlinedIcon from '@mui/icons-material/ClearOutlined';
import useUserGroup from './useUserGroup';

const UserGroup = (props) => {
  const { userGroup, createUserGroup, updateUserGroup, deleteUserGroup } =
    useUserGroup();
  const [formData, setFormData] = useState({});
  const [parentData, setParentData] = useState({});
  const [isEdit, setIsEdit] = useState(false);
  const [isFormChanged, setIsFormChanged] = useState(false);
  const [isAddingChild, setIsAddingChild] = useState(false);

  const handleNodeClick = (node) => {
    const data = {
      id: node.id,
      group_name: node.group_name,
      group_description: node.group_description,
    };
    setFormData(data);
    setIsEdit(true);
    setParentData({});
    setIsAddingChild(false);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const data = {
      ...formData,
    };

    if (isEdit) {
      data.id = formData.id;
      updateUserGroup(data);
    } else {
      if (isAddingChild) {
        data.parent_id = parentData.id;
      }
      createUserGroup(data);
    }

    handleClear();
  };

  const handleClear = (event) => {
    setFormData({});
    setIsEdit(false);
    setIsFormChanged(false);
    setParentData({});
    setIsAddingChild(false);
    event && event.preventDefault();
  };

  const handleDelete = () => {
    const data = {
      id: formData.id,
    };
    deleteUserGroup(data).then(() => {
      handleClear();
    });
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
    setIsFormChanged(true);
  };

  const handleParentData = (event) => {
    setIsAddingChild(true);
    setParentData({
      id: formData.id,
      group_name: formData.group_name,
      group_description: formData.group_description,
    });
    event.preventDefault();
    setFormData({});
    setIsEdit(false);
  };

  return (
    <AppLayout>
      <Box sx={{ width: '100%', maxWidth: { sm: '100%', md: '1700px' } }}>
        <Grid
          container
          spacing={2}
          columns={12}
          sx={{ mb: (theme) => theme.spacing(2) }}
        >
          <Grid size={{ xs: 12, sm: 6, lg: 6 }}>
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
            >
              <CustomTreeComponent
                data={userGroup}
                onNodeClick={handleNodeClick}
              />
            </SimpleTreeView>
          </Grid>
          <Grid size={{ xs: 12, sm: 6, lg: 6 }}>
            <Card variant="outlined" sx={{ width: '100%' }}>
              <Box
                component="form"
                onSubmit={handleSubmit}
                sx={{
                  display: 'flex',
                  flexDirection: 'column',
                  width: '100%',
                  gap: 2,
                }}
              >
                <Grid container spacing={3}>
                  {isAddingChild ? (
                    <FormGrid size={{ xs: 12 }}>
                      <FormLabel htmlFor="address1" required>
                        Parent Name
                      </FormLabel>
                      <OutlinedInput
                        id="name"
                        name="parent_name"
                        type="text"
                        placeholder="Parent Name"
                        autoComplete="off"
                        required
                        size="small"
                        value={parentData.group_name || ''}
                        onChange={handleInputChange}
                        disabled={true}
                      />
                    </FormGrid>
                  ) : null}
                  <FormGrid size={{ xs: 12 }}>
                    <FormLabel htmlFor="address1" required>
                      Name
                    </FormLabel>
                    <OutlinedInput
                      id="name"
                      name="group_name"
                      type="text"
                      placeholder="Name"
                      autoComplete="off"
                      required
                      size="small"
                      value={formData.group_name || ''}
                      onChange={handleInputChange}
                    />
                  </FormGrid>
                  <FormGrid size={{ xs: 12 }}>
                    <FormLabel htmlFor="address1" required>
                      Description
                    </FormLabel>
                    <OutlinedInput
                      id="description"
                      name="group_description"
                      type="text"
                      placeholder="Description"
                      autoComplete="off"
                      size="small"
                      value={formData.group_description || ''}
                      onChange={handleInputChange}
                    />
                  </FormGrid>
                  <FormGrid size={{ xs: 4 }}>
                    {isEdit ? (
                      isFormChanged ? (
                        <Button type="submit" fullWidth variant="contained">
                          {'Update'}
                        </Button>
                      ) : (
                        <Button
                          fullWidth
                          variant="contained"
                          onClick={handleParentData}
                        >
                          {'Add Child'}
                        </Button>
                      )
                    ) : (
                      <Button type="submit" fullWidth variant="contained">
                        {'Save'}
                      </Button>
                    )}
                  </FormGrid>
                  {Object.keys(formData).length > 0 ||
                  isAddingChild ||
                  isAddingChild ? (
                    <FormGrid size={{ xs: 4 }}>
                      <Button
                        type="clear"
                        fullWidth
                        variant="outlined"
                        startIcon={<ClearOutlinedIcon />}
                        onClick={handleClear}
                      >
                        Clear
                      </Button>
                    </FormGrid>
                  ) : null}
                  {isEdit && (
                    <FormGrid size={{ xs: 4 }}>
                      <Button
                        fullWidth
                        variant="outlined"
                        startIcon={<DeleteIcon />}
                        onClick={handleDelete}
                      >
                        Delete
                      </Button>
                    </FormGrid>
                  )}
                </Grid>
              </Box>
            </Card>
          </Grid>
        </Grid>
      </Box>
    </AppLayout>
  );
};

const CustomTreeComponent = ({ data, onNodeClick }) => {
  return data
    .sort((a, b) => a.id - b.id)
    .map((node) => (
      <CustomTreeItem
        key={node.id}
        itemId={node.id.toString()}
        label={node.group_name}
        onClick={() => onNodeClick(node)}
      >
        {node.child_groups && (
          <CustomTreeComponent
            data={node.child_groups}
            onNodeClick={onNodeClick}
          />
        )}
      </CustomTreeItem>
    ));
};
const root = ReactDOM.createRoot(document.getElementById('UserGroup'));
root.render(<UserGroup />);
