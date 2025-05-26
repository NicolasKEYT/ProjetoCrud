import Link from "next/link";

export default function Home() {
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col items-center justify-between px-6 py-12 sm:px-12">
      <main className="text-center sm:text-left max-w-2xl w-full">
        <h1 className="text-4xl sm:text-5xl font-bold mb-6 text-gray-900">Projeto do Joca 2</h1>
        <p className="text-gray-600 mb-8 text-lg">
          Gerencie seus Objetivos, Resultados-Chave e Iniciativas de forma simples.
        </p>

        <div className="grid gap-4">
          <Link
            href="/objetivos"
            className="block w-full bg-white hover:bg-blue-50 transition-colors rounded-xl shadow p-5 border border-gray-200"
          >
            <h2 className="text-xl font-semibold text-blue-600">Objetivos</h2>
            <p className="text-gray-500 text-sm">Acompanhe e edite seus objetivos estratégicos</p>
          </Link>

          <Link
            href="/krs"
            className="block w-full bg-white hover:bg-blue-50 transition-colors rounded-xl shadow p-5 border border-gray-200"
          >
            <h2 className="text-xl font-semibold text-blue-600">KRs</h2>
            <p className="text-gray-500 text-sm">Visualize e atualize seus resultados-chave</p>
          </Link>

          <Link
            href="/iniciativas"
            className="block w-full bg-white hover:bg-blue-50 transition-colors rounded-xl shadow p-5 border border-gray-200"
          >
            <h2 className="text-xl font-semibold text-blue-600">Iniciativas</h2>
            <p className="text-gray-500 text-sm">Gerencie iniciativas para alcançar seus KRs</p>
          </Link>
        </div>
      </main>

      <footer className="mt-12 text-sm text-gray-400 flex flex-wrap gap-4 justify-center">
        <Link href="" className="hover:text-blue-500">
          Nicolas Gonçalves
        </Link>
        <Link href="" className="hover:text-blue-500">
          Vinicius Gomes
        </Link>
      </footer>
    </div>
  );
}