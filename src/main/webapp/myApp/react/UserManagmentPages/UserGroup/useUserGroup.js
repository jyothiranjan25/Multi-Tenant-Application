import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import useGetAPIs from '../../components/GetApisService/GetAPIs';

const useUserGroup = () => {
  const [userGroup, setUserGroup] = useState([]);
  const { getUserGroup } = useGetAPIs();

  const get = async (data) => {
    getUserGroup(data).then((data) => {
      setUserGroup(data.map((item) => item.data));
    });
  };

  const createUserGroup = async (data) => {
    try {
      const response = await fetch('api/userGroup/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      const result = await response.json();
      if (response.ok) {
        get().then();
        toast.success('User Group created successfully');
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const updateUserGroup = async (data) => {
    try {
      const response = await fetch('api/userGroup/update', {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      const result = await response.json();
      if (response.ok) {
        get().then();
        toast.success('User Group updated successfully');
      } else {
        toast.error(result.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const deleteUserGroup = async (data) => {
    try {
      const response = await fetch('api/userGroup/delete', {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      const result = await response.json();
      if (response.ok) {
        get().then();
        toast.success(result);
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
    userGroup,
    setUserGroup,
    get,
    createUserGroup,
    updateUserGroup,
    deleteUserGroup,
  };
};

export default useUserGroup;
