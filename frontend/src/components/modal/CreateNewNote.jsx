import { useState } from "react";

function CreateNewNote({ onCreate, onClose }) {
  const [icon, setIcon] = useState("");
  const [content, setContent] = useState("");

  const handleCreate = () => {
    //if (!content.trim()) return;

    onCreate({
      icon,
      content,
    });

    setIcon("");
    setContent("");
  };

  return (
    <div
      className="fixed inset-0 bg-black/60 flex items-center justify-center"
      onClick={onClose}
    >
      <div
        className="bg-[#1E2C26] p-6 rounded-2xl w-full max-w-md border border-[#2A3A33]"
        onClick={(e) => e.stopPropagation()}
      >

        <h3 className="text-xl font-bold text-[#C2A878] mb-4">
          Create New Note
        </h3>

        <input
          placeholder="Icon (optional)"
          value={icon}
          onChange={(e) => setIcon(e.target.value)}
          className="bg-[#18231F] border border-[#2A3A33] p-3 rounded-xl text-[#E6E6E6] w-full mb-3"
        />

        <textarea
          placeholder="Content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          className="bg-[#18231F] border border-[#2A3A33] p-3 rounded-xl text-[#E6E6E6] w-full"
        />

        <div className="flex gap-2 mt-4">

          <button
            onClick={handleCreate}
            className="flex-1 bg-[#2F5D50] hover:bg-[#1E3D35] py-2 rounded-xl"
          >
            Create
          </button>

          <button
            onClick={onClose}
            className="flex-1 border border-[#2A3A33] py-2 rounded-xl text-[#A7B0AA]"
          >
            Cancel
          </button>

        </div>

      </div>
    </div>
  );
}

export default CreateNewNote;