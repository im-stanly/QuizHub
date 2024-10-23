export function validateEmail(str: string){
    return /^\S+@\S+\.\S+$/.test(str)
}

export function validatePassword(str: string){
    return str.length >= 8 
}
