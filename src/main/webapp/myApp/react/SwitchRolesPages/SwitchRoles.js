import * as React from 'react';
import AppLayout from '../components/AppLayout';
import Grid from '@mui/material/Grid2';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import CardActionArea from '@mui/material/CardActionArea';
import Avatar from '@mui/material/Avatar';
import ReactDOM from 'react-dom/client';
import useSwitchRoles from './useSwitchRoles';

const SwitchRoles = (props) => {
  const { appUserRoles, setAppUserRoles, saveAppUserRole } = useSwitchRoles();

  const handleCardClick = async (role) => {
    await saveAppUserRole(role);
  };

  return (
    <AppLayout showSearch={false} showSideMenu={false}>
      <CardHeader
        title="Switch Roles"
        titleTypographyProps={{ variant: 'h2' }}
        sx={{ padding: '10px' }}
      />
      <CardContent>
        <Box sx={{ width: '100%', maxWidth: { sm: '100%', md: '1700px' } }}>
          <Grid
            container
            spacing={2}
            columns={12}
            sx={{ mb: (theme) => theme.spacing(2) }}
          >
            {appUserRoles.map((data) => (
              <Grid key={data.id} size={{ xs: 12, sm: 6, lg: 3 }}>
                <Card sx={{ maxWidth: 345 }}>
                  <CardActionArea
                    sx={{ textAlign: 'center' }}
                    onClick={() => handleCardClick(data)}
                  >
                    <Box
                      sx={{
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        mt: 2,
                      }}
                    >
                      <Avatar
                        alt={data?.roles?.role_name}
                        src={
                          data?.roles?.role_icon
                            ? 'data:image/svg+xml;base64,' +
                              data?.roles?.role_icon
                            : 'public/images/user.png'
                        }
                        sx={{ width: 100, height: 100 }}
                      />
                    </Box>{' '}
                    <CardContent>
                      <Typography gutterBottom variant="h5" component="div">
                        {data?.roles?.role_name}
                      </Typography>
                      <Typography gutterBottom variant="h6" component="div">
                        {data?.user_group?.group_name}
                      </Typography>
                      {/*<Typography*/}
                      {/*  variant="body2"*/}
                      {/*  sx={{ color: 'text.secondary' }}*/}
                      {/*>*/}
                      {/*  {data?.roles?.role_description}*/}
                      {/*</Typography>*/}
                    </CardContent>
                  </CardActionArea>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Box>
      </CardContent>
    </AppLayout>
  );
};

const root = ReactDOM.createRoot(document.getElementById('SwitchRoles'));
root.render(<SwitchRoles />);
