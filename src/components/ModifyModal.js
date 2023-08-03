import { Avatar, Button, ButtonGroup, IconButton, Modal, Stack, styled, TextField, Typography } from '@mui/material';
import React, { useState } from 'react';
import { Add as Mood, PersonAdd, PhotoCamera } from '@mui/icons-material';
import { Box } from '@mui/system';
import Picker from 'emoji-picker-react';
import { toast } from 'react-toastify';
import { call } from '../service/ApiService';

const SytledModal = styled(Modal)({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
});

const UserBox = styled(Box)({
    display: 'flex',
    alignItems: 'center',
    gap: '10px',
    marginBottom: '20px',
});




const ModifyModal = ({ open, onClose, item }) => {
    const [inputStr, setInputStr] = useState('');
    const [showPicker, setShowPicker] = useState(false);
    // const [setItems] = useState({});
    
    const onEmojiClick = (event, emojiObject) => {
        setInputStr((prevInput) => prevInput + emojiObject.emoji);
        setShowPicker(false);
    };

    const updatedItem = {
        id: item.id,
        content: inputStr,
    };

    const handleEdit =() => {
        call("/post", "PUT", updatedItem).then((response) => {
            // setItems({item: response.data})
            onClose();
            toast.success('수정완료👍')
            window.location.reload();
        })
    }

    const handleCloseReset = () => {
        setInputStr(item.content);
        onClose();
    }

    const replaceNewLine = (str) => {
        return str.replace(/(\r\n|\n|\r)/gm, "\n");
    }

    return (
            <SytledModal
                open={open}
                onClose={onClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box
                    width={400}
                    height={280}
                    bgcolor={'background.default'}
                    color={'text.primary'}
                    p={3}
                    borderRadius={5}
                >
                    <Typography variant="h6" color="gray" textAlign="center">
                        수정하기
                    </Typography>
                    <UserBox>
                        <Avatar sx={{ width: 30, height: 30 }} />
                        <Typography fontWeight={500} variant="span">
                            username
                        </Typography>
                    </UserBox>
                    <TextField
                        sx={{ width: '100%' }}
                        multiline
                        rows={3}
                        placeholder="What's on your mind?"
                        variant="standard"
                        onChange={(e) => setInputStr(replaceNewLine(e.target.value))}
                        value={inputStr || item.content}
                        inputProps={{
                            style: { whiteSpace: 'pre-line' },
                        }}
                    />
                    <Stack direction="row" gap={1} mt={2} mb={3}>
                        <IconButton
                            aria-label="upload picture"
                            component="label"
                        >
                            <input
                                hidden
                                accept="image/*"
                                multiple
                                type="file"
                            />
                            <PhotoCamera color="success" />
                        </IconButton>

                        <div>
                            <IconButton onClick={() => setShowPicker((val) => !val)}>
                                <Mood color="primary" />
                            </IconButton>
                            {showPicker && (
                                <div>
                                    <Picker
                                        pickerStyle={{ width: 300, height: 200 }}
                                        onEmojiClick={onEmojiClick}
                                    />
                                </div>
                            )}
                        </div>

                        <IconButton>
                            <PersonAdd color="error" />
                        </IconButton>
                    </Stack>

                    <ButtonGroup
                        fullWidth
                        variant="contained"
                        aria-label="outlined primary button group"
                    >
                        <Button type="submit"  onClick={handleEdit}>확인</Button>
                        <Button sx={{ width: '150px' }} onClick={handleCloseReset}>취소</Button>
                    </ButtonGroup>
                </Box>
            </SytledModal>
    );
};

export default ModifyModal;