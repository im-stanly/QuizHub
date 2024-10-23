import { cookies } from "next/headers";
import { ApiResponse, User, Quiz, Question, QuizPost, GameModel } from "./model";

export function isAuthenticated(){
    // should e also check if the token is valid on the server
    return cookies().get("token") != undefined
}

export function apiRequest(path: string, method: string = "GET", body: any = undefined){
    var headers: HeadersInit = {
        "Content-Type": "application/json"
    }

    const token = cookies().get(TOKEN_COOKIE)
    if(token != undefined){
        headers = {
            ...headers, 
            "user-token": token.value
        }
    }

    var options: RequestInit = {
        method: method,
        headers: headers,
    } 
    if(body != undefined){
        options = { ...options, body: JSON.stringify(body) }
        console.log(JSON.stringify(body))
    }
    return new Request(process.env.NEXT_PUBLIC_API_HOST + path, options)
}

export const TOKEN_COOKIE = "token"

export const Api = {
    Auth: {
        login: async function(username: string, password: string) {
            const response =  await fetch(apiRequest("/user/login", "POST", {
                username: username,
                password: password
            }))
            
            const data = await response.json()
            if(response.ok){
                cookies().set({
                    name: "token",
                    value: data.token,
                    httpOnly: true,
                    maxAge: 60*60*24
                })
            }
            console.log(data)

            return { success: data.success == "true", message: data.error } as ApiResponse
        },
        register: async function(email: string, username: string, password: string) {
            const response =  await fetch(apiRequest("/user/register", "POST", {
                email: email,
                username: username,
                password: password
            }))
            const data = await response.json()
            return { success: data.success == "true", message: data.error } as ApiResponse
        },
        logout: function(){
            cookies().delete("token")
        },
        getSignedInUser: async function(){
            const response = await fetch(apiRequest("/user/token"))
            if(response.ok){
                return await response.json() as User
            }
        }
    },
    User: {
        get: async function(){
            const response = await fetch(apiRequest("/user"))
            if(response.ok){
                try {
                    return (await response.json()) as User[] 
                }
                catch {
                    return []
                }
            }
            return []
        },
        getById: async function(id: number){
            const response = await fetch(apiRequest("/user/id/" + id))
            if(response.ok){
                return await response.json() as User
            }
        },
        delete: async function(id: number){
            const response = await fetch(apiRequest("/user/id/" + id, "DELETE"))
            return response.ok
        }
    },

    Quiz: {
        get: async function(id: number){
            const response = await fetch(apiRequest("/quiz/id/" + id))
            if(response.ok){
                return await response.json() as Quiz
            }
        },
        post: async function(quiz: QuizPost){
            const response = await fetch(apiRequest("/quiz", "POST", quiz))
            const content = await response.json() as Quiz
            if(content.id !== undefined) return content
            return undefined
        },
        put: async function(quiz: Quiz){
            const response = await fetch(apiRequest("/quiz/id/"+quiz.id, "PUT", quiz))
            return response.ok
        },
        patch: async function(quiz: any, id: number){
            const response = await fetch(apiRequest("/quiz/id/"+id, "PATCH", quiz))
            return response.ok
        },
        delete: async function(id: number){
            const response = await fetch(apiRequest("/quiz/id/"+id, "DELETE"))
            return response.ok
        },
        getQuizzesByTag: async function(tag: string){
            const response = await fetch(apiRequest("/quiz/tag/"+tag))
            return await response.json() as Quiz[] 
        },
        getQuizzesByName: async function(name: string){
            const response = await fetch(apiRequest("/quiz/name/"+name))
            return await response.json() as Quiz[]
        },
        getQuizzesByCreator: async function(userId: number){
            const response = await fetch(apiRequest("/quiz/userId/"+userId))
            return await response.json() as Quiz[]
        }
    },

    Question: {
        get: async function(id: number){
            const response = await fetch(apiRequest("/question/id/"+id))
            if(response.ok){
                return (await response.json()) as Question
            }
        },
        getQuizId: async function(quizId: number){
            const response = await fetch(apiRequest("/question/quizId/"+quizId))
            if(response.ok){
                return (await response.json()) as Question[]
            }
        },
        getNthFromQuiz: async function(quizId: number, n: number){
            const response = await fetch(apiRequest("/question/quizId/"+quizId+"/n/"+n))
            if(response.ok){
                return (await response.json()) as Question
            }
        },
        getQuestionCount: async function(quizId: number){
            const response = await fetch(apiRequest("/question/quizId/"+quizId+"/count"))
            if(response.ok){
                return (await response.json()) as number
            }
        },
        post: async function(question: Question){
            const response = await fetch(apiRequest("/question", "POST", question))
            return response.ok
        },
        put: async function(question: Question){
            const response = await fetch(apiRequest("/question/id/"+question.id, "PUT", question))
            return response.ok
        },
        patch: async function(question: any, id: number){
            const response = await fetch(apiRequest("/question/id/"+id, "PATCH", question))
            return response.ok
        },
        delete: async function(id: number){
            const response = await fetch(apiRequest("/question/id/"+id, "DELETE"))
            return response.ok
        }
    },
    Game: {
        newGame: async function(quizId: number, userId: number){
            const response = await fetch(apiRequest("/game/new", "POST", {
                quizID: quizId,
                userID: userId
            }))
            if(response.ok){
                return await response.text()   
            }
        },
        addUserToGame: async function(room: string, username: string){
            const response = await fetch(apiRequest("/game/"+room+"/add_user", "POST",username))
            return response.ok
        },
        getGame: async function(room: string){
            const response = await fetch(apiRequest("/game/"+room))
            if(response.ok)
                return await response.json() as GameModel
        },
    }    
}
