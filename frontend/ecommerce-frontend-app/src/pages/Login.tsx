import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import * as z from "zod";
import { loginUser } from "@/services/authservices/authService"; // Assume this exists
import {Input} from "@/components/ui/Input";
import { Button } from "@/components/ui/Button";
import { useAuth } from '@/context/AuthContext';

// Schema validation
const loginSchema = z.object({
  email: z.string().email("Invalid email"),
  password: z.string().min(6, "Password must be at least 6 characters"),
});

type LoginFormData = z.infer<typeof loginSchema>;

export default function Login() {
  const location = useLocation();
  const navigate = useNavigate();
  const { setUser } = useAuth();
  const prefilledEmail = location.state?.email || "";

  const [serverError, setServerError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: prefilledEmail,
    },
  });


  const onSubmit = async (data: LoginFormData) => {
    try {
      console.log("login data -", data); // ✅ Correct way to log objects
  
      const response = await loginUser(data);

      console.log(" login response - ",response)
      
      const token = response.data.data.token;
      const username = response.data.data.username;
      const role = response.data.data.role;
      const expiresIn = response.data.data.expiresIn;
      console.log("added new log");
      console.log(" login response token - ",token);

      console.log(" login response username - ",username);

      console.log(" login response role - ",role);

    // const resData = response.data;

      if (token) {
          const user = {
          token: token,
          username: username,
          role: role,
          expiresIn: expiresIn,
        };

        setUser(user);
        navigate('/dashboard'); // Redirect to dashboard

      } else {
        setServerError("Login failed. No token received.");
      }
    } catch (err: any) {
      console.error("Login Error:", err);
      setServerError(err.response?.data?.message || "Login failed. Try again.");
    }
  };

  return (
    <div className="max-w-md mx-auto mt-20 p-6 shadow-xl rounded-xl border">
      <h2 className="text-2xl font-semibold mb-6">Login</h2>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <Input type="email" placeholder="Email" {...register("email")} />
        {errors.email && <p className="text-red-500">{errors.email.message}</p>}

        <Input type="password" placeholder="Password" {...register("password")} />
        {errors.password && <p className="text-red-500">{errors.password.message}</p>}

        {serverError && <p className="text-red-600">{serverError}</p>}

        <Button type="submit" disabled={isSubmitting} className="w-full">
          {isSubmitting ? "Logging in..." : "Login"}
        </Button>
      </form>
    </div>
  );
}
