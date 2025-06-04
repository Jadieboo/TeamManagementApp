/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
      "./tma/src/main/resources/templates/*.html",
      "./tma/src/main/resources/templates/fragments/*.html",
      "./tma/src/main/resources/static/js/*.js"
  ],
  theme: {
    container: {
      center: true,
    },
    extend: {
        boxShadow: {
            'xl-mid': '0 15px 20px -10px rgba(0, 0, 0, 0.5)', // halfway vibes
        },
        outline: {
            'black-1': '1px solid black',
        }
    },
  },
  plugins: [],
}
