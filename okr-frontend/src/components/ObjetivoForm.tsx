'use client'
import { useState, FormEvent } from 'react'

export type ObjetivoData = {
  titulo: string
  descricao: string
  porcentagemConclusao?: number
}

type Props = {
  initial?: ObjetivoData
  onSubmit: (data: ObjetivoData) => Promise<void>
}

export default function ObjetivoForm({ initial, onSubmit }: Props) {
  const [titulo, setTitulo] = useState(initial?.titulo || '')
  const [descricao, setDescricao] = useState(initial?.descricao || '')
  const [saving, setSaving] = useState(false)

  async function handleSubmit(e: FormEvent) {
    e.preventDefault()
    setSaving(true)
    await onSubmit({ titulo, descricao, porcentagemConclusao: initial?.porcentagemConclusao })
    setSaving(false)
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4 p-4 border rounded">
      <div>
        <label className="block font-semibold">Título</label>
        <input
          className="w-full border px-2 py-1"
          value={titulo}
          onChange={e => setTitulo(e.target.value)}
          required
        />
      </div>
      <div>
        <label className="block font-semibold">Descrição</label>
        <textarea
          className="w-full border px-2 py-1"
          value={descricao}
          onChange={e => setDescricao(e.target.value)}
          required
        />
      </div>
      <button
        type="submit"
        disabled={saving}
        className="bg-blue-600 text-white px-4 py-2 rounded disabled:opacity-50"
      >
        {saving ? 'Salvando…' : 'Salvar Objetivo'}
      </button>
    </form>
  )
}
