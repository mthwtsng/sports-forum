import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import Navbar from '../components/Navbar';
import { checkUserAuth, handleLogout } from '../utils/auth';
import "../styles/pages.css";

const Homepage = () => {
    const [user, setUser] = useState(null);
    const router = useRouter();

    useEffect(() => {
        checkUserAuth(setUser, router);
    }, [router]);


    return (
        <div id="app">
            <Navbar user={user} onLogout={() => handleLogout(setUser, router)} />
            <main>
                <h1>Welcome to the Homepage</h1>
            </main>
        </div>
    );
};

export default Homepage;
