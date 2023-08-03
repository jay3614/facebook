import LeftSidebar from "./components/LeftSidebar";
import RightSidebar from "./components/RightSidebar";
import Feed from "./components/Feed";
import { Box, createTheme, Stack, ThemeProvider } from "@mui/material";
import Navbar from "./components/Navbar";
import { useState } from "react";
import Header from "./components/Header";
import { ToastContainer } from "react-toastify";
import './App.css';
import { useNavigate } from 'react-router-dom';

function App() {

  const navigate = useNavigate();

  const [mode, setMode] = useState(() => {
    const modeValue = sessionStorage.getItem("mode");
    return modeValue ? modeValue : "light";
  });

  const token = sessionStorage.getItem("ACCESS_TOKEN");

  const darkTheme = createTheme({
    palette: {
      mode: mode,
    },
  });

  if(token === null || !token){
    setTimeout(() => {
      navigate("/login");
    }, 300);
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', flexDirection: 'column' }}>
        <img src={process.env.PUBLIC_URL + '/loading.gif'} alt="로딩중" />
        <Box sx={{ mt: 2 }}>
          Loading...
        </Box>
      </Box>
    );
  }else{
    return (
      <ThemeProvider theme={darkTheme}>
        <Box bgcolor={"background.default"} color={"text.primary"} sx={{ height: 1300 }}>
        <ToastContainer
          hideProgressBar
          closeButton={false}
          autoClose={600}
          limit={1}
        />
          {token ? <Navbar setMode={setMode} mode={mode}/> : <Header />}
          <Stack direction="row" spacing={2} justifyContent="space-between">
          <LeftSidebar/>
            <Feed />
            <RightSidebar/>
          </Stack>
        </Box>
      </ThemeProvider>
    );
  }
}

export default App;