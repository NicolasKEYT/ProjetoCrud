// okr-frontend/lib/fetcher.js
export default async function fetcher(url) {
  const res = await fetch(url)
  if (!res.ok) {
    throw new Error(`Fetch error (${res.status}) ao buscar ${url}`)
  }
  return res.json()
}
