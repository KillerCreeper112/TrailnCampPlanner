import { useEffect, useState } from "react";
import {api, ENDPOINTS} from "../api/api";
import {CreateNewTrip} from "../components/modal/CreateNewTrip.jsx";

function DashboardPage() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [tripName, setTripName] = useState("");
  const [tripDescription, setTripDescription] = useState("");
  const [tripStartDate, setTripStartDate] = useState("");

  const [trips, setTrips] = useState([]);

  useEffect(() => {
    async function loadTrips() {
      try {
        const response = await api.get(ENDPOINTS.TRIP)
        const data = await response.json();
        setTrips(data);
      } catch (err) {
        console.error("Failed to load trips", err);
      }
    }

    loadTrips();
  }, []);

  const createTrip = async () => {
    if (!tripName || !tripStartDate) return;

    try {
      const response = await api.post(ENDPOINTS.TRIP, {
        name: tripName,
        description: tripDescription,
      })

      const newTrip = await response.json();

      setTrips((prev) => [newTrip, ...prev]);

      setTripName("");
      setTripDescription("")
      setTripStartDate("");
      setIsModalOpen(false);
    } catch (err) {
      console.error("Failed to create trip", err);
    }
  };

  return (
    <div className="min-h-screen bg-[#121A17] text-[#E6E6E6] flex">

      <div className="w-64 bg-[#1E2C26] border-r border-[#2A3A33] p-6 flex flex-col">

        <h1 className="text-xl font-bold text-[#C2A878] mb-8">
          Trails N Camping
        </h1>

        <nav className="flex flex-col gap-3 text-[#A7B0AA]">

          <button className="text-left hover:text-[#C2A878]">
            Dashboard
          </button>

          <button className="text-left hover:text-[#C2A878]">
            My Trips
          </button>

          <button className="text-left hover:text-[#C2A878]">
            Trails
          </button>

        </nav>

        <div className="mt-auto">
          <button
            onClick={() => setIsModalOpen(true)}
            className="w-full bg-[#2F5D50] hover:bg-[#1E3D35] py-3 rounded-xl font-semibold transition"
          >
            + Create Trip
          </button>
        </div>

      </div>

      <div className="flex-1 p-6">

        <h2 className="text-3xl font-bold text-[#C2A878] mb-2">
          Dashboard
        </h2>

        <p className="text-[#A7B0AA] mb-8">
          Plan and manage your camping adventures
        </p>

        <div className="grid gap-4">

          {trips.map((trip) => (
            <div
              key={trip.id}
              className="bg-[#1E2C26] border border-[#2A3A33] p-5 rounded-2xl flex justify-between items-center"
            >
              <div>
                <p className="font-semibold">{trip.name}</p>
                <p className="text-sm text-[#A7B0AA]">
                  {trip.startDate ?? "No date set"}
                </p>
              </div>

              <button className="text-[#C2A878] hover:text-white text-sm">
                View
              </button>
            </div>
          ))}

        </div>
      </div>

      {isModalOpen && (
        <CreateNewTrip
          tripName={tripName}
          setTripName={setTripName}
          tripDescription={tripDescription}
          setTripDescription={setTripDescription}
          tripStartDate={tripStartDate}
          setTripStartDate={setTripStartDate}
          onCreate={createTrip}
          onClose={() => setIsModalOpen(false)}
        />
      )}

      {isModalOpen && (
        <div className="fixed inset-0 bg-black/60 flex items-center justify-center">

          <div className="bg-[#1E2C26] p-6 rounded-2xl w-full max-w-md border border-[#2A3A33]">

            <h3 className="text-xl font-bold text-[#C2A878] mb-4">
              Create New Trip
            </h3>

            <div className="flex flex-col gap-3">

              <input
                placeholder="Trip Name"
                value={tripName}
                onChange={(e) => setTripName(e.target.value)}
                className="bg-[#18231F] border border-[#2A3A33] p-3 rounded-xl text-[#E6E6E6]"
              />

              <input
                placeholder="Date (optional UI only for now)"
                value={tripStartDate}
                onChange={(e) => setTripStartDate(e.target.value)}
                className="bg-[#18231F] border border-[#2A3A33] p-3 rounded-xl text-[#E6E6E6]"
              />

              <div className="flex gap-2 mt-2">

                <button
                  onClick={createTrip}
                  className="flex-1 bg-[#2F5D50] hover:bg-[#1E3D35] py-2 rounded-xl"
                >
                  Create
                </button>

                <button
                  onClick={() => setIsModalOpen(false)}
                  className="flex-1 border border-[#2A3A33] py-2 rounded-xl text-[#A7B0AA]"
                >
                  Cancel
                </button>

              </div>

            </div>

          </div>

        </div>
      )}

    </div>
  );
}

export default DashboardPage;