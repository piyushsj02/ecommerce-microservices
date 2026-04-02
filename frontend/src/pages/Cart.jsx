import { useEffect, useState } from "react";
import api from "../api/axios";

function Cart() {
  const [cart, setCart] = useState([]);

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = async () => {
    try {
      const res = await api.get("/cart");
      setCart(res.data);
    } catch (err) {
      console.error("Error fetching cart", err);
    }
  };

  // ✅ Remove
  const removeItem = async (productId) => {
    try {
      await api.delete(`/cart/product/${productId}`);
      fetchCart();
    } catch (err) {
      console.error("Error removing item", err);
    }
  };

  // ✅ Update Quantity
  const updateQty = async (productId, qty) => {
    try {
      if (qty <= 0) {
        await removeItem(productId);
        return;
      }

      await api.put(
        `/cart/update?productId=${productId}&quantity=${qty}`
      );

      fetchCart();
    } catch (err) {
      console.error("Error updating quantity", err);
    }
  };

  // ✅ Total Price
  const total = cart.reduce((sum, item) => {
    const price = item.price || 0;
    return sum + price * item.quantity;
  }, 0);

  // ✅ Place Order
  const placeOrder = async () => {
  try {
    await api.post("/orders/place"); // ✅ correct endpoint
    alert("Order placed successfully ✅");
    fetchCart();
  } catch (err) {
    console.error("Order failed", err);
  }
};

  return (
    <div className="p-6 max-w-3xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">My Cart</h2>

      {cart.length === 0 ? (
        <p className="text-gray-500">Cart is empty</p>
      ) : (
        <>
          {cart.map((item) => (
            <div
              key={item.id}
              className="border p-4 mb-3 rounded shadow flex justify-between items-center"
            >
              {/* LEFT */}
              <div>
                <h3 className="font-semibold text-lg">
                  {item.productName || "Product"}
                </h3>

                <p className="text-gray-600">
                  ₹{item.price || 0}
                </p>

                {/* Quantity */}
                <div className="flex items-center gap-3 mt-2">
                  <button
                    className="bg-gray-300 px-3 py-1 rounded"
                    onClick={() =>
                      updateQty(item.productId, item.quantity - 1)
                    }
                  >
                    -
                  </button>

                  <span className="font-semibold">
                    {item.quantity}
                  </span>

                  <button
                    className="bg-gray-300 px-3 py-1 rounded"
                    onClick={() =>
                      updateQty(item.productId, item.quantity + 1)
                    }
                  >
                    +
                  </button>
                </div>
              </div>

              {/* RIGHT */}
              <button
                className="bg-red-500 text-white px-3 py-1 rounded"
                onClick={() => removeItem(item.productId)}
              >
                Remove
              </button>
            </div>
          ))}

          {/* TOTAL */}
          <div className="mt-6 text-xl font-bold">
            Total: ₹{total}
          </div>

          {/* PLACE ORDER */}
          <button
            className="bg-green-600 text-white px-4 py-2 mt-4 rounded w-full"
            onClick={placeOrder}
          >
            Place Order
          </button>
        </>
      )}
    </div>
  );
}

export default Cart;