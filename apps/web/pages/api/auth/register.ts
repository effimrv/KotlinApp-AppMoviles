import type { NextApiRequest, NextApiResponse } from 'next'
import { prisma } from '../../../../packages/db/src/prisma'
import bcrypt from 'bcryptjs'
import jwt from 'jsonwebtoken'

const JWT_SECRET = process.env.NEXTAUTH_SECRET || 'dev_secret_change'

function validateEmail(email: string) {
  return /^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(email)
}

function calculateAge(birthdate: string) {
  const bd = new Date(birthdate)
  const now = new Date()
  let age = now.getFullYear() - bd.getFullYear()
  const m = now.getMonth() - bd.getMonth()
  if (m < 0 || (m === 0 && now.getDate() < bd.getDate())) {
    age--
  }
  return age
}

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  if (req.method !== 'POST') return res.status(405).json({ error: 'Method not allowed' })

  const { name, email, password, birthdate, country, accept_tos, code_referido } = req.body
  if (!name || !email || !password || !birthdate || !country) {
    return res.status(400).json({ error: 'Missing required fields' })
  }

  if (!accept_tos) return res.status(400).json({ error: 'Terms must be accepted' })

  if (!validateEmail(email)) return res.status(400).json({ error: 'Invalid email' })

  if (typeof password !== 'string' || password.length < 8) return res.status(400).json({ error: 'Password too weak (min 8 chars)' })

  const age = calculateAge(birthdate)
  if (age < 18) return res.status(400).json({ error: 'Must be at least 18 years old' })

  // check unique email
  const existing = await prisma.user.findUnique({ where: { email } })
  if (existing) return res.status(409).json({ error: 'Email already registered' })

  const salt = bcrypt.genSaltSync(10)
  const passwordHash = bcrypt.hashSync(password, salt)

  // create user
  try {
    const user = await prisma.user.create({
      data: {
        email,
        passwordHash,
        name,
        birthdate: new Date(birthdate),
        role: 'user',
        pointsBalance: 0,
        referredBy: code_referido || null,
        verified: false
      },
    })

    // award referral points if code_referido corresponds to an existing user id
    if (code_referido) {
      const referrer = await prisma.user.findUnique({ where: { id: code_referido } })
      if (referrer) {
        // update referrer points + create transaction
        await prisma.user.update({ where: { id: referrer.id }, data: { pointsBalance: referrer.pointsBalance + 200 } })
        await prisma.pointsTransaction.create({ data: { userId: referrer.id, type: 'earn', points: 200, reason: 'referral_bonus', relatedOrderId: null } })

        // award referred user
        await prisma.user.update({ where: { id: user.id }, data: { pointsBalance: 100 } })
        await prisma.pointsTransaction.create({ data: { userId: user.id, type: 'earn', points: 100, reason: 'referred_bonus', relatedOrderId: null } })
      }
    }

    // issue JWT token
    const token = jwt.sign({ userId: user.id, email: user.email, role: user.role }, JWT_SECRET, { expiresIn: '7d' })
    res.setHeader('Set-Cookie', `token=${token}; HttpOnly; Path=/; Max-Age=${7 * 24 * 60 * 60}; SameSite=Strict`)

    const safeUser = { id: user.id, email: user.email, name: user.name, role: user.role, pointsBalance: user.pointsBalance }
    return res.status(201).json({ user: safeUser })
  } catch (err) {
    console.error('Register error', err)
    return res.status(500).json({ error: 'Server error' })
  }
}

