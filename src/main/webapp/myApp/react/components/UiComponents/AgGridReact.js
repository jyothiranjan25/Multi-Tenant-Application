import { ClientSideRowModelModule } from '@ag-grid-community/client-side-row-model';
import { ModuleRegistry } from '@ag-grid-community/core';
import { AgGridReact } from '@ag-grid-community/react';
import '@ag-grid-community/styles/ag-grid.css';
import '@ag-grid-community/styles/ag-theme-quartz.css';
import React, { useCallback, useMemo, useRef } from 'react';
import { useColorScheme } from '@mui/material/styles';
import { CsvExportModule } from '@ag-grid-community/csv-export';
import TablePagination from '@mui/material/TablePagination';

ModuleRegistry.registerModules([ClientSideRowModelModule, CsvExportModule]);

const AgGrid = ({ totalCount, pageOffset, PageSize, onChange, ...props }) => {
  // Color Scheme
  const theme = useColorScheme();
  const colorScheme = theme.colorScheme === 'dark';
  const darkTheme = colorScheme ? 'ag-theme-quartz-dark' : 'ag-theme-quartz';

  const AgGridStyles = {
    width: '100%',
    height: '100%',
    ...(darkTheme === 'ag-theme-quartz-dark' && {
      '--ag-border-color': 'var(--template-palette-TableCell-border)',
    }),
    '--ag-header-background-color': 'none',
    '--ag-background-color': 'none',
    '--ag-wrapper-border-radius': 'none',
    backgroundColor: 'transparent',
  };

  const PaginationStyles = {
    border:
      darkTheme === 'ag-theme-quartz-dark'
        ? '1px solid var(--template-palette-TableCell-border)'
        : 'var(--ag-borders) var(--ag-border-color)',
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
    console.log('Exporting...');
    gridRef.current.api.exportDataAsCsv();
  }, []);

  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
  const [total, setTotal] = React.useState(0);

  React.useEffect(() => {
    if (pageOffset !== undefined) setPage(pageOffset);
  }, [pageOffset]);

  React.useEffect(() => {
    if (PageSize !== undefined) setRowsPerPage(PageSize);
  }, [PageSize]);

  React.useEffect(() => {
    if (totalCount !== undefined) setTotal(totalCount);
  }, [totalCount]);

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  return (
    <>
      <div className={'grid ' + darkTheme} style={AgGridStyles}>
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
        <div style={PaginationStyles}>
          <TablePagination
            component="div"
            count={total}
            onPageChange={onChange}
            page={page}
            rowsPerPage={rowsPerPage}
            onRowsPerPageChange={handleChangeRowsPerPage}
            showFirstButton={true}
            showLastButton={true}
          />
        </div>
      </div>
    </>
  );
};

export default AgGrid;
