'use client'
import { Box, Typography, Button } from '@mui/material';
import { useRouter } from "next/navigation";
import SearchBar from '@/components/searchbar';
import { useState } from 'react';
import JoinGame from '@/components/game/join-game';



export default function Home() {
  const router = useRouter()

  return (
    <Box component="main" display="flex" flexDirection="column" alignItems="center" justifyContent="center" minHeight="70vh">  
      <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center" p={6} maxWidth="md" sx={{gap: "30px"}}>
        <Box display="flex" flexDirection="column" alignItems="center" justifyContent="space-between" p={6} sx={{gap: "10px"}}>
          <Typography variant="h1" component="p" fontWeight="bold"> quizHub </Typography>
          <Typography variant="h5" component="p"> destroy your friends in an <span style={{ fontWeight: "bold", color: "orange" }}>epic quiz</span> battle </Typography>
        </Box>
        <Box display="flex" flexDirection="column" alignItems="center" justifyContent="space-between" sx={{gap:"20px"}}>
          <SearchBar />
          <Button variant="contained" onClick={() => router.push("/quiz/create")} sx={{width: "100%"}}> Create Quiz </Button>
        </Box>
        <Box display="flex" flexDirection="column" alignItems="center" justifyContent="space-between" p={6} sx={{gap: "15px"}}>
          <Typography variant="body1" component="p">...or you can just join <span style={{ fontWeight: "bold", color: "orange" }}>the game</span></Typography>
          <JoinGame/>
        </Box>
      </Box>
    </Box>
  );
}
