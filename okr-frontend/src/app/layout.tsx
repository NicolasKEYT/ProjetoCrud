// src/app/layout.tsx
import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import Navbar from "@/components/Navbar";   // <-- importe a Navbar
import "./globals.css";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "OKR Dashboard",
  description: "Sistema simplificado de gestão de OKRs",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="pt-BR">
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased flex flex-col min-h-screen`}
      >
        {/* Navbar fica fixa no topo */}
        <Navbar />

        {/* Conteúdo principal */}
        <main className="flex-1 p-8">
          {children}
        </main>

        {/* Opcional: footer global */}
        <footer className="text-center py-4 border-t">
          © {new Date().getFullYear()} Nicolas Gonçalves
        </footer>
      </body>
    </html>
  );
}
