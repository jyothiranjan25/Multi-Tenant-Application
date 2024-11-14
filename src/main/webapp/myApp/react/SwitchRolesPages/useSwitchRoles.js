import { useEffect, useState } from 'react';
import {
  getUserDetails,
  setAppUserRole,
} from '../components/LocalStorageData/localStorageDataUtils';
import GetAPIs from '../components/GetApisService/GetAPIs';

const useSwitchRoles = () => {
  const data = getUserDetails();
  const [appUserRoles, setAppUserRoles] = useState([]);
  const { getAppUserRoles } = GetAPIs();

  const get = async (data) => {
    getAppUserRoles(data).then((data) => {
      setAppUserRoles(data.map((item) => item.data));
    });
  };

  const saveAppUserRole = async (data) => {
    try {
      const response = await fetch('api/userLoginDetails/store', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      if (response.ok) {
        setAppUserRole(data);
        window.location.href = 'dashboard';
      } else {
        console.error('Failed to post data');
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    get({ app_user_id: data?.user_details?.id }).then();
  }, []);

  return {
    appUserRoles,
    setAppUserRoles,
    getAppUserRoles,
    saveAppUserRole,
  };
};

export default useSwitchRoles;
