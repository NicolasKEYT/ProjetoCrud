// src/app/iniciativas/new/page.js
'use client'

import { useState } from 'react'
import useSWR from 'swr'
import fetcher from '../../../../lib/fetcher'
import { useRouter } from 'next/navigation'
import Link from 'next/link'

export default function NewIniciativaPage() {
  const router = useRouter()
  const { data: krs, error: krsError } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/krs`,
    fetcher
  )

  const [titulo, setTitulo] = useState('')
  const [descricao, setDescricao] = useState('')
  const [porcentagem, setPorcentagem] = useState(0)
  const [krId, setKrId] = useState('')
  const [saving, setSaving] = useState(false)

  async function handleSubmit(e) {
    e.preventDefault()
    setSaving(true)

    const payload = {
      titulo,
      descricao,
      porcentagemConclusao: Number(porcentagem),
      resultadoChave: { id: Number(krId) }
    }

    await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/iniciativas`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })

    router.push('/iniciativas')
  }

  if (krsError) {
    return <p className="text-red-600 text-center mt-8">Falha ao carregar KRs.</p>
  }
  if (!krs) {
    return <p className="text-gray-500 text-center mt-8 animate-pulse">Carregando KRs…</p>
  }

  return (
    <div className="max-w-md mx-auto mt-10 bg-white p-6 rounded-xl shadow space-y-6">
      {/* Cabeçalho */}
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-800">Nova Iniciativa</h1>
        <Link
          href="/iniciativas"
          className="px-3 py-1 text-sm rounded-xl bg-white border border-gray-200 shadow hover:shadow-md text-gray-700 transition"
        >
          ← Voltar
        </Link>
      </div>

      {/* Formulário */}
      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Título */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Título</label>
          <input
            type="text"
            required
            value={titulo}
            onChange={e => setTitulo(e.target.value)}
            className="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
          />
        </div>

        {/* Descrição */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Descrição</label>
          <textarea
            required
            value={descricao}
            onChange={e => setDescricao(e.target.value)}
            className="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
          />
        </div>

        {/* Porcentagem */}
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Conclusão (%)
          </label>
          <input
            type="number"
            min="0"
            max="100"
            required
            value={porcentagem}
            onChange={e => setPorcentagem(e.target.value)}
            className="mt-1 block w-24 px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 text-right"
          />
        </div>

        {/* Seleção de KR */}
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Resultado-Chave (KR)
          </label>
          <select
            required
            value={krId}
            onChange={e => setKrId(e.target.value)}
            className="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
          >
            <option value="">Selecione um KR</option>
            {krs.map(kr => (
              <option key={kr.id} value={kr.id}>
                {kr.descricao}
              </option>
            ))}
          </select>
        </div>

        {/* Botão de envio */}
        <button
          type="submit"
          disabled={saving}
          className="w-full px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition text-sm"
        >
          {saving ? 'Salvando…' : 'Criar Iniciativa'}
        </button>
      </form>
    </div>
  )
}
