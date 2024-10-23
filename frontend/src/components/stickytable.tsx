'use client'
import * as React from 'react';
import Box from "@mui/material/Box";
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import { CompareSharp } from '@mui/icons-material';

export interface Column {
    id: string;
    label: string;
    minWidth?: number;
    align?: 'right';
    format?: (value: number) => string;
}

function StickyTable(
    props: { 
      columns: Column[], 
      rows: any[], 
      editButtons?: Boolean, 
      addRow?: () => void;
      editRow?: (id: number) => void;
      clickRow?: (id: number) => void;
  }) {
  
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
      };
    
    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    return (
        <Paper sx={{ width: '100%', overflow: 'hidden' }}>
          <TableContainer 
            sx={{ 
              maxHeight: 440, 
              '&::-webkit-scrollbar': {
                width: '8px', 
                height: '8px', 
              },
              '&::-webkit-scrollbar-thumb': {
                backgroundColor: '#888', 
                borderRadius: '4px', 
              },
              '&::-webkit-scrollbar-thumb:hover': {
                backgroundColor: '#555', 
              },
              '&::-webkit-scrollbar-track': {
                backgroundColor: 'base',
              },
            }}>
            <Table stickyHeader aria-label="sticky table">
              <TableHead>
                <TableRow>
                  {props.columns.map((column) => (
                    <TableCell
                      key={column.id}
                      align={column.align}
                      style={{ minWidth: column.minWidth }}
                    >
                      {column.label}
                    </TableCell>
                  ))}
                  {props.editButtons && <TableCell>Edit</TableCell>}
                </TableRow>
              </TableHead>
              <TableBody>
                {props.rows
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((row) => {
                    return (
                      <TableRow 
                        hover 
                        role="checkbox" 
                        tabIndex={-1} 
                        key={row.code} 
                        sx={{cursor: props.clickRow !== undefined ? 'pointer' : 'default'}}
                        onClick={(e)=>{ if(props.clickRow != undefined) props.clickRow(row.id) }
                        }>
                        {props.columns.map((column) => {
                          const value = row[column.id];
                          return (
                            <TableCell key={column.id} align={column.align}>
                              {column.format && typeof value === 'number'
                                ? column.format(value)
                                : value}
                            </TableCell>
                          );
                        })}
                        {props.editButtons && (
                          <TableCell>
                            <Box key={props.columns.length + 1} sx={{ display: 'flex', gap: 1 }}>
                              <Button variant="contained" onClick={() => props.editRow && props.editRow(row.id)}>
                                Edit
                              </Button>
                            </Box>
                          </TableCell>
                        )}
                      </TableRow>
                    );
                  })}
              </TableBody>
            </Table>
          </TableContainer>
            <Box display="flex" flexDirection="row" sx={{width: "100%", justifyContent: "space-between", alignItems: "center", margin: 1}}>
            {props.editButtons && (
                <Button 
                  variant="contained"  
                  onClick={props.addRow}>
                add question
            </Button>
            )}
              <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={props.rows.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
              />
            </Box>
          
        </Paper>
      );
}

export default StickyTable;
