import React from 'react';
import { Box, Grid, Paper, Typography } from '@mui/material';
import ClassPerformanceChart from './ClassPerformanceChart';

// Example data for chart
const chartData = [
  { className: 'A', pass: 20, fail: 5, avg: 78 },
  { className: 'B', pass: 18, fail: 7, avg: 72 },
  { className: 'C', pass: 22, fail: 3, avg: 85 },
];

export default function MuiDashboard() {
  return (
    <Box sx={{ flexGrow: 1, p: 3 }}>
      <Typography variant='h4' gutterBottom>
        Material-UI School Dashboard
      </Typography>
      <Grid container spacing={2}>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant='h6'>Class Performance</Typography>
            <ClassPerformanceChart data={chartData} />
          </Paper>
        </Grid>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant='h6'>Pass/Fail Rates</Typography>
            {/* You can add a pie chart or summary here */}
            <ul>
              {chartData.map(d => (
                <li key={d.className}>
                  {d.className}: Pass {d.pass}, Fail {d.fail}, Avg {d.avg}
                </li>
              ))}
            </ul>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
}
