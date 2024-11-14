import * as React from 'react';
import PropTypes from 'prop-types';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import Drawer, { drawerClasses } from '@mui/material/Drawer';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';
import LogoutRoundedIcon from '@mui/icons-material/LogoutRounded';
import AltRouteIcon from '@mui/icons-material/AltRoute';
import {
  getUserDetails,
  clearAllStorage,
} from './LocalStorageData/localStorageDataUtils';

import MenuContent from './MenuContent';

function SideMenuMobile({ open, toggleDrawer, showMenuContent }) {
  const data = getUserDetails();
  const user = data?.user_details?.user_name;
  return (
    <Drawer
      anchor="right"
      open={open}
      onClose={toggleDrawer(false)}
      sx={{
        [`& .${drawerClasses.paper}`]: {
          backgroundImage: 'none',
          backgroundColor: 'background.paper',
        },
      }}
    >
      <Stack
        sx={{
          maxWidth: '70dvw',
          height: '100%',
        }}
      >
        <Stack direction="row" sx={{ p: 2, pb: 0, gap: 1 }}>
          <Stack
            direction="row"
            sx={{ gap: 1, alignItems: 'center', flexGrow: 1, p: 1 }}
          >
            <Avatar
              sizes="small"
              alt={user}
              // src="/static/images/avatar/7.jpg"
              sx={{ width: 24, height: 24 }}
            />
            <Typography component="p" sx={{ fontWeight: 500 }}>
              {user}
            </Typography>
          </Stack>
        </Stack>
        <Divider />
        <Stack sx={{ flexGrow: 1 }}>{showMenuContent && <MenuContent />}</Stack>
        <Divider />
        <Stack sx={{ p: 2, pb: 1, gap: 1 }}>
          <Button
            variant="outlined"
            href={'switchRoles'}
            fullWidth
            startIcon={<AltRouteIcon />}
          >
            Switch Role
          </Button>
          <Button
            variant="outlined"
            href={'logout'}
            onClick={() => {
              clearAllStorage();
            }}
            fullWidth
            startIcon={<LogoutRoundedIcon />}
          >
            Logout
          </Button>
        </Stack>
      </Stack>
    </Drawer>
  );
}

SideMenuMobile.propTypes = {
  open: PropTypes.bool,
  toggleDrawer: PropTypes.func.isRequired,
};

export default SideMenuMobile;
