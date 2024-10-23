"use client"
import { GameModel, User } from "@/api/model";
import { GameLobby, GameData, gameSocket, GameState }  from "@/socket/gameSocket";
import Lobby from "./views/lobby";
import Game from "./views/game";
import Summary from "./views/summary";
import React, { useEffect } from "react";
import { LinearProgress } from "@mui/material";
import { PowerInputSharp } from "@mui/icons-material";


export default function GameClient(props: {room: string, username: string, game: GameModel}){
    const {gameState, startGame, sendAnswer} = gameSocket(props.room, props.username)

    if(gameState.state == GameState.LOBBY){ 
        if(gameState.lobby == undefined) return <LinearProgress />

        return <Lobby 
            room={props.room} 
            state={gameState.lobby}
            startGame={startGame}
            isMaster={props.game.usersPermitted[0]==props.username}
            />
    }
    
    if(gameState.state == GameState.PLAYING){
        if(gameState.game == undefined) return <LinearProgress />
        return <Game 
            room={props.room} 
            state={gameState.game}
            sendAnswer={sendAnswer}
            username={props.username}
            />
    }

    if(gameState.state == GameState.SUMMARY){
        if(gameState.summary == undefined) return <LinearProgress />
        return <Summary 
                room={props.room}        
                state={gameState.summary}
                game={props.game}
                />
    }
    
    return <LinearProgress />
}

// state na temat stanu gry oraz odpowiedź.
// lamda do wysłania odpowiedzi (int)
// lamda do startowania gry kutasem