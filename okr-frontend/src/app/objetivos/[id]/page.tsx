'use client'

import { useRouter, useParams } from 'next/navigation'
import useSWR from 'swr'
import ObjetivoForm, { ObjetivoData } from '@/components/ObjetivoForm'
import fetcher from '../../../../lib/fetcher'
import Link from 'next/link'

export default function EditObjetivoPage() {
  const router = useRouter()
  const { id } = useParams()
  const { data, error } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos/${id}`,
    fetcher
  )

  if (error) {
    return (
      <div className="text-red-600 text-center mt-8">
        Erro ao carregar objetivo.
      </div>
    )
  }

  if (!data) {
    return (
      <div className="text-gray-500 text-center mt-8 animate-pulse">
        Carregando objetivo…
      </div>
    )
  }

  async function atualizar(updated: ObjetivoData) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(updated)
    })

    if (res.ok) {
      router.push('/objetivos')
    } else {
      alert('Erro ao atualizar o objetivo')
    }
  }

  return (
    <div className="max-w-2xl mx-auto bg-white border border-gray-200 rounded-xl shadow p-6 space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-800">Editar Objetivo</h1>
        <Link
          href="/objetivos"
          className="px-4 py-2 text-sm rounded-xl bg-white border border-gray-300 hover:shadow transition text-gray-700"
        >
          ← Voltar
        </Link>
      </div>

      <ObjetivoForm initial={data} onSubmit={atualizar} />
    </div>
  )
}
