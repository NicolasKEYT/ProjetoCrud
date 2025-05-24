'use client'

import useSWR from 'swr'
import fetcher from '../../../lib/fetcher'

export default function IniciativasPage() {
  const { data, error } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/iniciativas`,
    fetcher
  )

  if (error) return <p>Falha ao carregar iniciativas.</p>
  if (!data)  return <p>Carregando iniciativas…</p>

  return (
    <ul>
      {data.map(i => (
        <li key={i.id}>
          {i.titulo} – {i.porcentagemConclusao}%
        </li>
      ))}
    </ul>
  )
}
