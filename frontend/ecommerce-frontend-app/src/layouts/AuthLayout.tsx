// src/layouts/AuthLayout.tsx
import ThemeToggle from "@/components/ui/ThemeToggle";
import { ReactNode } from "react";

export default function AuthLayout({ children }: { children: ReactNode }) {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 dark:bg-gray-900 px-4">

        <ThemeToggle /> {/* Theme toggle button in top-right corner */}
      <div className="w-full max-w-md bg-white dark:bg-gray-800 shadow-2xl p-8 rounded-xl border border-gray-200 dark:border-gray-700">   
        {children}
      </div>
    </div>
  );
}
