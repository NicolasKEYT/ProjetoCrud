'use client'

import useSWR, { mutate } from 'swr'
import fetcher from '../../../lib/fetcher'
import Link from 'next/link'

export default function KrsPage() {
  const { data, error } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/krs`,
    fetcher
  )

  async function excluirKr(id) {
    if (!confirm('Tem certeza que deseja excluir este KR?')) return

    await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/krs/${id}`, {
      method: 'DELETE'
    })
    mutate(`${process.env.NEXT_PUBLIC_API_BASE_URL}/krs`)
    mutate(`${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos`)
  }

  if (error) {
    return (
      <div className="text-red-600 text-center mt-8">
        Falha ao carregar KRs.
      </div>
    )
  }

  if (!data) {
    return (
      <div className="text-gray-500 text-center mt-8 animate-pulse">
        Carregando KRs…
      </div>
    )
  }

  return (
    <div className="space-y-6">
      {/* Cabeçalho */}
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold">Key Results</h1>
        <div className="flex gap-2">
          <Link
            href="/"
            className="px-4 py-2 rounded-xl bg-white border border-gray-200 shadow hover:shadow-md text-sm text-gray-800 transition"
          >
            ← Voltar
          </Link>
          <Link
            href="/krs/new"
            className="px-4 py-2 rounded-xl bg-blue-600 text-white hover:bg-blue-700 text-sm transition"
          >
            + Novo KR
          </Link>
        </div>
      </div>

      {/* Grid de KRs */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {data.map(kr => (
          <div
            key={kr.id}
            className="p-4 rounded-xl shadow bg-white border border-gray-200 hover:shadow-md transition-all space-y-2"
          >
            <h2 className="text-lg font-semibold text-gray-800">
              {kr.descricao}
            </h2>

            {/* Contexto hierárquico */}
            <p className="text-xs text-gray-500 italic">
              Objetivo: {kr.objetivo.titulo}
            </p>

            <p className="text-sm text-gray-500">Meta: {kr.meta}</p>
            <p className="text-sm text-gray-500">Concluído: {kr.porcentagemConclusao}%</p>

            {/* Botões Editar / Excluir */}
            <div className="flex gap-4 text-sm">
              <Link
                href={`/krs/${kr.id}`}
                className="text-blue-600 hover:underline"
              >
                Editar
              </Link>
              <button
                onClick={() => excluirKr(kr.id)}
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
