'use client'
import { useState, useEffect } from 'react'
import { useRouter, useParams } from 'next/navigation'

export default function EditarProgressoPage() {
  const router = useRouter()
  const { id } = useParams()

  const [objetivo, setObjetivo] = useState(null)
  const [porcentagem, setPorcentagem] = useState('')
  const [loading, setLoading] = useState(true)
  const [erro, setErro] = useState(null)

  useEffect(() => {
    async function fetchObjetivo() {
      try {
        const res = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos/${id}`)
        if (!res.ok) throw new Error('Erro ao buscar objetivo')
        const data = await res.json()
        setObjetivo(data)
        setPorcentagem(data.porcentagemConclusao)
      } catch (e) {
        setErro('Erro ao carregar objetivo')
      } finally {
        setLoading(false)
      }
    }

    fetchObjetivo()
  }, [id])

  async function handleSubmit(e) {
    e.preventDefault()
    setLoading(true)

    const atualizado = {
      ...objetivo,
      porcentagemConclusao: Number(porcentagem),
    }

    try {
      await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(atualizado),
      })

      router.push('/objetivos')
    } catch (error) {
      setErro('Erro ao salvar')
      setLoading(false)
    }
  }

  if (loading) return <p className="text-center text-gray-500 mt-8">Carregando…</p>
  if (erro) return <p className="text-center text-red-500 mt-8">{erro}</p>

  return (
    <div className="max-w-md mx-auto mt-10 bg-white p-6 rounded-xl shadow border space-y-4">
      <h1 className="text-xl font-semibold text-gray-800">Atualizar Progresso</h1>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm text-gray-700 mb-1">
            Porcentagem de conclusão (%)
          </label>
          <input
            type="number"
            min="0"
            max="100"
            value={porcentagem}
            onChange={e => setPorcentagem(e.target.value)}
            className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
        </div>
        <div className="flex justify-between items-center">
          <button
            type="button"
            onClick={() => router.push('/objetivos')}
            className="px-4 py-2 bg-white border border-gray-300 rounded-lg text-sm hover:shadow"
          >
            Cancelar
          </button>
          <button
            type="submit"
            className="px-4 py-2 bg-blue-600 text-white rounded-lg text-sm hover:bg-blue-700"
          >
            Salvar
          </button>
        </div>
      </form>
    </div>
  )
}
