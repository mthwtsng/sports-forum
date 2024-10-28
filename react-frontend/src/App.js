import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import Signup from './components/Signup';
import Homepage from './components/Homepage';
import Start from './components/Start';
import { UserProvider } from './components/UserContext'; // Import UserProvider

function App() {
    return (
        <UserProvider>
            <Router>
                <Routes>
                    <Route exact path="/" element={<Start />} />
                    <Route exact path="/login" element={<Login />} />
                    <Route path="/signup" element={<Signup />} />
                    <Route path="/homepage" element={<Homepage />} />
                </Routes>
            </Router>
        </UserProvider>
    );
}

export default App;
