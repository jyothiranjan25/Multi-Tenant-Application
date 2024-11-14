import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import useGetAPIs from '../../components/GetApisService/GetAPIs';
const useResources = () => {
  const [resources, setResources] = useState([]);
  const { getResources } = useGetAPIs();

  const get = async (data) => {
    getResources(data).then((result) => {
      setResources(result.map((item) => item.data));
    });
  };

  const createResources = async (data) => {
    try {
      const response = await fetch('api/resources/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      const result = await response.json();
      if (response.ok) {
        toast.success('Resource created successfully');
        get().then();
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const updateResources = async (data) => {
    try {
      const response = await fetch('api/resources/update', {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      const result = await response.json();
      if (response.ok) {
        toast.success('Resource updated successfully');
        get().then();
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const deleteResources = async (data) => {
    try {
      const response = await fetch('api/resources/delete', {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      const result = await response.json();
      if (response.ok) {
        toast.success(result);
        get().then();
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    get().then();
  }, []);

  return {
    resources,
    setResources,
    get,
    createResources,
    updateResources,
    deleteResources,
  };
};

export default useResources;
