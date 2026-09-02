import { useEffect, useState } from "react";
import api from "../services/api";



interface ProjectGanttProps{
    projectId: string;
}

export const GanttView: React.FC<ProjectGanttProps> = ({projectId}) => {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        api.get(`/progetti/{progettoId}/task`).then(response => {
            const formattedTasks: Task[] = response.data.map((t: any) => ({
                id: t.id.toString(),
                name: t.titolo
            }))
        })
    })
}