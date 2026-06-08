import {Marker, OverlayView} from "@react-google-maps/api";
import {useState} from "react";

function NotePin({note, onDragEnd, onClick}){
  const [hover, setHover] = useState(false);

  return (<>
    <Marker
      key={`marker-${note.id}`}
      position={{
        lat: note.latitude,
        lng: note.longitude,
      }}
      draggable
      onDragEnd={(e) => {
        const lat = e.latLng.lat();
        const lng = e.latLng.lng();

        onDragEnd?.(note.id, lat, lng);
      }}
      icon={{
        path: window.google.maps.SymbolPath.CIRCLE,
        scale: 5
      }}
    />
    <OverlayView
      key={`overlay-${note.id}`}
      position={{
        lat: note.latitude,
        lng: note.longitude,
      }}
      mapPaneName={OverlayView.OVERLAY_MOUSE_TARGET}
    >
      <div
        className="bg-black border border-black px-2 py-1 flex shadow rounded w-fit cursor-pointer"
        onMouseEnter={(e) => setHover(true)}
        onMouseLeave={(e) => setHover(false)}
        onMouseDown={(e) =>{
          e.stopPropagation();
          onClick?.(note);
        }}
      >
        <span className="text-white">{note.icon}</span>
        {hover && (
          <span className="ml-1 whitespace-nowrap">{note.content}</span>
        )}
      </div>
    </OverlayView>
  </>)
}

export default NotePin;