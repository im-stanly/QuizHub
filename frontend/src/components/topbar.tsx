"use client"

import React from 'react';
import { Box, Link, useTheme, Typography } from '@mui/material';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import HomeIcon from '@mui/icons-material/Home';
import { User } from '@/api/model'
import SearchIcon from '@mui/icons-material/Search';

interface NavEntity {
    content: React.ReactNode,
    href: string,
    right?: boolean
}

export default function TopBar({user}: {user: User | undefined}){
    const theme = useTheme();

    var objects: NavEntity[] = [
        {
            content: <div style={{display:"flex", alignItems:"center"}}><Typography><HomeIcon/></Typography></div>,
            href: "/"
        },
        {
            content: <Typography fontWeight="bold"><SearchIcon/></Typography>,
            href: "/search"
        },
        {
            content: <Typography>Create Quiz</Typography>,
            href: "/quiz/create"
        },
        
    ]

    if(user == undefined){
        objects = [
            ...objects,
            {
                content: <Typography>log in</Typography>,
                href: "/auth/login",
                right: true
            },
            {
                content: <Typography>sign up</Typography>,
                href: "/auth/signup",
                right: true
            }
        ]
    } else {
        objects = [
            ...objects, 
            {
                content: <div style={{display:"flex", alignItems:"center"}}><AccountCircleIcon/><Typography sx={{marginLeft:"5px"}}>{user.username}</Typography></div>,
                href: "/profile",
                right: true
            },
            {
                content: <Typography>Logout</Typography>,
                href: "/auth/logout",
                right: true
            }
        ]
    }

    
    
    const tile = (navEntity: NavEntity, index: number) => 
        <Box key={index} sx={{ p: 0, m: 0, float:"left"}}>
            <Link
            href={navEntity.href}
            underline="none" 
            sx={{
                color: 'white', 
                fontFamily: theme.typography.fontFamily,
                textTransform: 'none',
                px: 2,
                py: 2,
                display: 'inline-block',
                '&:hover': {
                    color: 'black', 
                    backgroundColor: 'orange', 
                },
            }}
            >
            {navEntity.content}
            </Link>
        </Box>

    const showTiles = (filter: (arg0: NavEntity)=>Boolean)=>
        objects.filter(filter).map((navEntity, index) => tile(navEntity, index))
    



    return(
        <Box component="nav" display="flex" alignItems="center" justifyContent="center" sx={{ overflow: 'hidden', m: 0, p: 0 }}>
            <Box sx={{ width:"100%", maxWidth:"1200px", display: 'flex', flexDirection:"row", p: 0, m: 0, justifyContent: "space-between"}}>
                <div>{showTiles((ne)=>ne.right != true)}</div>
                <div>{showTiles((ne)=>ne.right == true)}</div>
            </Box>
        </Box>
    );  
}