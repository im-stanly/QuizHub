'use client'
import { Box, Button, LinearProgress, Typography } from '@mui/material'
import CircleIcon from '@mui/icons-material/Circle';
import React, { useEffect, useState } from 'react';
import QuizButton from '@/components/quizbutton';
import Timer from '@/components/timer';
import { timerReset } from '@/components/timer';
import { GameData } from '@/socket/gameSocket';

export default function Game(props: {room: string, state: GameData, sendAnswer: (answer: number) => void, username: string}){
    if (props.state === undefined || props.state?.question === undefined || props.state?.answers === undefined) {
        return <LinearProgress />
    }
    
    const [question, setQuestion] = useState<string>("");
    const [answers, setAnswers] = useState<string[]>(["","","",""]);
    const [answerResults, setAnswerResults] = useState<number[]>([0, 0, 0, 0, 0]);

    const [answer, setAnswer] = useState<number | null>(null);

    const handleButtonClick = (answer: number) => {
        setAnswer(answer);
        props.sendAnswer(answer);
    }

    const handleTimeEnds = () => {
        // setAnswer(-1);
        // props.sendAnswer(-1);
    }

    useEffect(()=>{
        if(props.state.correctAnswerId !== undefined){
            setAnswerResults(
                answers => answers.map(
                    (value, index) => index == props.state.questionNumber-1 ? 
                    (answer === props.state.correctAnswerId ? 1 : 2) : value))  
        }
    },[props.state.correctAnswerId]

    )

    useEffect(()=>{
        setQuestion(props.state.question)
        setAnswers(props.state.answers)
        setAnswer(null)
        timerReset()
    },[props.state.questionNumber])

    if (props.state?.correctAnswerId !== undefined) {
        // setCorrectAnswer(props.state?.correctAnswerId ?? undefined);
        // if (answer !== null) {
        //     if (answer === props.state?.correctAnswerId) {
        //         setAnswerResults([false, false, false, false, true])
        //     } else {
        //         setAnswerResults([false, false, false, false, false])
        //     }
        // }
    }

    return(
        <Box component="main" display="flex" flexDirection="row" maxWidth="md" sx={{margin: "auto", padding: "48px"}}> 
            <Box minWidth="sm" display="flex" flexDirection="column" sx={{width:"100%", margin: "auto", gap: "60px"}}>
                <Box minWidth="sm" display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", margin: "auto"}}> 
                    {/* timer box*/}
                    <Box minWidth="sm" display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", margin: "auto"}}>
                        {/* time */}
                        <Timer time={20} handleTimeEnds={handleTimeEnds} />
                    </Box>
                </Box>

                <Box minWidth="sm" display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", margin: "auto", gap: "10px"}}>
                    <Typography variant="h2" fontWeight="bold">{question}</Typography>
                </Box>

                <Box minWidth="sm" display="flex" flexDirection="column" alignContent="center" sx={{width:"100%", margin: "auto", gap: "20px"}}>
                    {/* answers box */}
                    <Box display="flex" flexDirection="row" justifyContent="center" alignItems="center" sx={{width:"100%", margin: "auto", gap: "20px"}}>
                        {/* answer 1 and 2*/}
                        <QuizButton buttonID={0} answerString={answers[0]} correctAnswer={props.state?.correctAnswerId} answer={answer} handleButtonClick={handleButtonClick} />
                        <QuizButton buttonID={1} answerString={answers[1]} correctAnswer={props.state?.correctAnswerId} answer={answer} handleButtonClick={handleButtonClick} />
                    </Box>
                     
                    <Box display="flex" flexDirection="row" justifyContent="center" alignItems="center" sx={{width:"100%", margin: "auto", gap: "20px"}}>
                        {/* answer 3 and 4*/}
                        <QuizButton buttonID={2} answerString={answers[2]} correctAnswer={props.state?.correctAnswerId} answer={answer} handleButtonClick={handleButtonClick} />
                        <QuizButton buttonID={3} answerString={answers[3]} correctAnswer={props.state?.correctAnswerId} answer={answer} handleButtonClick={handleButtonClick} />
                    </Box>       
                </Box>

                <Box minWidth="sm" display="flex" flexDirection="row" justifyContent="center" alignItems="center" sx={{width:"100%", margin: "auto", gap: "70px"}}>
                    {answerResults.map((result, index) => (
                        <CircleIcon
                        key={index}
                        sx={{
                            fontSize: "30px", 
                            color: result === 0 ? "white" : result === 1 ? "green" : "red", 
                        }}
                        />
                    ))}
                </Box>
            </Box>
        </Box>
    )

}