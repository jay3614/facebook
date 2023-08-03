import { Avatar, Box, Divider, Typography } from '@mui/material';
import { Cake, Email, Male } from '@mui/icons-material';
import WidgetWrapper from '../styles/WidgetWrapper';
import FlexBetween from '../styles/FlexBetween';
import { useTheme } from '@emotion/react';
import React from 'react';
import { call } from '../service/ApiService';

const LeftSidebar = ({ userId }) => {

    const { palette } = useTheme();
    const [user, setUser] = React.useState({});

    React.useEffect(() => {
        call('/auth', 'GET', null)
        .then((response) => {
            setUser(response.data5)
        })
        .catch((error) => {
            console.error('Error:', error);
          }
        );
      }, []);

    return (
        <Box flex={2} p={2} sx={{ display: { xs: 'none', sm: 'block' } }}>
            <Box position="fixed" width={350}>
                <WidgetWrapper>

                    {/* FIRST ROW */}
                    <FlexBetween gap="0.5rem" pb="1.1rem">
                        <FlexBetween gap="1rem">
                            <Avatar sx={{ width: 60, height: 60 }} src="https://i.imgur.com/J6UmAxs.png"/>
                            <Box>
                                <Typography
                                    variant="h4"
                                    fontWeight="500"
                                    sx={{
                                        '&:hover': {
                                            color: palette.primary.light,
                                            cursor: 'pointer',
                                        },
                                    }}
                                >
                                    {user.userName}
                                </Typography>
                            </Box>
                        </FlexBetween>
                    </FlexBetween>
                    <Divider />

                    {/* SECOND ROW */}
                    <Box p="1rem 0">
                        <Box
                            display="flex"
                            alignItems="center"
                            gap="1rem"
                            mb="0.5rem"
                        >
                            <Cake fontSize="large" />
                            <Typography>birthday : {user.birthday}</Typography>
                        </Box>
                        <Box display="flex" alignItems="center" gap="1rem">
                            <Male fontSize="large" />
                            <Typography>gender : {user.gender}</Typography>
                        </Box>
                    </Box>
                    <Divider />

                    {/* THIRD ROW */}
                    <Box p="1rem 0">
                        <FlexBetween mb="0.5rem">
                            <Typography>팔로워</Typography>
                            <Typography fontWeight="500">1</Typography>
                        </FlexBetween>
                        <FlexBetween>
                            <Typography>팔로잉</Typography>
                            <Typography fontWeight="500">0</Typography>
                        </FlexBetween>
                    </Box>
                    <Divider />

                    {/* FOURTH ROW */}
                    <Box p="1rem 0">
                        <FlexBetween gap="1rem" mb="0.5rem">
                            <FlexBetween gap="1rem">
                                <Email />
                                <Box>
                                    <Typography fontWeight="500">
                                    {user.email}
                                    </Typography>
                                </Box>
                            </FlexBetween>
                        </FlexBetween>
                    </Box>
                </WidgetWrapper>
            </Box>
        </Box>
    );
};

export default LeftSidebar;
