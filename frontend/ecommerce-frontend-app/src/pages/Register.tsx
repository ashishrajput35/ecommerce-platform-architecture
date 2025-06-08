import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { registerUser } from "@/services/authservices/authService";
import { useState } from "react";
import { Input } from "@/components/ui/Input";
import { Button } from "@/components/ui/Button";
import { useNavigate } from "react-router-dom";

// Step 1: Define schema using Zod
const formSchema = z
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

// Step 2: Infer form data type from Zod schema
type FormData = z.infer<typeof formSchema>;

export default function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<FormData>({
    resolver: zodResolver(formSchema),
  });

  const [serverError, setServerError] = useState<string | null>(null);
  const navigate = useNavigate();

  // Step 3: Handle form submission
  const onSubmit = async (data: FormData) => {

    try {
      const { username, email, password } = data;

      const response = await registerUser({ username, email, password });

      console.log("register response - ",response);

      if (response?.status === 201) {

        const registeredEmail = response.data?.data?.useremail || email; // Fallback if backend doesn’t send it

        console.log("register response  registerEmail- ",registeredEmail);


        navigate("/login", { state: { email: registeredEmail } });

      } else {
        setServerError("Unexpected registration response.");
      }

    } catch (err: any) {
      console.error("API Error:", err);
      setServerError(err.response?.data?.message || "Something went wrong");

    }
  };

  return (

    <div className="max-w-md mx-auto mt-20 p-6 shadow-xl rounded-xl border">
      <h2 className="text-2xl font-semibold mb-6">Register</h2>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <Input placeholder="Username" {...register("username")} />
        {errors.username && <p className="text-red-500">{errors.username.message}</p>}

        <Input type="email" placeholder="Email" {...register("email")} />
        {errors.email && <p className="text-red-500">{errors.email.message}</p>}

        <Input type="password" placeholder="Password" {...register("password")} />
        {errors.password && <p className="text-red-500">{errors.password.message}</p>}

        <Input type="password" placeholder="Confirm Password" {...register("confirmPassword")} />
        {errors.confirmPassword && <p className="text-red-500">{errors.confirmPassword.message}</p>}

        {serverError && <p className="text-red-600">{serverError}</p>}

        <Button type="submit" disabled={isSubmitting} className="w-full">
          {isSubmitting ? "Registering..." : "Register"}
        </Button>
      </form>
    </div>
  );
}
