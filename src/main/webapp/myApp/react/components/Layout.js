import * as React from 'react';
import { alpha } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import AppNavbar from './AppNavbar';
import SideMenu from './SideMenu';
import AppTheme from './sharedTheme/AppTheme';
import Toastify from './UiComponents/Toastify';

const Layout = ({ children, showSideMenu = true, ...props }) => {
  return (
    <AppTheme {...props}>
      <CssBaseline enableColorScheme />
      <Box sx={{ display: 'flex' }}>
        {showSideMenu && <SideMenu />}
        <AppNavbar showSideMenu={showSideMenu} />
        {/* Main content */}
        <Box
          component="main"
          sx={(theme) => ({
            flexGrow: 1,
            backgroundColor: theme.vars
              ? `rgba(${theme.vars.palette.background.defaultChannel} / 1)`
              : alpha(theme.palette.background.default, 1),
            overflow: 'auto',
          })}
        >
          <Stack
            spacing={2}
            sx={{
              alignItems: 'center',
              mx: 2,
              // pb: 5,
              mt: { xs: 8, md: 0 },
            }}
          >
            {children}
          </Stack>
          <Toastify />
        </Box>
      </Box>
    </AppTheme>
  );
};

export default Layout;
