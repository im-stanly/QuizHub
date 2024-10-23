"use server"

import { Api } from "@/api/calls"
import { redirect } from "next/navigation"
import { userAgent } from "next/server"

export type FormState = {
    message: string,
}

export async function handleQuizCreate(state: FormState, data: FormData){
    const { name, description, tags } = {
        name: data.get("name") as string,
        description: data.get("description") as string,
        tags: data.getAll("tags") as string[],
    }
    
    const user = await Api.Auth.getSignedInUser()
    if(!user){
        return { message: "Not signed in!" }
    }

    if(name.length == 0) 
        return { message: "Quiz name is empty!"}
    console.log(tags)
    // shall fetch currently signed in user but we dont have that now...

    const result = await Api.Quiz.post({
        name: name,
        description: description,
        tags: tags,
        creatorId: user.id
    })
    console.log(result)
    if(result !== undefined) 
        redirect("/quiz/"+result.id)

    return {message: "Something went wrong!"}
}

export async function handleQuizUpdate(state: FormState, data: FormData) {
    const { id, name, description, tags } = {
        id: data.get("id") as string,
        name: data.get("name") as string,
        description: data.get("description") as string,
        tags: data.getAll("tags") as string[],
    }

    if (id == undefined)
        return { message: "Something went wrong!" }

    const user = await Api.Auth.getSignedInUser()
    if(!user)
        return { message: "Not signed in!" }
    

    // if(name.length == 0) 
    //     return { message: "Quiz name is empty!"}
    // console.log(tags)

    const result = await Api.Quiz.patch({
        name: name,
        description: description,
        tags: tags,
        creatorId: user.id
        },
        parseInt(id)
    )

    if (result) 
        redirect("/quiz/" + id)


    return {message: "Something went wrong!"}
}

export async function handleQuestionCreate(state: FormState, data: FormData){
    const { quizId, id, question, answers, correctAnswerId } = {
        quizId: data.get("quizId") as string,
        id: data.get("id") as string,
        question: data.get("question") as string,
        answers: data.getAll("answers") as string[],
        correctAnswerId: data.get("correctAnswerId") as string,
    }

    const result = await Api.Question.post({
        id: parseInt(id),
        correctAnswerId: parseInt(correctAnswerId),
        question: question,
        answers: answers,
        quizId: parseInt(quizId)
    })

    if(result) 
        redirect("/quiz/" + quizId)

    return {message: "Something went wrong!"}
}

export async function handleQuestionUpdate(state: FormState, data: FormData){
    const { quizId, id, question, answers, correctAnswerId } = {
        quizId: data.get("quizId") as string,
        id: data.get("id") as string,
        question: data.get("question") as string,
        answers: data.getAll("answers") as string[],
        correctAnswerId: data.get("correctAnswerId") as string,
    }

    if (id == undefined)
        return { message: "Something went wrong!" }

    const result = await Api.Question.patch({
        id: parseInt(id),
        correctAnswerId: parseInt(correctAnswerId),
        question: question,
        answers: answers,
        quizId: parseInt(quizId)
    }, parseInt(id))

    if(result) 
        redirect("/quiz/" + quizId)

    return {message: "Something went wrong!"}
}

export async function handleQuestionDelete(id: number | undefined, quizId: number | undefined){
    if (id == undefined || id == -1)
        return { message: "Something went wrong!" }

    const result = await Api.Question.delete(id)

    if(result) 
        redirect("/quiz/" + quizId)

    return {message: "Something went wrong!"}
}