import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Grid, Link, Typography } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  footer: {
    backgroundColor: '#ffffff',
    padding: theme.spacing(6, 0),
    width: 2000
  },
  logo: {
    width: 130,
  },
  footerLink: {
    margin: theme.spacing(1, 2),
  },
}));

export default function Footer() {
  const classes = useStyles();

  return (
    <footer className={classes.footer}>
      <Container maxWidth="xl">
        <Grid container spacing={4}>
          <Grid item xl={12} sm={4}>
            <Typography variant="h5" component="h2" gutterBottom>
              ReactBook
            </Typography>
            <Typography variant="body1" color="textSecondary" gutterBottom>
              Reactbook은 React 기반으로 facebook 페이지를 만들어본 프로젝트입니다.
            </Typography>
          </Grid>
          <Grid item xs={12} sm={4}>
            <Typography variant="h6" component="h3" gutterBottom>
              About
            </Typography>
            <div>
              <Link href="#" className={classes.footerLink}>
                프로젝트 소개
              </Link>
            </div>
            <div>
              <Link href="#" className={classes.footerLink}>
                커뮤니티 가입
              </Link>
            </div>
            <div>
              <Link href="#" className={classes.footerLink}>
                문의하기
              </Link>
            </div>
          </Grid>
          <Grid item xs={12} sm={4}>
            <Typography variant="h6" component="h3" gutterBottom>
              Links
            </Typography>
            <div>
              <Link href="#" className={classes.footerLink}>
                GitHub
              </Link>
            </div>
            <div>
              <Link href="#" className={classes.footerLink}>
                Twitter
              </Link>
            </div>
            <div>
              <Link href="#" className={classes.footerLink}>
                Facebook
              </Link>
            </div>
          </Grid>
        </Grid>
        <Typography variant="body2" color="textSecondary" align="center">
          {'© '}
          {new Date().getFullYear()}{' '}
          <Link color="inherit" href="https://facebook.com/">
            ReactBook
          </Link>
          {'.'}
        </Typography>
      </Container>
    </footer>
  );
}