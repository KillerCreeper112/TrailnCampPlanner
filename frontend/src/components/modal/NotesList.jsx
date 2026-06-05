import NoteView from "@/components/modal/NoteView.jsx";

function NotesList({notes}){
  return (
    <div className="flex flex-col gap-2 w-full">
      {notes.map((note) => (
        <NoteView key={note.id} note={note} />
      ))}
    </div>
  );
}

export default NotesList;