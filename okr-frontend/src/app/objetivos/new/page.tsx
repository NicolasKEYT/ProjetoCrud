'use client'
import { useRouter } from 'next/navigation'
import ObjetivoForm, { ObjetivoData } from '@/components/ObjetivoForm'

export default function NewObjetivoPage() {
  const router = useRouter()

  async function criar(data: ObjetivoData) {
    await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    })
    router.push('/objetivos')
  }

  return (
    <div className="max-w-xl mx-auto">
      <h1 className="text-2xl mb-4">Criar Objetivo</h1>
      <ObjetivoForm onSubmit={criar} />
    </div>
  )
}
