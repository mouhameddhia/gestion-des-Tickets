import React, { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Container, Paper, Button, Select, MenuItem, InputLabel, FormControl, Snackbar } from '@mui/material';
import { styled } from '@mui/system';
import MuiAlert from '@mui/material/Alert';

// Styled component for the form box
const FormBox = styled(Box)(({ theme }) => ({
  '& > :not(style)': {
    margin: theme.spacing(1),
    width: '100%',
  },
}));

const paperStyle = { padding: '50px 20px', width: 600, margin: '20px auto' };

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

export default function Ticket() {
  const [branche, setBranche] = useState('');
  const [textarea, setTextarea] = useState('');
  const [tickets, setTickets] = useState([]);
  const [openSnackbar, setOpenSnackbar] = useState(false);

  // Fetch tickets from the backend when the component mounts
  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = () => {
    fetch("http://localhost:8080/Tickets/getAllTickets")
      .then(response => response.json())
      .then(data => {
        setTickets(data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };

  const handleClick = (e) => {
    e.preventDefault();
    const ticket = { branche, textarea };
    console.log(ticket);

    fetch("http://localhost:8080/Tickets/CreateTicketJson", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(ticket)
    })
      .then(response => response.json())
      .then(data => {
        console.log("Response data:", data);
        console.log("New ticket added");
        setOpenSnackbar(true);
        fetchTickets(); // Fetch the updated list of tickets
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };

  const handleSnackbarClose = () => {
    setOpenSnackbar(false);
  };

  return (
    <Container>
      <Paper elevation={3} style={paperStyle}>
        <h1 style={{ color: 'blue' }}><u>Add Ticket</u></h1>
        <FormBox component="form" noValidate autoComplete="off">
          <FormControl fullWidth>
            <InputLabel id="branche-label">Ticket Branche</InputLabel>
            <Select
              labelId="branche-label"
              id="branche-select"
              value={branche}
              onChange={(e) => setBranche(e.target.value)}
              label="Ticket Branche"
            >
              <MenuItem value="Assurance Automobile">Assurance Automobile</MenuItem>
              <MenuItem value="Assurance Santé">Assurance Santé</MenuItem>
              <MenuItem value="Assurance Habitation">Assurance Habitation</MenuItem>
              <MenuItem value="Assurance Vie">Assurance Vie</MenuItem>
              <MenuItem value="Assurance Voyage">Assurance Voyage</MenuItem>
              <MenuItem value="Assurance Entreprise">Assurance Entreprise</MenuItem>
            </Select>
          </FormControl>
          <TextField
            id="filled-basic"
            label="Textarea"
            variant="filled"
            fullWidth
            value={textarea}
            onChange={(e) => setTextarea(e.target.value)}
          />
          <Button variant="contained" color="secondary" onClick={handleClick}>
            Submit
          </Button>
        </FormBox>
      </Paper>
      <h1>Tickets</h1>
      <Paper elevation={3} style={paperStyle}>
        {tickets.map((ticket, index) => (
          <Paper elevation={6} style={{ margin: '10px', padding: '15px', textAlign: 'left' }} key={index}>
            Branche: {ticket.branche}<br />
            Textarea: {ticket.textarea}
          </Paper>
        ))}
      </Paper>
      <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleSnackbarClose}>
        <Alert onClose={handleSnackbarClose} severity="success">
          New ticket added successfully!
        </Alert>
      </Snackbar>
    </Container>
  );
}
