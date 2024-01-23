// components/TableComponent.tsx
import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import { universalData } from '@/app/types/interfaces';
import identifier from '@mui/material/styles/identifier';

// Defining the properties that the table component will accept
interface TableProps {
  identifier: string;
  data: universalData[];
}

// Creating a styled TableCell using MUI's styled
const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: '#52525b',
    color: 'white',
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
    backgroundColor: '#f4f4f5',
    color: 'black',
  },
}));

// Creating a styled TableRow using MUI's styled
const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

// Defining the TableComponent that accepts table properties
const TableComponent: React.FC<TableProps> = ({ data, identifier }) => {
  // State to store the original data and the search term
  const [originalData, setOriginalData] = useState(data);
  const [searchTerm, setSearchTerm] = useState('');

  // Effect to update the original data when external data changes
  useEffect(() => {
    setOriginalData(data);
  }, [data]);

  // Filtering data based on the search term
  const filteredData = originalData.filter((item) => {
    return (
      item.identifier.toLowerCase().includes(searchTerm.toLowerCase()) ||
      item.id.toString().includes(searchTerm.toLowerCase())
    );
  });

  return (
    <div className="my-4">
      {/* TextField component for entering the search term */}
      <TextField
        id="standard-basic"
        label="Search by ID or Name"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className='text-black'
        size="small"
        margin="dense"
      />

      {/* TableContainer component to contain the table */}
      <TableContainer component={Paper}>
        {/*Table component */}
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
          {/* TableHead component for the table header */}
          <TableHead>
            <TableRow>
              {/* Styled header cells */}
              <StyledTableCell>ID</StyledTableCell>
              <StyledTableCell>{identifier}</StyledTableCell>
              <StyledTableCell>Latitude</StyledTableCell>
              <StyledTableCell>Longitude</StyledTableCell>
            </TableRow>
          </TableHead>
          {/* TableBody component for the table body */}
          <TableBody>
            {/* Mapping and displaying filtered data as StyledTableRow */}
            {filteredData.map((item) => (
              <StyledTableRow key={item.id}>
                {/* Body cells of the table */}
                <StyledTableCell component="th" scope="row">
                  {item.id}
                </StyledTableCell>
                <StyledTableCell>{item.identifier}</StyledTableCell>
                <StyledTableCell>{item.latitude}</StyledTableCell>
                <StyledTableCell>{item.longitude}</StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default TableComponent; // Exporting the TableComponent
