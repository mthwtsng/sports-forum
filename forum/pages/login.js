import React, { useState } from 'react';
import { useRouter } from 'next/router'; 
import "../styles/auth.css"; 
import "../styles/pages.css"; 

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const router = useRouter(); 

    // Checks session if user logged in, sends to homepage if true, else go to login page 
    const checkUserAuth = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/me', {
                method: 'GET',
                credentials: 'include',
            });
    
            if (response.ok) {
                router.push('/homepage'); 
            }
        } catch (error) {
            console.error('Error checking authentication:', error);

        }
    };
    

    React.useEffect(() => {
        checkUserAuth();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
    
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
                <h3>Don't have an account? <a href="/signup">Sign up</a></h3>
            </div>
        </div>
    );
};

export default Login;
