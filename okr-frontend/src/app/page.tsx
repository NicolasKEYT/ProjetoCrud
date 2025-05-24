// src/app/page.tsx
import Link from 'next/link'

export default function Home() {
  return (
    <div
      className="
        grid 
        grid-rows-[20px_1fr_20px] 
        items-center 
        justify-items-center 
        min-h-screen 
        p-8 
        pb-20 
        gap-16 
        sm:p-20 
        font-[family-name:var(--font-geist-sans)]
      "
    >
      {/* Main content */}
      <main className="flex flex-col gap-8 row-start-2 items-center sm:items-start">
        <h1 className="text-4xl font-bold">OKR Dashboard</h1>
        <ul
          className="
            list-inside 
            list-decimal 
            text-sm 
            text-center 
            sm:text-left 
            font-[family-name:var(--font-geist-mono)]
            space-y-2
          "
        >
          <li>
            <Link
              href="/objetivos"
              className="text-lg underline hover:text-blue-600"
            >
              Objetivos
            </Link>
          </li>
          <li>
            <Link
              href="/krs"
              className="text-lg underline hover:text-blue-600"
            >
              KRs
            </Link>
          </li>
          <li>
            <Link
              href="/iniciativas"
              className="text-lg underline hover:text-blue-600"
            >
              Iniciativas
            </Link>
          </li>
        </ul>
      </main>

      {/* Footer (opcional: mantenha ou personalize) */}
      <footer className="row-start-3 flex gap-6 flex-wrap items-center justify-center">
        {/* Exemplo de links extras, remova se não precisar */}
        <Link href="https://nextjs.org/docs">
          <span className="hover:underline">Docs</span>
        </Link>
        <Link href="https://vercel.com">
          <span className="hover:underline">Deploy</span>
        </Link>
      </footer>
    </div>
  )
}
