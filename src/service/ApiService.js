import { API_BASE_URL } from "../app-config/app-config";
import 'react-toastify/dist/ReactToastify.css';

export async function call(api, method, request){

    let headers = new Headers({
        "Content-type": "application/json",
    });

    const accessToken = sessionStorage.getItem("ACCESS_TOKEN");

    if(accessToken && accessToken !== null){
        headers.append("Authorization", "Bearer " + accessToken);
    }

    let options = {
        headers: headers,
        url: API_BASE_URL + api,
        method: method,
    };

    if(request){
        options.body=JSON.stringify(request);
    }

    return fetch(options.url, options)
        .then((response) => {
            if(!response.ok){
                return Promise.reject(response);
            }
            return response.json();
        }
    );
}

export async function signin(userDTO){
    return call("/auth/signin", "POST", userDTO)
        .then((response) => {
            if(response.token){
                sessionStorage.setItem("ACCESS_TOKEN", response.token);
                sessionStorage.setItem("userId", response.id);
                window.location.href="/";
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('아이디 또는 비밀번호를 다시 확인하세요.');
        }
    );
}

export async function signout(){
    sessionStorage.setItem("ACCESS_TOKEN", null);
    sessionStorage.setItem("userId", null);
    window.location.href="/login";
}

export async function signUp(userDTO){
    return call("/auth/signup", "POST", userDTO);
}

export async function resetPass(userDTO) {
    return call("/auth/reset", "POST", userDTO);
}