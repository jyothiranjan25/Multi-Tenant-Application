// App User Details
import {useState} from 'react';

const userDetailsKey = 'user_details';

export const setUserDetails = (userDetails) => {
  localStorage.setItem(userDetailsKey, JSON.stringify(userDetails));
};

export const getUserDetails = () => {
  const data = localStorage.getItem(userDetailsKey);
  return data ? JSON.parse(data) : null;
};
// End App User Details

// App User Role
const appUserRoleKey = 'app_user_role';
export const setAppUserRole = (appUserRoles) => {
  localStorage.setItem(appUserRoleKey, JSON.stringify(appUserRoles));
};

export const getAppUserRole = () => {
  const data = localStorage.getItem(appUserRoleKey);
  return data ? JSON.parse(data) : null;
};
// End App User Role

// App User Resources
const appUserResourcesKey = 'app_user_resources';
export const setAppUserResources = (appUserResources) => {
  localStorage.setItem(appUserResourcesKey, JSON.stringify(appUserResources));
};

export const getAppUserResources = () => {
  const data = localStorage.getItem(appUserResourcesKey);
  return data ? JSON.parse(data) : null;
};
// End App User Resources

// Clear All Local Storage Data
export const clearAllStorage = () => {
  // localStorage.clear();
  localStorage.removeItem(userDetailsKey);
  localStorage.removeItem(appUserRoleKey);
  localStorage.removeItem(appUserResourcesKey);
};

export const useLocalStorageData = (key, initialValues) => {
    const [storedValues, setStoredValues] = useState(() => {
        try {
            const item = window.localStorage.getItem(key);
            return item ? JSON.parse(item) : initialValues;
        } catch (error) {
            console.error(error);
            return initialValues;
        }
    });

    const setValue = (value) => {
        try {
            setStoredValues(value);
            window.localStorage.setItem(key, JSON.stringify(value));
        } catch (error) {
            console.error(error);
        }
    };
    return [storedValues, setValue];
};
