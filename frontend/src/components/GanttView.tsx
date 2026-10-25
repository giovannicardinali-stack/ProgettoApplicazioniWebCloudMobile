import { useEffect, useState } from "react";
import api from "../services/api";
import { Gantt, Task, ViewMode } from "gantt-task-react";
// @ts-ignore
import "gantt-task-react/dist/index.css";

interface ProjectGanttProps {
  projectId: string;
}

export const GanttView: React.FC<ProjectGanttProps> = ({ projectId }) => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<boolean>(false);

  useEffect(() => {
    if (!projectId) {
      setLoading(false);
      return;
    }

    setLoading(true);
    setError(false);

    const ruolo = localStorage.getItem("ruolo");
    const endpoint =
      ruolo === "ADMIN"
        ? `/api/v1/admin/progetti/${projectId}/tasks`
        : `/api/v1/dipendente/progetti/${projectId}/tasks`;

    api
      .get(endpoint)
      .then((response) => {
        const raw = Array.isArray(response.data) ? response.data : [];
        const formattedTasks: Task[] = [];

        for (const task of raw) {
          if (!task) continue;

          const stato = task.statoTask;
          if(stato === "ARCHIVIATO"){
            continue;
          }

          const startStr = task.dataInizio || task.data_inizio;
          const endStr = task.dataFine || task.data_fine;

          if (!startStr || !endStr) continue;

          const startDate = new Date(startStr);
          let endDate = new Date(endStr);

          if (isNaN(startDate.getTime()) || isNaN(endDate.getTime())) {
            continue;
          }

          if (endDate <= startDate) {
            endDate = new Date(startDate.getTime() + 60 * 60 * 1000);
          }

          // colori dinamici basati sugli stati esatti dell'enum
          let barColor = "#ffc107"; // Giallo (NONINIZIATO)
          if (stato === "TERMINATO") {
            barColor = "#198754"; // Verde (Terminato e Approvato)
          } else if (stato === "INCORSO") {
            barColor = "#0d6efd"; // Blu (In corso)
          } else if (stato === "INRITARDO") {
            barColor = "#dc3545"; // Rosso (In ritardo)
          }

          formattedTasks.push({
            id: task.id ? task.id.toString() : Math.random().toString(),
            name: task.titolo || task.nome || "Task senza titolo",
            start: startDate,
            end: endDate,
            type: "task",
            progress: 0,
            isDisabled: true,
            styles: {
              backgroundColor: barColor,
              backgroundSelectedColor: barColor,
            },
          });
        }

        setTasks(formattedTasks);
      })
      .catch((err) => {
        console.error("Errore recupero task Gantt:", err);
        setError(true);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [projectId]);

  if (loading) {
    return <div className="p-3 text-muted">Caricamento diagramma...</div>;
  }

  if (error) {
    return (
      <div className="p-3 text-danger border rounded bg-light">
        Errore durante il caricamento del diagramma di Gantt.
      </div>
    );
  }

  if (!tasks || tasks.length === 0) {
    return (
      <div className="p-3 text-muted border rounded bg-light text-center">
        <i className="bi bi-calendar-x me-2"></i>
        Nessuna task pianificata per questo progetto.
      </div>
    );
  }

  return (
    <div style={{ width: "100%", overflow: "hidden" }}>
      <h4 className="mb-3">Cronologia Progetto</h4>

      {/* Finestra di scorrimento interna */}
      <div
        style={{
          width: "100%",
          overflowX: "auto",
          border: "1px solid #dee2e6",
          borderRadius: "0.375rem",
        }}
      >
        <div style={{ minWidth: "1000px" }}>
          <Gantt
            tasks={tasks}
            viewMode={ViewMode.Day}
            locale="it"
            ganttHeight={300}
            columnWidth={60}
            listCellWidth="110px"
          />
        </div>
      </div>
    </div>
  );
};
