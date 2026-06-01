import * as React from "react"
import { format } from "date-fns"
import { Calendar } from "@/components/ui/calendar"
import { Button } from "@/components/ui/button"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"

export function TripDatePicker({ value, onChange }) {
  return (
    <Popover>
      <PopoverTrigger asChild>
        <Button
          variant="outline"
          className="w-full justify-start text-left bg-[#18231F] border border-[#2A3A33] text-[#E6E6E6] hover:bg-[#1E2C26]"
        >
          {value?.from ? (
            value.to ? (
              <>
                {format(value.from, "PPP")} → {format(value.to, "PPP")}
              </>
            ) : (
              format(value.from, "PPP")
            )
          ) : (
            <span className="text-[#A7B0AA]">Select trip dates</span>
          )}
        </Button>
      </PopoverTrigger>

      <PopoverContent className="w-auto p-3 bg-[#1E2C26] border border-[#2A3A33]">
        <Calendar
          mode="range"
          selected={value}
          onSelect={onChange}
          numberOfMonths={2}
          initialFocus
        />
      </PopoverContent>
    </Popover>
  )
}