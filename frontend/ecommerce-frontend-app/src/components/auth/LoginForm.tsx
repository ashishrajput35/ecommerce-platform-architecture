import { useForm } from "react-hook-form";
import { useEffect, useState } from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate } from "react-router-dom";
import * as z from "zod";
import { loginUser } from "@/services/authservices/authService";
import {Input}  from "@/components/ui/Input";
import { useAuth } from "@/context/AuthContext";
import { Button } from "../ui/Button";
import { toast } from "react-hot-toast";

// Zod schema
const loginSchema = z.object({
  email: z.string().email("Invalid email"),
  password: z.string().min(6, "Password must be at least 6 characters"),
});

type LoginFormData = z.infer<typeof loginSchema>;

type Props = {
  prefillEmail?: string;
};

export default function LoginForm({ prefillEmail = "" }: Props) {
  const navigate = useNavigate();
  const { setUser } = useAuth();
  const [serverError, setServerError] = useState<string | null>(null);
  const [showPassword, setShowPassword] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
    setValue,
  } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: prefillEmail || "",
    },
  });

  useEffect(() => {
    if (prefillEmail) {
      setValue("email", prefillEmail);
    }
  }, [prefillEmail, setValue]);

  const onSubmit = async (data: LoginFormData) => {
    try {
      const response = await loginUser(data);
      const { token, username, role, expiresIn } = response.data.data;

      if (token) {
        setUser({ token, username, role, expiresIn });
        toast.success("Logged in successfully!");
        navigate("/dashboard");
      } else {
        setServerError("Login failed. No token received.");
      }
    } catch (err: any) {
      console.error("Login Error:", err);
      setServerError(err.response?.data?.message || "Login failed. Try again.");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="space-y-6 bg-white shadow-2xl p-8 rounded-xl border border-gray-200 max-w-sm w-full"
      >
        <h2 className="text-3xl font-bold text-center text-gray-800 mb-4">Welcome Back</h2>

        <div className="space-y-4">
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
              Email
            </label>
            <Input
              id="email"
              type="email"
              placeholder="you@example.com"
              autoFocus
              {...register("email")}
            />
            {errors.email && (
              <p className="text-red-500 text-sm mt-1">{errors.email.message}</p>
            )}
          </div>

          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
              Password
            </label>
            <div className="relative">
              <Input
                id="password"
                type={showPassword ? "text" : "password"}
                placeholder="••••••••"
                {...register("password")}
              />
              <button
                type="button"
                className="absolute right-3 top-1/2 transform -translate-y-1/2 text-sm text-gray-500"
                onClick={() => setShowPassword((prev) => !prev)}
              >
                {showPassword ? "Hide" : "Show"}
              </button>
            </div>
            {errors.password && (
              <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>
            )}
            <div className="text-right text-sm mt-1">
              <a href="/forgot-password" className="text-blue-600 hover:underline">
                Forgot password?
              </a>
            </div>
          </div>
        </div>

        {serverError && (
          <p className="text-red-600 text-sm text-center">{serverError}</p>
        )}

        <Button
          type="submit"
          disabled={isSubmitting}
          className="w-full"
        >
          {isSubmitting ? "Logging in..." : "Login"}
        </Button>
      </form>
    </div>
  );
}
