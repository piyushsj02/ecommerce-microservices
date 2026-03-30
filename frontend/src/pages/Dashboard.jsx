import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axios";

function Dashboard() {
    const [user, setUser] = useState("");

    const getUser = async () => {
        const res = await api.get("users/me");
        setUser(res.data);
    };

    useEffect(() => {
        getUser();
    }, []);

    return (
        <div>
            <Navbar user={user} />

            <div className="p-6">
                <h1 className="text-2xl font-bold">Dashboard</h1>
                <p className="mt-4">Welcome: {user}</p>
            </div>
        </div>
    );
}

export default Dashboard;