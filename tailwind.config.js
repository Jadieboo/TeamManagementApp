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
    extend: {},
  },
  plugins: [],
}
