import React from "react";
import { Outlet } from "react-router-dom";
import ThemeToggle  from "@/components/ui/ThemeToggle";

const RootLayout: React.FC = () => {
  return (
    <div className="relative min-h-screen bg-white dark:bg-gray-900 text-black dark:text-white">
      {/* Top-right Theme Toggle */}
      <div className="absolute top-4 right-4 z-50">
        <ThemeToggle />
      </div>

      {/* Page Content */}
      <main className="pt-16">
        <Outlet />
      </main>
    </div>
  );
};

export default RootLayout;
