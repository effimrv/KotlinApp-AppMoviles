Level-Up Gamer — Arquitectura (resumen)

Monorepo structure (recommended):
- apps/web — Next.js frontend
- apps/api — optional NestJS backend
- packages/ui — shared components
- packages/db — prisma schema and db utilities
- infra — terraform/k8s/docker manifests

Stack (MVP): Next.js, TypeScript, Prisma, PostgreSQL, Redis, Stripe, SendGrid, Cloudinary, Sentry

Sprint 0 deliverables created in repo:
- apps/web minimal scaffold (Next.js)
- packages/db/prisma/schema.prisma with core models
- CI workflow skeleton (.github/workflows/ci.yml)
- .env.example and .gitignore

Next steps (Sprint 1):
1. Implement Prisma client and run migration locally (requires DATABASE_URL)
2. Implement Auth endpoints and register flow with age verification
3. Create frontend Register/Login pages wired to API

Make small commits per task and open PRs for review.

