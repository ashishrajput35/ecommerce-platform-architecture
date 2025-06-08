// src/pages/Dashboard.tsx
import React from 'react';
import { useAuth } from '@/context/AuthContext';
import  Card  from '@/components/ui/Card';


const Dashboard: React.FC = () => {
  const { user, logout } = useAuth();

  return (
    <div className="p-4">
      <div style={{ padding: '2rem' }}>
      <Card title="DashBoard"  >
      <h1>Welcome to the Dashboard, {user?.username}!</h1>
      <button onClick={logout} className="bg-red-500 text-white p-2 rounded">
        Logout
      </button>
        </Card>
    </div>
     
      
    </div>
  );
}

export default Dashboard;
