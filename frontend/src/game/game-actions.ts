"use server"
import { Api } from "@/api/calls"
import { redirect } from "next/navigation"
export async function joinGame(data: FormData){ 
    const code = data.get("game-code") as string
    const signedUser = await Api.Auth.getSignedInUser()
    if(code == undefined || signedUser == undefined){
        return { message: "Very wrong" }
    }
    
    const result = await Api.Game.addUserToGame(code, signedUser.username)
    if(result){
        return redirect("/game/"+code)
    }
}

export async function createGame(data: FormData){
    const quizId = parseInt(data.get("quiz-id") as string) 
    const signedUser = await Api.Auth.getSignedInUser()
    const userId = signedUser?.id

    if(quizId == undefined){
        return { message: "Quiz id is undefined" }
    }

    if(userId == undefined){
        return { message: "You are not logged in" }
    }


    const result = await Api.Game.newGame(quizId, userId)
    if(result == undefined){
        return { message: "Something went wrong" }
    }
    return redirect("/game/"+result.replace(" ",""))
}