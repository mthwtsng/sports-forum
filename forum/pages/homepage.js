import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import Navbar from '../components/Navbar';
import { checkUserAuth, handleLogout } from '../utils/auth';
import "../styles/pages.css";

const Homepage = () => {
    const [user, setUser] = useState(null);
    const [posts, setPosts] = useState([]);
    const [isPopupOpen, setIsPopupOpen] = useState(false);

    const router = useRouter();

    useEffect(() => {
        checkUserAuth(setUser, router);
        fetchPosts();
    }, [router]);

    const fetchPosts = async () => {
        try {
            const response = await fetch('http://localhost:8080/posts');
            if (response.ok) {
                const data = await response.json();
                setPosts(data);
            } else {
                console.error('Failed to fetch posts');
            }
        } catch (error) {
            console.error('Error fetching posts:', error);
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
                fetchPosts(); 
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
                <div className="posts-container">
                    {posts.length > 0 ? (
                        posts.map(post => (
                            <div key={post.id} className="post">
                                <h2>{post.title}</h2>
                                <p>{post.content}</p>
                                <p>By {post.author}</p>
                            </div>
                        ))
                    ) : (
                        <p>No posts available</p>
                    )}
                </div>
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
