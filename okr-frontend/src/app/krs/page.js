'use client'

import useSWR from 'swr'
import fetcher from '../../../lib/fetcher'
import Link from 'next/link'

export default function KrsPage() {
  const { data, error } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/krs`,
    fetcher
  )

  if (error) return (
    <div className="text-red-600 text-center mt-8">
      Falha ao carregar KRs.
    </div>
  )

  if (!data) return (
    <div className="text-gray-500 text-center mt-8 animate-pulse">
      Carregando KRs…
    </div>
  )

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold">Progresso Atual</h1>
        <Link
          href="/"
          className="px-4 py-2 rounded-xl bg-white border border-gray-200 shadow hover:shadow-md transition text-sm text-gray-800"
        >
          Voltar para Home
        </Link>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {data.map((kr) => (
          <div
            key={kr.id}
            className="p-4 rounded-xl shadow bg-white border border-gray-200 hover:shadow-md transition-all"
          >
            <h2 className="text-lg font-semibold text-gray-800">{kr.descricao}</h2>
            <p className="text-sm text-gray-500">Meta: {kr.meta}</p>
            <p className="text-sm text-gray-500">Concluído: {kr.porcentagemConclusao}%</p>
          </div>
        ))}
      </div>
    </div>
  )
}
