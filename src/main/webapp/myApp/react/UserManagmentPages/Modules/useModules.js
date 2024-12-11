import { toast } from 'react-toastify';
import useGetAPIs from '../../components/GetApisService/GetAPIs';
import * as React from 'react';

const useModules = () => {
  const [modules, setModules] = React.useState([]);
  const { getModules } = useGetAPIs();

  const getModulesData = async (params) => {
    getModules().then((data) => {
      setModules(data.data);
    });
  };

  const createModules = async (data) => {
    try {
      const response = await fetch('api/modules/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      const result = await response.json();
      if (response.ok) {
        toast.success('Resource created successfully');
        return result;
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const updateModules = async (data) => {
    try {
      const response = await fetch('api/modules/update', {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      const result = await response.json();
      if (response.ok) {
        toast.success('Resource updated successfully');
        return result;
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const deleteModules = async (data) => {
    try {
      const response = await fetch('api/modules/delete', {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      const result = await response.json();
      if (response.ok) {
        toast.success(result);
        return result;
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  return {
    modules,
    setModules,
    getModulesData,
    createModules,
    updateModules,
    deleteModules,
  };
};

export default useModules;
