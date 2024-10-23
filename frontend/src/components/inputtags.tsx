import { Cancel, Tag } from "@mui/icons-material";
import { FormControl, Stack, TextField, Typography } from "@mui/material";
import { Box } from "@mui/system";
import React, { MutableRefObject, useRef, useState } from "react";

const Tags = (props: {data:string, handleDelete:(arg0: string)=>void}) => {
  return (
    <Box
      sx={{
        background: "#283240",
        height: "100%",
        display: "flex",
        padding: "0.4rem",
        margin: "0 0.5rem 0 0",
        justifyContent: "center",
        alignContent: "center",
        color: "#ffffff",
      }}
    >
      <Stack direction='row' gap={1}>
        
        <Typography>{props.data}</Typography>
            <Cancel
            sx={{ cursor: "pointer" }}
            onClick={() => {
                props.handleDelete(props.data);
            }}/> 
      
      </Stack>
    </Box>
  );
};

export default function InputTags(props: {defaultTags?: string[]}) {
  const [tags, SetTags] = useState<string[]>(props.defaultTags || []);
  const [text, setText] = useState("");

  const handleDelete = (value: any) => {
    const newtags = tags.filter((val) => val !== value);
    SetTags(newtags);
  };
  const handleOnKeyDown = (e: React.KeyboardEvent) => {
    if(e.key === 'Enter' || e.key === ' '){
        if(text.length > 0){
            if(tags.length < 5) SetTags([...tags, text]);
            
            setText("");
        }
        e.preventDefault();
    }
   
  };
  return (
    <Box sx={{ flexGrow: 1 }}>
      
        <TextField
            onKeyDown={handleOnKeyDown}
            value={text}
            onChange={(e)=>setText(e.target.value)}
            fullWidth
            variant='standard'
            size='small'
            sx={{ margin: "1rem 0", width:"100%"}}
            margin='none'
            
            placeholder={tags.length < 5 ? "Enter tags" : ""}
            slotProps={{
                htmlInput: {
                    maxLength: 15,
                    style:{boxSizing: "border-box"}
                },  
                input: {
                    style:{      
                        boxSizing: "border-box"
                    },
                    startAdornment: (
                        <Box sx={{ margin: "0 0.2rem 0 0", display: "flex" }}>
                        {tags.map((data, index) => {
                            return (
                            <Tags data={data} handleDelete={handleDelete} key={index} />
                            );
                        })}
                        </Box>
                    ),
                }
                
            }}
            />

        {tags.map(tag => <input hidden name="tags" value={tag}/>)}
    </Box>
  );
}