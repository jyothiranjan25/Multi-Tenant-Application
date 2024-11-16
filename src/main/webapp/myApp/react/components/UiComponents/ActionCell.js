import { IconButton, Tooltip } from '@mui/material';
import ViewCompactIcon from '@mui/icons-material/ViewCompact';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import * as React from 'react';

const ActionCellRenderer = ({
  onAddClick,
  onEditClick,
  onViewClick,
  onDeleteClick,
}) => (
  <div>
    {onAddClick ? (
      <Tooltip title="Add" placement="bottom">
        <IconButton
          onClick={onAddClick}
          color="inherit"
          size="small"
          sx={{ width: '30px', height: '30px', marginRight: '8px' }}
        >
          <AddIcon />
        </IconButton>
      </Tooltip>
    ) : null}
    {onEditClick ? (
      <Tooltip title="Edit" placement="bottom">
        <IconButton
          onClick={onEditClick}
          color="inherit"
          size="small"
          sx={{ width: '30px', height: '30px', marginRight: '8px' }}
        >
          <EditIcon />
        </IconButton>
      </Tooltip>
    ) : null}
    {onViewClick ? (
      <Tooltip title="View" placement="bottom">
        <IconButton
          onClick={onViewClick}
          color="inherit"
          size="small"
          sx={{ width: '30px', height: '30px', marginRight: '8px' }}
        >
          <ViewCompactIcon />
        </IconButton>
      </Tooltip>
    ) : null}
    {onDeleteClick ? (
      <Tooltip title="Delete" placement="bottom">
        <IconButton
          onClick={onDeleteClick}
          color="inherit"
          size="small"
          sx={{ width: '30px', height: '30px' }}
        >
          <DeleteIcon />
        </IconButton>
      </Tooltip>
    ) : null}
  </div>
);
export default ActionCellRenderer;
