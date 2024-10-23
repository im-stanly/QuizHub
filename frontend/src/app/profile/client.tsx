'use client'
import { Box, Typography } from '@mui/material';
import StickyTable, { Column } from '@/components/stickytable';
import React from 'react';
import { useRouter } from 'next/navigation';

const columns: Column[] = [
    {
      id: 'name',
      label: 'Name',
      minWidth: 110,
    },
    {
      id: 'description',
      label: 'description',
      minWidth: 110,
    },
];

const statsColumns: Column[] = [
    {
      id: 'name',
      label: 'Name',
      minWidth: 110,
    },
    {
      id: 'played',
      label: 'Played',
      minWidth: 110,
    },
    {
        id: 'avgScore',
        label: 'Avg score',
        minWidth: 110,
    },
    {
        id: 'creator',
        label: 'Creator',
        minWidth: 110,
    },
];

export default function Profile(props: {username: string, myQuizzes: any[], myStats: any[]}) {
    const username = props.username;
    const router = useRouter();

    return(
        <Box component="main" display="flex" flexDirection="column" maxWidth="md" sx={{height: "50vh", margin: "auto", padding: "48px", gap: "60px"}}>
            <Box display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", margin: "auto", gap: "100px"}}>
                <Box display="flex" flexDirection="row" alignItems="center" sx={{width:"100%", margin: "auto", gap: "20px"}}>
                    {/* profile info */}
                    <Box display="flex" flexDirection="row" justifyContent="center" alignItems="center" sx={{width:"100%", gap: "30px"}}>
                        <Box
                            component="img"
                            src="https://i.imgur.com/bWExwkK.png"
                            alt= {username}
                            sx={{
                                width: 100,                
                                height: 100,               
                                borderRadius: "50%",
                                border: "2px solid white",       
                                objectFit: "cover"         
                            }}
                        />
                        <Typography variant="h4" fontWeight="bold">{username}</Typography>    
                    </Box>
                    {/* <Box display="flex" flexDirection="column" alignItems="flex-end" sx={{width:"100%", gap: "5px"}}>
                        <Button variant="contained">Change Password</Button>
                    </Box> */}
                </Box>

                <Box display="flex" flexDirection={{ xs: "column", md: "row" }} alignItems="center" sx={{width:"100%", height:"100%", gap: "20px"}}>
                    <Box display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", height:"100%", gap: "5px"}}>
                        {/* my quizzes */}
                        <Typography variant="h5" sx={{fontWeight: "bold"}} >My Quizzes</Typography>
                        <StickyTable columns={columns} rows={props.myQuizzes} clickRow={(id)=>{router.push("/quiz/"+id)}}/>
                    </Box>

                    {/* <Box display="flex" flexDirection="column" alignItems="center" sx={{width:"100%", height:"100%", gap: "5px"}}>
                        <Typography variant="h5" sx={{fontWeight: "bold"}} >My Stats</Typography>
                        <StickyTable columns={statsColumns} rows={statsData}/>
                    </Box> */}
                </Box>
            </Box>
        </Box>
    )
}