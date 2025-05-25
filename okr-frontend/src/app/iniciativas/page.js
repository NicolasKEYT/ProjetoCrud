'use client'

import useSWR, { mutate } from 'swr'
import fetcher from '../../../lib/fetcher'
import Link from 'next/link'

export default function IniciativasPage() {
  const { data, error } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/iniciativas`,
    fetcher
  )

  async function atualizarPct(id, novaPct) {
    const iniciativaAtual = data.find(i => i.id === id)
    if (!iniciativaAtual) return

    const payload = {
      ...iniciativaAtual,
      porcentagemConclusao: Number(novaPct)
    }

    await fetch(
      `${process.env.NEXT_PUBLIC_API_BASE_URL}/iniciativas/${id}`,
      {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      }
    )

    mutate(`${process.env.NEXT_PUBLIC_API_BASE_URL}/iniciativas`)
    mutate(`${process.env.NEXT_PUBLIC_API_BASE_URL}/krs`)
    mutate(`${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos`)
  }

  async function excluirIniciativa(id) {
    if (!confirm('Deseja realmente excluir esta iniciativa?')) return

    await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/iniciativas/${id}`, {
      method: 'DELETE'
    })
    mutate(`${process.env.NEXT_PUBLIC_API_BASE_URL}/iniciativas`)
  }

  if (error) {
    return (
      <div className="text-red-600 text-center mt-8">
        Falha ao carregar iniciativas.
      </div>
    )
  }

  if (!data) {
    return (
      <div className="text-gray-500 text-center mt-8 animate-pulse">
        Carregando iniciativas…
      </div>
    )
  }

  return (
    <div className="space-y-6">
      {/* Cabeçalho */}
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold">Iniciativas</h1>
        <div className="flex gap-2">
          <Link
            href="/"
            className="px-4 py-2 rounded-xl bg-white border border-gray-200 shadow hover:shadow-md text-sm text-gray-800 transition"
          >
            ← Voltar
          </Link>
          <Link
            href="/iniciativas/new"
            className="px-4 py-2 rounded-xl bg-blue-600 text-white hover:bg-blue-700 text-sm transition"
          >
            + Nova Iniciativa
          </Link>
        </div>
      </div>

      {/* Grid de iniciativas */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {data.map(i => (
          <div
            key={i.id}
            className="p-4 rounded-xl shadow bg-white border border-gray-200 hover:shadow-md transition-all space-y-3"
          >
            <h2 className="text-lg font-semibold text-gray-800">
              {i.titulo}
            </h2>

            {/* Contexto hierárquico */}
            <p className="text-xs text-gray-500">
              KR: {i.resultadoChave.descricao}
            </p>
            <p className="text-xs text-gray-500">
              Objetivo: {i.resultadoChave.objetivo.titulo}
            </p>

            <p className="text-sm text-gray-600">{i.descricao}</p>

            {/* Inline input para % */}
            <div className="flex items-center gap-2">
              <label className="text-sm text-gray-600">Concluído:</label>
              <input
                type="number"
                min="0"
                max="100"
                defaultValue={i.porcentagemConclusao}
                onBlur={e => atualizarPct(i.id, e.target.value)}
                className="w-16 border rounded px-2 py-1 text-right text-sm"
              />
              <span className="text-sm text-gray-600">%</span>
            </div>

            {/* Botões Editar / Excluir */}
            <div className="flex gap-4 text-sm">
              <Link
                href={`/iniciativas/${i.id}`}
                className="text-blue-600 hover:underline"
              >
                Editar
              </Link>
              <button
                onClick={() => excluirIniciativa(i.id)}
                className="text-red-500 hover:underline"
              >
                Excluir
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
