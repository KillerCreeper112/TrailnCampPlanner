import { useState } from 'react'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css'
import HeaderBar from './components/HeaderBar';
import SignUpPage from './pages/SignUpPage';

function App() {
  return (
    <BrowserRouter>
      <HeaderBar />

      <Routes>
        <Route path="/sign_up" element={<SignUpPage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
