import { useState } from 'react'
import { useRouter } from 'next/router'
import { validateEmail, calculateAge } from '../lib/validation'

type FormState = {
  name: string
  email: string
  password: string
  birthdate: string
  country: string
  accept_tos: boolean
  code_referido?: string
}

export default function RegisterPage() {
  const router = useRouter()
  const [form, setForm] = useState<FormState>({
    name: '',
    email: '',
    password: '',
    birthdate: '',
    country: '',
    accept_tos: false,
    code_referido: ''
  })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState<string | null>(null)

  const onChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target
    if (type === 'checkbox') {
      setForm(prev => ({ ...prev, [name]: (e.target as HTMLInputElement).checked }))
    } else {
      setForm(prev => ({ ...prev, [name]: value }))
    }
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)
    setSuccess(null)

    if (!form.name || !form.email || !form.password || !form.birthdate || !form.country) {
      setError('Completa todos los campos requeridos')
      return
    }
    if (!validateEmail(form.email)) {
      setError('Email inválido')
      return
    }
    if (form.password.length < 8) {
      setError('La contraseña debe tener al menos 8 caracteres')
      return
    }
    const age = calculateAge(form.birthdate)
    if (age < 18) {
      setError('Debes ser mayor de 18 años para registrarte')
      return
    }
    if (!form.accept_tos) {
      setError('Debes aceptar los términos y condiciones')
      return
    }

    setLoading(true)
    try {
      const resp = await fetch('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name: form.name,
          email: form.email,
          password: form.password,
          birthdate: form.birthdate,
          country: form.country,
          accept_tos: form.accept_tos,
          code_referido: form.code_referido || undefined
        })
      })
      const data = await resp.json()
      if (!resp.ok) {
        setError(data?.error || 'Error en el registro')
        setLoading(false)
        return
      }
      setSuccess('Registro exitoso. Redireccionando...')
      // cookie JWT is set by the API; redirect to profile or home
      setTimeout(() => router.push('/'), 1200)
    } catch (err) {
      console.error(err)
      setError('Error de red. Intenta nuevamente')
    } finally {
      setLoading(false)
    }
  }

  return (
    <main style={{ padding: 24, maxWidth: 720, margin: '0 auto' }}>
      <h1 style={{ color: 'var(--accent-blue)' }}>Crear cuenta — Level‑Up Gamer</h1>
      <form onSubmit={handleSubmit} style={{ display: 'grid', gap: 12, marginTop: 16 }}>
        <label style={{ display: 'flex', flexDirection: 'column' }}>
          Nombre completo
          <input name="name" value={form.name} onChange={onChange} />
        </label>

        <label style={{ display: 'flex', flexDirection: 'column' }}>
          Email
          <input name="email" value={form.email} onChange={onChange} />
        </label>

        <label style={{ display: 'flex', flexDirection: 'column' }}>
          Contraseña
          <input name="password" type="password" value={form.password} onChange={onChange} />
        </label>

        <label style={{ display: 'flex', flexDirection: 'column' }}>
          Fecha de nacimiento
          <input name="birthdate" type="date" value={form.birthdate} onChange={onChange} />
        </label>

        <label style={{ display: 'flex', flexDirection: 'column' }}>
          País
          <select name="country" value={form.country} onChange={onChange}>
            <option value="">Selecciona un país</option>
            <option value="CL">Chile</option>
            <option value="AR">Argentina</option>
            <option value="PE">Perú</option>
            <option value="US">Estados Unidos</option>
          </select>
        </label>

        <label style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
          <input name="accept_tos" type="checkbox" checked={form.accept_tos} onChange={onChange} />
          <span>Acepto los términos y condiciones</span>
        </label>

        <label style={{ display: 'flex', flexDirection: 'column' }}>
          Código de referido (opcional)
          <input name="code_referido" value={form.code_referido} onChange={onChange} />
        </label>

        {error && <div style={{ color: 'var(--accent-green)', background: 'rgba(255,255,255,0.04)', padding: 8, borderRadius: 6 }}>{error}</div>}
        {success && <div style={{ color: 'var(--accent-blue)', background: 'rgba(255,255,255,0.04)', padding: 8, borderRadius: 6 }}>{success}</div>}

        <button type="submit" disabled={loading} style={{ padding: '10px 16px', background: 'var(--accent-blue)', color: 'var(--text)', border: 'none', borderRadius: 8 }}>
          {loading ? 'Registrando...' : 'Crear cuenta'}
        </button>
      </form>
    </main>
  )
}
