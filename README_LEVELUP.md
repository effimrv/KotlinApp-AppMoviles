Level-Up Gamer — Quickstart

Este repo contiene un scaffold inicial para comenzar el desarrollo del e-commerce "Level-Up Gamer".

Requisitos locales:
- Node >= 18
- npm o pnpm
- Postgres (local o docker)

Comandos ejemplo (desde la raíz):

# instalar dependencias del frontend
cd apps\web
npm install

# levantar dev server (después de instalar deps)
npm run dev

# prisma (después de configurar DATABASE_URL)
cd packages\db
# instalar dependencias para prisma y generar cliente
npm install
# generar cliente de prisma (ejecutar en packages/db)
npm run prisma:generate
# aplicar migraciones locales (interactivo)
npm run prisma:migrate:dev
# abrir Prisma Studio
npm run prisma:studio

# Nota: si prefieres usar Docker para Postgres, puedes levantar un contenedor rápido:
# docker run --name levelup-postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=levelupdb -p 5432:5432 -d postgres:15

# Variables de entorno
# Copia .env.example a .env y completa las variables (usar \"\\\" en rutas de windows si es necesario)
copy .env.example .env

Sigue el backlog definido en docs/ARCHITECTURE.md para los siguientes pasos.

Sugerencia de commits iniciales:
- feat(init): scaffold Next.js app + prisma schema
- chore(ci): add basic GitHub Actions workflow

Para la siguiente tarea puedo:
1) Crear `packages/db/package.json` con scripts de prisma y dependencias (ya lo crearé ahora).
2) Implementar endpoint `POST /api/auth/register` y la página `pages/register.tsx`.
