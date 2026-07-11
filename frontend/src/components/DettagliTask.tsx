import { useEffect, useState } from "react"
import api from "../services/api";


interface Props{
    idTask: string;
    onBack: () => void;
}



const DettagliTask({idTask, onBack}: Props) => {
    const[task, setTask] = useState<any>(null);

    useEffect(() => {
        api.get(`/api/v1/admin/`)
    })
}