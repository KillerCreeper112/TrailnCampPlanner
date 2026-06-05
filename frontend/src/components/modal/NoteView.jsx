function NoteView({ note }) {
  return (
    <div className="flex gap-2 bg-[#18231F] p-2 rounded-xl border border-[#2A3A33]">
      <div className="text-[#E6E6E6]">
        <span className="text-[#C2A878]">{note.icon}</span>
        <span className="ml-2">{note.content}</span>
      </div>
    </div>
  );
}

export default NoteView;