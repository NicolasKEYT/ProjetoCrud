import React from 'react'

type Props = { percent: number }

export default function ProgressBar({ percent }: Props) {
  return (
    <div className="w-full bg-gray-200 h-2 rounded overflow-hidden">
      <div
        className="h-full bg-green-500"
        style={{ width: `${percent}%`, transition: 'width 0.3s ease' }}
      />
    </div>
  )
}
