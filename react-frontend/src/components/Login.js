import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import { useUser } from './UserContext';
import "./css/Auth.css";

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const { setUser } = useUser();
    const navigate = useNavigate(); // Initialize useNavigate

    const handleSubmit = async (e) => {
    e.preventDefault();

    try {
        const response = await fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password }),
            credentials: 'include', // Include cookies for session
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || 'Login failed');
        }

        const data = await response.json();
        console.log('Login successful:', data);
        navigate('/homepage'); // Redirect to homepage
    } catch (err) {
        setError(err.message);
    }
};


    return (
        <div className="login-container">
            <h2>Login</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Username:</label>
                    <input 
                        type="text" 
                        value={username} 
                        onChange={(e) => setUsername(e.target.value)} 
                        required 
                    />
                </div>
                <div>
                    <label>Password:</label>
                    <input 
                        type="password" 
                        value={password} 
                        onChange={(e) => setPassword(e.target.value)} 
                        required 
                    />
                </div>
                <button type="submit">Login</button>
            </form>
            <div>
                <h3>Don't have an account? <a href="/signup">Signup</a></h3>
            </div>
        </div>
    );
};

export default Login;
