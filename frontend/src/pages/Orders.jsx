import { useEffect, useState } from "react";
import api from "../api/axios";

function Orders() {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        try {
            const res = await api.get("/orders");
            setOrders(res.data);
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <div className="p-6">
            <h2 className="text-2xl font-bold mb-4">My Orders</h2>

            <div className="grid gap-4">
                {orders.map((order) => (
                    <div
                        key={order.id}
                        className="bg-white p-4 rounded-xl shadow-md border"
                    >
                        <p className="font-bold text-lg">{order.productName}</p>
                        <p>Price: ₹{order.price}</p>
                        <p>Quantity: {order.quantity}</p>
                        <p className="text-sm text-gray-500">
                            {order.orderDate}
                        </p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Orders;