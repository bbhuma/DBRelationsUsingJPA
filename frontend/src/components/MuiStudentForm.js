import React from 'react';
import { Box, Button, TextField, Typography, Paper } from '@mui/material';

export default function MuiStudentForm({ onSubmit }) {
  const [form, setForm] = React.useState({
    name: '',
    className: '',
    marks: '',
  });
  const handleChange = e =>
    setForm({ ...form, [e.target.name]: e.target.value });
  const handleSubmit = e => {
    e.preventDefault();
    if (onSubmit) onSubmit(form);
    setForm({ name: '', className: '', marks: '' });
  };
  return (
    <Paper elevation={3} sx={{ p: 3, maxWidth: 400, mt: 4, mx: 'auto' }}>
      <Typography variant='h5' gutterBottom>
        Register Student (Material-UI)
      </Typography>
      <Box component='form' onSubmit={handleSubmit}>
        <TextField
          label='Name'
          name='name'
          value={form.name}
          onChange={handleChange}
          fullWidth
          margin='normal'
          required
        />
        <TextField
          label='Class'
          name='className'
          value={form.className}
          onChange={handleChange}
          fullWidth
          margin='normal'
          required
        />
        <TextField
          label='Marks'
          name='marks'
          value={form.marks}
          onChange={handleChange}
          type='number'
          fullWidth
          margin='normal'
          required
        />
        <Button type='submit' variant='contained' sx={{ mt: 2 }}>
          Register
        </Button>
      </Box>
    </Paper>
  );
}
