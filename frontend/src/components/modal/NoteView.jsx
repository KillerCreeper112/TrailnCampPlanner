import {api, ENDPOINTS} from "@/api/api.js";

function NoteView({ note, onDelete }) {
  const handleDeleteNote = async (noteId) => {
    await api.delete(`${ENDPOINTS.NOTE}/${noteId}`);
    onDelete(noteId);
  }

  return (
    <div className="flex gap-2 bg-[#18231F] p-2 rounded-xl border border-[#2A3A33]">
      <div className="text-[#E6E6E6]">
        <span className="text-[#C2A878]">{note.icon}</span>
        <span className="ml-2">{note.content}</span>
      </div>
      <button
        className="ml-auto bg-red-500 rounded"
        onClick={(e) => handleDeleteNote(note.id)}
      >
        X
      </button>
    </div>
  );
}

export default NoteView;