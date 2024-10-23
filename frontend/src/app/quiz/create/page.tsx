'use client'
import { Typography, Box, Input } from '@mui/material';
import TextField from '@mui/material/TextField';
import Button from "@mui/material/Button"
import { useFormState } from "react-dom"
import { handleQuizCreate } from "../actions"
import InputTags from '@/components/inputtags';

export default function Page(){
    const [state, formAction] = useFormState(handleQuizCreate, {message: ""});

    return(
        <Box component="main" display="flex" flexDirection="column" maxWidth="sm" sx={{margin: "auto", padding: "48px"}}> 
            <Typography variant="h3" fontWeight="bold">Create Quiz!</Typography>
            <Box component="form" autoComplete="off" action={formAction} sx={{paddingTop: "30px"}}>
                <div style={{padding: "16px", height: "128px"}}>
                    <TextField
                        required
                        id="quiz-name"
                        name="name"
                        label="Quiz name"
                        sx={{width:"100%", marginBottom:"15px"}}
                        />
                    <TextField
                        multiline={true}
                        rows={5}
                        id="quiz-description"
                        name="description"
                        label="Description"
                        variant="outlined"
                        sx={{
                            width: "100%",
                            marginBottom:"15px"
                        }}
                    />
                    <InputTags />
                    <Button variant="contained" type="submit" sx={{width:"100%"}}>Next</Button>
                    <div style={{height:"30px", margin: "5px", width:"100%"}}>
                        <Typography color="red">{state.message}</Typography>
                    </div>  
                </div>
            </Box>
        </Box>
    )
}