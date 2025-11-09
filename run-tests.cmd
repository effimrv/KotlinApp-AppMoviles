@echo off
REM run-tests.cmd - Automatiza instalación, prisma generate y ejecución de tests (cmd.exe)
SETLOCAL

echo Installing dependencies for apps/web...
cd "%~dp0apps\web"
npm install || (echo npm install failed & exit /b 1)

echo Installing dependencies for packages/db...
cd "%~dp0packages\db"
npm install || (echo npm install failed & exit /b 1)

echo Generating Prisma client...
npx prisma generate --schema="%~dp0packages\db\prisma\schema.prisma" || (echo prisma generate failed & exit /b 1)

echo Running Prisma db push for test (ensure DATABASE_URL is not production)...
npx prisma db push --schema="%~dp0packages\db\prisma\schema.prisma" || (echo prisma db push failed & exit /b 1)

echo Running tests (apps/web)...
cd "%~dp0apps\web"
npm test || (echo tests failed & exit /b 1)

echo All done.
ENDLOCAL
exit /b 0

