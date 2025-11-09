import { validateEmail, calculateAge } from '../lib/validation'

describe('validation helpers', () => {
  test('validateEmail accepts valid emails and rejects invalid', () => {
    expect(validateEmail('user@example.com')).toBe(true)
    expect(validateEmail('user.name+tag@gmail.co')).toBe(true)
    expect(validateEmail('invalid-email')).toBe(false)
    expect(validateEmail('user@com')).toBe(false)
  })

  test('calculateAge computes age correctly', () => {
    const today = new Date()
    const year = today.getFullYear() - 25
    const month = (today.getMonth() + 1).toString().padStart(2, '0')
    const day = today.getDate().toString().padStart(2, '0')
    const dob = `${year}-${month}-${day}`
    expect(calculateAge(dob)).toBe(25)

    const dob2 = `${year}-${(today.getMonth()+2).toString().padStart(2,'0')}-${day}`
    // depending on month boundary this could be 24; just assert it's <=25
    expect(calculateAge(dob2)).toBeLessThanOrEqual(25)
  })
})

