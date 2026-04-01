import { Link } from "react-router-dom";

function Navbar({ user }) {

  const logout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <div className="bg-blue-600 text-white p-4 flex justify-between">

      <div className="flex gap-6 items-center">
        <h1 className="text-lg font-bold">E-Commerce App</h1>
        <Link to="/products">Products</Link>
        <Link to="/orders" className="hover:underline">My Orders</Link>
        <Link to="/cart" className="hover:underline">Cart</Link>
      </div>
      <div className="flex gap-4 items-center">
        <span>{user}</span>

        <button
          className="bg-red-500 px-3 py-1 rounded"
          onClick={logout}
        >
          Logout
        </button>
      </div>

    </div>
  );
}

export default Navbar;