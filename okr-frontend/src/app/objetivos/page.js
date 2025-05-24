'use client'

import useSWR from 'swr'
import fetcher from '../../../lib/fetcher'

export default function ObjetivosPage() {
  // monta a URL completa a partir da env var
  const endpoint = `${process.env.NEXT_PUBLIC_API_BASE_URL}/objetivos`
  const { data, error } = useSWR(endpoint, fetcher)

  if (error)   return <p>Falha ao carregar objetivos.</p>
  if (!data)   return <p>Carregando objetivos…</p>

  return (
    <ul>
      {data.map(o => (
        <li key={o.id}>{o.titulo}</li>
      ))}
    </ul>
  )
}
