'use client'
import useSWR from 'swr'
import fetcher from '../../../lib/fetcher'
import Link from 'next/link'
import ProgressBar from '@/components/ProgressBar'

export default function ObjetivosPage() {
  const { data, error } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos`,
    fetcher
  )
  if (error) return <p>Falha</p>
  if (!data) return <p>Carregando…</p>

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl">Objetivos</h1>
        <Link href="/objetivos/new" className="btn-blue">
          + Novo
        </Link>
      </div>

      {data.map(obj => (
        <div key={obj.id} className="p-4 border rounded space-y-2">
          <h2 className="font-semibold">{obj.titulo}</h2>
          <p className="text-sm">{obj.descricao}</p>
          <ProgressBar percent={obj.porcentagemConclusao} />
          <div className="text-sm text-gray-600">
            {obj.porcentagemConclusao}%
          </div>
          <Link
            href={`/objetivos/${obj.id}`}
            className="text-blue-600 hover:underline text-sm"
          >
            Editar
          </Link>
        </div>
      ))}
    </div>
  )
}
