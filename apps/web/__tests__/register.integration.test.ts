import path from 'path'
import fs from 'fs'
import { execSync } from 'child_process'
import httpMocks from 'node-mocks-http'

// We'll set DATABASE_URL to a sqlite file in packages/db/prisma/test.db
const schemaPath = path.resolve(__dirname, '..', '..', 'packages', 'db', 'prisma', 'schema.prisma')
const dbFile = path.resolve(__dirname, '..', '..', 'packages', 'db', 'prisma', 'test.db')

jest.setTimeout(120000)

describe('POST /api/auth/register (integration with sqlite)', () => {
  beforeAll(() => {
    // ensure old test DB removed
    try { fs.unlinkSync(dbFile) } catch (e) { /* ignore */ }

    // set env for prisma client before requiring modules
    process.env.DATABASE_URL = `file:${dbFile}`

    // Generate Prisma client and push schema
    try {
      execSync(`npx prisma generate --schema="${schemaPath}"`, { stdio: 'inherit' })
      execSync(`npx prisma db push --schema="${schemaPath}"`, { stdio: 'inherit' })
    } catch (err) {
      console.error('Error preparing prisma test db', err)
      throw err
    }
  })

  afterAll(() => {
    // remove test DB file
    try { fs.unlinkSync(dbFile) } catch (e) { /* ignore */ }
  })

  test('creates a user successfully', async () => {
    // reset module registry to ensure prisma picks up DATABASE_URL
    jest.resetModules()
    const handler = require('../pages/api/auth/register').default
    const { prisma } = require('../../packages/db/src/prisma')

    const email = `test.user+${Date.now()}@example.com`
    const req = httpMocks.createRequest({
      method: 'POST',
      url: '/api/auth/register',
      body: {
        name: 'Test User',
        email,
        password: 'strongpassword',
        birthdate: '1990-01-01',
        country: 'CL',
        accept_tos: true
      }
    })
    const res = httpMocks.createResponse({ eventEmitter: require('events').EventEmitter })

    await handler(req, res)

    expect(res.statusCode).toBe(201)
    const data = res._getJSONData()
    expect(data.user).toBeDefined()
    expect(data.user.email).toBe(email)

    // verify user in db
    const dbUser = await prisma.user.findUnique({ where: { email } })
    expect(dbUser).not.toBeNull()
    expect(dbUser.email).toBe(email)

    await prisma.$disconnect()
  })

  test('rejects underage user', async () => {
    jest.resetModules()
    const handler = require('../pages/api/auth/register').default
    const { prisma } = require('../../packages/db/src/prisma')

    const email = `young.user+${Date.now()}@example.com`
    const req = httpMocks.createRequest({
      method: 'POST',
      url: '/api/auth/register',
      body: {
        name: 'Young User',
        email,
        password: 'strongpassword',
        birthdate: '2010-01-01', // under 18
        country: 'CL',
        accept_tos: true
      }
    })
    const res = httpMocks.createResponse({ eventEmitter: require('events').EventEmitter })

    await handler(req, res)

    expect(res.statusCode).toBe(400)
    const data = res._getJSONData()
    expect(data.error).toMatch(/18/)

    await prisma.$disconnect()
  })
})

