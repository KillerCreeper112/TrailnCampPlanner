import { GoogleMap, Polyline, Marker, useJsApiLoader } from "@react-google-maps/api";

const containerStyle = {
  width: "100%",
  height: "600px",
};

const center = {
  lat: 40.0,
  lng: -75.0,
};

function TripMap({ routes, selectedRouteId, onClick, onDragStart, onMarkerDragEnd, onMarkerRightClick}) {
  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_KEY,
  });

  if (!isLoaded) return <div>Loading map...</div>;

  const colors = ["#ff4d4d", "#4d79ff", "#4dff88", "#ffcc4d"];

  const route = routes.find(r => r.id === selectedRouteId);
  const path = route?.points
    .map((p) => ({
      lat: p.latitude,
      lng: p.longitude
    })) ?? [];

  return (
    <GoogleMap
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
    </GoogleMap>
  );
}

export default TripMap;