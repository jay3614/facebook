let backEndHost;

const hostname = window&&window.location&&window.location.hostname;

if(hostname === 'localhost'){
    backEndHost = "http://localhost:8080";
}

export const API_BASE_URL = `${backEndHost}`;  // = 백틱