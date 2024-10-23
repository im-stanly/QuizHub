import { joinGame } from "@/game/game-actions"
import { Box, IconButton, TextField } from "@mui/material" 
import ArrowCircleRightIcon from '@mui/icons-material/ArrowCircleRight'

const JoinGame = ()=>
    <Box sx={{display: "flex", textAlign: "center" }}
        component="form" 
        action={joinGame}>
        <TextField 
            name="game-code" 
            label="Join Game 🎲" 
            placeholder="enter game code"/>
        <IconButton type="submit">
            <ArrowCircleRightIcon/>
        </IconButton>
    </Box>


export default JoinGame