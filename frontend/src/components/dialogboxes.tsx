import { Dialog, DialogTitle, DialogContent, TextField, DialogActions, Button, Box, Radio, Tooltip } from "@mui/material";
import InputTags from "./inputtags";
import { handleQuestionCreate, handleQuestionDelete, handleQuestionUpdate, handleQuizUpdate } from "@/app/quiz/actions";
import ArrowLeftIcon from '@mui/icons-material/ArrowLeft';
import ArrowRightIcon from '@mui/icons-material/ArrowRight';
import React from "react";
import { useFormState } from "react-dom";

interface EditQuizProps {
    nameEdit?: boolean;
    descEdit?: boolean;
    tagsEdit?: boolean;
    quizId: number | undefined;
    quizName: string | undefined;
    quizDesc: string | undefined;
    quizTags: string[] | undefined;
    isOpen: boolean;
    handleClose: () => void;
}

interface EditQuestionProps {
    quizId: number | undefined;
    questionId: number | undefined;
    question: string | undefined;
    answerA: string | undefined;
    answerB: string | undefined;
    answerC: string | undefined;
    answerD: string | undefined;
    correctAns: number | undefined;
    isOpen: boolean;
    handleClose: () => void;
}

const EditQuizDialog = (props: EditQuizProps) => {
    const {
        nameEdit = false,  
        descEdit = false,  
        tagsEdit = false,  
        quizId,
        quizName,
        quizDesc,
        quizTags,
        isOpen,
        handleClose
    } = props;
    const [state, formAction] = useFormState(handleQuizUpdate, {message: ""});

    return (
        <Dialog
            open={props.isOpen}
            onClose={props.handleClose}
            key="EditQuizDialog"
            maxWidth="sm"
            fullWidth={true}
            PaperProps={{
                component: 'form',
                action: formAction,
            }}
        >
            <DialogTitle>
                {props.nameEdit
                        ? 'Edit Name'
                        : props.descEdit
                        ? 'Edit Description'
                        : props.tagsEdit
                        ? 'Edit Tags'
                        : 'Edit Quiz'}
            </DialogTitle>
            <input type="hidden" name="id" value={quizId} />
            {!props.tagsEdit && props.quizTags && props.quizTags.map((tag) => <input type="hidden" name="tags" value={tag} />)}
            <DialogContent>
                {props.nameEdit && (
                    <TextField
                        id="quiz-name"
                        name="name"
                        autoComplete="off"
                        autoFocus
                        required
                        defaultValue={props.quizName}
                        fullWidth
                    />
                )}

                {props.descEdit && (
                    <TextField
                        id="quiz-description"
                        name="description"
                        autoComplete="off"
                        multiline
                        rows={5}
                        required
                        autoFocus
                        defaultValue={props.quizDesc}
                        fullWidth
                    />
                )}

                {props.tagsEdit && (
                    <InputTags defaultTags={props.quizTags}/>                
                )}
            </DialogContent>
            <DialogActions>
            <Button onClick={props.handleClose} sx={{color: "white"}}>Cancel</Button>
            <Button type="submit" onClick={props.handleClose} sx={{color: "white"}}>Save</Button>
            </DialogActions>
        </Dialog>
    );
}

const EditQuestionDialog = (props: EditQuestionProps) => {
    const [ansNum, setAnsNum] = React.useState(''+4);
    const [correctId, setCorrectId] = React.useState(0)
    const answers = [props.answerA, props.answerB, props.answerC, props.answerD];
    const [stateCreate, formCreateAction] = useFormState(handleQuestionCreate, {message: ""});
    const [stateUpdate, formUpdateAction] = useFormState(handleQuestionUpdate, {message: ""});

    const Answer = ({id}: {id: number} ) => 
        <Box display="flex" flexDirection="row" alignItems="center" 
            sx={{
                marginTop: "15px", 
                borderStyle: (id==correctId ? "solid" : "none"),
                backgroundColor:(id==correctId ? "green" : "#232323"), 
                
                }}>
            <Radio checked={correctId==id} onChange={(event: React.ChangeEvent) => {if((event.target as HTMLInputElement).checked) setCorrectId(id) }} />
            <TextField 
                multiline 
                rows={2} 
                required
                id="answers"
                name="answers" 
                defaultValue={answers[id]}
                sx={{
                    width:"100%", 
                    borderStyle: "none",
                    fontStyle: (id==correctId ? "bold" : "normal"),
                }}>    
            </TextField>
        </Box>

    let ans = parseInt(ansNum);
    const arr = Array.from({length: ans}, (_,i)=>i);

    const deleteQuestion = () => {
        handleQuestionDelete(props.questionId, props.quizId)
        props.handleClose()
    }

    return(
        <Dialog
            open={props.isOpen}
            key="EditQuestionDialog"
            onClose={props.handleClose}
            maxWidth="md"
            fullWidth={true}
            PaperProps={{
                component: 'form',
                action: props.questionId == -1 ? formCreateAction : formUpdateAction,
            }}
        >   
            <DialogTitle>{props.questionId == -1 ? "Add" : "Edit" } Question</DialogTitle>
            <input key="id" type="hidden" name="id" value={props.questionId}/>
            <input key="quizId" type="h bidden" name="quizId" value={props.quizId} />
            <input key="correctAnswerId" type="hidden" name="correctAnswerId" value={correctId} />
            <Box
                display="flex" 
                flexDirection="row" 
                alignItems="center"
                justifyContent="center"
                sx={{ width: "100%"}} 
                >
                {/* {props.questionId != -1 && <SideBtn title="Previous question" onClick={previousQuestion}><ArrowLeftIcon fontSize="large"/></SideBtn> } */}
                <Box
                    maxWidth="sm" 
                    sx={{width:"100%", margin: "20px"}}
                >
                    <DialogContent>
                            <TextField
                                    multiline={true}
                                    rows={5}
                                    id="question"
                                    name="question"
                                    required
                                    label="Question"
                                    variant="outlined"
                                    defaultValue={props.question}
                                    sx={{
                                        width: "100%",
                                        marginTop:"15px",
                                        marginBottom:"15px"
                                    }}/>
                            {arr.map((val => Answer({id: val})))}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={props.handleClose} sx={{color: "white"}}>Close</Button>
                        {props.questionId != -1 && <Button onClick={deleteQuestion} sx={{color: "white"}}>Delete</Button>}
                        <Button type="submit" onClick={props.handleClose} sx={{color: "white"}}>{props.questionId == -1 ? "Add" : "Save"}</Button>     
                    </DialogActions>
                </Box>
                {/* {props.questionId != -1 && <SideBtn title="Next question" onClick={nextQuestion}><ArrowRightIcon fontSize="large"/></SideBtn>} */}
            </Box>
        </Dialog>
    ) 
}

function SideBtn({title, onClick, children} : {title: string, onClick: ()=>void, children:React.ReactNode}) {
    return (
        <Box display="flex" alignItems="center" flexDirection="column" textAlign="center" sx={{height:"100%", width:"80px"}}>
            <Tooltip title={title} placement="top" arrow>
                <Button sx={{
                    background: "none", 
                    width: "100px", 
                    height:"150px", 
                    margin:"10px", 
                    borderRadius: "20px", 
                    border: "1px solid #ccc",
                    color: "white"}} variant="contained" onClick={onClick}>
                    {children}
                </Button>
            </Tooltip>
        </Box>
    )
}

export {EditQuizDialog, EditQuestionDialog};
