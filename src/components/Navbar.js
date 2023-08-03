import { DarkMode, Facebook, LightMode } from "@mui/icons-material";
import { AppBar, Avatar, Box, IconButton, Menu, MenuItem, styled, Toolbar, Typography } from "@mui/material";
import React from "react";
import { signout } from "../service/ApiService";
import '../Navbar.css';

const StyledToolbar = styled(Toolbar)({
  display: "flex",
  justifyContent: "space-between",
});

const StyledAppbar = styled(AppBar)(({ theme }) => ({
  backgroundColor: theme.palette.background.default,
}));

const Icons = styled(Box)(({ theme }) => ({
  display: "none",
  alignItems: "center",
  gap: "20px",
  [theme.breakpoints.up("sm")]: {
    display: "flex",
  },
}));

const UserBox = styled(Box)(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  gap: "10px",
  [theme.breakpoints.up("sm")]: {
    display: "none",
  },
}));

class Navbar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
      item: {
        title : ""
      },
    };
    this.handleOpen = this.handleOpen.bind(this);
    this.handleClose = this.handleClose.bind(this);
  }

  toggleMode = () => {
    const { mode, setMode } = this.props;
    const newMode = mode === 'light' ? 'dark' : 'light';
    setMode(newMode);
    sessionStorage.setItem("mode", newMode);
  };

  handleOpen() {
    this.setState({ open: true });
  }

  handleClose() {
    this.setState({ open: false });
  }

  oninputChange = (e) => {
    const thisItem = this.state.item;
    thisItem.title = e.target.value;
    this.setState({item: thisItem});
    console.log(thisItem);
  }

  render() {
    const {mode } = this.props;
    return (
      <StyledAppbar position="sticky">
        <StyledToolbar>
          <Typography
            variant="h4"
            sx={{ display: { xs: "none", sm: "block" } }}
          >
            <a href="/">
              <img src={process.env.PUBLIC_URL + '/facebook.png'} alt="Facebook logo" style={{width:"130px"}}/>
            </a>
          </Typography>
          <Facebook
            sx={{ display: { xs: "block", sm: "none" } }}
          />

          <p className = "pstyle" style={{color:"#1877F2",fontSize:"20px",fontWeight:"bold"}}>FashionBook</p>
          <Icons>
            <IconButton onClick={this.toggleMode}>
              {mode === 'light' ? <LightMode /> : <DarkMode />}
            </IconButton>
            <Avatar
              sx={{ width: 50, height: 50 }}
              src="https://i.imgur.com/J6UmAxs.png"
              onClick={this.handleOpen}
            />
          </Icons>
          <UserBox onClick={this.handleOpen}>
            <Avatar
              sx={{ width: 30, height: 30 }}
              src="https://i.imgur.com/J6UmAxs.png"
            />
            <Typography variant="span">John</Typography>
          </UserBox>
        </StyledToolbar>
        <Menu
          id="demo-positioned-menu"
          aria-labelledby="demo-positioned-button"
          open={this.state.open}
          onClose={() => this.handleClose()}
          anchorOrigin={{
            vertical: "top",
            horizontal: "right",
          }}
          transformOrigin={{
            vertical: "top",
            horizontal: "right",
          }}
        >
          <MenuItem onClick={signout}>Logout</MenuItem>
        </Menu>
      </StyledAppbar>
    );
  }
}

export default Navbar;