import * as React from 'react';
import Stack from '@mui/material/Stack';
import ColorModeIconDropdown from './sharedTheme/ColorModeIconDropdown';
import Search from './Search';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import OptionsMenu from './OptionsMenu';
import { getUserDetails } from './LocalStorageData/localStorageDataUtils';
import { styled } from '@mui/material/styles';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';

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

export default function Header({ showSearch }) {
  const data = getUserDetails();
  const userName = data?.user_details?.user_name;
  const userEmail = data?.user_details?.email;
  return (
    <>
      <StyledAppBar
        sx={{
          display: { xs: 'none', md: 'flex' },
        }}
      >
        <Toolbar
          variant="dense"
          direction="row"
          sx={{
            display: { xs: 'none', md: 'flex' },
            width: '100%',
            alignItems: { xs: 'flex-start', md: 'center' },
            justifyContent: 'space-between',
            maxWidth: { sm: '100%', md: '1700px' },
            p: '8px 12px',
            padding: 2,
          }}
          spacing={2}
        >
          <Stack direction="row" sx={{ flexGrow: 1, justifyContent: 'center' }}>
            {showSearch && <Search />}
          </Stack>
          <Stack
            direction="row"
            sx={{
              gap: 1,
              alignItems: 'center',
            }}
          >
            <Avatar
              sizes="small"
              alt="Riley Carter"
              // src="/public/images/avatar/7.jpg"
              sx={{ width: 36, height: 36 }}
            />
            <Box sx={{ mr: 'auto' }}>
              <Typography
                variant="body2"
                sx={{
                  fontWeight: 500,
                  lineHeight: '16px',
                  color: 'text.secondary',
                }}
              >
                {userName}
              </Typography>
              <Typography variant="caption" sx={{ color: 'text.secondary' }}>
                {userEmail}
              </Typography>
            </Box>
            <OptionsMenu />
            <ColorModeIconDropdown />
          </Stack>
        </Toolbar>
      </StyledAppBar>
    </>
  );
}
