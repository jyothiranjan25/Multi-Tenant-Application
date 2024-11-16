import React from 'react';
import { ConfigProvider, theme } from 'antd';
import { useColorScheme } from '@mui/material/styles';

const AntDesign = ({ children }) => {
  const themeScheme = useColorScheme();
  const colorScheme = themeScheme.colorScheme === 'dark';
  const darkTheme = colorScheme ? theme.darkAlgorithm : theme.compactAlgorithm;

  return (
    <ConfigProvider
      theme={{
        algorithm: darkTheme,
        components: {
          Tree: {
            style: {
              width: '100%',
            },
          },
          fontSize: '1rem',
          fontWeight: '400',
        },
      }}
    >
      {children}
    </ConfigProvider>
  );
};
export default AntDesign;
