import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../api/api";
import { ENDPOINTS } from "../api/api";
import {TripDatePicker} from "../api/api_trip.jsx";

function EditTripPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [loading, setLoading] = useState(true);

  const [tripName, setTripName] = useState("");
  const [tripDates, setTripDates] = useState({
    from: undefined,
    to: undefined,
  })
  const [description, setDescription] = useState("");
  const [difficulty, setDifficulty] = useState("MEDIUM");

  useEffect(() => {
    async function loadTrip() {
      try {
        const res = await api.get(`${ENDPOINTS.TRIP}/${id}`);
        const data = await res.json();

        setTripName(data.name || "");
        setTripDates(data.dates || {
          from: undefined,
          to: undefined,
        });
        setDescription(data.description || "");
        setDifficulty(data.difficulty || "MEDIUM");
      } catch (err) {
        console.error("Failed to load trip", err);
      } finally {
        setLoading(false);
      }
    }

    loadTrip();
  }, [id]);

  const saveTrip = async () => {
    try {
      const res = await api.put(`${ENDPOINTS.TRIP}/${id}`, {
        name: tripName,
        description,
        startDate: tripDates?.from
          ? tripDates.from.toISOString().split("T")[0]
          : null,

        endDate: tripDates?.to
          ? tripDates.to.toISOString().split("T")[0]
          : null,
        difficulty,
      });

      await res.json();
      navigate("/dashboard");
    } catch (err) {
      console.error("Failed to update trip", err);
    }
  };

  if (loading) {
    return (
      <div className="text-[#E6E6E6] p-6">
        Loading trip...
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#121A17] text-[#E6E6E6] flex items-center justify-center">

      <div className="bg-[#1E2C26] p-6 rounded-2xl w-full max-w-md border border-[#2A3A33]">

        <h2 className="text-xl font-bold text-[#C2A878] mb-4">
          Edit Trip
        </h2>

        <div className="flex flex-col gap-3">

          <input
            value={tripName}
            onChange={(e) => setTripName(e.target.value)}
            className="bg-[#18231F] border border-[#2A3A33] p-3 rounded-xl"
            placeholder="Trip name"
          />

          <TripDatePicker
            value={tripDates}
            onChange={setTripDates}
          />

          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className="bg-[#18231F] border border-[#2A3A33] p-3 rounded-xl"
            placeholder="Description"
          />

          <select
            value={difficulty}
            onChange={(e) => setDifficulty(e.target.value)}
            className="bg-[#18231F] border border-[#2A3A33] p-3 rounded-xl"
          >
            <option value="EASY">Easy</option>
            <option value="MEDIUM">Medium</option>
            <option value="HARD">Hard</option>
          </select>

          <div className="flex gap-2 mt-2">

            <button
              onClick={saveTrip}
              className="flex-1 bg-[#2F5D50] hover:bg-[#1E3D35] py-2 rounded-xl"
            >
              Save
            </button>

            <button
              onClick={() => navigate("/dashboard")}
              className="flex-1 border border-[#2A3A33] py-2 rounded-xl text-[#A7B0AA]"
            >
              Cancel
            </button>

          </div>

        </div>

      </div>

    </div>
  );
}

export default EditTripPage;