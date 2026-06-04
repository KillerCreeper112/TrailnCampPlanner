import { Link } from "react-router-dom";
import {useAuth} from "@/components/auth/AuthContext.jsx";

function HeaderBar(){
  const { isAuth, login, logout } = useAuth();

  return (
    <nav className="flex items-center justify-between px-6 py-4 bg-slate-900 text-white shadow-md">
      <Link
      to="/"
      className="text-xl tracking-wide"
      >
        Trails n' Camping!
      </Link>

      <div className="flex items-center gap-6">
        <Link className="hover:text-green-400 transition" to="/">
          Home
        </Link>
        <Link className="hover:text-green-400 transition" to="/dashboard">
          Dashboard
        </Link>

        {isAuth ? (
          <>
            <button
              className="hover:text-red-400 transition"
              onClick={() => {
                logout();
                window.location.href = "/login";
              }}
            >
              Sign Out
            </button>
          </>
        ) : (
          <>
            <Link className="hover:text-green-400 transition" to="/login">
              Login
            </Link>
            <Link className="hover:text-green-400 transition" to="/sign_up">
              Sign Up
            </Link>
          </>
        )}
      </div>
    </nav>
  )
}
export default HeaderBar