const { PrismaClient } = require('@prisma/client')
const prisma = new PrismaClient()

async function main() {
  const referrer = await prisma.user.create({
    data: {
      email: 'referrer@example.com',
      passwordHash: 'seeded-password-hash',
      name: 'Referrer Seed',
      birthdate: new Date('1990-01-01'),
      role: 'user',
      pointsBalance: 1000,
      verified: true
    }
  })

  console.log('Referrer created with id:', referrer.id)
}

main()
  .catch(e => {
    console.error(e)
    process.exit(1)
  })
  .finally(async () => {
    await prisma.$disconnect()
  })

