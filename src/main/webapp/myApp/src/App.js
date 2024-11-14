import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import SignInSide from "./sign-in-side/SignInSide";
import SignUp from "./sign-up/SignUp";
import Dashboard from "./dashboard/Dashboard";
import Blog from "./blog/Blog";
import Checkout from "./checkout/Checkout";
import MarketingPage from "./marketing-page/MarketingPage";
import SignIn from "./sign-in/SignIn";

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
    </Routes>
  );
}

export default App;
