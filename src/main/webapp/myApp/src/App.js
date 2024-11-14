import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import SignInSide from './sign-in-side/SignInSide';
import SignUp from './sign-up/SignUp';
import Dashboard from './dashboard/Dashboard';
import Blog from './blog/Blog';
import Checkout from './checkout/Checkout';
import MarketingPage from './marketing-page/MarketingPage';
import SignIn from './sign-in/SignIn';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const NotFound = () => {
  const navigate = useNavigate();

  useEffect(() => {
    // Redirect to the previous page after 2 seconds
    const timer = setTimeout(() => {
      navigate(-1); // Goes back to the previous page
    }, 2000);

    return () => clearTimeout(timer); // Cleanup the timer on component unmount
  }, [navigate]);
  return (
    <div>
      <h1>404 - Page Not Found</h1>
      <p>Redirecting to the previous page...</p>
    </div>
  );
};

function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/signin" replace />} />
      <Route path="/signin" element={<SignIn />} />
      <Route path="/signup" element={<SignUp />} />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/blog" element={<Blog />} />
      <Route path="/checkout" element={<Checkout />} />
      <Route path="/marketing" element={<MarketingPage />} />
      <Route path="/login" element={<SignInSide />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}

export default App;
