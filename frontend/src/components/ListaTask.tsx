import { use, useState } from "react";
import api from "../api";

interface Task {
  id: string;
  titolo: string;
  obiettivo: string;
}

interface Props {
  idProgetto: string;
  tasks: Task[];
  onTaskCreated: () => void;
}

const ListaTask = ({ idProgetto, tasks, onTaskCreated }: Props) => {
  const [titolo, setTitolo] = useState("");
  const [obiettivo, setObiettivo] = useState("");
  const [dataInizio, setDataInizio] = useState("");
  const [dataFine, setDataFine] = useState("");

  const [showForm, setShowForm] = useState(false);

  const creaTask = () => {
    api
      .post(`/api/v1/admin/progetti/${idProgetto}/task`, {
        titolo,
        obiettivo,
        dataInizio,
        dataFine,
      })
      .then(() => {
        setTitolo("");
        setObiettivo("");
        setDataInizio("");
        setDataFine("");
        setShowForm(false);
        onTaskCreated();
      })
      .catch((err) => console.error("Errore creazione Task: ", err));
  };
};
