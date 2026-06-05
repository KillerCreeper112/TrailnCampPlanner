import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../api/api";
import { ENDPOINTS } from "../api/api";
import { TripDatePicker } from "../api/api_trip.jsx";
import TripMap from "@/components/ui/TripMap.jsx";
import CreateNewNote from "@/components/modal/CreateNewNote.jsx";
import NotesList from "@/components/modal/NotesList.jsx";

function EditTripPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [loading, setLoading] = useState(true);

  const [tripName, setTripName] = useState("");
  const [tripDates, setTripDates] = useState({
    from: undefined,
    to: undefined
  });
  const [description, setDescription] = useState("");
  const [difficulty, setDifficulty] = useState("MEDIUM");
  const [routes, setRoutes] = useState([]);
  const [activeRouteId, setActiveRouteId] = useState(null);
  const [editorMode, setEditorMode] = useState(false);

  const [contextMenu, setContextMenu] = useState(null);
  const [createNoteMenu, setCreateNoteMenu] = useState(null);

  const [viewingNotes, setViewingNotes] = useState(null);

  const createRoute = async () => {
    setContextMenu(null);
    const response = await api.post(`${ENDPOINTS.TRIP}/${id}/routes`, {});
    const newRoute = await response.json();

    setRoutes((prev) => [...prev, newRoute]);
    setActiveRouteId(newRoute.id);
    setEditorMode(true);
  };

  useEffect(() => {
    async function loadTrip() {
      try {
        const res = await api.get(`${ENDPOINTS.TRIP}/${id}`);
        const data = await res.json();

        setTripName(data.name || "");
        setTripDates({
          from: data.startDate ? new Date(data.startDate) : null,
          to: data.endDate ? new Date(data.endDate) : null,
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
        difficulty,
      });

      await res.json();
      navigate("/dashboard");
    } catch (err) {
      console.error("Failed to update trip", err);
    }
  };

  const handleMapClick = async (event) => {
    setContextMenu(null);
    if (!activeRouteId) return;

    const lat = event.latLng.lat();
    const lng = event.latLng.lng();

    const route = routes.find((r) => r.id === activeRouteId);
    if (!route) return;

    const response = await api.post(`${ENDPOINTS.ROUTE}/${route.id}`, {
      latitude: lat,
      longitude: lng,
      orderIndex: route.points.length,
    });

    const newPoint = await response.json();

    setRoutes((prev) =>
      prev.map((r) =>
        r.id !== activeRouteId
          ? r
          : { ...r, points: [...r.points, newPoint] }
      )
    );
  };

  const handleMarkerDragEnd = async (routeId, pointId, event) => {
    const lat = event.latLng.lat();
    const lng = event.latLng.lng();

    const response = await api.put(
      `${ENDPOINTS.ROUTE_POINT}/${pointId}`,
      { latitude: lat, longitude: lng }
    );

    const newPoint = await response.json();

    setRoutes((prev) =>
      prev.map((route) =>
        route.id !== routeId
          ? route
          : {
            ...route,
            points: route.points.map((p) =>
              p.id === pointId ? newPoint : p
            ),
          }
      )
    );
  };

  const handleDeleteRoute = async (routeId) => {
    await api.delete(`${ENDPOINTS.ROUTE}/${routeId}`);
    setRoutes((prev) => prev.filter((r) => r.id !== routeId));
  };

  const handleDeletePoint = async (routeId, pointId) => {
    await api.delete(`${ENDPOINTS.ROUTE_POINT}/${pointId}`);

    setRoutes((prev) =>
      prev.map((route) =>
        route.id !== routeId
          ? route
          : {
            ...route,
            points: route.points.filter((p) => p.id !== pointId),
          }
      )
    );

    setContextMenu(null);
  };

  if (loading) {
    return <div className="text-[#E6E6E6] p-6">Loading trip...</div>;
  }

  return (
    <div className="relative min-h-screen bg-[#121A17] text-[#E6E6E6] p-6 space-y-6">

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
                setActiveRouteId((prev) =>
                  prev === r.id ? null : r.id
                );
                setEditorMode(true);
              }}
              className={`p-2 rounded mb-2 cursor-pointer ${
                r.id === activeRouteId
                  ? "bg-[#2F5D50]"
                  : "bg-[#18231F]"
              }`}
            >
              <div className="flex justify-between items-center">
                <span>Route #{r.id}</span>

                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    handleDeleteRoute(r.id);
                  }}
                  className="p-1 bg-red-500 rounded"
                >
                  🗑️
                </button>
              </div>
            </div>
          ))}
        </div>

        <div className="flex-1">
          <TripMap
            routes={routes}
            selectedRouteId={activeRouteId}
            onClick={editorMode ? handleMapClick : undefined}
            onMarkerDragEnd={handleMarkerDragEnd}
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
      </div>

      {contextMenu && (
        <div
          className="fixed bg-[#1E2C26] border border-[#2A3A33] rounded-lg shadow-lg p-2 z-50"
          style={{ top: contextMenu.y, left: contextMenu.x }}
        >
          <button
            onClick={() =>
              handleDeletePoint(
                contextMenu.routeId,
                contextMenu.pointId
              )
            }
            className="text-red-400 px-3 py-1 w-full text-left"
          >
            Delete Point
          </button>

          <button
            onClick={() => {
              setCreateNoteMenu({
                link: `${ENDPOINTS.NOTE}/route_points/${contextMenu.pointId}`,
              });
              setContextMenu(null);
            }}
            className="px-3 py-1 w-full text-left"
          >
            Add Note
          </button>

          <button
            onClick={async () => {
              const pointId = contextMenu.pointId;
              setContextMenu(null);
              const res = await api.get(
                `${ENDPOINTS.NOTE}/route_points/${pointId}`
              );
              const data = await res.json();
              setViewingNotes(data);
            }}
            className="px-3 py-1 w-full text-left"
          >
            View Notes
          </button>

          <button
            onClick={() => setContextMenu(null)}
            className="px-3 py-1 w-full text-left"
          >
            Cancel
          </button>
        </div>
      )}
      {viewingNotes && (
        <div
          className="fixed inset-0 bg-black/40 z-40"
          onClick={() => setViewingNotes(null)}
        />
      )}
      <div
        className={`
          fixed top-0 right-0 h-full w-[380px]
          bg-[#1E2C26] border-l border-[#2A3A33]
          z-50 shadow-2xl
          transform transition-transform duration-300
          ${viewingNotes ? "translate-x-0" : "translate-x-full"}
        `}
      >
        <div className="p-4 h-full flex flex-col">
          <div className="flex justify-between mb-4">
            <h3 className="text-[#C2A878] font-bold">Notes</h3>

            <button onClick={() => setViewingNotes(null)}>
              X
            </button>
          </div>

          <div className="flex-1 overflow-y-auto">
            {viewingNotes && (
              <NotesList notes={viewingNotes} />
            )}
          </div>
        </div>
      </div>

      {createNoteMenu && (
        <CreateNewNote
          onCreate={async (e) => {
            await api.post(createNoteMenu.link, e);
            setCreateNoteMenu(null);
          }}
          onClose={() => setCreateNoteMenu(null)}
        />
      )}
    </div>
  );
}

export default EditTripPage;