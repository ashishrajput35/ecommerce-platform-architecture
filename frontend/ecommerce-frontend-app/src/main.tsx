// src/index.tsx
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import App from './App';
import './index.css';
import RootLayout from './layouts/RootLayout';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

root.render(
   <React.StrictMode>
   
    <AuthProvider>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </AuthProvider>
   
   </React.StrictMode>
);
