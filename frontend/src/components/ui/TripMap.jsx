import {
  GoogleMap,
  Polyline,
  Marker,
  useJsApiLoader,
  StandaloneSearchBox,
  InfoBox,
  OverlayView
} from "@react-google-maps/api";
import {useRef, useState} from "react";
import {Button} from "@base-ui/react";

const containerStyle = {
  width: "100%",
  height: "600px",
};

const center = {
  lat: 40.0,
  lng: -75.0,
};

function TripMap({ routes, mapNotes, selectedRouteId, onClick, onDragStart, onMarkerDragEnd, onMarkerRightClick}) {
  const searchBoxRef = useRef(null);
  const [map, setMap] = useState(null);
  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_KEY,
    libraries: ["places"]
  });

  if (!isLoaded) return <div>Loading map...</div>;

  const onPlacesChanged = () =>{
    const places = searchBoxRef.current.getPlaces();
    const place = places?.[0];
    if(!place) return;
    const location = place.geometry.location;
    const lng = location.lng();
    const lat = location.lat();

    map?.panTo({lat, lng});
  };

  const colors = ["#ff4d4d", "#4d79ff", "#4dff88", "#ffcc4d"];

  const route = routes.find(r => r.id === selectedRouteId);
  const path = route?.points
    .map((p) => ({
      lat: p.latitude,
      lng: p.longitude
    })) ?? [];

  return (
    <div className="relative">
      <GoogleMap
        onLoad={(map) => setMap(map)}
        mapContainerStyle={containerStyle}
        center={center}
        zoom={10}
        onClick={onClick}
        onDragStart={onDragStart}
      >
        <Polyline
          path={path}
          options={{
            strokeColor: colors[1 % colors.length],
            strokeWeight: 4,
          }}
        />
        {route && (
          <>
            {route.points.map((p) => (
              <Marker
                key={p.id}
                position={{
                  lat: p.latitude,
                  lng: p.longitude,
                }}
                draggable
                onDragEnd={(e) =>
                  onMarkerDragEnd?.(route.id, p.id, e)
                }
                onRightClick={(e) =>{
                  onMarkerRightClick?.(route.id, p.id, e)
                }}
              />
            ))}
          </>
        )}
        {mapNotes && (
          <>
            {
              mapNotes.map((note) =>(
                <OverlayView
                  id={`note/${note.id}`}
                  position={{
                    lat: note.latitude,
                    lng: note.longitude
                  }}
                  mapPaneName={OverlayView.OVERLAY_MOUSE_TARGET}
                >
                  <div className="bg-black border border-black p-1 flex shadow rounded w-fit">
                    <span className="text-white-100">{note.icon}</span>
                    <span className="ml-1">{note.content}</span>
                  </div>
                </OverlayView>
              ))
            }
          </>
        )}
      </GoogleMap>

      <div className="absolute top-2 left-2 z-10">
        <StandaloneSearchBox
          onLoad={(box) => searchBoxRef.current = box}
          onPlacesChanged={onPlacesChanged}
        >
          <input
            type="text"
            placeholder="Search"
            className="p-2 w-64 mt-2 rounded shadow"
          />
        </StandaloneSearchBox>
      </div>
    </div>
  );
}

export default TripMap;