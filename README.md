**Database setup**
Requires:
 - Local mySQL database at: 127.0.01
 - Dev test user: uname='testuser' pass='password'
 - Two schemas (can be empty, will be wiped)
   - dbo
   - dbapi

**Clean Database**
`gradle flywayClean`

**Rebuild Database**
`gradle fylwayMigrate`

**Build And Run Tests**
`gradle build`
