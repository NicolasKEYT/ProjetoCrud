import Link from 'next/link'

export default function Navbar() {
  return (
    <nav className="bg-white shadow p-4 flex gap-6">
      <Link href="/" className="font-semibold">Home</Link>
      <Link href="/objetivos">Objetivos</Link>
      <Link href="/krs">KRs</Link>
      <Link href="/iniciativas">Iniciativas</Link>
    </nav>
  )
}
