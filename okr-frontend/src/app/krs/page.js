'use client'

import useSWR from 'swr'
import fetcher from '../../../lib/fetcher'

export default function KrsPage() {
  const { data, error } = useSWR(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/krs`,
    fetcher
  )

  if (error) return <p>Falha ao carregar KRs.</p>
  if (!data)  return <p>Carregando KRs…</p>

  return (
    <ul>
      {data.map(kr => (
        <li key={kr.id}>
          {kr.descricao} – Meta: {kr.meta} – Concluído: {kr.porcentagemConclusao}%
        </li>
      ))}
    </ul>
  )
}
