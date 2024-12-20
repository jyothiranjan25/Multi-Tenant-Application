import React from 'react';
import MUIModal from '../../components/UiComponents/Modal';
import { SimpleTreeView } from '@mui/x-tree-view/SimpleTreeView';
import {
  CollapseIcon,
  CustomTreeItem,
  EndIcon,
  ExpandIcon,
} from '../../components/UiComponents/CustomSimpleTreeView';

const ViewModuleResource = ({ openModal, onClose, viewData }) => {
  const [defaultSelected, setDefaultSelected] = React.useState([]);
  const handleNodeClick = (node) => {
    console.log(node);
  };
  return (
    <MUIModal header={'view'} openModal={openModal} onClose={onClose}>
      <SimpleTreeView
        aria-label="customized"
        expansionTrigger="iconContainer"
        slots={{
          expandIcon: ExpandIcon,
          collapseIcon: CollapseIcon,
          endIcon: EndIcon,
        }}
        sx={{
          overflowX: 'scroll',
          minHeight: 270,
          maxHeight: 300,
          flexGrow: 1,
        }}
      >
        <CustomTreeComponent
          data={viewData}
          onNodeClick={handleNodeClick}
          defaultSelected={defaultSelected}
          setDefaultSelected={setDefaultSelected}
        />
      </SimpleTreeView>
    </MUIModal>
  );
};

const CustomTreeComponent = ({
  data,
  onNodeClick,
  defaultSelected,
  setDefaultSelected,
}) => {
  const roleId = data.id;
  const moduleResources = data.modules_resources;
  if (!moduleResources) return null;

  return moduleResources
    .sort((a, b) => a.module_order - b.module_order)
    .map((node) => {
      const moduleId = node.id;
      const resources = node.resources;
      if (!resources) return null;

      const renderResources = (resources) => {
        return resources.map((resource) => {
          const resourceId = resource.id;
          const key =
            roleId.toString() +
            '.' +
            moduleId.toString() +
            '.' +
            resourceId.toString();
          React.useEffect(() => {
            if (resource?.show_in_menu === true) {
              setDefaultSelected((prevSelected) => [...prevSelected, key]);
            }
          }, []);

          return (
            <CustomTreeItem
              key={resourceId}
              itemId={key}
              label={resource.resource_name}
            >
              {resource.child_resources &&
                renderResources(resource.child_resources)}
            </CustomTreeItem>
          );
        });
      };

      return (
        <CustomTreeItem
          key={moduleId}
          itemId={moduleId.toString()}
          label={node.module_name}
        >
          {node.resources.length > 0 && (
            <SimpleTreeView
              multiSelect
              checkboxSelection
              defaultSelectedItems={defaultSelected}
              disabledItemsFocusable={true}
              expansionTrigger="iconContainer"
              key={moduleId}
              slots={{
                expandIcon: ExpandIcon,
                collapseIcon: CollapseIcon,
                endIcon: EndIcon,
              }}
              onItemSelectionToggle={(event, ids) => {
                onNodeClick(ids);
              }}
            >
              {renderResources(resources)}
            </SimpleTreeView>
          )}
        </CustomTreeItem>
      );
    });
};

export default ViewModuleResource;
