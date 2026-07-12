import React, {createContext, useContext, useState } from "react";

//gestione dell'autenticazione

interface AuthContextType{
    isAuthenticated: boolean;
    login: () => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode}) => {

    //Verifichiamo se l'utente è loggato guardando se esiste il ruolo nel localStorage
    const[isAuthenticated, setIsAuthenticated] = useState<boolean>(!!localStorage.getItem("ruolo")); 

    const login = () => {
        setIsAuthenticated(true);
    };

    const logout = () => {
        localStorage.removeItem("ruolo");
        localStorage.removeItem("nomeUtenteLoggato");
        setIsAuthenticated(false);
    };

    return(
        <AuthContext.Provider value ={{isAuthenticated, login, logout}}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth deve essere usato dentro un AuthProvider");
    }
    return context;
};