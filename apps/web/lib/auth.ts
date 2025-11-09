import type { NextApiRequest } from 'next'
import jwt from 'jsonwebtoken'
import { prisma } from '../../../../../packages/db/src/prisma'

const JWT_SECRET = process.env.NEXTAUTH_SECRET || 'dev_secret_change'

export async function getUserFromRequest(req: NextApiRequest) {
  try {
    const cookie = req.headers?.cookie
    if (!cookie) return null
    const match = cookie.split(';').map(c => c.trim()).find(c => c.startsWith('token='))
    if (!match) return null
    const token = match.split('=')[1]
    if (!token) return null

    const payload = jwt.verify(token, JWT_SECRET) as { userId: string }
    const userId = (payload as any).userId
    if (!userId) return null

    const user = await prisma.user.findUnique({ where: { id: userId } })
    if (!user) return null

    // return safe user object
    return { id: user.id, email: user.email, name: user.name, role: user.role, pointsBalance: user.pointsBalance }
  } catch (err) {
    console.error('getUserFromRequest error', err)
    return null
  }
}

