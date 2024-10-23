'use client'
import { GameModel } from '@/api/model';
import { Api } from '@/api/calls'
import { GameResults, gameSocket, GameState } from '@/socket/gameSocket';
import { Box, Button, Typography } from '@mui/material';
import React from 'react';
import { useRouter } from 'next/navigation';

export default function Summary(props: {room: string, state: GameResults, game: GameModel}){
    const router = useRouter()

    const guysArray = Object.entries(props.state.guys)

    return(
        <Box component="main" display="flex" flexDirection="column" justifyContent="center" alignItems="center" maxWidth="md" sx={{height: "70vh", margin: "auto", padding: "48px"}}>
            <Box display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", margin: "auto", gap: "50px"}}>
                {/* score */}
                <Typography variant="h1" fontWeight="bold">Score</Typography>
                <Box display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", height: "40vh", maxWidth: "1200px", margin: "auto", gap: "50px"}}>
                    {/* socre board */}
                    {guysArray.map(([player, correct], index) => {
                        return (
                              <Box
                                display="flex"
                                flexDirection="row"
                                alignItems="center"
                                justifyContent="center"
                                sx={{ width: "100%", gap: "10px", marginBottom: "10px" }}
                              >
                                <UserScore username={player} correct={correct as number} />
                              </Box>);
                    })}
                    <Box display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", margin: "auto"}}>
                        <Button variant="contained" onClick={() => {router.push("/quiz/" + props.game.quizID)}}>
                            Play again!
                        </Button>
                    </Box>
                </Box>
            </Box>
        </Box>
    )
}

export function UserScore(props: {username: string, correct: number}){
    return (
        <Box display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", maxWidth: "1200px", margin: "auto", gap: "20px"}}>
            <Box display="flex" flexDirection="column" alignItems="center" sx={{gap: "5px"}}>
                <Typography variant="h3" fontWeight="bold">{props.username}</Typography>
            </Box>

            <Box display="flex" flexDirection="column" alignItems="center" sx={{gap: "5px"}}>
                <Box display="flex" flexDirection="row" alignItems="center" sx={{gap: "10px"}}>
                    <Typography variant="h5">correct answers:</Typography>
                    <Typography variant="h5" sx={{color: "green", fontWeight: "bold" }}>{props.correct}</Typography>
                </Box>

                <Box display="flex" flexDirection="row" alignItems="center" sx={{gap: "10px"}}>
                    <Typography variant="h5">incorrect answers:</Typography>
                    <Typography variant="h5" sx={{color: "red", fontWeight: "bold" }}>{5 - props.correct}</Typography>
                </Box>
            </Box>
        </Box>
    )
}