import * as React from 'react';
import AppLayout from '../components/AppLayout';
import MainGrid from './MainGrid';
import ReactDOM from 'react-dom/client';

const Dashboard = (props) => {
  return (
    <AppLayout>
      <MainGrid />
    </AppLayout>
  );
};

const root = ReactDOM.createRoot(document.getElementById('Dashboard'));
root.render(<Dashboard />);
