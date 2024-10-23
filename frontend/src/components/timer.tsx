import React, { useEffect } from 'react';
import { useState, useRef } from 'react';
import { Typography, Box, LinearProgress } from '@mui/material';

interface TimerProps {
    time: number; 
    handleTimeEnds: () => void;
}

var guest = false

export function timerReset(){
    guest = !guest
}


const Timer : React.FC<TimerProps> = ({time, handleTimeEnds}) => {
    interface Clock {
        minutes: number,
        seconds: number
    }

    const parseTime = (secs: number) => {
        const seconds = Math.floor(secs % 60);
        const minutes = Math.floor((secs / 60) % 60);
        
        return {
            minutes: minutes,
            seconds: seconds,
        };
    };
    
    const timeString = ({minutes, seconds} : Clock) =>
        (minutes > 9 ? minutes : "0" + minutes) + ":" +
            (seconds > 9 ? seconds : "0" + seconds)
    

    const Ref = useRef<null | NodeJS.Timeout>(null);
    const counter = useRef<number>(time);
    const [timer, setTimer] = useState(timeString(parseTime(counter.current)));
    const [level, setLevel] = useState<number>(0);

    const updateTimer = (secs: number) => {
        let time = parseTime(secs);
        setTimer(
            timeString(time)
        );
    
    };

    const resetTimer = () => {
        counter.current = time;
        updateTimer(time);
    }

    useEffect(()=>{
        resetTimer()
        setLevel(0);
        // if (Ref.current) clearInterval(Ref.current);
        const id = setInterval(()=> {
            if(counter.current <= 0) {
                handleTimeEnds();
                clearInterval(id);
                return;
            }
            counter.current -= 1;
            updateTimer(counter.current)
            const timer = (time - counter.current) / time * 100;
            setLevel(timer);
            
        }, 1000)

        return ()=>{ clearInterval(id) }
    },[guest])


    return (
        <Box minWidth="sm" display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", margin: "auto", gap:"10px"}}>
            <Typography variant="h4">{timer}</Typography>
            <LinearProgress variant="determinate" value={level} 
                            sx={{
                                width: "100%", 
                                maxWidth: "600px", 
                                height: "5px", 
                                margin: "auto",
                                "& .MuiLinearProgress-bar": {
                                    backgroundColor: "green",
                                }}
                            }/>
        </Box>
    )
}

export default Timer;