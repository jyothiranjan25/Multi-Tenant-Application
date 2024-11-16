import { useEffect, useState } from 'react';

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
  return { data, error, loading };
};
export default useFetchAPI;
