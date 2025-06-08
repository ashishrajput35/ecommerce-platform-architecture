import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import * as z from "zod";
import { registerUser } from "@/services/authservices/authService";
import { Input } from "@/components/ui/Input";
import { Button } from "@/components/ui/Button";

// Schema
const registerSchema = z
  .object({
    username: z.string().min(3, "Username is required"),
    email: z.string().email("Invalid email"),
    password: z.string().min(6, "Password must be at least 6 characters"),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });

type RegisterFormData = z.infer<typeof registerSchema>;

type Props = {
    onSuccess: (email: string) => void;
  };
  

export default function RegisterForm({ onSuccess }: Props) {
  const [serverError, setServerError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<RegisterFormData>({
    resolver: zodResolver(registerSchema),
  });

  const onSubmit = async (data: RegisterFormData) => {
    try {
      const { username, email, password } = data;
      const response = await registerUser({ username, email, password });

      if (response?.status === 201) {
        const registeredEmail = response.data?.data?.useremail || email;
        onSuccess(registeredEmail); //  trigger flip + set email
      } else {
        setServerError("Unexpected registration response.");
      }
    } catch (err: any) {
      console.error("Registration Error:", err);
      setServerError(err.response?.data?.message || "Something went wrong");
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 bg-white shadow-xl p-6 rounded-xl border max-w-sm">
      <h2 className="text-2xl font-semibold text-center">Register</h2>

      <Input placeholder="Username" {...register("username")} />
      {errors.username && <p className="text-red-500 text-sm">{errors.username.message}</p>}

      <Input type="email" placeholder="Email" {...register("email")} />
      {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}

      <Input type="password" placeholder="Password" {...register("password")} />
      {errors.password && <p className="text-red-500 text-sm">{errors.password.message}</p>}

      <Input type="password" placeholder="Confirm Password" {...register("confirmPassword")} />
      {errors.confirmPassword && <p className="text-red-500 text-sm">{errors.confirmPassword.message}</p>}

      {serverError && <p className="text-red-600 text-sm">{serverError}</p>}

      <Button type="submit" disabled={isSubmitting} className="w-full">
        {isSubmitting ? "Registering..." : "Register"}
      </Button>
    </form>
  );
}
