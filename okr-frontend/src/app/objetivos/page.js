'use client'
import useSWR, { mutate } from 'swr'
import fetcher from '../../../lib/fetcher'
import Link from 'next/link'
import ProgressBar from '@/components/ProgressBar'
import { useRouter } from 'next/navigation'

export default function ObjetivosPage() {
  const { data, error } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos`,
    fetcher
  )

  const router = useRouter()

  async function excluirObjetivo(id) {
    const confirmar = window.confirm('Tem certeza que deseja excluir este objetivo?')
    if (!confirmar) return

    await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos/${id}`, {
      method: 'DELETE',
    })

    mutate(`${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos`)
  }

  if (error) return (
    <div className="text-red-600 text-center mt-8">
      Falha ao carregar objetivos.
    </div>
  )

  if (!data) return (
    <div className="text-gray-500 text-center mt-8 animate-pulse">
      Carregando objetivos…
    </div>
  )

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-3xl font-bold">Objetivos</h1>
        <div className="flex gap-2">
          <Link
            href="/"
            className="px-4 py-2 rounded-xl bg-white border border-gray-200 shadow hover:shadow-md text-sm text-gray-800 transition"
          >
            Voltar para Home
          </Link>
          <Link
            href="/objetivos/new"
            className="px-4 py-2 rounded-xl bg-blue-600 text-white hover:bg-blue-700 text-sm transition"
          >
            + Novo
          </Link>
        </div>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {data.map(obj => (
          <div
            key={obj.id}
            className="p-4 rounded-xl shadow bg-white border border-gray-200 hover:shadow-md transition-all space-y-2"
          >
            <h2 className="text-lg font-semibold text-gray-800">{obj.titulo}</h2>
            <p className="text-sm text-gray-600 line-clamp-3">{obj.descricao}</p>
            <ProgressBar percent={obj.porcentagemConclusao} />
            <div className="text-sm text-gray-500">
              {obj.porcentagemConclusao}%
            </div>
            <div className="flex gap-4 text-sm">
              <button
                onClick={() => router.push(`/objetivos/${obj.id}/progresso`)}
                className="text-green-600 hover:underline"
              >
                Atualizar Progresso
              </button>
              <Link
                href={`/objetivos/${obj.id}`}
                className="text-blue-600 hover:underline"
              >
                Editar
              </Link>
              <button
                onClick={() => excluirObjetivo(obj.id)}
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
