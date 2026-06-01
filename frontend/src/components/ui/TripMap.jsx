import { GoogleMap, Polyline, Marker, useJsApiLoader } from "@react-google-maps/api";

const containerStyle = {
  width: "100%",
  height: "600px",
};

const center = {
  lat: 40.0,
  lng: -75.0,
};

function TripMap({ routes, onClick, onDragStart, onMarkerDragEnd, onMarkerRightClick}) {
  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_KEY,
  });

  if (!isLoaded) return <div>Loading map...</div>;

  const colors = ["#ff4d4d", "#4d79ff", "#4dff88", "#ffcc4d"];

  return (
    <GoogleMap
      mapContainerStyle={containerStyle}
      center={center}
      zoom={10}
      onClick={onClick}
      onDragStart={onDragStart}
    >
      {routes.map((route, idx) => {
        const path = route.points
          .sort((a, b) => a.orderIndex - b.orderIndex)
          .map((p) => ({
            lat: p.latitude,
            lng: p.longitude,
          }));

        return (
          <div key={route.id}>
            {/* Polyline */}
            <Polyline
              path={path}
              options={{
                strokeColor: colors[idx % colors.length],
                strokeWeight: 4,
              }}
            />

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
          </div>
        );
      })}
    </GoogleMap>
  );
}

export default TripMap;