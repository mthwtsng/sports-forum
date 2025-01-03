export const checkUserAuth = async (setUser, setLoading, router) => {
    const response = await fetch('http://localhost:8080/api/me', {
        method: 'GET',
        credentials: 'include',
    });
    if (response.ok) {
        const data = await response.json();
        setUser(data);
    }
};

export const handleLogout = async (setUser, router) => {
    try {
        const response = await fetch('http://localhost:8080/api/logout', {
            method: 'POST',
            credentials: 'include',
        });

        if (response.ok) {
            setUser(null);
            router.push('/login');
        } else {
            console.error('Logout failed');
        }
    } catch (error) {
        console.error('Error during logout:', error);
    }
};
