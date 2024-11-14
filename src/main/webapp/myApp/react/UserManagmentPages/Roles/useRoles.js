import { toast } from 'react-toastify';

const useRoles = () => {
  const createModules = async (data) => {
    try {
      const response = await fetch('api/roles/create', {
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
      const response = await fetch('api/roles/update', {
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
      const response = await fetch('api/roles/delete', {
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
    createModules,
    updateModules,
    deleteModules,
  };
};

export default useRoles;
