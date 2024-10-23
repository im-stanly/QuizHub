'use client';
import { Inter } from "next/font/google";
import { createTheme } from '@mui/material/styles';

const inter = Inter({ subsets: ["latin"] });

const theme = createTheme({
  typography: {
    fontFamily: inter.style.fontFamily,
    allVariants: {
      color: '#ffffff',
    },
  },
  palette: {
    mode: "dark",
    primary: {
      main: "#ffa500",
    },
    secondary: {
      main: "#000000",
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          fontWeight: "bold",
          color: "#000000",
        },
      },
    },
  },
});

export default theme;