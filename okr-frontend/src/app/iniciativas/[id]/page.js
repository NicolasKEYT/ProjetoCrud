'use client'

import { useState, useEffect } from 'react'
import useSWR from 'swr'
import fetcher from '../../../../lib/fetcher'
import { useRouter, useParams } from 'next/navigation'
import Link from 'next/link'

export default function IniciativaPage() {
  const router = useRouter()
  const { id } = useParams()

  // 1) Busca os dados da iniciativa
  const { data: init, error: initError } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/iniciativas/${id}`,
    fetcher
  )

  // estados de formulário
  const [titulo, setTitulo] = useState('')
  const [descricao, setDescricao] = useState('')
  const [porcentagem, setPorcentagem] = useState('')
  const [saving, setSaving] = useState(false)
  const [errorMsg, setErrorMsg] = useState('')

  // 2) Popula o form assim que os dados forem carregados
  useEffect(() => {
    if (init) {
      setTitulo(init.titulo)
      setDescricao(init.descricao)
      setPorcentagem(init.porcentagemConclusao)
    }
  }, [init])

  if (initError) {
    return <p className="text-red-600 text-center mt-8">Falha ao carregar iniciativa.</p>
  }
  if (!init) {
    return <p className="text-gray-500 text-center mt-8 animate-pulse">Carregando iniciativa…</p>
  }

  // 3) Envia o PUT com os dados atualizados
  async function handleSubmit(e) {
    e.preventDefault()
    setSaving(true)
    setErrorMsg('')

    try {
      const payload = {
        ...init,
        titulo,
        descricao,
        porcentagemConclusao: Number(porcentagem)
      }

      const res = await fetch(
        `${process.env.NEXT_PUBLIC_API_BASE_URL}/iniciativas/${id}`,
        {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(payload)
        }
      )

      if (!res.ok) throw new Error('Falha ao salvar')
      router.push('/iniciativas')
    } catch (err) {
      setErrorMsg(err.message || 'Erro ao salvar')
      setSaving(false)
    }
  }

  return (
    <div className="max-w-md mx-auto mt-10 bg-white p-6 rounded-2xl shadow space-y-6">
      {/* Cabeçalho */}
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-800">
          Editar Iniciativa #{id}
        </h1>
        <Link
          href="/iniciativas"
          className="px-3 py-1 text-sm rounded-xl bg-white border border-gray-200 shadow hover:shadow-md text-gray-700 transition"
        >
          ← Voltar
        </Link>
      </div>

      {errorMsg && (
        <p className="text-red-600 text-sm">{errorMsg}</p>
      )}

      {/* Formulário */}
      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Título */}
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Título
          </label>
          <input
            type="text"
            required
            value={titulo}
            onChange={e => setTitulo(e.target.value)}
            className="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
          />
        </div>

        {/* Descrição */}
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Descrição
          </label>
          <textarea
            required
            value={descricao}
            onChange={e => setDescricao(e.target.value)}
            className="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
          />
        </div>

        {/* Porcentagem */}
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Conclusão (%)
          </label>
          <input
            type="number"
            min="0"
            max="100"
            required
            value={porcentagem}
            onChange={e => setPorcentagem(e.target.value)}
            className="mt-1 block w-24 px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 text-right"
          />
        </div>

        {/* Botões */}
        <div className="flex justify-between">
          <button
            type="button"
            onClick={() => router.push('/iniciativas')}
            className="px-4 py-2 bg-white border border-gray-300 rounded-lg text-sm hover:shadow transition"
          >
            Cancelar
          </button>
          <button
            type="submit"
            disabled={saving}
            className="px-4 py-2 bg-blue-600 text-white rounded-lg text-sm hover:bg-blue-700 transition"
          >
            {saving ? 'Salvando…' : 'Salvar Alterações'}
          </button>
        </div>
      </form>
    </div>
  )
}
