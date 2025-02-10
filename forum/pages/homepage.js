import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import Navbar from '../components/Navbar';
import { checkUserAuth, handleLogout } from '../utils/auth';
import "../styles/pages.css";

const Homepage = () => {
    const [user, setUser] = useState(null);
    const [latestPost, setLatestPost] = useState(null);
    const [isPopupOpen, setIsPopupOpen] = useState(false);

    const router = useRouter();

    useEffect(() => {
        checkUserAuth(setUser, router);
        fetchLatestPost();
    }, [router]);

    const fetchLatestPost = async () => {
        try {
            const response = await fetch('http://localhost:8080/posts/latest');
            if (response.ok) {
                const data = await response.json();
                setLatestPost(data);
            } else {
                console.error('Failed to fetch latest post');
            }
        } catch (error) {
            console.error('Error fetching latest post:', error);
        }
    };

    const handleCreatePost = async (post) => {
        try {
            const response = await fetch('http://localhost:8080/posts', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(post),
            });
            if (response.ok) {
                fetchLatestPost();
                setIsPopupOpen(false); 
            } else {
                console.error('Failed to create post');
            }
        } catch (error) {
            console.error('Error creating post:', error);
        }
    };

    return (
        <div id="app">
            <Navbar user={user} onLogout={() => handleLogout(setUser, router)} />
            <main>
                <button onClick={() => setIsPopupOpen(true)}>New Post</button>
                {isPopupOpen && (
                    <PostPopup
                        onClose={() => setIsPopupOpen(false)}
                        onSubmit={handleCreatePost}
                    />
                )}
                {latestPost && (
                    <div className="latest-post">
                        <h2>{latestPost.title}</h2>
                        <p>{latestPost.content}</p>
                        <p>By {latestPost.author}</p>
                    </div>
                )}
            </main>
        </div>
    );
};

const PostPopup = ({ onClose, onSubmit }) => {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({ title, content });
    };

    return (
        <div className="popup">
            <div className="popup-content">
                <h2>Create a New Post</h2>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label>Title:</label>
                        <input
                            type="text"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label>Content:</label>
                        <textarea
                            value={content}
                            onChange={(e) => setContent(e.target.value)}
                            required
                        ></textarea>
                    </div>
                    <button type="submit">Submit</button>
                    <button type="button" onClick={onClose}>Close</button>
                </form>
            </div>
        </div>
    );
};

export default Homepage;
