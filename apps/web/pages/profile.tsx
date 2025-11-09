import { useEffect, useState } from 'react'
import { useRouter } from 'next/router'

export default function ProfilePage() {
  const router = useRouter()
  const [user, setUser] = useState<any | null>(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    async function fetchMe() {
      try {
        const res = await fetch('/api/users/me')
        if (res.ok) {
          const data = await res.json()
          setUser(data.user)
        }
      } catch (err) {
        console.error(err)
      } finally {
        setLoading(false)
      }
    }
    fetchMe()
  }, [])

  const handleLogout = async () => {
    try {
      await fetch('/api/auth/logout', { method: 'POST' })
    } catch (err) {
      console.error('Logout error', err)
    } finally {
      setUser(null)
      router.push('/')
    }
  }

  if (loading) return <div style={{ padding: 24 }}>Cargando...</div>
  if (!user) return <div style={{ padding: 24 }}>No autenticado. <a href="/register">Regístrate</a> o <a href="/login">Inicia sesión</a>.</div>

  return (
    <main style={{ padding: 24 }}>
      <h1>Mi Perfil</h1>
      <p><strong>Nombre:</strong> {user.name}</p>
      <p><strong>Email:</strong> {user.email}</p>
      <p><strong>Puntos:</strong> {user.pointsBalance}</p>
      <div style={{ marginTop: 16 }}>
        <button onClick={handleLogout} style={{ padding: '8px 12px', background: 'transparent', border: '1px solid var(--accent-blue)', color: 'var(--accent-blue)', borderRadius: 8 }}>
          Cerrar sesión
        </button>
      </div>
    </main>
  )
}
