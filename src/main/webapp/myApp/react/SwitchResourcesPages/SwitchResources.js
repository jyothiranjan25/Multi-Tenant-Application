import * as React from 'react';
import AppLayout from '../components/AppLayout';
import ReactDOM from 'react-dom/client';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid2';
import Card from '@mui/material/Card';
import CardActionArea from '@mui/material/CardActionArea';
import Box from '@mui/material/Box';
import CardContent from '@mui/material/CardContent';
import Divider from '@mui/material/Divider';
import { randomId } from '@mui/x-data-grid-generator';
import { getAppUserResources } from '../components/LocalStorageData/localStorageDataUtils';

const SwitchResources = (props) => {
  const data = getAppUserResources();
  return (
    <AppLayout>
      {data?.resources
        .sort((a, b) => a.resource_order - b.resource_order)
        .map((resource) => (
          <Box
            key={randomId()}
            sx={{ width: '100%', maxWidth: { sm: '100%', md: '1700px' } }}
          >
            <Typography component="h4" variant="h4" sx={{ mb: 2 }}>
              {resource.resource_name}
            </Typography>
            <Typography component="h6" variant="h6" sx={{ mb: 2 }}>
              {resource.resource_description}
            </Typography>
            <Grid
              key={randomId()}
              container
              spacing={2}
              columns={12}
              sx={{
                mb: (theme) => theme.spacing(2),
                display: 'flex',
                alignItems: 'stretch',
              }}
            >
              {resource?.child_resources?.length > 0 &&
                resource.child_resources
                  .sort((a, b) => a.resource_order - b.resource_order)
                  .map((child_resource) => (
                    <>
                      <Grid
                        key={randomId()}
                        size={{ xs: 12, sm: 6, lg: 4 }}
                        sx={{ display: 'flex' }}
                      >
                        <Card
                          sx={{
                            maxWidth: 345,
                            minWidth: '-webkit-fill-available',
                          }}
                        >
                          <CardActionArea
                            sx={{
                              padding: 1.5,
                              minHeight: '-webkit-fill-available',
                            }}
                            onClick={() =>
                              (window.location.href =
                                child_resource.resource_url)
                            }
                          >
                            <CardContent>
                              <Typography
                                gutterBottom
                                variant="h5"
                                component="div"
                              >
                                {child_resource.resource_name}
                              </Typography>
                              <Typography
                                variant="body2"
                                sx={{
                                  color: 'text.secondary',
                                  textAlign: 'justify',
                                }}
                              >
                                {child_resource.resource_description}
                              </Typography>
                            </CardContent>
                          </CardActionArea>
                        </Card>
                      </Grid>
                    </>
                  ))}
            </Grid>
            <Divider />
          </Box>
        ))}
    </AppLayout>
  );
};

const root = ReactDOM.createRoot(document.getElementById('SwitchResources'));
root.render(<SwitchResources />);
