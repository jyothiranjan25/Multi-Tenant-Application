import * as React from 'react';
import { useEffect, useState } from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Stack from '@mui/material/Stack';
import DashboardIcon from '@mui/icons-material/Dashboard';
import DisabledByDefaultIcon from '@mui/icons-material/DisabledByDefault';
import GetAPIs from './GetApisService/GetAPIs';

import {
  getAppUserRole,
  setAppUserResources,
} from './LocalStorageData/localStorageDataUtils';

export default function MenuContent() {
  const appUserRoleData = getAppUserRole();
  const { getRoles } = GetAPIs();
  const [mainListItems, setMainListItems] = useState([]);
  const [roleModulesData, setRoleModulesData] = useState({});

  useEffect(() => {
    getRoles({ id: appUserRoleData?.roles?.id }).then((data) => {
      setRoleModulesData(data.data[0]);
    });
  }, []);

  useEffect(() => {
    const dashboardItem = {
      id: 0,
      text: 'Dashboard',
      icon: <DashboardIcon />,
      props: { module_url: '/dashboard' },
    };
    if (roleModulesData && roleModulesData.modules_resources) {
      const roleModules = roleModulesData.modules_resources
        .sort((a, b) => a.id - b.id) // Sort by order property
        .map((module) => ({
          id: module?.model_order,
          text: module.module_name,
          icon: <DisabledByDefaultIcon />,
          props: module,
        }));
      setMainListItems([dashboardItem, ...roleModules]);
    }
  }, [roleModulesData]);

  const handleItemClick = (props) => {
    setAppUserResources(props);
    if (props?.module_url) {
      window.location.href = props?.module_url;
    } else {
      window.location.href = 'selectResources';
    }
  };
  return (
    <Stack sx={{ flexGrow: 1, p: 1, justifyContent: 'space-between' }}>
      <List dense>
        {mainListItems && mainListItems.length > 0
          ? mainListItems
              .sort((a, b) => a.id - b.id)
              .map((item, index) => (
                <ListItem key={index} disablePadding sx={{ display: 'block' }}>
                  {/*<ListItemButton selected={index === 0}>*/}
                  <ListItemButton onClick={() => handleItemClick(item.props)}>
                    <ListItemIcon>{item.icon}</ListItemIcon>
                    <ListItemText primary={item.text} />
                  </ListItemButton>
                </ListItem>
              ))
          : null}
      </List>
    </Stack>
  );
}

const icon = () => {
  return <HomeRoundedIcon />;
};
