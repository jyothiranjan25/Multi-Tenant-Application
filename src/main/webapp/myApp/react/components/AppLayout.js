import * as React from 'react';
import Header from './Header';
import Layout from './Layout';
import { Card } from '@mui/material';
import Paper from '@mui/material/Paper';

const AppLayout = ({
  props,
  children,
  showSideMenu = true,
  showSearch = true,
}) => {
  return (
    <Layout {...props} showSideMenu={showSideMenu}>
      <Header showSearch={showSearch} />
      <Card variant="outlined" sx={{ width: '100%' }}>
        <Paper
          sx={{
            minHeight: 'calc(100vh - 128px)',
            width: '100%',
            background: 'none',
          }}
        >
          {children}
        </Paper>
      </Card>
    </Layout>
  );
};

export default AppLayout;
