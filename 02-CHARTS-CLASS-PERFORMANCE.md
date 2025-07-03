# 02. Charts & Analytics: Class Performance and Pass/Fail Rates

## What Was Added

- Chart.js-based bar and pie charts for:
  - Class performance (average marks per class)
  - Pass/fail rates (school-wide and per class)
- Example data and integration points for real backend data
- Chart component: `ClassPerformanceChart.js`

## How to Use

- Import and use `ClassPerformanceChart` in any dashboard (admin, teacher, etc.)
- Pass data as an array of objects, e.g.:
  ```js
  [
    { className: 'A', pass: 20, fail: 5, avg: 78 },
    { className: 'B', pass: 18, fail: 7, avg: 72 },
    ...
  ]
  ```
- The chart will display pass/fail bars and can be extended for pie/line charts.

## Next Steps

- Connect to real backend endpoints for live data
- Add charts to admin and teacher dashboards

---

**See `frontend/src/components/ClassPerformanceChart.js` for the implementation.**
