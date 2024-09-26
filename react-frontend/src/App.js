import React from 'react';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from './components/Login';
import Signup from './components/Signup';
import Homepage from './components/Homepage';


function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route exact path = "/" element = {<Homepage />}></Route>
          <Route exact path = "/login" element = {<Login />}></Route>
          <Route path = "/signup" element = {<Signup />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
