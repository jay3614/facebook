import { DeleteOutlined, EditOutlined, Favorite, FavoriteBorder, ModeCommentOutlined, MoreVert, Share } from '@mui/icons-material';
import { Avatar, Box, Button, Card, CardActions, CardContent, CardHeader, Checkbox, FormControl,
  IconButton, ListItemIcon, ListItemText, Menu, MenuItem, OutlinedInput, Typography } from '@mui/material';
import moment from 'moment/moment';
import React from 'react';
import { toast } from 'react-toastify';
import { call } from '../service/ApiService';
import ModifyModal from "./ModifyModal";
import FlexBetween from '../styles/FlexBetween';

class Post extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      item: props.item,
      user: props.user,
      reply: props.reply,
      anchorEl: null,
      share:0,
      likes: 0,
      liked: false,
      heart: {
        userId: "bb89b9cc88429627018842963b6c0000",
        postId: 3,
      },
      isMenuOpen: false,
      modalOpen: false,
      commentText: '',
      comments: [],
    };
    this.handleModalClose = this.handleModalClose.bind(this);
  }

  handleClick = (event) => {
      this.setState({ anchorEl: event.currentTarget, isMenuOpen: true });
  };

  handleClose = () => {
      this.setState({ anchorEl: null, isMenuOpen: false });
  };

  handleModalClose = () => {
      this.setState({ modalOpen: false });
  };

  handleShare = () => {
    this.setState({ share: this.state.share + 1 });
  };

  handleLike = (postId) => {
    if (this.state.liked) {
      this.setState((prevState) => ({
        likes: prevState.likes - 1,
        liked: false,
      }));
    } else {
      const postId = this.state.item.id;
      const data = {};
      call(`/post/${postId}`, 'POST', data)
          .then(() => {
              setTimeout(() => {
                window.location.reload();
              }, 1000);

          })
          .catch((error) => {
            console.error('Error:', error);
            toast.error('좋아요 오류가 발생했습니다.');
          }
        );
    }
  };

  handleDelete = () => {
    const userId = sessionStorage.getItem('userId');
    if(this.state.item.userId === userId){
      call('/post', 'DELETE', this.state.item)
        .then((response) => {
          this.setState({ items: response.data });
          this.handleClose();
          toast.success('삭제완료');
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        })
        .catch((error) => {
          console.error('Error:', error);
          toast.error('글 삭제 중 오류가 발생했습니다.');
        });
    }else{
      toast.error("작성자만 삭제할 수 있습니다.");
    }
  };

  handleEdit = () => {
    const userId = sessionStorage.getItem('userId');
    if(this.state.item.userId === userId){
      this.handleClose();
      this.setState({ modalOpen: true });
    }else{
      toast.error("작성자만 수정할 수 있습니다.");
    }
  };
  handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      const postId = this.state.item.id;
      const requestBody = { content: this.state.commentText };
      console.log(requestBody);
      call(`/reply/${postId}`, 'POST', requestBody)
        .then(() => {
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        })
        .catch((error) => {
          console.error('Error:', error);
          toast.error('댓글 업로드 중 오류가 발생했습니다.');
        }
      );
    }
  };

  handleComment = (e) => {
    this.setState({ commentText: e.target.value });
  };

  deleteReply = (rno, replyer) => {
    if(replyer === this.state.user.userName){
      call(`/reply/${rno}`, 'DELETE', null)
          .then(() => {
              toast.success("삭제되었습니다.");
              setTimeout(() => {
                window.location.reload();
              }, 1000);

          })
          .catch((error) => {
            console.error('Error:', error);
            toast.error('댓글 삭제 중 오류가 발생했습니다.');
          }
        );
    }else{
      toast.error("작성자만 삭제할 수 있습니다.");
    }
  }
  
  getRelativeTime() {
    const item = this.state.item;
    const postDate = moment(item.regDate);
    const now = moment();
    const diff = now.diff(postDate);
    const duration = moment.duration(diff);

    if (duration.asSeconds() < 60) {
      return `${Math.round(duration.asSeconds())}초 전`;
    } else if (duration.asMinutes() < 60) {
      return `${Math.round(duration.asMinutes())}분 전`;
    } else if (duration.asHours() < 24) {
      return `${Math.round(duration.asHours())}시간 전`;
    } else if (duration.asDays() < 30) {
      return `${Math.round(duration.asDays())}일 전`;
    } else if (duration.asMonths() < 12) {
      return `${Math.round(duration.asMonths())}달 전`;
    } else {
      return `${Math.round(duration.asYears())}년 전`;
    }
  }

  render() {
    const item = this.state.item;
    const user = this.state.user;
    const reply = this.state.reply;
    const relativeTime = this.getRelativeTime();
    const { commentText } = this.state;
    const { anchorEl, likes, liked, isMenuOpen, modalOpen } = this.state;
      
    return (
      <Card style={{marginBottom: "2.5em"}}>
        <CardHeader
          avatar={
            <Avatar
              aria-label="recipe"
              src="https://i.imgur.com/J6UmAxs.png"
            />
          }
          action={
            <>
              <IconButton
                aria-label="settings"
                id="settings"
                aria-controls={
                  isMenuOpen ? 'long-menu' : undefined
                }
                aria-expanded={isMenuOpen ? 'true' : undefined}
                aria-haspopup="true"
                onClick={this.handleClick}
              >
                <MoreVert />
              </IconButton>
              <Menu
                MenuListProps={{
                  'aria-labelledby': 'settings',
                }}
                anchorEl={anchorEl}
                open={isMenuOpen}
                onClose={this.handleClose}
                anchorOrigin={{
                  vertical: 'top',
                  horizontal: 'left',
                }}
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'left',
                }}
              >
                <MenuItem onClick={this.handleDelete}>
                  <ListItemIcon>
                    <DeleteOutlined fontSize="small" />
                  </ListItemIcon>
                  <ListItemText>삭제하기</ListItemText>
                </MenuItem>
                <MenuItem onClick={this.handleEdit}>
                  <ListItemIcon>
                    <EditOutlined fontSize="small" />
                  </ListItemIcon>
                  <ListItemText>수정하기</ListItemText>
                </MenuItem>
              </Menu>
              <ModifyModal
                open={modalOpen}
                onClose={this.handleModalClose}
                item={this.state.item}
              />
            </>
          }
          title={user.userName}
          subheader={`${relativeTime}에 게시됨`}
        />
        <CardContent>
          <Typography
            variant="body2"
            color="text.secondary"
            id="4"
          >
            {item.content.split('\n').map((line, index) => (
    <React.Fragment key={index}>
      {line}
      <br />
    </React.Fragment>
  ))}
          </Typography>
        </CardContent>
            
        {/* 좋아요,댓글,공유 아이콘 */}
        <CardActions
          disableSpacing
          sx={{ justifyContent: 'space-between' }}
        >
          <IconButton
            aria-label="add to favorites"
            onClick={this.handleLike}
          >
            <Checkbox
              icon={<FavoriteBorder />}
              checkedIcon={<Favorite sx={{ color: 'red' }} />}
              checked={liked}
            />
            {likes > 0 && <span>{likes}</span>}
          </IconButton>

          <Box sx={{ display: 'flex', alignItems: 'center', gap: 7 }}>
            <IconButton aria-label="comment">
              <ModeCommentOutlined />
            </IconButton>

            <IconButton
              aria-label="share"
              style={{ marginRight: 28 }}
             onClick={this.handleShare}
            >
              <Share />
              {this.state.share > 0 && <span>{this.state.share}</span>}
            </IconButton>
          </Box>
        </CardActions>

        {/* 댓글창 */}
        <Box
          component="form"
          noValidate
          autoComplete="off"
          sx={{
            display: 'flex',
            alignItems: 'center',
            gap: 2,
            marginBottom: 3,
            marginRight: 3,
          }}
        >
          <Box>
            <Avatar style={{ marginLeft: 15, marginBottom: 20 }} />
          </Box>
          <FormControl
            sx={{
              width: '100%',
            }}
          >
            <OutlinedInput
              placeholder="Please enter Comment"
              value={commentText}
              onChange={this.handleComment}
              onKeyDown={this.handleKeyDown}
            />
          </FormControl>
        </Box>

        <Box sx={{ mt: 2 }}>
          {reply.map((r) => (
            <Box
              key={r.rno}
                sx={{
                marginTop: '0.5rem',
                display: 'flex',
                gap: '0.5rem',
                alignItems: 'center',
              }}
            >
              <Avatar />
              <Box
                sx={{
                  backgroundColor: '#f5f5f5',
                  bgcolor: 'background.default',
                  paddingTop: '0.5rem',
                  paddingBottom: '0.5rem',
                  paddingLeft: '1rem',
                  paddingRight: '1rem',
                  borderRadius: '1.5rem',
                  width: '100%'
                }}
              >
                <FlexBetween>
                  <Typography
                    sx={{
                      textDecoration: 'underline',
                      fontWeight: 600,
                      marginRight: '1.25rem',
                    }}
                  >
                    {r.replyer}
                  </Typography>
                  <Typography
                    sx={{
                      fontSize: '0.875rem',
                      lineHeight: '1.25rem',
                      color: '#9e9e9e',
                    }}
                  >
                    {r.regDate}
                  </Typography>
                  <Button variant='outlined'
                    sx={{
                      marginLeft: 'auto'
                    }}
                    onClick={() => this.deleteReply(r.rno, r.replyer)}
                  >
                    삭제
                  </Button>
                </FlexBetween>
                <Typography
                  style={{
                    fontSize: '0.875rem',
                    lineHeight: '1.25rem',
                    whiteSpace: 'pre-wrap'
                  }}
                >
                  {r.content}
                </Typography>
              </Box>
            </Box>
          ))}
        </Box>
      </Card>
    );
  }
}

export default Post;
