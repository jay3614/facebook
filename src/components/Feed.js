import { Box, Stack, Skeleton } from "@mui/material";
import React from "react";
import Post from "./Post";
import { call } from "./../service/ApiService";
import PostForm from "./PostForm";

class Feed extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      items: [],
      users: [],
      reply: [],
    };
  }
  
  componentDidMount() {

    call("/post", "GET", null).then((response) =>
      this.setState({ items: response.response1.data, users: response.response2.data, reply: response.response3.data, loading:false }),
      );

    setTimeout(() => {
      this.setState({ loading: false });
    }, [3000]);
  }

  render() {

    const { loading, items, users, reply, myinfo } = this.state;
    
    return (
      <Box flex={4} p={{ xs: 0, md: 2 }}>
        {loading ? (
          <Stack spacing={1}>
            <Skeleton variant="text" height={100} />
            <Skeleton variant="text" height={20} />
            <Skeleton variant="text" height={20} />
            <Skeleton variant="rectangular" height={300} />
          </Stack>
        ) : (
            <>
            <a href="http://localhost:1531/index" target="_blank" rel="noreferrer">
              <img src={process.env.PUBLIC_URL + '/hero1.jpg'} style={{ width: "100%" }} alt="" />
            </a>
            <br/>
            <PostForm
              myinfo={myinfo}
            />
            {items.map((item) => {
              const matchingReplies = reply.filter((r) => r.id === item.id);
              return (
                <Post
                  key={item.id}
                  item={item}
                  user={users.find((user) => user.id === item.userId)}
                  reply={matchingReplies}
                />
              );
            })}
          </>
        )}
      </Box>
    );
  }
};

export default Feed;