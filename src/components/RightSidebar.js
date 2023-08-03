import { Box, IconButton } from "@mui/material";
import React, { useState } from "react";
import CloseIcon from '@mui/icons-material/Close';

const ImageBanner = () => {
  const [isVisible, setIsVisible] = useState(true);

  const handleClose = () => {
    setIsVisible(false);
  };

  return (
    <Box position="fixed" width={250} style={{ marginLeft: "8em" }}>
      {isVisible && <a href="https://ujb.greenart.co.kr" target="_blank" rel="noreferrer"><img src={process.env.PUBLIC_URL + '/banner.jpg'} alt="광고문의는 000으로" className="blink"/></a>}
      {isVisible && (<IconButton sx={{ position: "absolute", top: 0, right: 0 }} onClick={handleClose}><CloseIcon /></IconButton>)}
    </Box>
  );
};

const RightSidebar = () => {
  return (
    <Box flex={2} p={2} sx={{ display: { xs: "none", sm: "block" } }}>
      <ImageBanner />
    </Box>
  );
};

export default RightSidebar;
