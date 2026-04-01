import { useEffect, useState } from "react";
import api from "../api/axios";

function Cart() {
  const [cart, setCart] = useState([]);

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = async () => {
    const res = await api.get("/cart");
    setCart(res.data);
  };

  const removeItem = async (id) => {
    await api.delete(`/cart/${id}`);
    fetchCart();
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">My Cart</h2>

      {cart.map((item) => (
        <div
          key={item.id}
          className="border p-4 mb-2 rounded"
        >
          <p>Product ID: {item.productId}</p>
          <p>Quantity: {item.quantity}</p>

          <button
            className="bg-red-500 text-white px-2 py-1 mt-2 rounded"
            onClick={() => removeItem(item.id)}
          >
            Remove
          </button>
        </div>
      ))}
    </div>
  );
}

export default Cart;