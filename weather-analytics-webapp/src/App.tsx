import React from 'react';
import logo from './logo.svg';
import './App.css';
import WeatherDataTable from './components/WeatherDataTable';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <WeatherDataTable/>
      </header>
    </div>
  );
}

export default App;
