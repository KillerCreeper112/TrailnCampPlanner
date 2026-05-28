import { Link } from "react-router-dom";

function HeaderBar(){
  return (
    <nav className="flex items-center justify-between px-6 py-4 bg-slate-900 text-white shadow-md">
      <h2 className="text-xl font-bold tracking-wide">
        Trails N Camping!
      </h2>

      <div className="flex items-center gap-6">
        <Link className="hover:text-green-400 transition" to="/">
          Home
        </Link>
        <Link className="hover:text-green-400 transition" to="/my_trips">
          My Trips
        </Link>
        <Link className="hover:text-green-400 transition" to="/login">
          Login
        </Link>
        <Link className="hover:text-green-400 transition" to="/sign_up">
          Sign Up
        </Link>
      </div>
    </nav>
  )
}
export default HeaderBar