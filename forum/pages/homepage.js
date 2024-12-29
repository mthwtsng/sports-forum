import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/router';

const Homepage = () => {
    const [user, setUser] = useState(null);
    const router = useRouter();

    useEffect(() => {
        // Check if the user is logged in by hitting a backend endpoint
        const checkUserAuth = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/me', {
                    method: 'GET',
                    credentials: 'include', // Include cookies for session
                });

                if (response.ok) {
                    const userData = await response.json();
                    setUser(userData); // Set the user data
                } else {
                    router.push('/login'); // Redirect to login if not authenticated
                }
            } catch (error) {
                console.error('Error checking authentication:', error);
            }
        };

        checkUserAuth();
    }, []);

    return (
        <div>
            {user ? (
                <h1>Welcome, {user.name}!</h1>
            ) : (
                <h1>Loading...</h1>
            )}
        </div>
    );
};

export default Homepage;

