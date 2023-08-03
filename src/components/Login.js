import React from 'react';
import { Facebook } from '@material-ui/icons';
import { makeStyles } from '@material-ui/core/styles';
import { Grid, Paper, Typography, TextField, Button, Link as MuiLink } from '@material-ui/core';
import { signin } from '../service/ApiService';
import Footer from './Footer';
import Header from './Header';

const useStyles = makeStyles((theme) => ({
  root: {
    height: '100vh',
    backgroundColor: '#F0F2F5',
  },
  image: {
    maxWidth: '30%',
    maxHeight: '30%',
    marginRight: 300,
  },
  paper: {
    margin: theme.spacing(8, 4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  logo: {
    margin: theme.spacing(3, 0, 2),
    fontSize: 200,
    color: 'white',
  },
  form: {
    width: '100%',
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
    backgroundColor: theme.palette.primary.main,
    '&:hover': {
      backgroundColor: theme.palette.primary.dark,
    },
  },
}));

export default function Login() {
    const classes = useStyles();
  
    const handleSubmit = (event) => {
      event.preventDefault();
          
      const data = new FormData(event.target);
      const email = data.get("email");
      const password = data.get("password");

      signin({
        email: email,
        password: password
      });
    };

    const accessToken = sessionStorage.getItem("ACCESS_TOKEN");

    if(accessToken){
      sessionStorage.removeItem("ACCESS_TOKEN");
      return (
        window.location.href="/"
      );
    }else{
      return (
        <>
        <Header/>
        <Grid container component="main" className={classes.root} justifyContent="center" alignItems="center">
          <Grid item xs={false} sm={4} md={7} className={classes.image}>
            <img src={process.env.PUBLIC_URL + '/facebook.png'} alt="Logo" />
            <h1 style={{ fontSize: '2em' }}>Facebook에서 전세계에 있는 친구, 가족, 지인들과 함께 이야기를 나눠보세요.</h1>
          </Grid>
          <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
            <div className={classes.paper}>
              <Facebook className={classes.logo} />
    
              <Typography component="h1" variant="h5">
                Sign in to your account
              </Typography>
    
              <form className={classes.form} noValidate onSubmit={handleSubmit}>
                <TextField
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  id="email"
                  label="이메일 주소"
                  name="email"
                  autoComplete="email"
                  autoFocus
                />
    
                <TextField
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  name="password"
                  label="비밀번호"
                  type="password"
                  id="password"
                  autoComplete="current-password"
                />
    
                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  color="primary"
                  className={classes.submit}
                >
                  로그인
                </Button>
              </form>
    
              <Typography variant="body2" color="textSecondary" align="center">
                <MuiLink
                  href="/reset"
                  variant="body2"
                  className={classes.link}
                >
                  비밀번호를 잊으셨나요?
                </MuiLink>
              </Typography>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                className={classes.submit}
                onClick={() => { window.location.href = "/signup"; }}
              >
                새 계정 만들기
              </Button>
            </div>
          </Grid>
          <Footer />
        </Grid>
        </>
      );
    }
    
  }