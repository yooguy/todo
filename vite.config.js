import { defineConfig } from 'vite'
import { viteStaticCopy } from 'vite-plugin-static-copy'
import { execSync } from 'node:child_process'
import fs from 'node:fs'

// 복사 대상 라이브러리
const staticTargets = [
    { src: 'node_modules/vue/dist/vue.global.prod.js', dest: 'vue' },
    { src: 'node_modules/axios/dist/axios.min.js', dest: 'axios' },
    { src: 'node_modules/@shoelace-style/shoelace/cdn/**', dest: 'shoelace-style' },
    { src: 'node_modules/alpinejs/dist/cdn.min.js', dest: 'alpinejs', rename: 'alpine.min.js' },
    { src: 'node_modules/htmx.org/dist/htmx.min.js', dest: 'htmx' },
    { src: 'node_modules/exceljs/dist/exceljs.min.js', dest: 'exceljs' },
    { src: 'node_modules/chart.js/dist/chart.umd.js', dest: 'chartjs' },
    { src: 'node_modules/list.js/dist/list.min.js', dest: 'listjs' },
    { src: 'node_modules/vuetify/dist/vuetify.min.*', dest: 'vuetify' },
    { src: 'node_modules/@mdi/font/css/materialdesignicons.min.css', dest: 'mdi' },
    { src: 'node_modules/@mdi/font/fonts/*', dest: 'fonts' },
    { src: 'node_modules/@toast-ui/editor/dist/**/*.{js,css}', dest: 'toastui' },
    { src: 'node_modules/jsoneditor/dist/*', dest: 'jsoneditor' },
];

export default defineConfig({
    plugins: [
        viteStaticCopy({ targets: staticTargets }),

        // Tailwind CSS CLI 빌드 자동화
        {
            name: 'build-tailwind',
            closeBundle: () => {
                const output = 'src/main/resources/static/vendors/tailwind/tailwind.min.css';
                try {
                    fs.mkdirSync('src/main/resources/static/vendors/tailwind', { recursive: true });
                    execSync(`npx @tailwindcss/cli -i ./tailwind.input.css -o ${output} --minify`);
                    console.log('Tailwind CSS 빌드 완료');
                } catch (err) {
                    console.error('Tailwind CSS 빌드 실패:', err.message);
                }
            }
        }
    ],
    build: {
        outDir: 'src/main/resources/static/vendors',
        emptyOutDir: false,
        rollupOptions: {
            input: 'src/main/resources/static/main.js',
        }
    }
});