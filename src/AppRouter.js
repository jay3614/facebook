import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import App from "./App";
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import FindPass from "./components/FindPass";
class AppRouter extends React.Component{

    render(){
        return(
            <div>
                {/* 라우팅을 할 때 Root Element는 아래이다. */}
                <BrowserRouter>
                    <div>
                        <Routes>
                            <Route path="/" element={<App />}/>
                            <Route path="/login" element={<Login />}/>
                            <Route path="/signup" element={<SignUp />}/>
                            <Route path="/reset" element={<FindPass />}/>
                        </Routes>
                    </div>
                </BrowserRouter>
            </div>
        );
    }

}
export default AppRouter;
