import { Gaegu } from "next/font/google";
import { useCallback, useEffect, useRef, useState } from "react";
import { io } from "socket.io-client";

export enum GameState {
  NONE ="",
  LOBBY ="LOBBY",
  PLAYING="PLAYING",
  SUMMARY="SUMMARY"
}

export interface GameLobby {
  gameMaster: string,
  quizName: string
  guys: string[],
}

export interface GameData {
  questionNumber: number,
  question: any,
  answers: any[],
  correctAnswerId?: number,
}

export interface GameResults {
  guys: any 
}

export const gameSocket = (room: string, username: string) => {
  const socket = useRef(undefined) as any;
  
  const [gameState, setGameState] = useState({
    state: GameState.NONE,
    lobby: undefined as GameLobby | undefined,
    game: undefined as GameData | undefined,
    summary: undefined as GameResults | undefined
  })
  
  const startGame = useCallback(() => {
    socket.current.emit("start_game", {
      room: room,
      username: username
    })
  }, [socket.current, room])

  const sendAnswer = useCallback((answer: number) => {
    console.log("sending answer", answer)
    socket.current.emit("check_answer", answer)
  }, [socket.current, room])

  useEffect(() => {
    console.log("connecting to socket");
    const s = io(process.env.NEXT_PUBLIC_SOCKET_HOST!, {
      reconnection: true,
      path:"",
      query: {
        username: username,
        room: room
      },
      transports: ["websocket"]
    });
    socket.current = s;
    s.on("connect", () => {console.log(s.connected)});
    s.on("disconnect", () => {console.log(s.connected)});
    s.on("room_members_update", (res: any[]) => {
      console.log("room_members_update", res)
      setGameState(obj=> {
        return {
          ...obj,
          state: res[0],
          lobby: {
            gameMaster: res[2][0],
            quizName: res[1],
            guys: res[2]
          }
        }
      })
    })

    s.on("read_question", (res: any[]) => {
      console.log("read_question", res)

      setGameState(obj => {return {...obj, game: {...res[1], correctAnswerId: undefined, questionNumber: res[0]}}})
    })
    s.on("read_answer", (res: any) => {
      console.log("read_answer", res)

      setGameState(obj => {
        return {
          ...obj,
          game: {
            ...obj.game,
            correctAnswerId: res as number
          }
        } as any
      })
      
    })
    s.on("final_results", (res: any) => {
      setGameState(obj => {
        return {
          ...obj,
          state: GameState.SUMMARY,
          summary: {
            guys: res
          }
        }
      })

      console.log(res)
    })
  

    return () => {
      s.disconnect();
    };
  }, []);

  return { gameState, startGame, sendAnswer };
};

