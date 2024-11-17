import * as React from 'react';
import Box from '@mui/material/Box';
import { DataGrid } from '@mui/x-data-grid';

const FullFeaturedCrudGrid = ({ rows = [], columns = [], ...props }) => {
  let newData = [];
  rows.map((item, index) => {
    newData.push({ serial_no: index + 1, ...item });
  });

  columns.unshift({
    field: 'serial_no',
    headerName: 'S_NO',
    flex: 0.5,
  });

  return (
    <Box
      sx={{
        height: 500,
        width: '100%',
        '& .actions': {
          color: 'text.secondary',
        },
        '& .textPrimary': {
          color: 'text.primary',
        },
      }}
    >
      <DataGrid rows={newData} columns={columns} props />
    </Box>
  );
};

export default FullFeaturedCrudGrid;
