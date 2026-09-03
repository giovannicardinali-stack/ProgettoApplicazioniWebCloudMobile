import { useEffect, useState } from "react";
import api from "../services/api";

import { Gantt, Task } from "gantt-task-react";
// @ts-ignore
import "gantt-task-react/dist/index.css";



interface ProjectGanttProps{
    projectId: string;
}

export const GanttView: React.FC<ProjectGanttProps> = ({projectId}) => {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {

        const ruolo = localStorage.getItem("ruolo");
        const endpoint = ruolo === "ADMIN" 
            ? `/api/v1/admin/progetti/${projectId}/task` 
            : `/api/v1/dipendente/progetti/${projectId}/task`;

        api.get(endpoint).then(response => {
            const formattedTasks: Task[] = response.data.map((t: any) => ({
                id: t.id.toString(),
                name: t.titolo,
                start: new Date(t.dataInizio),
                end: new Date(t.dataFine),
                type: 'task',
                progress: 50,
                isDisabled: true,
            }));
            setTasks(formattedTasks);
            setLoading(false);
        })
        .catch(err => {
                console.error("Errore nel caricamento del gantt", err);
                setLoading(false);
        });
    }, [projectId]);

    if (loading) return <div>Caricamento diagramma in corso...</div>;
    if (tasks.length === 0) return <div>Nessuna task pianificata per questo progetto.</div>;

    return (
        <div style={{ padding: '20px', background: '#fff', borderRadius: '8px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)' }}>
            <h2>Cronologia Progetto</h2>
            <Gantt
                tasks={tasks}
                viewMode="Day"
                locale="it"
            />
        </div>
    );
}