import React from 'react';
import { Container, Form, Button } from 'react-bootstrap';

export default function BootstrapStudentForm({ onSubmit }) {
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
    <Container style={{ maxWidth: 400, marginTop: 32 }}>
      <h4>Register Student (Bootstrap)</h4>
      <Form onSubmit={handleSubmit}>
        <Form.Group className='mb-3'>
          <Form.Label>Name</Form.Label>
          <Form.Control
            name='name'
            value={form.name}
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group className='mb-3'>
          <Form.Label>Class</Form.Label>
          <Form.Control
            name='className'
            value={form.className}
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group className='mb-3'>
          <Form.Label>Marks</Form.Label>
          <Form.Control
            name='marks'
            value={form.marks}
            onChange={handleChange}
            type='number'
            required
          />
        </Form.Group>
        <Button variant='primary' type='submit'>
          Register
        </Button>
      </Form>
    </Container>
  );
}
