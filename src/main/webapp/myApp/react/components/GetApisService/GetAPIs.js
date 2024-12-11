import { toast } from 'react-toastify';

const useGetAPIs = () => {
  // This function is used to get user group data
  const getUserGroup = async (data) => {
    const querystring = new URLSearchParams({
      ...data,
    }).toString();
    try {
      const response = await fetch('api/userGroup/get?' + querystring);
      const result = await response.json();
      const data = result.data;
      if (response.ok) {
        return result;
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  // This function is used to get resources data
  const getResources = async (data) => {
    const querystring = new URLSearchParams({
      ...data,
    }).toString();
    try {
      const response = await fetch('api/resources/get?' + querystring);
      const result = await response.json();
      const data = result.data;
      if (response.ok) {
        return result;
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  // This function is used to get modules data
  const getModules = async (data) => {
    const querystring = new URLSearchParams({
      ...data,
    }).toString();
    try {
      const response = await fetch('api/modules/get?' + querystring);
      const result = await response.json();
      const data = result.data;
      if (response.ok) {
        return result;
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const getRoles = async (data) => {
    const querystring = new URLSearchParams({
      ...data,
    }).toString();
    try {
      const response = await fetch('api/roles/get?' + querystring);
      const result = await response.json();
      const data = result.data;
      if (response.ok) {
        return result;
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const getAppUserRoles = async (data) => {
    const querystring = new URLSearchParams({
      ...data,
    }).toString();
    try {
      const response = await fetch('api/appUserRoles/get?' + querystring);
      const result = await response.json();
      const data = result.data;
      if (response.ok) {
        return result;
      }
    } catch (error) {
      console.log(error);
    }
  };

  return {
    getUserGroup,
    getResources,
    getModules,
    getRoles,
    getAppUserRoles,
  };
};
export default useGetAPIs;
