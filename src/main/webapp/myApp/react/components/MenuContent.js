import * as React from 'react';
import { useEffect, useState } from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Stack from '@mui/material/Stack';
import HomeRoundedIcon from '@mui/icons-material/HomeRounded';
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
      setRoleModulesData(data.map((item) => item.data)[0]);
    });
  }, []);

  useEffect(() => {
    if (roleModulesData && roleModulesData.module_resources) {
      const roleModules = roleModulesData.module_resources
        .sort((a, b) => a.model_order - b.model_order) // Sort by order property
        .map((module) => ({
          text: module.module_name,
          icon: <HomeRoundedIcon />,
          props: module,
        }));
      setMainListItems(roleModules);
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
        {mainListItems.map((item, index) => (
          <ListItem key={index} disablePadding sx={{ display: 'block' }}>
            {/*<ListItemButton selected={index === 0}>*/}
            <ListItemButton onClick={() => handleItemClick(item.props)}>
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Stack>
  );
}
