// Homepage.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useUser } from './UserContext'; // Import useUser
import './css/pages.css';

const Homepage = () => {
    const navigate = useNavigate();
    const { user } = useUser(); // Get user from context

    const handleNavigation = (path) => {
        navigate(`/${path}`);
    };

    return (
        <div id="app">
            <header className="navbar">
                <div className="navbar-left">
                    <button onClick={() => handleNavigation('home')}>Home</button>
                    <button onClick={() => handleNavigation('services')}>Services</button>
                    <button onClick={() => handleNavigation('about')}>About</button>
                    <button onClick={() => handleNavigation('contact')}>Contact</button>
                </div>
                <div className="navbar-right">
                    {user ? (
                        <span>Welcome, {user.name}!</span>  // Display username
                    ) : (
                        <>
                            <button onClick={() => handleNavigation('login')}>Login</button>
                            <button onClick={() => handleNavigation('signup')}>Signup</button>
                        </>
                    )}
                </div>
            </header>

            <main>
                <h1>Welcome to the Home Page!</h1>
            </main>
        </div>
    );
};

export default Homepage;
