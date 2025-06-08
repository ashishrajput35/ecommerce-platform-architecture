// src/routes/ProtectedRoute.tsx
import { Navigate } from "react-router-dom";
import { useAuth } from "@/context/AuthContext";

// Define the ProtectedRoute component
const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { user } = useAuth(); // Access the user from context

  // If there is no user (i.e., not authenticated), redirect to login
  if (!user) {
    return <Navigate to="/" />;
  }

  // If the user exists (authenticated), render the children
  return <>{children}</>;
};

export default ProtectedRoute;
