import { toast } from 'react-toastify';
import {useEffect, useState} from 'react';

// general function to get data from the API
const useFetchAPI = async (url, filter, options) => {
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            const querystring = new URLSearchParams({
                ...filter,
            }).toString();
            try {
                const response = await fetch(url + '?' + querystring, options);
                const result = await response.json();
                if (response.ok) {
                    setData(result);
                } else {
                    setError(result.message);
                }
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [url, filter, options]);
    return {data, error, loading};
};

const useGetAPIs = () => {
  // This function is used to get user group data
  const getUserGroup = async (data) => {
    const querystring = new URLSearchParams({
      ...data,
    }).toString();
    try {
      const response = await fetch('api/userGroup/get?' + querystring);
      const result = await response.json();
      if (response.ok) {
          return result
              ? result.map((item) => ({
                  id: item.id,
                  name: item.group_name,
                  data: item,
              }))
              : [];
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
      if (response.ok) {
        return result.map((item) => ({
          id: item.id,
          name: item.resource_name,
          data: item,
        }));
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
      if (response.ok) {
        return result.map((item) => ({
          id: item.id,
          name: item.resource_name,
          data: item,
        }));
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
      if (response.ok) {
        return result.map((item) => ({
          id: item.id,
          name: item.role_name,
          data: item,
        }));
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
      if (response.ok) {
        return result.map((item) => ({
          id: item.id,
          name: item.roles.role_name,
          data: item,
        }));
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
