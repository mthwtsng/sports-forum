import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import "../styles/pages.css"; 

const Homepage = () => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);  // Keep track of loading state
    const router = useRouter();

    // Check if there is user session activated, send to login if false
    useEffect(() => {
        const checkUserAuth = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/me', {
                    method: 'GET',
                    credentials: 'include',
                });

                if (response.ok) {
                    const data = await response.json();
                    setUser(data);  // Set the user if authenticated
                } else {
                    router.push('/login'); // Redirect to login if not authenticated
                }
            } catch (error) {
                console.error('Error checking authentication:', error);
                router.push('/login'); // Redirect to login if error occurs
            } finally {
                setLoading(false);  // Stop loading after the check is complete
            }
        };

        checkUserAuth();
    }, [router]);

    // Logout request logic
    const handleLogout = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/logout', {
                method: 'POST',
                credentials: 'include', 
            });

            if (response.ok) {
                setUser(null);  // Clear user state
                router.push('/login');
            } else {
                console.error('Logout failed');
            }
        } catch (error) {
            console.error('Error during logout:', error);
        }
    };

    // Display loading spinner or content based on the loading state
    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div id="app">
            <header className="navbar">
                <div className="navbar-left">
                    <button onClick={() => router.push('/homepage')}>Home</button>
                    <button onClick={() => router.push('/profile')}>Profile</button>
                </div>
                <div className="navbar-right">
                    {user ? (
                        <>
                            <span>Welcome, {user.username}!</span> {/* Display username */}
                            <button onClick={handleLogout}>Logout</button> {/* Logout button */}
                        </>
                    ) : (
                        <>
                            <button onClick={() => router.push('/login')}>Login</button>
                            <button onClick={() => router.push('/signup')}>Signup</button>
                        </>
                    )}
                </div>
            </header>

            <main>
                <h1>Welcome to the Homepage</h1>
            </main>
        </div>
    );
};

export default Homepage;
