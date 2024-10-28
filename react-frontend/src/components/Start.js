import React from 'react';
import { useNavigate } from 'react-router-dom';

const Start = () => {
    const navigate = useNavigate();

    const handleLogin = () => {
        navigate('/login');
    };

    const handleSignup = () => {
        navigate('/signup');
    };

    return (
        <div style={styles.container}>
            <h1 style={styles.title}>Sports Lounge</h1>
            <div style={styles.buttonContainer}>
                <button style={styles.button} onClick={handleLogin}>Login</button>
                <button style={styles.button} onClick={handleSignup}>Sign Up</button>
            </div>
        </div>
    );
};

const styles = {
    container: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        height: '100vh',
        textAlign: 'center'
    },
    title: {
        fontSize: '2.5rem',
        marginBottom: '20px'
    },
    buttonContainer: {
        display: 'flex',
        gap: '10px'
    },
    button: {
        padding: '10px 20px',
        fontSize: '1rem',
        cursor: 'pointer'
    }
};

export default Start;
