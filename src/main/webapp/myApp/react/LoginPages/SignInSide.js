import * as React from 'react';
import ReactDOM from 'react-dom/client';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import CssBaseline from '@mui/material/CssBaseline';
import Stack from '@mui/material/Stack';
import { styled } from '@mui/material/styles';
import SignInCard from './SignInCard';
import Content from './Content';
import AppTheme from '../components/sharedTheme/AppTheme';
import ColorModeIconDropdown from '../components/sharedTheme/ColorModeIconDropdown';

const SignInSide = () => {
  return (
    <>
      <AppTheme>
        <Box
          sx={{ height: '100dvh', display: 'flex', flexDirection: 'column' }}
        >
          <StyledAppBar>
            <Toolbar
              variant="dense"
              disableGutters
              sx={{
                display: 'flex',
                justifyContent: 'space-between',
                width: '100%',
                p: '8px 12px',
              }}
            >
              <Button></Button>
              <Box sx={{ display: 'flex', gap: 1 }}>
                <ColorModeIconDropdown />
              </Box>
            </Toolbar>
          </StyledAppBar>
          <Box sx={{ flex: '1 1', overflow: 'auto' }}>
            <CssBaseline enableColorScheme />
            <Stack
              direction="column"
              component="main"
              sx={[
                {
                  justifyContent: 'space-between',
                  height: { xs: 'auto', md: '100%' },
                },
                (theme) => ({
                  backgroundImage:
                    'radial-gradient(ellipse at 70% 51%, hsl(210, 100%, 97%), hsl(0, 0%, 100%))',
                  backgroundSize: 'cover',
                  ...theme.applyStyles('dark', {
                    backgroundImage:
                      'radial-gradient(at 70% 51%, hsla(210, 100%, 16%, 0.5), hsl(220, 30%, 5%))',
                  }),
                }),
              ]}
            >
              <Stack
                direction={{ xs: 'column-reverse', md: 'row' }}
                sx={{
                  justifyContent: 'center',
                  gap: { xs: 6, sm: 12 },
                  p: { xs: 2, sm: 4 },
                  m: 6,
                }}
              >
                {/*<Content />*/}
                <Box
                  component="img"
                  alt="Secure-login-amico.svg"
                  src="public/assets/Secure-login-amico.svg" // Adjusted path if needed
                  sx={{
                    width: { xs: '100%', sm: '75%', md: '50%', lg: '40%' }, // Responsive width for different breakpoints
                    maxWidth: '500px', // Sets a max limit for larger screens
                    height: 'auto', // Maintains aspect ratio
                    display: { xs: 'none', sm: 'block' }, // Hides on extra-small screens, shows on small and larger
                  }}
                />
                <SignInCard />
              </Stack>
            </Stack>
          </Box>
        </Box>
      </AppTheme>
    </>
  );
};

const StyledAppBar = styled(AppBar)(({ theme }) => ({
  position: 'relative',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-between',
  flexShrink: 0,
  borderBottom: '1px solid',
  borderColor: theme.palette.divider,
  backgroundColor: theme.palette.background.paper,
  boxShadow: 'none',
  backgroundImage: 'none',
  zIndex: theme.zIndex.drawer + 1,
  flex: '0 0 auto',
}));

const root = ReactDOM.createRoot(document.getElementById('Login'));
root.render(<SignInSide />);
