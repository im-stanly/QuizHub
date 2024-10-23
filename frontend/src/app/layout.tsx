import type { Metadata } from "next";
import { AppRouterCacheProvider } from '@mui/material-nextjs/v13-appRouter';
import { ThemeProvider } from '@mui/material/styles';
import theme from '../theme';
import "./globals.css";
import TopBar from "@/components/topbar";
import React from "react";
import {Api} from "@/api/calls"

export const metadata: Metadata = {
  title: "QuizHub",
  description: "project for IO",
};

export default async function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {

  const user = await Api.Auth.getSignedInUser()

  return (
    <html lang="pl">
      <body>
        <TopBar user={user}/>
          <AppRouterCacheProvider>
            <ThemeProvider theme={theme}>
            {children}
            </ThemeProvider>
          </AppRouterCacheProvider>
      </body>
    </html>
  );
}
