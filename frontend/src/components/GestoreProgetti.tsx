import { useState } from "react"


interface Progetto{
    id: string;
    nome: string;
}

const GestoreProgetti = () => {
    const [progetti, setProgetti] = useState<Progetto[]>([]);
    const [loading, setLoading] = useState(true);
}