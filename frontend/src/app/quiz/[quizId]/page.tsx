import { Api } from "@/api/calls"
import {QuizDetails} from "./client";
import { redirect } from "next/navigation";

export default async function Quiz({params}: {params: {quizId: number}}){
    const quiz = await Api.Quiz.get(params.quizId);
    if(quiz == undefined) redirect("/")
    const questions = await Api.Question.getQuizId(params.quizId)
    const user = await Api.User.getById(quiz.creatorId)
    const signedUser = await Api.Auth.getSignedInUser()

    return <QuizDetails quiz={quiz} questions={questions} userName={user?.username} isOwner={quiz.creatorId === signedUser?.id} />
}