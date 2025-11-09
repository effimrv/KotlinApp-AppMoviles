import { useState } from 'react'
import { useRouter } from 'next/router'
import { validateEmail } from '../lib/validation'

export default function LoginPage() {
  const router = useRouter()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)
    if (!validateEmail(email)) return setError('Email inválido')
    if (!password) return setError('Ingresa tu contraseña')

    setLoading(true)
    try {
      const res = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      })
      const data = await res.json()
      if (!res.ok) {
        setError(data.error || 'Error en login')
        setLoading(false)
        return
      }
      // login success, cookie set by API. Redirect to profile
      router.push('/profile')
    } catch (err) {
      console.error(err)
      setError('Error de red. Intenta nuevamente')
    } finally {
      setLoading(false)
    }
  }

  return (
    <main style={{ padding: 24, maxWidth: 640, margin: '0 auto' }}>
      <h1 style={{ color: 'var(--accent-blue)' }}>Iniciar sesión</h1>
      <form onSubmit={handleSubmit} style={{ display: 'grid', gap: 12, marginTop: 16 }}>
        <label style={{ display: 'flex', flexDirection: 'column' }}>
          Email
          <input name="email" value={email} onChange={e => setEmail(e.target.value)} />
        </label>
        <label style={{ display: 'flex', flexDirection: 'column' }}>
          Contraseña
          <input name="password" type="password" value={password} onChange={e => setPassword(e.target.value)} />
        </label>
        {error && <div style={{ color: 'var(--accent-green)', background: 'rgba(255,255,255,0.04)', padding: 8, borderRadius: 6 }}>{error}</div>}
        <button type="submit" disabled={loading} style={{ padding: '10px 16px', background: 'var(--accent-blue)', color: 'var(--text)', border: 'none', borderRadius: 8 }}>
          {loading ? 'Ingresando...' : 'Ingresar'}
        </button>
      </form>
    </main>
  )
}

