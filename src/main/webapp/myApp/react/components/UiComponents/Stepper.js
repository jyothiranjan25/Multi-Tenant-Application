import * as React from 'react';
import { Box, Button, Step, StepLabel, Stepper } from '@mui/material';

function stepper({ steps, isEdit, children, handleClear, handleFinish }) {
  const [activeStep, setActiveStep] = React.useState(0);

  const handleNext = () =>
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  const handleBack = () =>
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  const handleReset = () => {
    setActiveStep(0);
    handleClear();
  };
  return (
    <Box sx={{ width: '100%' }}>
      <Stepper activeStep={activeStep}>
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
      <Box sx={{ width: '100%', mt: 2, mb: 1 }}>{children[activeStep]}</Box>
      <Box sx={{ display: 'flex', flexDirection: 'row' }}>
        <Button
          color="inherit"
          disabled={activeStep === 0}
          onClick={handleBack}
          sx={{ mr: 1 }}
        >
          Back
        </Button>
        <Box sx={{ flex: '1 1 auto' }} />
        {!isEdit && <Button onClick={handleReset}>Reset</Button>}
        {activeStep === steps.length - 1 ? (
          <Button onClick={handleFinish}>Save</Button>
        ) : (
          <Button onClick={handleNext}>Next</Button>
        )}
      </Box>
    </Box>
  );
}

export default stepper;
