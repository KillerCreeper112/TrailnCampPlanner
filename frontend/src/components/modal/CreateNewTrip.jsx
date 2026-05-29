export function CreateNewTrip({
                         tripName,
                         setTripName,
                         tripDescription,
                         setTripDescription,
                         tripStartDate,
                         setTripStartDate,
                         onCreate,
                         onClose
                       }) {
  return (
    <div className="fixed inset-0 bg-black/60 flex items-center justify-center">
      <div className="bg-[#1E2C26] p-6 rounded-2xl w-full max-w-md border border-[#2A3A33]">

        <h3 className="text-xl font-bold text-[#C2A878] mb-4">
          Create New Trip
        </h3>

        <div className="flex flex-col gap-3">

          <input
            placeholder="Trip Name"
            value={tripName}
            onChange={(e) => setTripName(e.target.value)}
            className="bg-[#18231F] border border-[#2A3A33] p-3 rounded-xl text-[#E6E6E6]"
          />

          <input
            placeholder="Date"
            value={tripStartDate}
            onChange={(e) => setTripStartDate(e.target.value)}
            className="bg-[#18231F] border border-[#2A3A33] p-3 rounded-xl text-[#E6E6E6]"
          />

          <div className="flex gap-2 mt-2">

            <button
              onClick={onCreate}
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
    </div>
  )
}