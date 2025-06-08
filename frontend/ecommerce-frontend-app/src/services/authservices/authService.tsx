// src/services/authService.ts

import axios from "axios";
import Cookies from 'js-cookie';

// Axios instance
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api/v1/auth', // Replace with your API URL
  headers: {
    'Content-Type': 'application/json',
  },
});

// const API_BASE = "http://localhost:8080/api/v1/auth"; // Change if different

export interface RegisterData {
  username: string;
  email: string;
  password: string;
}

export const registerUser = async (data: RegisterData) => {

 // Print the data to the console before hitting the API
 console.log("Registering user with data:", data);

  try {

    // const response = await axios.post(`${API_BASE}/register`, data);

    const response =  await axiosInstance.post('/register', data)

    console.log("response", response);

    console.log("Registering  response with data:", response.data);

    return response.data;

  } catch (error: any) {
    // Let the error bubble up to the UI
    throw error;
  }


};

// login page

export const loginUser = async (data: { email: string; password: string }) => {

  // Print the data to the console before hitting the API
 console.log("login user with data:", data);

 try {

  //  const response = await axios.post(`${API_BASE}/login`, data);

   const response =  await axiosInstance.post('/login', data)


   console.log("response", response);

   console.log("Login  response with data:", response.data);

   return response;

 } catch (error: any) {
   // Let the error bubble up to the UI
   throw error;
 }
   
};


// Axios interceptor for adding the token to the request
axiosInstance.interceptors.request.use(
  (config) => {
    const accessToken = Cookies.get('access_token');
    if (accessToken) {
      config.headers['Authorization'] = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Axios interceptor for handling token refresh on 401 error
axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response && error.response.status === 401) {
      // Token expired, attempt to refresh the token
      try {
        const refreshToken = getCookie('refresh_token'); // Get the refresh token from HTTP-only cookie
        if (refreshToken) {
          const refreshResponse = await axiosInstance.post('/refresh-token', { refreshToken });
          const newAccessToken = refreshResponse.data.token;

          // Save the new access token
          Cookies.set('access_token', newAccessToken, { expires: 1, path: '/' });

          // Retry the failed request with the new token
          error.config.headers['Authorization'] = `Bearer ${newAccessToken}`;
          return axiosInstance(error.config);
        }
      } catch (refreshError) {
        console.error('Refresh Token Error:', refreshError);
      }
    }

    return Promise.reject(error);
  }
);

// Utility function to get refresh token from cookies
function getCookie(name: string): string | null {
  const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  return match ? match[2] : null;
}

export default axiosInstance;