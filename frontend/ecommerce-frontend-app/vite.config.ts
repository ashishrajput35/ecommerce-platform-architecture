import path from "path"
import { defineConfig } from 'vite'
import tailwindcss from '@tailwindcss/vite'
import react from '@vitejs/plugin-react-swc'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss()
  ],
  css: {
    postcss: './postcss.config.js', // Ensure the PostCSS config path is correct
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'), // Resolve `@` to `src` directory
    },
    
  }
})
