import * as React from 'react';
import Header from './Header';
import Layout from './Layout';
import { Box, Paper, Typography, Button } from '@mui/material';

const AppLayout = ({
  props,
  children,
  showSideMenu = true,
  showSearch = true,
  headerTitle,
  Button,
}) => {
  return (
    <Layout {...props} showSideMenu={showSideMenu}>
      <Header showSearch={showSearch} />
      <Box
        sx={{
          width: '100%',
          border: '1px solid var(--template-palette-divider)',
          borderRadius: 'var(--template-shape-borderRadius)',
        }}
      >
        {headerTitle || Button ? (
          <Box
            sx={{
              width: '100%',
              borderBottom: '1px solid var(--template-palette-divider)',
              display: 'flex',
              justifyContent: 'space-between',
              padding: '10px',
            }}
          >
            <Typography variant="h4">{headerTitle}</Typography>
            {Button}
          </Box>
        ) : null}
        <Box
          sx={{
            width: '100%',
            padding: '16px',
          }}
        >
          <Paper
            sx={{
              background: 'none',
              minHeight:
                headerTitle || Button
                  ? 'calc(100vh - 197px)' // Header present
                  : 'calc(100vh - 140px)', // No header
            }}
          >
            {children}
          </Paper>
        </Box>
      </Box>
    </Layout>
  );
};

export default AppLayout;
