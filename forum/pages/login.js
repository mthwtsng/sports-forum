// pages/login.js
import React, { useState } from 'react';
import { useRouter } from 'next/router';
import LoginForm from '../components/LoginForm';
import '../styles/auth.css';
import '../styles/pages.css';


const Login = () => {
    const [error, setError] = useState(null);
    const router = useRouter();

    const handleSubmit = async ({ username, password }) => {
        try {
            const response = await fetch('http://localhost:8080/api/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }),
                credentials: 'include',
            });

            if (!response.ok) {
                const errorText = await response.text(); 
                throw new Error(errorText || 'Login failed');
            }
            
            router.push('/homepage');
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div className="login-page">
            <LoginForm onSubmit={handleSubmit} error={error} />
        </div>
    );
};

export default Login;