import { useEffect, useState } from "react";
import api from "../api/axios";
import { jwtDecode } from "jwt-decode";

function Products() {
    const [products, setProducts] = useState([]);
    const [name, setName] = useState("");
    const [price, setPrice] = useState("");
    const [description, setDescription] = useState("");

    const token = localStorage.getItem("token");

    let role = "";

    if (token) {
        const decoded = jwtDecode(token);
        role = decoded.role; // 🔥 comes from backend
    }

    const getProducts = async () => {
        const res = await api.get("/products");
        setProducts(res.data);
    };

    const addProduct = async () => {
        await api.post("/products", {
            name,
            price,
            description,
        });

        getProducts(); // refresh list
    };

    const deleteProduct = async (id) => {
        await api.delete(`/products/${id}`);
        getProducts();
    };

    useEffect(() => {
        getProducts();
    }, []);

    return (
        <div className="p-6">

            <h1 className="text-2xl font-bold mb-4">Products</h1>

            {/* Add Product Form */}
            {role === "ADMIN" && (
                <div className="mb-6">
                    <input className="border p-2 mr-2" placeholder="Name"
                        onChange={(e) => setName(e.target.value)} />

                    <input className="border p-2 mr-2" placeholder="Price"
                        onChange={(e) => setPrice(e.target.value)} />

                    <input className="border p-2 mr-2" placeholder="Description"
                        onChange={(e) => setDescription(e.target.value)} />

                    <button
                        className="bg-green-500 text-white px-4 py-2"
                        onClick={addProduct}
                    >
                        Add
                    </button>
                </div>
            )}

            {/* Product List */}
            {products.map((p) => (
                <div
                    key={p.id}
                    className="border p-4 mb-2 flex justify-between"
                >
                    <div>
                        <h2 className="font-bold">{p.name}</h2>
                        <p>₹{p.price}</p>
                        <p>{p.description}</p>
                    </div>

                    <button
                        className="bg-red-500 text-white px-3"
                        onClick={() => deleteProduct(p.id)}
                    >
                        Delete
                    </button>
                </div>
            ))}

        </div>
    );
}

export default Products;