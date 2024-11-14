import * as React from 'react';
import PropTypes from 'prop-types';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';

function ForgotPassword({ open, handleClose }) {
  const [emailError, setEmailError] = React.useState(false);
  const [emailErrorMessage, setEmailErrorMessage] = React.useState('');
  const emailRef = React.useRef(null);

  const validateInputs = () => {
    const email = emailRef.current.value;
    let isValid = true;

    if (!email || !/\S+@\S+\.\S+/.test(email)) {
      setEmailError(true);
      setEmailErrorMessage('Please enter a valid email address.');
      isValid = false;
    } else {
      setEmailError(false);
      setEmailErrorMessage('');
    }
    return isValid;
  };

  const handleSubmit = (event) => {
    if (!validateInputs()) {
      event.preventDefault();
      event.stopPropagation();
      return; // Stop execution if validation fails
    }
    event.preventDefault();
    event.stopPropagation(); // Stop the event from propagating to parent forms
    const email = emailRef.current.value;

    console.log('Form submitted successfully');
    console.log('Email:', email);
  };

  return (
    <Dialog
      open={open}
      onClose={handleClose}
      PaperProps={{
        component: 'form',
        onSubmit: handleSubmit,
      }}
    >
      <DialogTitle>Reset password</DialogTitle>
      <DialogContent
        sx={{ display: 'flex', flexDirection: 'column', gap: 2, width: '100%' }}
      >
        <DialogContentText>
          Enter your account&apos;s email address, and we&apos;ll send you a
          link to reset your password.
        </DialogContentText>
        <TextField
          error={emailError}
          helperText={emailErrorMessage}
          margin="dense"
          id="email"
          type="email"
          name="email"
          placeholder="Email address"
          autoComplete="off"
          fullWidth
          variant="outlined"
          color={emailError ? 'error' : 'primary'}
          inputRef={emailRef}
        />
      </DialogContent>
      <DialogActions sx={{ pb: 3, px: 3 }}>
        <Button onClick={handleClose}>Close</Button>
        <Button variant="contained" type="submit">
          Continue
        </Button>
      </DialogActions>
    </Dialog>
  );
}

ForgotPassword.propTypes = {
  handleClose: PropTypes.func.isRequired,
  open: PropTypes.bool.isRequired,
};

export default ForgotPassword;
