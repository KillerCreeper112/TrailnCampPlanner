import {useState} from 'react'
import {BrowserRouter, Routes, Route} from "react-router-dom";
import './App.css'
import HeaderBar from './components/HeaderBar';
import SignUpPage from './pages/SignUpPage';
import LoginPage from "./pages/LoginPage.jsx";
import HomePage from "./pages/HomePage.jsx";
import DashboardPage from "./pages/DashboardPage.jsx";
import EditTripPage from "./pages/EditTripPage.jsx";

function App() {
  return (
    <BrowserRouter>
      <HeaderBar/>
      <Routes>
        <Route path="/" element={<HomePage/>}/>
        <Route path="/sign_up" element={<SignUpPage/>}/>
        <Route path="/login" element={<LoginPage/>}/>
        <Route path="/dashboard" element={<DashboardPage/>}/>
        <Route path="/trips/:id/edit" element={<EditTripPage/>}/>
      </Routes>
    </BrowserRouter>
  )
}

export default App
