import React, {useState} from "react";
import axios from "axios";


interface LoginProps{
    onLoginSuccess: (ruolo: string) => void;
}

const LoginForm = () => {

    const [username, setUsername] = useState<String>("");
    const [password, setPassword] = useState<String>("");

    return(
    <div className="container mt-5">
        <div className="row justify-content-center">
            <div className="col-md-4">
                <div className="card shadow p-4">
                    <h2 className="text-center mb-4"> Login </h2>
                </div>
            </div>
        </div>
    </div>
)
}


export default LoginForm;