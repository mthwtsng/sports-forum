import { useEffect } from 'react';
import { useRouter } from 'next/router';
import '../styles/auth.css';
import '../styles/pages.css';


const Index = () => {
    const router = useRouter();

    useEffect(() => {
        router.push('/homepage');
    }, [router]);

    return <div>Redirecting...</div>;
};

export default Index;