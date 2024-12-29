// pages/signup.js
import React, { useState } from 'react';
import { useRouter } from 'next/router'; 
import '../styles/auth.css'; 

const Signup = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [email, setEmail] = useState('');
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const router = useRouter(); 

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        try {
            const response = await fetch('http://localhost:8080/api/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password, email }),
                credentials: 'include',
            });
    
            if (!response.ok) {
                const errorData = await response.json().catch(() => ({})); 
                throw new Error(errorData.message || 'Signup failed');
            }
    
            const data = await response.json().catch(() => ({}));
            setSuccessMessage(data.message || 'Signup successful!');
            setError(null);
        } catch (err) {
            setError(err.message);
            setSuccessMessage('');
        }
    };
    

    return (
        <div className="login-container">
            <h2>Signup</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>} {}
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
                    <label>Email:</label>
                    <input 
                        type="email" 
                        value={email} 
                        onChange={(e) => setEmail(e.target.value)} 
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
                <div>
                    <label>Confirm Password:</label>
                    <input 
                        type="password" 
                        value={confirmPassword} 
                        required 
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                </div>
                <button type="submit">Signup</button>
            </form>

            {}
            {successMessage && (
                <div>
                    <h3>Already have an account? 
                        <button onClick={() => router.push('/login')}>Login</button>
                    </h3>
                </div>
            )}
        </div>
    );
};

export default Signup;
