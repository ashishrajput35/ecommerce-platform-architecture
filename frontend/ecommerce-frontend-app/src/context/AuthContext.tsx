// src/context/AuthContext.tsx
import React, { createContext, useContext, useState, ReactNode } from "react";

import Cookies from 'js-cookie';


// Define the type for the context's user
interface User {
  token: string;
  username: string;
  role: string;
  expiresIn: number;
}

// Define the types for AuthContext
interface AuthContextType {
  user: User | null;
  setUser: (user: User | null) => void;
  login: (userData: User) => void;
  logout: () => void;
}

// Create the AuthContext with default values
const AuthContext = createContext<AuthContextType | undefined>(undefined);

// Type for the AuthProvider props
interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {

  const [user, setUserState] = useState<User | null>(() => {
    const cookieUser = Cookies.get('user');
      return cookieUser ? JSON.parse(cookieUser) : null;
  });

  const setUser = (user: User | null) => {
    setUserState(user);
    if (user) {
      Cookies.set('user', JSON.stringify(user), { expires: 1, sameSite: 'Strict', secure: window.location.protocol === 'https:' });
      Cookies.set('access_token', user.token, { expires: 1, sameSite: 'Strict', secure: window.location.protocol === 'https:' });
    } else {
      Cookies.remove('user');
      Cookies.remove('access_token');
    }
  };

  const login = (userData: User) => {
    setUser(userData); // Now declared and works
  };


  const logout = () => {
    Cookies.remove('user');
    Cookies.remove('access_token');// Remove access token
    setUserState(null);
    document.cookie = 'refresh_token=; Path=/; HttpOnly; Secure; SameSite=Strict'; // Remove refresh token
  };

  // useEffect(() => {
  //   if (user) {
  //     localStorage.setItem('user', JSON.stringify(user));
  //   }
  // }, [user]);

  return (
    <AuthContext.Provider value={{ user, setUser, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
};