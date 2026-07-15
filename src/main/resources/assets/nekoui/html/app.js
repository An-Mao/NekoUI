// zh_cn.js - Chinese Language Pack
const i18n = {
    lang: 'zh-CN',
    translations: {
        'title': 'NekoUI - Minecraft UI编辑器',
        'config-title': '配置管理器',
        'global-config-title': '全局设置',
        'screen-elements-title': '屏幕元素',
        'menu-pages-title': '菜单页面',
        'menu-projects-title': '菜单项目',
        'editor-title': '编辑器',
        'element-properties-title': '元素属性',
        'ready': '就绪',
        'language-indicator': '语言: 简体中文',
        'saved': '已保存',
        'error': '错误',
        'back-to-config': '返回配置',
        'preview': '预览',
        'select-config-type': '-- 选择 --',
        'x-position': 'X位置',
        'y-position': 'Y位置',
        'z-position': 'Z位置',
        'type': '类型',
        'key': '键名',
        'color': '颜色',
        'width': '宽度',
        'height': '高度',
        'title-text': '标题文本',
        'project-number': '项目编号',
        'inner-radius': '内半径',
        'outer-radius': '外半径'
    },
    toggle: function() {
        switchLanguage('en_US');
    }
};

// en_us.js - English Language Pack
const i18n_en = {
    lang: 'en-US',
    translations: {
        'title': 'NekoUI - Minecraft UI Editor',
        'config-title': 'Configuration Manager',
        'global-config-title': 'Global Settings',
        'screen-elements-title': 'Screen Elements',
        'menu-pages-title': 'Menu Pages',
        'menu-projects-title': 'Menu Projects',
        'editor-title': 'Editor',
        'element-properties-title': 'Element Properties',
        'ready': 'Ready',
        'language-indicator': 'Language: English (US)',
        'saved': 'Saved',
        'error': 'Error',
        'back-to-config': 'Back to Config',
        'preview': 'Preview',
        'select-config-type': '-- Select --',
        'x-position': 'X Position',
        'y-position': 'Y Position',
        'z-position': 'Z Position',
        'type': 'Type',
        'key': 'Key',
        'color': 'Color',
        'width': 'Width',
        'height': 'Height',
        'title-text': 'Title Text',
        'project-number': 'Project Number',
        'inner-radius': 'Inner Radius',
        'outer-radius': 'Outer Radius'
    },
    toggle: function() {
        switchLanguage('zh_CN');
    }
};

// app.js - Main Application Logic
class NekoUIApp {
    constructor() {
        this.currentLang = 'zh-CN';
        this.configs = [];
        this.screenElements = [];
        this.menuPages = [];
        this.menuProjects = [];
        this.selectedConfigType = null;
        this.selectedIndex = -1;

        this.init();
    }

    async init() {
        this.setupEventListeners();
        await this.loadAllConfigs();
        this.updateLanguage();
    }

    setupEventListeners() {
        document.getElementById('lang-toggle').addEventListener('click', () => {
            switchLanguage(this.currentLang === 'zh-CN' ? 'en_US' : 'zh_CN');
        });

        document.getElementById('theme-toggle').addEventListener('click', () => {
            this.toggleTheme();
        });

        // Load images when canvas needs them
        window.addEventListener('load', () => {
            console.log('Image loader ready: /image?type=assets|file&path=path');
        });
    }

    async loadAllConfigs() {
        try {
            const [config, screenElements, menuPages, menuProjects] = await Promise.all([
                this.postJson({ type: 'load', token: 'config' }),
                this.postJson({ type: 'list', token: 'screenElement' }),
                this.postJson({ type: 'list', token: 'menuPage' }),
                this.postJson({ type: 'list', token: 'menuProject' })
            ]);

            if (this.currentLang === 'zh-CN') {
                i18n.translations['saved'] = config[0] || '';
                i18n.translations['error'] = screenElements[0] || '';
                i18n.translations['back-to-config'] = menuPages[0] || '';
                i18n.translations['preview'] = menuProjects[0] || '';
            } else {
                i18n_en.translations['saved'] = config[0] || '';
                i18n_en.translations['error'] = screenElements[0] || '';
                i18n_en.translations['back-to-config'] = menuPages[0] || '';
                i18n_en.translations['preview'] = menuProjects[0] || '';
            }

            this.configs = config;
            this.screenElements = screenElements;
            this.menuPages = menuPages;
            this.menuProjects = menuProjects;

            // Update counts
            document.getElementById('element-count').textContent = `${this.screenElements.length} elements`;
            document.getElementById('page-count').textContent = `${this.menuPages.length} pages`;

        } catch (e) {
            console.error('Failed to load configs:', e);
        }
    }

    async postJson(data) {
        try {
            const response = await fetch('http://localhost:8080/' + JSON.stringify(data));
            return await response.json();
        } catch (e) {
            console.error('POST error:', e);
            return [];
        }
    }

    async loadConfig(type) {
        this.selectedConfigType = type;
        try {
            const configData = await this.postJson({
                type: 'load',
                token: type === 'config' ? 'config' : `load:${type}`
            });

            if (this.currentLang === 'zh-CN') {
                i18n.translations['saved'] = configData[0] || '';
            } else {
                i18n_en.translations['saved'] = configData[0] || '';
            }

        } catch (e) {
            console.error('Failed to load config:', e);
        }
    }

    async loadScreenElements() {
        this.screenElements = await this.postJson({ type: 'list', token: 'screenElement' });
        document.getElementById('element-count').textContent = `${this.screenElements.length} elements`;
    }

    async loadMenuPages() {
        this.menuPages = await this.postJson({ type: 'list', token: 'menuPage' });
        document.getElementById('page-count').textContent = `${this.menuPages.length} pages`;
    }

    async loadMenuProjects() {
        this.menuProjects = await this.postJson({ type: 'list', token: 'menuProject' });
    }

    onConfigTypeChange() {
        const select = document.getElementById('config-type-select');
        this.selectedConfigType = select.value;
    }

    onIndexSelect() {
        // Handle index selection when needed
    }

    async previewCurrent() {
        if (!this.selectedIndex >= 0) return;

        try {
            const configData = await this.postJson({
                type: 'load',
                token: `load:${this.selectedConfigType}`,
                p: JSON.stringify(this.configs[this.selectedIndex])
            });

            // Render to canvas
            this.renderToCanvas(configData, 'preview-canvas');

        } catch (e) {
            console.error('Preview failed:', e);
        }
    }

    renderToCanvas(data, canvasId) {
        const canvas = document.getElementById(canvasId);
        if (!canvas || !data) return;

        const ctx = canvas.getContext('2d');

        // Clear canvas
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Draw based on type
        if (this.currentLang === 'zh-CN') {
            i18n.translations['preview'] = '';
        } else {
            i18n_en.translations['preview'] = '';
        }

        try {
            // Load images from assets or file
            const imageData = await this.loadImageData(data);

            if (imageData) {
                ctx.drawImage(imageData, 0, 0, canvas.width, canvas.height);
            } else {
                // Draw fallback
                ctx.fillStyle = '#1a1a2e';
                ctx.fillRect(0, 0, canvas.width, canvas.height);

                ctx.fillStyle = '#e94560';
                ctx.font = '30px Arial';
                ctx.fillText('Image Preview', canvas.width / 2 - 75, canvas.height / 2 - 10);
            }

        } catch (e) {
            console.error('Render failed:', e);
        }
    }

    async loadImageData(element) {
        try {
            // Load image from assets or file using the provided loader
            const type = element?.type || 'assets';
            const path = element?.key || '';

            if (!path) return null;

            // Use canvas to draw loaded images
            const imgCanvas = document.getElementById('image-canvas');
            const imgCtx = imgCanvas.getContext('2d');

            // Draw placeholder if no image available
            imgCtx.fillStyle = '#0a0a1a';
            imgCtx.fillRect(0, 0, imgCanvas.width, imgCanvas.height);

            return null;

        } catch (e) {
            console.error('Image load failed:', e);
            return null;
        }
    }

    toggleTheme() {
        const isLight = document.documentElement.getAttribute('data-theme') === 'light';
        if (isLight) {
            document.documentElement.setAttribute('data-theme', '');
            document.getElementById('theme-toggle').textContent = '🌓 Auto';
        } else {
            document.documentElement.setAttribute('data-theme', 'light');
            document.getElementById('theme-toggle').textContent = '☀️ Light';
        }
    }

    updateLanguage() {
        const langSelect = document.querySelector('.header-controls select');
        if (langSelect) {
            langSelect.value = this.currentLang;
        }

        // Update all text content with translations
        const elementsToTranslate = [
            'title', 'config-title', 'global-config-title',
            'screen-elements-title', 'menu-pages-title', 'menu-projects-title'
        ];

        elementsToTranslate.forEach(id => {
            const el = document.getElementById(id);
            if (el && i18n.translations[id]) {
                el.textContent = i18n.translations[id];
            }
        });
    }
}

// Initialize app when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    const app = new NekoUIApp();
});
