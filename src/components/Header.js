import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { TextField, Button, Link } from '@material-ui/core';
import { signin } from '../service/ApiService';


const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  title: {
    flexGrow: 1,
    display: 'none',
    [theme.breakpoints.up('sm')]: {
      display: 'block',
    },
  },
  appBar: {
    backgroundColor: 'white',
    position: 'relative',
  },
  textField: {
    width: '130x',
    marginRight: theme.spacing(2),
  },
  button: {
    fontSize: '100%',
    backgroundColor: '#1877F2',
    marginRight:'10px',
    color:'white',
    height:'40px',
    
  },
  link: {
    fontSize: '100%',
    color: '#1877F2',
    backgroundColor:'white',
    fontWeight:"2",

  },
}));

export default function Header() {
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

  return (
    <div className={classes.root}>
      <AppBar position="static" className={classes.appBar}
      style={{marginBottom:"60px"}}>

        <Toolbar>
          <Typography className={classes.title} variant="h6" noWrap>
            <a href='/login'>
            <img src="https://static.xx.fbcdn.net/rsrc.php/y8/r/dF5SId3UHWd.svg" alt="Facebook logo"
            style={{width:"130px"}}/>
            </a>
          </Typography>
          <div>
            <form className={classes.form} noValidate onSubmit={handleSubmit}>
              <TextField
              InputLabelProps={{ shrink: true }}
              name="email"
              variant="outlined"
              size="small"
              placeholder="아이디"
              className={classes.textField}
              />
              <TextField
              InputLabelProps={{ shrink: true }}
              type='password'
              name="password"
              variant="outlined"
              size="small"
              placeholder="비밀번호"
              className={classes.textField}
              />
              <Button size="small" type="submit"  className={classes.button}>
                로그인
              </Button>
              <Link href="/reset" underline="none">
                {'계정을 잊으셨나요?'}
              </Link>
            </form>
         
          </div>
        </Toolbar>
      </AppBar>
    </div>
  );
}
