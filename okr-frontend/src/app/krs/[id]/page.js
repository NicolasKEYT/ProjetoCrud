'use client'

import { useState, useEffect } from 'react'
import useSWR from 'swr'
import fetcher from '../../../../lib/fetcher'
import { useRouter, useParams } from 'next/navigation'
import Link from 'next/link'

export default function EditKrPage() {
  const router = useRouter()
  const { id } = useParams()

  // 1) dados do KR
  const { data: kr, error: krError } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/krs/${id}`,
    fetcher
  )
  // 2) lista de objetivos
  const { data: objetivos, error: objError } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos`,
    fetcher
  )

  const [descricao, setDescricao] = useState('')
  const [meta, setMeta] = useState('')
  const [objetivoId, setObjetivoId] = useState('')
  const [saving, setSaving] = useState(false)
  const [errMsg, setErrMsg] = useState('')

  useEffect(() => {
    if (kr) {
      setDescricao(kr.descricao)
      setMeta(kr.meta)
      setObjetivoId(kr.objetivo?.id ?? '')
    }
  }, [kr])

  if (krError) {
    return <p className="text-red-600 text-center mt-8">Erro ao carregar KR.</p>
  }
  if (!kr) {
    return <p className="text-gray-500 text-center mt-8 animate-pulse">Carregando KR…</p>
  }
  if (objError) {
    return <p className="text-red-600 text-center mt-8">Erro ao carregar Objetivos.</p>
  }
  if (!objetivos) {
    return <p className="text-gray-500 text-center mt-8 animate-pulse">Carregando Objetivos…</p>
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setSaving(true)
    setErrMsg('')

    try {
      const payload = {
        descricao,
        meta: Number(meta),
        objetivo: { id: Number(objetivoId) }
      }

      const res = await fetch(
        `${process.env.NEXT_PUBLIC_API_BASE_URL}/krs/${id}`,
        {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(payload)
        }
      )
      if (!res.ok) throw new Error('Falha ao salvar')

      router.push('/krs')
    } catch (err) {
      setErrMsg(err.message || 'Erro ao salvar')
      setSaving(false)
    }
  }

  return (
    <div className="max-w-md mx-auto mt-10 bg-white p-6 rounded-2xl shadow space-y-6">
      {/* Cabeçalho */}
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-800">Editar KR #{id}</h1>
        <Link
          href="/krs"
          className="px-3 py-1 text-sm rounded-xl bg-white border border-gray-200 shadow hover:shadow-md text-gray-700 transition"
        >
          ← Voltar
        </Link>
      </div>

      {errMsg && <p className="text-red-600 text-sm">{errMsg}</p>}

      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Descrição */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Descrição</label>
          <input
            type="text"
            required
            value={descricao}
            onChange={e => setDescricao(e.target.value)}
            className="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
          />
        </div>

        {/* Meta */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Meta</label>
          <input
            type="number"
            min="0"
            required
            value={meta}
            onChange={e => setMeta(e.target.value)}
            className="mt-1 block w-32 px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 text-right"
          />
        </div>

        {/* Objetivo */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Objetivo</label>
          <select
            required
            value={objetivoId}
            onChange={e => setObjetivoId(e.target.value)}
            className="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
          >
            <option value="">Selecione um Objetivo</option>
            {objetivos.map(o => (
              <option key={o.id} value={o.id}>
                {o.titulo}
              </option>
            ))}
          </select>
        </div>

        {/* Botões */}
        <div className="flex justify-between">
          <button
            type="button"
            onClick={() => router.push('/krs')}
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
