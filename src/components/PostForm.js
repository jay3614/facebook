import { Mood, People, PhotoCamera, Send } from '@mui/icons-material';
import { Avatar, Button, Card, IconButton, InputBase, Stack } from '@mui/material';
import React from 'react';
import Picker from 'emoji-picker-react';
import 'react-toastify/dist/ReactToastify.css';
import FlexBetween from '../styles/FlexBetween';
import { call } from '../service/ApiService';
import { toast } from 'react-toastify';

class PostForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            inputStr: '',
            showPicker: false,
            items: [],
            item: {
                title: '',
                content: '',
                author: '',
                imageUrl: '',
            },
            myinfo: [],
        };
        this.onEmojiClick = this.onEmojiClick.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handlePickerClick = this.handlePickerClick.bind(this);
    }

    componentDidMount() {

        call("/auth", "GET", null).then((response) =>
          this.setState({ myinfo: response.data5, loading:false }),
          );
    
        setTimeout(() => {
          this.setState({ loading: false });
        });
    }

    onEmojiClick(event, emojiObject) {
        this.setState((prevState) => ({
            inputStr: prevState.inputStr + emojiObject.emoji,
            showPicker: false,
        }));
    }

    handleInputChange(e) {
        this.setState({
            inputStr: e.target.value,
        });
    }

    handlePickerClick() {
        this.setState((prevState) => ({
            showPicker: !prevState.showPicker,
        }));
    }

    handleSave = (e) => {
        e.preventDefault();
        const { item } = this.state;
        item.content = document.getElementById('content').value;

        call('/post', 'POST', item)
            .then((response) => {
            this.setState({ items: response.data });
            toast.success('글 작성 완료👍');
            setTimeout(() => {
                window.location.reload();
            }, 1000);
        })
        .catch((error) => {
            console.error('Error:', error);
            toast.error('글 작성 중 오류가 발생했습니다.');
        });
    };

    render() {
        const { inputStr, showPicker, myinfo } = this.state;

        return (
            <Card style={{marginBottom: "2.5em"}}>
                <form onSubmit={this.handleSave}>
                    <FlexBetween gap="1.5rem">
                        <Avatar
                            style={{ marginLeft: 25, overflow: 'hidden' }}
                        />
                        <InputBase
                            multiline
                            name="content"
                            id="content"
                            placeholder={`${myinfo.userName}님, 무슨 생각을 하고 계신가요?`}
                            value={inputStr}
                            onChange={this.handleInputChange}
                            sx={{
                                width: '100%',
                                borderRadius: '2rem',
                                padding: '1.3rem 2rem',
                            }}
                        />
                    </FlexBetween>
                    <Stack
                        direction="row"
                        alignItems="center"
                        spacing={2}
                        justifyContent="space-between"
                    >
                        <IconButton aria-label="upload picture" component="label">
                            <input hidden accept="image/*" multiple type="file" />
                            <PhotoCamera />
                        </IconButton>

                        <IconButton>
                            <People />
                        </IconButton>

                        <IconButton onClick={this.handlePickerClick}>
                            <Mood />
                        </IconButton>

                        <Button
                            type="submit"
                            variant="contained"
                            endIcon={<Send />}
                            style={{ marginLeft: 'auto', marginRight: 5 }}
                        >
                            Send
                        </Button>

                        {showPicker && (
                            <Picker
                                pickerStyle={{ width: 300, height: 200 }}
                                onEmojiClick={this.onEmojiClick}
                            />
                        )}
                    </Stack>
                </form>
            </Card>
        );
    }
}

export default PostForm;