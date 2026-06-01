import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../api/api";
import { ENDPOINTS } from "../api/api";
import {TripDatePicker} from "../api/api_trip.jsx";
import TripMap from "@/components/ui/TripMap.jsx";

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
  const [routes, setRoutes] = useState([]);
  const [activeRouteId, setActiveRouteId] = useState(null);
  const [editorMode, setEditorMode] = useState(false);
  const [contextMenu, setContextMenu] = useState(null);

  const createRoute = async () => {
    setContextMenu(null)
    const response = await api.post(`${ENDPOINTS.TRIP}/${id}/routes`, {})
    const newRoute = await response.json()
    setRoutes(prev => [...prev, newRoute]);
    setActiveRouteId(newRoute.id);
    setEditorMode(true);
  };

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
        setRoutes(data.routes || []);
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
        difficulty
      });

      await res.json();
      navigate("/dashboard");
    } catch (err) {
      console.error("Failed to update trip", err);
    }
  };

  const handleMapClick = async (event) => {
    setContextMenu(null)
    if (!activeRouteId) return;

    const lat = event.latLng.lat();
    const lng = event.latLng.lng();

    setRoutes(prev =>
      prev.map(async route => {
        if (route.id !== activeRouteId) return route;

        const response = await api.post(`${ENDPOINTS.ROUTE}/${route.id}`, {
          latitude: lat,
          longitude: lng,
          orderIndex: route.points.length
        });

        const newPoint = await response.json();

        return {
          ...route,
          points: [...route.points, newPoint],
        };
      })
    );
  };

  const handleMarkerDragEnd = (routeId, pointId, event) => {
    const lat = event.latLng.lat();
    const lng = event.latLng.lng();

    setRoutes(prev =>
      prev.map(async route => {
        if (route.id !== routeId) return route;

        const response = await api.put(`${ENDPOINTS.ROUTE_POINT}/${route.id}`, {
          latitude: lat,
          longitude: lng
        })

        const newPoint = await response.json();

        return {
          ...route,
          points: route.points.map(p =>
            p.id === pointId
              ? newPoint
              : p
          ),
        };
      })
    );
  };

  const handleDeletePoint = (routeId, pointId) => {
    setRoutes(prev =>
      prev.map(async route => {
        if (route.id !== routeId) return route;

        const response = await api.delete(`${ENDPOINTS.ROUTE}/${route.id}`);
        const result = await response.json()

        return {
          ...route,
          points: route.points.filter(p => p.id !== pointId),
        };
      })
    );

    setContextMenu(null);
  };

  if (loading) {
    return (
      <div className="text-[#E6E6E6] p-6">
        Loading trip...
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#121A17] text-[#E6E6E6] p-6 space-y-6">

      <div className="bg-[#1E2C26] p-6 rounded-2xl border border-[#2A3A33] max-w-md mx-auto">
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

          <TripDatePicker value={tripDates} onChange={setTripDates} />

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

      <div className="flex gap-4 max-w-6xl mx-auto">

        <div className="w-64 bg-[#1E2C26] p-4 rounded-xl">
          <h3 className="text-[#C2A878] font-bold mb-2">Routes</h3>

          <button
            onClick={createRoute}
            className="mb-3 w-full bg-[#2F5D50] hover:bg-[#1E3D35] py-2 rounded-xl"
          >
            + Add Route
          </button>

          {routes.map((r) => (
            <div
              key={r.id}
              onClick={() => {
                if(activeRouteId === r.id){
                  setActiveRouteId(null)
                  setEditorMode(false)
                  return;
                }
                setActiveRouteId(r.id);
                setEditorMode(true);
              }}
              className={`p-2 rounded mb-2 cursor-pointer ${
                r.id === activeRouteId
                  ? "bg-[#2F5D50]"
                  : "bg-[#18231F]"
              }`}
            >
              Route #{r.id}
            </div>
          ))}
        </div>

        <div className="flex-1">
          <TripMap
            routes={routes}
            onClick={editorMode ? handleMapClick : undefined}
            onDragStart={(e) =>{
              setContextMenu(null)
            }}
            onMarkerDragEnd={(routeId, pointId, event) =>{
              handleMarkerDragEnd(routeId, pointId, event);
            }}
            onMarkerRightClick={(routeId, pointId, e) => {
              setContextMenu({
                x: e.domEvent.clientX,
                y: e.domEvent.clientY,
                routeId,
                pointId,
              });
            }}
          />
        </div>

        {contextMenu && (
          <div
            className="fixed bg-[#1E2C26] border border-[#2A3A33] rounded-lg shadow-lg p-2 z-50"
            style={{
              top: contextMenu.y,
              left: contextMenu.x,
            }}
          >
            <button
              onClick={() =>
                handleDeletePoint(
                  contextMenu.routeId,
                  contextMenu.pointId
                )
              }
              className="text-red-400 hover:bg-[#2A3A33] px-3 py-1 rounded w-full text-left"
            >
              Delete Point
            </button>

            <button
              onClick={() => setContextMenu(null)}
              className="text-gray-300 hover:bg-[#2A3A33] px-3 py-1 rounded w-full text-left"
            >
              Cancel
            </button>
          </div>
        )}

      </div>
    </div>
  );
}

export default EditTripPage;