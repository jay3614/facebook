import React from "react";
import { resetPass } from './../service/ApiService';
import { Button, Container, Grid, Paper, TextField } from '@material-ui/core';
import { createTheme } from '@material-ui/core/styles';
import Header from "./Header";
import './../FindPass.css';

export default function FindPass() {

    const customTheme = createTheme({
        palette: {
        
          customColor: {
            main: '#1877F2',
          },
        },
      });

    const handleSubmit = (event) => {
        event.preventDefault();

        const data = new FormData(event.target);
        const email = data.get("email");

        if(!email) {
            alert("이메일을 입력하세요.");
            return;
        }

        if(!email.includes("@") || !email.includes(".")) {
            alert("올바른 이메일 주소를 입력하세요.");
            return;
        }

        resetPass({
            email: email,
        }).then((response) => {
            if(response){
                alert("입력하신 이메일로 임시 패스워드가 발급 되었읍니다.");
                window.location.href="/login";
            }else{
                alert("없는 이메일 입니다.");
            }
        });
    };

    const accessToken = sessionStorage.getItem("ACCESS_TOKEN");
    if(accessToken){
        sessionStorage.removeItem("ACCESS_TOKEN");
        return (
            window.location.href="/"
        );
    }else{
        return(
            <div style={{ backgroundColor: "#F0F2F5", padding: "0 0 50px 0", maxWidth: "xs"}}>
            <>
            <Header/>
            <Container component="main" maxWidth="xs" style={{margin: "8"}}
            justifyContent="center" alignItems="center" id ="mm">
                <form noValidate  onSubmit={handleSubmit}>
                    <Paper>
                        <div className="mar">
                            <h2>비밀번호 찾기</h2>
                            <hr style={{ borderColor: '#dadde1', borderStyle: 'solid'}} />
                            <p>계정을 검색하려면 이메일 주소를 입력하세요.</p>

                            <Grid  item xs={12}>
                                <TextField
                                autoComplete="fName"
                                name="email"
                                variant="outlined"
                                required
                                fullWidth
                                id="email"
                                label="이메일을 입력하세요."
                                autoFocus
                                className="custom"
                                />
                            </Grid>
                            <hr style={{ borderColor: '#dadde1', borderStyle: 'solid'}} />
                            <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
                                <Button 
                                type="submit"
                                value="확인"
                                variant="outlined"
                                size="small"
                                style={{backgroundColor: customTheme.palette.customColor.main,color:"white",marginRight:"5px"}}>
                                    확인
                                </Button>

                                <Button 
                                type="reset"
                                value="취소"
                                variant="outlined"
                                size="small"
                                onClick={() => window.history.back()}>
                                    취소
                                </Button>
                            </div>
                        </div>
                    </Paper>
                </form>
            </Container>
            </>
            </div>
        );
    }
}
