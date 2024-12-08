import { ClientSideRowModelModule } from '@ag-grid-community/client-side-row-model';
import { ModuleRegistry } from '@ag-grid-community/core';
import { AgGridReact } from '@ag-grid-community/react';
import '@ag-grid-community/styles/ag-grid.css';
import '@ag-grid-community/styles/ag-theme-quartz.css';
import React, { useCallback, useMemo, useRef } from 'react';
import { useColorScheme } from '@mui/material/styles';
import { CsvExportModule } from '@ag-grid-community/csv-export';
import TablePagination from '@mui/material/TablePagination';
import Box from '@mui/material/Box';

ModuleRegistry.registerModules([ClientSideRowModelModule, CsvExportModule]);

const AgGrid = (props) => {
  // Color Scheme
  const theme = useColorScheme();
  const colorScheme = theme.colorScheme === 'dark';
  const darkTheme = colorScheme ? 'ag-theme-quartz-dark' : 'ag-theme-quartz';

  const styles = {
    width: '100%',
    height: '100%',
    ...(darkTheme === 'ag-theme-quartz-dark' && {
      '--ag-border-color': 'var(--template-palette-TableCell-border)',
      '--ag-header-background-color': 'none',
      '--ag-background-color': 'none',
      backgroundColor: 'transparent',
    }),
  };

  // Grid Reference
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

  const onBtnExport = useCallback(() => {
    gridRef.current.api.exportDataAsCsv();
  }, []);

  return (
    <>
      <div className={'grid ' + darkTheme} style={styles}>
        <AgGridReact
          ref={gridRef}
          defaultColDef={defaultColDef}
          // pagination={true}
          // paginationPageSize={paginationPageSize}
          // paginationPageSizeSelector={paginationPageSizeSelector}
          // Pass Modules to this individual grid
          modules={[ClientSideRowModelModule, CsvExportModule]}
          rowModelType={'clientSide'}
          {...props}
        />
      </div>
    </>
  );
};

export default AgGrid;
