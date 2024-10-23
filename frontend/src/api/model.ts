export interface ApiResponse {
    success: boolean,
    message: string
}

export interface User {
    id: number,
    email: string,
    username: string,
    userPermissions: string
}

export interface Quiz {
    id: number,
    name: string,
    description: string,
    tags: string[],
    creatorId: number
}

export interface QuizPost {
    name: string,
    description: string,
    tags: string[],
    creatorId: number
}

export interface Question {
    id: number,
    correctAnswerId: number,
    question: string,
    answers: string[],
    quizId: number
}

export interface GameModel {
    id: number,
    quizID: number,
    quizName: string,
    room: string,
    usersPermitted: string[]
}