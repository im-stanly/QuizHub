'use client'
import { useState } from 'react';
import { Button, Tooltip, Box, Typography } from '@mui/material';
import SearchBar from '@/components/searchbar';
import StickyTable, { Column } from '@/components/stickytable';
import { useSearchParams, useRouter } from 'next/navigation'

import { Quiz } from "@/api/model"

type TempQuizItem = { id: number, name: string, description: string, played: number };

const tempData = [
    { id: 1, name: "Quiz 1", played: 10, rating: 4.5, creator: "Kanye" },
    { id: 2, name: "Fuiz 2", played: 20, rating: 3.5, creator: "Kanye" },
    { id: 3, name: "Quiz 3", played: 30, rating: 2.5, creator: "Kanye" },
    { id: 4, name: "Szuiz 4", played: 40, rating: 1.5, creator: "Kanye" },
    { id: 5, name: "Buiz 5", played: 50, rating: 0.5, creator: "Kanye" },
    { id: 6, name: "Lquiz 6", played: 60, rating: 4.5, creator: "Kanye" },
    { id: 7, name: "fuiz 7", played: 70, rating: 3.5, creator: "Kanye" },
    { id: 8, name: "Caiz 8", played: 80, rating: 2.5, creator: "Kanye" },
    { id: 9, name: "Ruiz 9", played: 90, rating: 1.5, creator: "Kanye" },
    { id: 10, name: "Duiz 10", played: 100, rating: 0.5, creator: "Kanye" },
];

const columns: Column[] = [
    {
      id: 'name',
      label: 'Name',
      minWidth: 80,
    },
    {
      id: 'description',
      label: 'Description', 
      minWidth: 200,    
    },
    {
        id: 'played',
        label: 'Played',
        minWidth: 50,
    },
  ];

const filterData = (query: string, data: TempQuizItem[]): TempQuizItem[] => {
    if (!query) {
        return data;
    } else {
        return data.filter((d) => d.name.toLowerCase().includes(query.toLowerCase()));
    }
}

export default function SearchClientComponent(props: { quizzes: Quiz[] }){

    const params = useSearchParams();
    const query = params.get("query")??""
    const type = params.get("type")??"name";

    const router = useRouter()

    return(
        <Box component="main" display="flex" flexDirection="column" minHeight="100vh" maxWidth="1200px" sx={{margin: "auto", padding: "48px"}}>  
            <Typography variant="h3" fontWeight="bold" sx={{width: "100%", textAlign: "center"}}>Find Quiz</Typography>
            <Box 
                sx={{
                    display: "flex",
                    minHeight: "100vh",
                    flexDirection: "column",
                    alignItems: "center",
                    padding: 6, 
                    m: 0,
                }}
            >
                <Box 
                    sx={{
                        display: "flex", 
                        width: "100%", 
                        alignItems: "center", 
                        justifyContent: "center",
                        gap: "10px",
                    }}
                >
                    <SearchBar query={query} type={type}/>
                    <RandomQuizButton />
                </Box>
                <Box sx={{ height: "400px", minHeight: "sm", width: '100%', marginTop: 8 }}>
                    {Array.isArray(props.quizzes) && <StickyTable columns={columns} rows={
                        props.quizzes.map(quiz=>{
                                return {id: quiz.id, name: quiz.name, description: quiz.description, played: 0} 
                            })
                        } clickRow={(id)=>{router.push("/quiz/"+id)}}/>}
                </Box>
            </Box>
        </Box>
    )
}

function RandomQuizButton(){
    const [hover, setHover] = useState(false);
    const router = useRouter();

    return(
        <Tooltip title="Get random quiz!" placement="top" arrow>
        <Button
            variant="contained"
            sx={{
                backgroundColor: "transparent",
                display: "flex", 
                alignItems: "center", 
                justifyContent: "center",
                fontSize: 30,
                minWidth: "auto",
                padding: 0,
                
                '&:hover': {
                    content: '"🎉"', 
                },
              }}
            onMouseEnter={() => setHover(true)}
            onMouseLeave={() => setHover(false)}
            onClick={() => router.push("/quiz/randomQuiz")}
        > {hover ? '🎉' : '🎲'} </Button>
        </Tooltip>
    )
}