import React from 'react';
import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';

export default function Copyright(props) {
  return (
    <Box
      component="footer"
      sx={{
        position: 'sticky',
        bottom: 0,
        paddingBottom: '16px',
        textAlign: 'center',
      }}
    >
      <Typography
        variant="body2"
        align="center"
        {...props}
        sx={[
          {
            color: 'text.secondary',
          },
          ...(Array.isArray(props.sx) ? props.sx : [props.sx]),
        ]}
      >
        {'Copyright © '}
        <Link color="inherit" href="https://mui.com/">
          Joshith
        </Link>{' '}
        {new Date().getFullYear()}
        {'.'}
      </Typography>
    </Box>
  );
}
