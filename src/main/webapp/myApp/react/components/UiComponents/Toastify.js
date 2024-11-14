import * as React from 'react';
import { ToastContainer } from 'react-toastify';
import { useColorScheme } from '@mui/material/styles';
import 'react-toastify/dist/ReactToastify.css';
const Toastify = () => {
  const theme = useColorScheme();
  const colorScheme = theme.colorScheme;
  return (
    <>
      <ToastContainer
        position="bottom-right"
        autoClose={2000}
        hideProgressBar
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme={colorScheme}
      />
    </>
  );
};

export default Toastify;
