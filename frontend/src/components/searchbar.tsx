"use client"

import React, { FC } from "react";
import { Box } from "@mui/material";
import IconButton from "@mui/material/IconButton";
import SearchIcon from "@mui/icons-material/Search";
import TextField from "@mui/material/TextField";
import Select from "@/components/select"

import {useState} from "react"
import { useRouter } from "next/navigation";

interface SearchBarProps {
  query?: string,
  type?: string
}

const SearchBar: FC<SearchBarProps> = (props) => {
  const [searchQuery, setSearchQuery] = useState(props.query ?? "");
  const [searchType, setSearchType] = useState(props.type ?? "name");
  const router = useRouter()

  return (
  <Box display="flex" gap={1}>
    <Box sx={{width:"150px"}}><Select key="type-select" label="Select type" options={["tag","name"]} val={searchType} setValue={setSearchType}></Select></Box>

    <TextField
        value={searchQuery}
        key="search-bar"
        id="search-bar"
        className="text"
        autoComplete="off"
        onChange={(e) => setSearchQuery(e.target.value)}
        label="Quiz Search 🔎"
        variant="outlined"
        placeholder="what are you looking for..."
        sx = {{
            width: "100%",
            maxWidth: "sm",
            margin: "0px",

            '& .MuiOutlinedInput-root': {
              '& fieldset': {
                borderWidth: "2px", // Adjust border thickness
              },
              '&:hover fieldset': {
                borderWidth: "3px", // Adjust border thickness on hover
              },
              '&.Mui-focused fieldset': {
                borderWidth: "3px", // Adjust border thickness when focused
              },
            },
        }}
    />
    <IconButton aria-label="search" 
        onClick={()=>{
            if (searchQuery == "") return
            router.push("/search?type="+searchType+"&query="+searchQuery)
          }}>
        <SearchIcon style={{ fill: 'white' }} />
    </IconButton>
  </Box>);
}

export default SearchBar;
