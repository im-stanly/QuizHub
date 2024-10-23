import { Api } from "@/api/calls"
import { redirect } from "next/navigation";
import Profile from "./client";

export default async function Prof(){
    const user = await Api.Auth.getSignedInUser()

    if(!user){
        redirect("/login")
        return null
    }

    const quizzes = await Api.Quiz.getQuizzesByCreator(user.id)

    return <Profile username={user.username} myQuizzes={quizzes} myStats={[]}/>
}