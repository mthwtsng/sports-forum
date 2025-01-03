import React, { useState } from 'react';
import { useRouter } from 'next/router';
import SignupForm from '../components/SignupForm';
import '../styles/auth.css'; // Ensure this is imported correctly
import '../styles/pages.css'; // Optional, if you still want to keep other global styles

const Signup = () => {
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const router = useRouter();

    const handleSubmit = async ({ username, password, email }) => {
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
        <div className="signup-page">
            <SignupForm 
                onSubmit={handleSubmit} 
                error={error} 
                successMessage={successMessage} 
            />
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