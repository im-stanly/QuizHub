'use client'
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid2";
import Paper from '@mui/material/Paper';
import EditIcon from '@mui/icons-material/Edit';
import StickyTable, {Column} from "@/components/stickytable";

import AccessibleForwardIcon from '@mui/icons-material/AccessibleForward';
import Button from '@mui/material/Button';
import { styled } from "@mui/material";
import { useState } from "react";
import {EditQuizDialog, EditQuestionDialog} from "@/components/dialogboxes";

import { Quiz, Question } from "@/api/model"

import { createGame } from "@/game/game-actions";

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
    ...theme.applyStyles('dark', {
      backgroundColor: '#1A2027',
    }),
  }));
  
  const columns: Column[] = [
    {
      id: 'question',
      label: 'Question',
      minWidth: 220,
    },
    {
      id: 'answerA',
      label: 'Answer A',
      minWidth: 50,
    },
    {
      id: 'answerB',
      label: 'Answer B',
      minWidth: 50,
    },
    {
      id: 'answerC',
      label: 'Answer C',
      minWidth: 50,
    },
    {
      id: 'answerD',
      label: 'Answer D',
      minWidth: 50,
    },
    {
      id: 'correct',
      label: 'Correct',
      minWidth: 50,
    },
  ];

export function QuizDetails(props: {quiz: Quiz | undefined, questions: Question[] | undefined, userName: string | undefined, isOwner: boolean}){
    // we need to check if the quiz belongs to the signed-in user
    // if so, the page should show options for quiz manipulation
    // and hide them otherwise
    // let data = await fetch("https://api.restful-api.dev/objects")
    const [openName, setOpenName] = useState(false);
    const [openDesc, setOpenDesc] = useState(false);
    const [openTags, setOpenTags] = useState(false);
    const [editQuestion, setEditQuestion] = useState(false);
    const [currentlyEdited, setCurrentlyEdited] = useState<any>({});

    const handleOpenName = () => setOpenName(true);
    const handleCloseName = () => setOpenName(false);
    
    const handleOpenDesc = () => setOpenDesc(true);
    const handleCloseDesc = () => setOpenDesc(false);
    
    const handleOpenTags = () => setOpenTags(true);
    const handleCloseTags = () => setOpenTags(false);
    
    const handleCloseEditQuestion = () => setEditQuestion(false);
    const handleEditQuestion = (id: number) => {
        const questionToEdit = props.questions?.find((question) => question.id === id);
        setCurrentlyEdited(mappedQuestions(questionToEdit ? [questionToEdit] : undefined)[0]);
        setEditQuestion(true);
    }

    const handleAddQuestion = () => {
        setCurrentlyEdited({
            id: -1,
            question: "",
            answerA: "",
            answerB: "",
            answerC: "",
            answerD: "",
            correct: "",
        });
        setEditQuestion(true);
    }

    const mappedQuestions = (questions: Question[] | undefined) => {
        if (questions == undefined) {
            return ["",  "",  "",  "",  "",  "", ""];
        }
        
        return questions.map((question) => ({
            id: question.id,
            question: question.question,
            answerA: question.answers[0] || '',
            answerB: question.answers[1] || '',
            answerC: question.answers[2] || '',
            answerD: question.answers[3] || '',
            correct: question.answers[question.correctAnswerId] || '',
        }))
    };

    return(
        <Box component="main" display="flex" flexDirection="column" maxWidth="1200px" sx={{margin: "auto", padding: "48px"}}> 
            <div>
                <EditQuizDialog 
                    nameEdit={true} 
                    quizId={props.quiz?.id}
                    quizName={props.quiz?.name} 
                    quizDesc={props.quiz?.description}
                    quizTags={props.quiz?.tags}
                    isOpen={openName} 
                    handleClose={handleCloseName} />
                
                <EditQuizDialog 
                    descEdit={true} 
                    quizId={props.quiz?.id}
                    quizName={props.quiz?.name} 
                    quizDesc={props.quiz?.description}
                    quizTags={props.quiz?.tags}
                    isOpen={openDesc} 
                    handleClose={handleCloseDesc} />
                
                <EditQuizDialog 
                    tagsEdit={true} 
                    quizId={props.quiz?.id}
                    quizName={props.quiz?.name} 
                    quizDesc={props.quiz?.description}
                    quizTags={props.quiz?.tags}
                    isOpen={openTags} 
                    handleClose={handleCloseTags} />
                
                <EditQuestionDialog
                    isOpen={editQuestion}
                    handleClose={handleCloseEditQuestion}
                    quizId={props.quiz?.id}
                    questionId={currentlyEdited.id}
                    question={currentlyEdited.question}
                    answerA={currentlyEdited.answerA}
                    answerB={currentlyEdited.answerB}
                    answerC={currentlyEdited.answerC}
                    answerD={currentlyEdited.answerD}
                    correctAns={currentlyEdited.correct} />

                <Grid container spacing={2}>
                    <Grid size={4} direction="row">
                        <Item>
                            <div style={{display: "flex", justifyContent: "space-between", alignItems:"center", height: "40px", marginLeft: "10px"}}><strong>{props.quiz?.name}</strong>
                                { props.isOwner && <Button variant="contained" onClick={handleOpenName}><EditIcon/></Button> }
                            </div>  
                        </Item>
                    </Grid>
                    <Grid size={4} direction="row">
                        <Item>
                            <div style={{display: "flex", justifyContent: "space-between", alignItems:"center", height: "40px", marginLeft: "10px", gap: "10px"}}>
                                {props.quiz?.tags.map((item) => (
                                    <div style={{ 
                                        border: "1px solid #ccc",
                                        borderRadius: "3px", 
                                        marginRight: "3px", 
                                        padding: "5px",  
                                        background: "#5C746B",
                                        flex: 1 
                                    }}>
                                    {item}
                                    </div>))}
                                { props.isOwner && <Button variant="contained" onClick={handleOpenTags}><EditIcon/></Button> }
                            </div>
                        </Item>
                    </Grid>
                    <Grid size={4} direction="row">
                        <Item>
                        <div style={{display: "flex", justifyContent: "center", alignItems:"center", height: "40px"}}>
                            <AccessibleForwardIcon/> <span>{props.userName}</span></div></Item>
                    </Grid>

                    <Grid size={12} columns={6}>
                        <Item><div style={{display: "flex", margin:"10px"}}>
                            {props.quiz?.description}
                        </div>
                        { props.isOwner &&
                            <div style={{display:"flex", justifyContent:"flex-end"}}> 
                                <Button variant="contained" onClick={handleOpenDesc}><EditIcon/></Button>
                            </div>
                        }   
                        </Item>
                    </Grid>
                    <Grid size={6}>
                        <Item><div style={{display: "flex", justifyContent: "center", alignItems:"center",height:"40px"}}>Number of plays: {/* num of plays here */}</div></Item>
                    </Grid>
                    <Grid size={6}>
                        <Item>
                            <form action={createGame}>
                                <input hidden name="quiz-id" value={props.quiz?.id}/>
                                <Button type="submit" variant="contained" sx={{width:"100%", height:"40px"}}>Play</Button>
                            </form>
                        </Item>
                    </Grid>
                </Grid>
            </div>
            <Box sx={{ height: 400, width: '100%', marginTop: 5, gap: "10px" }}>
                <StickyTable columns={columns} rows={mappedQuestions(props.questions)} editButtons={props.isOwner} editRow={handleEditQuestion} addRow={handleAddQuestion}/>     
            </Box>
        </Box>
    )
}

