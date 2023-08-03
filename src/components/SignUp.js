import { Button, Container, Grid, Link, TextField, FormControl,
    Box, FormControlLabel, Radio,FormLabel,RadioGroup, makeStyles} from "@material-ui/core";
import React from "react";
import { signUp } from "./../service/ApiService";
import Header from "./Header";
import { createTheme } from '@material-ui/core/styles';
import { purple, green } from '@material-ui/core/colors';

const useStyles = makeStyles((theme) => ({
    formBox: {
      boxShadow: '0px 0px 20px rgba(0, 0, 0, 0.2)',
      borderRadius: theme.spacing(1),
      padding: theme.spacing(3),
      backgroundColor: theme.palette.background.paper
    }
  }));

  const customTheme = createTheme({
    palette: {
      primary: {
        main: purple[500],
      },
      secondary: {
        main: green[500],
      },
      customColor: {
        main: '#1877F2',
      },
    },
  });

export default function Up() {

    const classes = useStyles();

    const handleSubmit = (event) => {
        event.preventDefault();

        const data = new FormData(event.target);
        const userName = data.get("userName");
        const email = data.get("email");
        const password = data.get("password");
        const birthday = data.get("birthday");
        const gender = data.get("gender");

        if (!email) {
            alert("이메일 주소를 입력해주세요.");
            return;
        }
        
        if (!email.includes("@") || !email.includes(".")) {
            alert("이메일 주소는 example@example.com 형식으로 입력해주세요.");
            return;
        }
        
        if(password.length <4){
            alert("비밀번호는 4자리 이상이어야 합니다.");
            return;
        }

        const emptyFields = [];
        if (!userName) {
          emptyFields.push("사용자 이름");
        }
        if (!email) {
          emptyFields.push("이메일");
        }
        if (!password) {
          emptyFields.push("비밀번호");
        }
        if (!birthday) {
          emptyFields.push("생일");
        }
        if (!gender) {
          emptyFields.push("성별");
        }
      
        if (emptyFields.length > 0) {
          const message = `다음 필드를 입력해주세요: ${emptyFields.join(", ")}`;
          alert(message);
          return;
        }

        const selectedDate = new Date(birthday);
        const today = new Date();
        if(selectedDate > today){
            alert("불가능한 생일입니다.")
            return;
        }

        signUp({
            email: email,
            userName: userName,
            password: password,
            birthday: birthday,
            gender: gender
        }).then((response) => {
            window.location.href="/login";
        });
    }

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
                <Container component="main" maxWidth="xs" style={{margin: "8",borderBlockColor:"black"}}
                    justifyContent="center" alignItems="center" id ="mm">
                
                <Box className={classes.formBox}>
                    <form noValidate  onSubmit={handleSubmit} id="mainform" >
                        <div id="form" style={{fontSize : "30px",fontWeight:"700"}}>
                            <p>가입하기</p>
                        </div>
                        <div id="form">
                            <p>빠르고 쉽게 가입할 수 있습니다.</p>
                        </div>
                        <hr style={{ borderColor: '#dadde1', borderStyle: 'solid'}} />
                        <Grid  item xs={12} style={{ paddingBottom: "8px" ,marginBottom: "5px" }}>
                            <TextField
                                autoComplete="fName"
                                name="userName"
                                variant="outlined"
                                required
                                fullWidth
                                id="userName"
                                label="사용자이름"
                                autoFocus
                                className="custom"
                                />
                        </Grid>

                        <Grid item xs={12} style={{ paddingBottom: "8px" ,marginBottom: "5px" }}>
                            <TextField
                                autoComplete="email"
                                name="email"
                                variant="outlined"
                                required
                                fullWidth
                                id="email"
                                label="이메일주소"
                                autoFocus
                                className="custom"
                                />
                        </Grid>

                        <Grid item xs={12} style={{ paddingBottom: "25px",marginBottom: "5px" }}>
                            <TextField
                                name="password"
                                type="password"
                                variant="outlined"
                                required
                                fullWidth
                                id="password"
                                label="새 비밀번호"
                                autoComplete="current-password"
                                className="custom"
                                />
                        </Grid>

                        <Grid item xs={12} style={{ paddingBottom: "8px" }}>
                            <FormControl>
                                <FormLabel id="demo-row-radio-buttons-group-label">성별</FormLabel>
                                <RadioGroup
                                    row
                                    aria-labelledby="demo-row-radio-buttons-group-label"
                                    name="gender"
                                    >
                                    <FormControlLabel value="여성" control={<Radio />} label="여성" />
                                    <FormControlLabel value="남성" control={<Radio />} label="남성" />
                                </RadioGroup>
                            </FormControl>
                        </Grid>

                        <Grid container spacing={3} style={{ paddingBottom: "20px" }}>
                            <Grid item xs={12}>
                                <FormLabel id="demo-row-radio-buttons-group-label">생일</FormLabel>
                            
                                    <TextField
                                        name="birthday"
                                        type="date"
                                        variant="outlined"
                                        required
                                        fullWidth
                                        id="password"
                                        autoComplete="birthday"
                                        className="custom"
                                        />
                            </Grid>
                        </Grid>

                        <Grid item xs={12}>
                            <Button
                            variant="contained"
                            fullWidth
                            type="submit"
                            justifyContent = "flex-end"
                            style={{backgroundColor: customTheme.palette.customColor.main, color: 'white',fontWeight:'bold',height:'45px',fontSize:'15px'}}>
                                계정 생성
                            </Button>
                        </Grid>
                            
                    {/* 이미 계정이 있는 경우 로그인 페이지로 링크 */}
                    <Grid container style={{ paddingBottom: "30px" , paddingTop:"10px"}} justify="center">
                            <Grid item>
                                <Link href="/login" variant="body2">
                                    계정이 이미 있다면 여기를 클릭
                                </Link>
                            </Grid>
                        </Grid>

                    </form>
                    </Box>
                </Container>
                </>
            </div>
        );
    }
    
}
