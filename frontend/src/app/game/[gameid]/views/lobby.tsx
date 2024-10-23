'use client'
import { GameLobby } from '@/socket/gameSocket';
import { Box, Button, Typography } from '@mui/material'
import React, { useEffect } from 'react';


export default function Lobby(props: {room: string, state: GameLobby, startGame: ()=>void, isMaster: boolean}){
    // const [gameCode, setGameCode] = React.useState<string>("?gameCode?");
        
    return(
        <Box component="main" display="flex" flexDirection="column" alignItems="center" maxWidth="md" sx={{height: "50vh", margin: "auto", padding: "48px", gap: "10px"}}>
            <Box display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", margin: "auto"}}>
                <Typography variant="h1" fontWeight="bold">Lobby</Typography>
                <Typography variant="h5" fontWeight="bold" sx={{color: "orange"}}>{props.state.quizName}</Typography>
            </Box>

            <Box display="flex" flexDirection="row" alignItems="center"
                sx={{
                    width:"100%", 
                    padding: 3,
                    border: "4px solid",
                    borderColor: "linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)",
                    borderRadius: "16px",
                    boxShadow: "0px 4px 20px rgba(0, 0, 0, 0.1)",
                    }}>
                <Box display="flex" flexDirection="column" alignItems="left" sx={{width:"100%", gap: "10px"}}>
                    <Typography variant="h4" fontWeight="bold">Players:</Typography>
                    {props.state.guys.map((player) => <Typography variant="h5">{player}</Typography>)}
                </Box>
                <Box display="flex" flexDirection="column" justifyContent="center" alignItems="center" 
                    sx={{
                        width:"100%", 
                        padding: 3,
                        border: "4px solid",
                        borderColor: "linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)",
                        borderRadius: "16px",
                        boxShadow: "0px 4px 20px rgba(0, 0, 0, 0.1)",
                        }}>
                    <Typography variant="h5">Game code:</Typography>
                    <Typography variant="h3" fontWeight="bold" style={{ color: "orange" }}>{props.room}</Typography>
                </Box>
            </Box>
            {props.isMaster && <Button sx={{width: "50%", marginTop: "25px"}} variant="contained" onClick={() => props.startGame()}>Play</Button>}
        </Box>
    )
}