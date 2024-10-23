'use client'
import { TextField, Typography, Box, Button, Link } from "@mui/material";
import React, { useState } from 'react'
import { useFormState } from 'react-dom';
import { handleLoginSubmit } from "../actions";


const errors = [
    "",
    "Niepoprawny email",
    "Hasło jest za krótkie",
    "Hasła nie są takie same"
]

export default function Page(){
    const [user, setUser] = useState("")
    const [password, setPassword] = useState("")

    const [error, setError] = useState(0)

    const tfStyle = {width:"100%", marginTop:"15px"}
    const [state, formAction] = useFormState(handleLoginSubmit, {message: ""});


    return(
        <Box component="form" action={formAction}
            maxWidth="400px" sx={{height: "70vh" ,margin: "auto", marginTop: "50px", textAlign:"center"}}>
            <Typography sx={{marginBottom: "20px"}} variant="h4" fontWeight="bold">Log In</Typography>
            <TextField  sx={tfStyle} variant="outlined" error = {error == 1}
                label={"User"} name="username" value={user} 
                onChange={(e)=>{setUser(e.target.value); setError(0)}}/>

            <TextField  sx={tfStyle} variant="outlined" error = {error == 2 || error==3}
                label={"Password"} type="password" name="password" value={password} 
                onChange={(e)=>{setPassword(e.target.value);setError(0)}}/>

                <div style={{height:"30px", margin: "5px", width:"100%"}}>
                    <Typography color="red">{errors[error]}{state.message}</Typography>
                </div>        

            <Box sx={{margin: "auto", gap:"10px"}}>
                <Button type="submit" variant="contained" sx={{width:"70%"}}>log in</Button>
                <Typography sx={{marginTop: "15px"}}>New here? <Link href="/auth/signup/" fontWeight="bold">Sign Up</Link></Typography>
            </Box>

        </Box>
    )
}