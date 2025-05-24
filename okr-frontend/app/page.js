// app/page.js
import Link from 'next/link';

export default function Home() {
  return (
    <main style={{ padding: '2rem' }}>
      <h1>OKR Dashboard</h1>
      <ul>
        <li><Link href="/objetivos">Objetivos</Link></li>
        <li><Link href="/krs">KRs</Link></li>
        <li><Link href="/iniciativas">Iniciativas</Link></li>
      </ul>
    </main>
  );
}
