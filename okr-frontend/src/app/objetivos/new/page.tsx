'use client'
import { useRouter } from 'next/navigation'
import ObjetivoForm, { ObjetivoData } from '@/components/ObjetivoForm'
import Link from 'next/link'

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
    <div className="flex flex-col items-center justify-center min-h-[70vh] px-4">
      <div className="w-full max-w-xl bg-white border border-gray-200 shadow rounded-2xl p-6 space-y-6">
        <div className="flex items-center justify-between">
          <h1 className="text-2xl font-bold text-gray-800">Criar Objetivo</h1>
          <Link
            href="/objetivos"
            className="px-4 py-2 text-sm rounded-xl bg-white border border-gray-300 hover:shadow transition text-gray-700"
          >
            ← Voltar
          </Link>
        </div>
        <ObjetivoForm onSubmit={criar} />
      </div>
    </div>
  )
}
