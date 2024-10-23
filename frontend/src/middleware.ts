"use server"
import { NextRequest, NextResponse } from 'next/server'
import { cookies }  from 'next/headers'
import { Api } from "@/api/calls"
 
const protectedRoutes = ['/quiz', "/game", "/profile"]
const authRoutes = ["/auth/login", "/auth/signup"]

export default async function middleware(req: NextRequest) {
    const path = req.nextUrl.pathname

    const user = await Api.Auth.getSignedInUser()

    if(user == undefined){
        req.cookies.delete("token")
    }


    const cookie = req.cookies.get("token")

    if(protectedRoutes.some(it => path.startsWith(it)) && cookie == undefined){
        return NextResponse.redirect(new URL("/auth/login", req.nextUrl))
    }

    console.log(path)
    if(authRoutes.some(it => path.startsWith(it)) && cookie != undefined){
        return NextResponse.redirect(new URL("/", req.nextUrl))
    }
    
    return NextResponse.next()
}

export const config = {
    matcher: ['/((?!api|_next/static|_next/image|.*\\.png$).*)'],
}