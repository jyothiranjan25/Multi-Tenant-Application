import * as React from 'react';
import { useColorScheme } from '@mui/material/styles';
import { useMemo, useRef, useCallback } from 'react';
import { AgGridReact } from '@ag-grid-community/react';
import '@ag-grid-community/styles/ag-grid.css';
import '@ag-grid-community/styles/ag-theme-quartz.css';
import { ClientSideRowModelModule } from '@ag-grid-community/client-side-row-model';
import { ModuleRegistry } from '@ag-grid-community/core';

ModuleRegistry.registerModules([ClientSideRowModelModule]);

const AgGrid = (props) => {
  // Color Scheme
  const theme = useColorScheme();
  const colorScheme = theme.colorScheme === 'dark';
  const darkTheme = colorScheme ? 'ag-theme-quartz-dark' : 'ag-theme-quartz';

  const gridRef = useRef();

  // Default Column Definition
  const defaultColDef = useMemo(() => {
    return {
      flex: 1,
      minWidth: 150,
      filter: 'agTextColumnFilter',
      suppressHeaderMenuButton: true,
      suppressHeaderContextMenu: true,
    };
  }, []);

  // sets 10 rows per page (default is 100)
  const paginationPageSize = 10;

  // allows the user to select the page size from a predefined list of page sizes
  const paginationPageSizeSelector = [10, 20, 50];

  return (
    <div className={darkTheme} style={{ width: '100%', height: '100%' }}>
      <AgGridReact
        {...props}
        ref={gridRef}
        defaultColDef={defaultColDef}
        pagination={true}
        paginationPageSize={paginationPageSize}
        paginationPageSizeSelector={paginationPageSizeSelector}
        rowModelType={'clientSide'}
      />
    </div>
  );
};

export default AgGrid;
