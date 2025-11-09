import path from 'path'
import fs from 'fs'
import { execSync } from 'child_process'
import httpMocks from 'node-mocks-http'
import bcrypt from 'bcryptjs'

const schemaPath = path.resolve(__dirname, '..', '..', 'packages', 'db', 'prisma', 'schema.prisma')
const dbFile = path.resolve(__dirname, '..', '..', 'packages', 'db', 'prisma', 'test.db')

jest.setTimeout(120000)

describe('POST /api/auth/login (integration with sqlite)', () => {
  beforeAll(async () => {
    try { fs.unlinkSync(dbFile) } catch (e) { /* ignore */ }
    process.env.DATABASE_URL = `file:${dbFile}`
    try {
      execSync(`npx prisma generate --schema="${schemaPath}"`, { stdio: 'inherit' })
      execSync(`npx prisma db push --schema="${schemaPath}"`, { stdio: 'inherit' })
    } catch (err) {
      console.error('Error preparing prisma test db', err)
      throw err
    }
  })

  afterAll(() => {
    try { fs.unlinkSync(dbFile) } catch (e) { /* ignore */ }
  })

  test('login succeeds with correct credentials and fails with wrong ones', async () => {
    jest.resetModules()
    const { prisma } = require('../../packages/db/src/prisma')
    const handler = require('../pages/api/auth/login').default

    const password = 'supersecret'
    const passwordHash = bcrypt.hashSync(password, 10)

    const created = await prisma.user.create({ data: {
      email: 'login.test@example.com',
      passwordHash,
      name: 'Login Test',
      birthdate: new Date('1990-01-01'),
      role: 'user',
      verified: true
    }})

    // correct credentials
    const req = httpMocks.createRequest({ method: 'POST', url: '/api/auth/login', body: { email: 'login.test@example.com', password } })
    const res = httpMocks.createResponse({ eventEmitter: require('events').EventEmitter })
    await handler(req, res)
    expect(res.statusCode).toBe(200)
    const data = res._getJSONData()
    expect(data.user).toBeDefined()
    expect(data.user.email).toBe('login.test@example.com')
    const cookies = res.getHeader('Set-Cookie')
    expect(cookies).toBeDefined()

    // wrong password
    const req2 = httpMocks.createRequest({ method: 'POST', url: '/api/auth/login', body: { email: 'login.test@example.com', password: 'wrongpass' } })
    const res2 = httpMocks.createResponse({ eventEmitter: require('events').EventEmitter })
    await handler(req2, res2)
    expect(res2.statusCode).toBe(401)
    const data2 = res2._getJSONData()
    expect(data2.error).toMatch(/Invalid credentials/)

    await prisma.$disconnect()
  })
})

