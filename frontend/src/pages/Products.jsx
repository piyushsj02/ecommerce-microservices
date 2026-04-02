import { useEffect, useState } from "react";
import api from "../api/axios";
import { jwtDecode } from "jwt-decode";

import { useNavigate } from "react-router-dom";


function Products() {
    const [products, setProducts] = useState([]);
    const [cartMap, setCartMap] = useState({}); // 🔥 important

    const [name, setName] = useState("");
    const [price, setPrice] = useState("");
    const [description, setDescription] = useState("");

    const token = localStorage.getItem("token");

    const navigate = useNavigate();

    let role = "";

    if (token) {
        const decoded = jwtDecode(token);
        role = decoded.role;
    }

    // ✅ Fetch products
    const getProducts = async () => {
        const res = await api.get("/products");
        setProducts(res.data);
    };

    // ✅ Fetch cart (VERY IMPORTANT)
    const getCart = async () => {
        try {
            const res = await api.get("/cart");

            const map = {};
            res.data.forEach((item) => {
                map[item.productId] = item.quantity;
            });

            setCartMap(map);
        } catch (err) {
            console.log("Cart fetch error");
        }
    };

    useEffect(() => {
        getProducts();
        getCart(); // 🔥 sync cart
    }, []);

    // ✅ Add product (admin)
    const addProduct = async () => {
        await api.post("/products", {
            name,
            price,
            description,
        });

        getProducts();
    };

    const deleteProduct = async (id) => {
        await api.delete(`/products/${id}`);
        getProducts();
    };

    // ✅ Add to cart
    const addToCart = async (productId) => {
        try {
            await api.post(`/cart/${productId}`);
            getCart(); // 🔥 refresh
        } catch (err) {
            alert("Error adding to cart");
        }
    };

    // ✅ Update qty
    const updateQty = async (productId, qty) => {
        try {
            if (qty === 0) {
                await api.delete(`/cart/product/${productId}`);
            } else {
                await api.put(
                    `/cart/update?productId=${productId}&quantity=${qty}`
                );
            }

            getCart(); // 🔥 refresh
        } catch (err) {
            console.log("Qty error");
        }
    };

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Products</h1>

            {/* ADMIN */}
            {role === "ADMIN" && (
                <div className="mb-6">
                    <input
                        className="border p-2 mr-2"
                        placeholder="Name"
                        onChange={(e) => setName(e.target.value)}
                    />
                    <input
                        className="border p-2 mr-2"
                        placeholder="Price"
                        onChange={(e) => setPrice(e.target.value)}
                    />
                    <input
                        className="border p-2 mr-2"
                        placeholder="Description"
                        onChange={(e) => setDescription(e.target.value)}
                    />
                    <button
                        className="bg-green-500 text-white px-4 py-2"
                        onClick={addProduct}
                    >
                        Add
                    </button>
                </div>
            )}

            {/* PRODUCTS */}
            {products.map((p) => (
                <div
                    key={p.id}
                    className="border p-4 mb-3 flex justify-between items-center"
                >
                    <div>
                        <h2 className="font-bold">{p.name}</h2>
                        <p>₹{p.price}</p>
                        <p>{p.description}</p>
                    </div>

                    {/* ADMIN DELETE */}
                    {role === "ADMIN" && (
                        <button
                            className="bg-red-500 text-white px-3"
                            onClick={() => deleteProduct(p.id)}
                        >
                            Delete
                        </button>
                    )}

                    {/* 🔥 CART LOGIC */}
                    <div>
                        {!cartMap[p.id] ? (
                            // 👉 Show Add to Cart
                            <button
                                className="bg-yellow-500 text-white px-3 py-1 rounded"
                                onClick={() => addToCart(p.id)}
                            >
                                Add to Cart
                            </button>
                        ) : (
                            // 👉 Show Quantity Controls
                            <div className="flex items-center gap-2">
                                <button
                                    className="bg-gray-300 px-3 py-1 rounded"
                                    onClick={() =>
                                        updateQty(p.id, cartMap[p.id] - 1)
                                    }
                                >
                                    -
                                </button>

                                <span>{cartMap[p.id]}</span>

                                <button
                                    className="bg-gray-300 px-3 py-1 rounded"
                                    onClick={() =>
                                        updateQty(p.id, cartMap[p.id] + 1)
                                    }
                                >
                                    +
                                </button>
                            </div>
                        )}


                    </div>
                </div>
            ))}

            {/* Go to cart */}
            <button
                className="bg-blue-500 text-white px-3 py-1 rounded mt-2 block"
                onClick={() => navigate("/cart")}
            >
                Go to Cart
            </button>
        </div>
    );
}

export default Products;