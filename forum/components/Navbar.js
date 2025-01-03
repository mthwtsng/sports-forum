import React from 'react';
import { useRouter } from 'next/router';

const Navbar = ({ user, onLogout }) => {
    const router = useRouter();

    return (
        <header className="navbar">
            <div className="navbar-left">
                <button onClick={() => router.push('/homepage')}>Home</button>
                <button onClick={() => router.push('/profile')}>Profile</button>
            </div>
            <div className="navbar-right">
                {user ? (
                    <>
                        <span>Welcome, {user.username}!</span>
                        <button onClick={onLogout}>Logout</button>
                    </>
                ) : (
                    <>
                        <button onClick={() => router.push('/login')}>Login</button>
                        <button onClick={() => router.push('/signup')}>Signup</button>
                    </>
                )}
            </div>
        </header>
    );
};

export default Navbar;
