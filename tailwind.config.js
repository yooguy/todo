/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/main/resources/templates/**/*.html"],
    theme: {
        extend: {},
    },
    plugins: [
        require('@tailwindcss/forms'),
        require('@tailwindcss/typography'),
        require('@tailwindcss/aspect-ratio'),
        require('@tailwindcss/line-clamp')
    ],
}