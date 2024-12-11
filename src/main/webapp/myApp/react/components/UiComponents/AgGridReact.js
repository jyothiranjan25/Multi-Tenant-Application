import { ClientSideRowModelModule } from '@ag-grid-community/client-side-row-model';
import { ModuleRegistry } from '@ag-grid-community/core';
import { AgGridReact } from '@ag-grid-community/react';
import '@ag-grid-community/styles/ag-grid.css';
import '@ag-grid-community/styles/ag-theme-quartz.css';
import React, { useCallback, useMemo, useRef } from 'react';
import { useColorScheme } from '@mui/material/styles';
import { CsvExportModule } from '@ag-grid-community/csv-export';
import TablePagination from '@mui/material/TablePagination';
import { Button } from '@mui/material';
import FileDownloadOutlinedIcon from '@mui/icons-material/FileDownloadOutlined';

ModuleRegistry.registerModules([ClientSideRowModelModule, CsvExportModule]);

const AgGrid = ({ pagination, totalCount = 0, ...props }) => {
  const { AgGridStyles, PaginationStyles, darkTheme } = getAgGridStyles();

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

  // set the page number
  const [page, setPage] = React.useState(0);
  // sets 10 rows per page (default is 100)
  const [paginationPageSize, setPaginationPageSize] = React.useState(10);
  // allows the user to select the page size from a predefined list of page sizes
  const paginationPageSizeSelector = [1, 10, 20, 50];

  const onBtnExport = useCallback(() => {
    gridRef.current.api.exportDataAsCsv();
  }, []);

  return (
    <>
      <div className={'grid ' + darkTheme} style={AgGridStyles}>
        <AgGridReact
          ref={gridRef}
          defaultColDef={defaultColDef}
          pagination={!pagination}
          paginationPageSize={paginationPageSize}
          paginationPageSizeSelector={paginationPageSizeSelector}
          // Pass Modules to this individual grid
          modules={[ClientSideRowModelModule, CsvExportModule]}
          rowModelType={'clientSide'}
          {...props}
        />
        {pagination && (
          <div style={PaginationStyles}>
            <AgGridPagination
              rowsPerPageOptions={paginationPageSizeSelector}
              rowsPerPage={paginationPageSize}
              setRowsPerPage={setPaginationPageSize}
              page={page}
              setPage={setPage}
              totalCount={totalCount}
            />
            <ActionComponents onBtnExport={onBtnExport} />
          </div>
        )}
      </div>
    </>
  );
};

const AgGridPagination = ({
  rowsPerPage,
  setRowsPerPage,
  page,
  setPage,
  totalCount,
  ...props
}) => {
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };
  return (
    <>
      <TablePagination
        {...props}
        component="div"
        count={totalCount}
        onPageChange={handleChangePage}
        page={page}
        rowsPerPage={rowsPerPage}
        onRowsPerPageChange={handleChangeRowsPerPage}
        showFirstButton={true}
        showLastButton={true}
      />
    </>
  );
};

const ActionComponents = ({ onBtnExport }) => {
  return (
    <>
      <Button
        color="inherit"
        size="small"
        variant="outlined"
        onClick={onBtnExport}
        sx={{
          minWidth: '0',
          width: '36px',
          height: '36px',
          marginRight: '10px',
        }}
      >
        <FileDownloadOutlinedIcon />
      </Button>
    </>
  );
};

const getAgGridStyles = () => {
  // Color Scheme
  const theme = useColorScheme();
  const colorScheme = theme.colorScheme === 'dark';
  const darkTheme = colorScheme ? 'ag-theme-quartz-dark' : 'ag-theme-quartz';

  // AgGrid Styles
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

  // Pagination Styles
  const PaginationStyles = {
    display: 'flex',
    justifyContent: 'end',
    alignItems: 'center',
    border:
      darkTheme === 'ag-theme-quartz-dark'
        ? '1px solid var(--template-palette-TableCell-border)'
        : 'var(--ag-borders) var(--ag-border-color)',
  };

  return { AgGridStyles, PaginationStyles, darkTheme };
};

export default AgGrid;
