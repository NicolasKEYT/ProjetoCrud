'use client'
import { useRouter, useParams } from 'next/navigation'
import useSWR from 'swr'
import ObjetivoForm, { ObjetivoData } from '@/components/ObjetivoForm'
import fetcher from '../../../../lib/fetcher'


export default function EditObjetivoPage() {
  const router = useRouter()
  const { id } = useParams()
  const { data, error } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos/${id}`,
    fetcher
  )

  if (error) return <p>Erro ao carregar</p>
  if (!data) return <p>Carregando…</p>

  async function atualizar(updated: ObjetivoData) {
    await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(updated)
    })
    router.push('/objetivos')
  }

  return (
    <div className="max-w-xl mx-auto">
      <h1 className="text-2xl mb-4">Editar Objetivo</h1>
      <ObjetivoForm initial={data} onSubmit={atualizar} />
    </div>
  )
}
