import { Link } from "react-router-dom";
import {useAuth} from "@/components/auth/AuthContext.jsx";

function HomePage() {
  const { isAuth } = useAuth();
  return (
    <div className="min-h-screen bg-[#121A17] text-[#E6E6E6]">

      <section className="text-center px-6 py-20 flex flex-col items-center">
        <h2 className="text-5xl font-bold text-[#C2A878] mb-4">
          Plan Your Next Adventure
        </h2>

        <p className="text-[#A7B0AA] max-w-xl mx-auto mb-8">
          Organize camping trips, track trails, and plan outdoor experiences all in one place.
        </p>

        <div className="flex justify-center gap-4">
          { isAuth ? (
            <Link
              to="/dashboard"
              className="bg-[#2F5D50] hover:bg-[#1E3D35] px-6 py-3 rounded-xl font-semibold transition"
            >
              Get Started
            </Link>
          ) : (<>
            <Link
              to="/sign_up"
              className="bg-[#2F5D50] hover:bg-[#1E3D35] px-6 py-3 rounded-xl font-semibold transition"
            >
              Get Started
            </Link>

            <Link
              to="/login"
              className="border border-[#2A3A33] px-6 py-3 rounded-xl hover:border-[#C2A878] transition"
            >
              Login
            </Link>
            </>)
          }
        </div>
      </section>

      <section className="px-6 pb-20 grid md:grid-cols-3 gap-6 max-w-5xl mx-auto">

        <div className="bg-[#1E2C26] border border-[#2A3A33] p-6 rounded-2xl">
          <h3 className="text-[#C2A878] text-xl font-semibold mb-2">
            Plan Trips
          </h3>
          <p className="text-[#A7B0AA] text-sm">
            Create and organize camping trips with dates, locations, and notes.
          </p>
        </div>

        <div className="bg-[#1E2C26] border border-[#2A3A33] p-6 rounded-2xl">
          <h3 className="text-[#C2A878] text-xl font-semibold mb-2">
            Track Trails
          </h3>
          <p className="text-[#A7B0AA] text-sm">
            Save and manage your favorite hiking trails and routes.
          </p>
        </div>

        <div className="bg-[#1E2C26] border border-[#2A3A33] p-6 rounded-2xl">
          <h3 className="text-[#C2A878] text-xl font-semibold mb-2">
            Stay Organized
          </h3>
          <p className="text-[#A7B0AA] text-sm">
            Keep all your outdoor plans in one clean dashboard.
          </p>
        </div>

      </section>

      <footer className="text-center text-[#6B7A73] text-sm pb-10">
        Built for outdoor explorers 🌲
      </footer>

    </div>
  );
}

export default HomePage;