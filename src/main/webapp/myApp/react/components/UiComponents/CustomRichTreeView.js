import * as React from 'react';
import { RichTreeView } from '@mui/x-tree-view/RichTreeView';
import {
  ExpandIcon,
  CollapseIcon,
  EndIcon,
  CustomTreeItem,
} from './CustomSimpleTreeView';

const CustomRichTreeView = ({ items }) => (
  <RichTreeView
    slots={{
      expandIcon: ExpandIcon,
      collapseIcon: CollapseIcon,
      endIcon: EndIcon,
      item: CustomTreeItem,
    }}
    items={items}
  />
);

// example data
const mapGroupsToTreeData = (groups) => {
  return groups
    .sort((a, b) => a.id - b.id) // Sort by id at the current level
    .map((group) => ({
      id: group.id.toString(),
      label: group.group_name,
      children: group.child_groups
        ? mapGroupsToTreeData(group.child_groups)
        : [],
    }));
};

export default CustomRichTreeView;
