'use server'

import { requestToBodyStream } from "next/dist/server/body-streams"
import { cookies } from "next/headers"
import { Api } from "@/api/calls"
import { ApiResponse } from "@/api/model"
import { redirect } from "next/navigation"

export type FormState = {
    message: string,
}

export async function handleRegistrationSubmit(prevState: FormState, data: FormData){
    let credentials = {
        email: data.get("email") as string,
        username: data.get("username") as string,
        password: data.get("password") as string,
    }

    const result = await Api.Auth.register(credentials.email, credentials.username, credentials.password)
    if(!result.success){
        console.log("Registration failed")
        return { message: result.message }
    } 
    const login = await Api.Auth.login(credentials.username, credentials.password);
    if(login.success){
        return redirect("/")
    }
    return { message: login.message } 
}

export async function handleLoginSubmit(prevState: FormState, data: FormData){
    let credentials = {
        username: data.get("username") as string,
        password: data.get("password") as string,
    }

    const result = await Api.Auth.login(credentials.username, credentials.password)
    if(!result.success){
        console.log("Login failed")
        return {message: result.message}
    }
    return redirect("/") 

}