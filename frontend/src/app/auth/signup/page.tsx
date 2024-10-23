'use client'
import { TextField, Typography, Box, Button, Link } from "@mui/material";
import React, { useState } from 'react';
import { useFormState } from 'react-dom';
import { validateEmail, validatePassword } from "@/components/auth/components";
import { handleRegistrationSubmit, FormState } from "../actions";


const errors = [
    "",
    "Niepoprawny email",
    "Hasło jest za krótkie",
    "Hasła nie są takie same"
]

export default function Page(){
    const [email, setEmail] = useState("")
    const [user, setUser] = useState("")
    const [password, setPassword] = useState("")
    const [pass2, setPass2] = useState("")

    const [error, setError] = useState(0)

    const tfStyle = {width:"100%", marginTop:"15px"}
    const [state, formAction] = useFormState(handleRegistrationSubmit, {message: ""});


    const handleSubmit = (e: React.FormEvent) => {
        let err = 0
        if(password != pass2) err=3             
        if(!validatePassword(password)) err=2
        if(!validateEmail(email)) err=1
        if(err){
            e.preventDefault()
            setError(err)
        } 
        
    }     

    return(<Box component="form" onSubmit={handleSubmit} action={formAction}
        maxWidth="400px" sx={{margin: "auto", marginTop: "50px", textAlign:"center"}}>
        <Typography sx={{marginBottom: "20px"}} variant="h4" fontWeight="bold">Sign Up</Typography>
        <TextField  sx={tfStyle} variant="outlined" error = {error == 1}
            label={"e-mail"} type="email" name="email" value={email} 
            onChange={(e)=>{setEmail(e.target.value); setError(0)}}/>
        <TextField  sx={tfStyle} variant="outlined" 
            label={"username"} name="username" value={user} 
            onChange={(e)=>{setUser(e.target.value); setError(0)}}/>

        <TextField  sx={tfStyle} variant="outlined" error = {error == 2 || error==3}
            label={"password"} type="password" name="password" value={password} 
            onChange={(e)=>{setPassword(e.target.value);setError(0)}}/>
        <TextField  sx={tfStyle} variant="outlined" error = {error==3}
            label={"repeat password"} type="password" value={pass2} 
            onChange={(e)=>{setPass2(e.target.value); setError(0)}}/>

            <div style={{height:"30px", margin: "5px", width:"100%"}}>
                <Typography color="red">{errors[error]} {state.message}</Typography>
            </div>        

        <Box sx={{margin: "auto", gap:"10px"}}>
            <Button type="submit" variant="contained" sx={{width:"70%"}}>Sign Up</Button>
            <Typography sx={{marginTop: "15px"}}>Already a user? <Link href="/auth/login/" fontWeight="bold">Log In</Link></Typography>
        </Box>
    </Box>)
}