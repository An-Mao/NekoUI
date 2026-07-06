// main.js
document.addEventListener('DOMContentLoaded', () => {
    console.log("NekoUI 配置面板已加载...");

    // ================================================
    // 1. 初始化与状态管理 (Initialization & State)
    // ================================================

    let currentLanguage = 'zh_CN'; // 默认语言
    const API_ENDPOINT = '/api/nekoui/config'; // 假设的POST请求端点

    /**
     * @type {object} 全局状态存储当前激活的主题 ('light' | 'dark')
     */
    let currentTheme = 'light'; 
    
    // TODO: 初始化语言选择器，调用 i18n.loadLanguage(currentLanguage)
    initializeLocaleSelector();

    // ================================================
    // 2. 主题和国际化 (Theming & I18N)
    // ================================================

    /**
     * 根据系统设置或用户偏好初始化主题。
     */
    function initializeTheme() {
        // TODO: 实现从 localStorage 或操作系统获取初始主题的逻辑
        document.body.setAttribute('data-theme', 'light'); // 默认值
    }

    /**
     * 处理语言切换。需要调用 POST API 来验证或更新用户偏好（如果适用）。
     * @param {string} langCode - 新的语言代码 (e.g., 'zh_CN')
     */
    function switchLanguage(langCode) {
        // 1. 更新页面文本内容（通过 i18n 库）
        console.log(`切换语言到: ${langCode}`);
        // TODO: 实现根据 langCode 读取并渲染所有界面文本的逻辑

        // 2. (可选) 发送 POST 请求更新服务器端的用户偏好设置
        fetch(API_ENDPOINT, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ preference: 'language', value: langCode })
        })
        .then(() => console.log(`语言偏好已更新到 ${langCode}`))
        .catch(err => console.error('更新语言偏好失败:', err));
    }


    // ================================================
    // 3. 模块切换与配置加载 (Module Switching & Data Fetching)
    // ================================================

    /**
     * 根据侧边栏选择的模块，显示对应的编辑区域。
     * @param {string} moduleId - 'general', 'screen_elements', 或 'menu_pages'
     */
    function loadConfigModule(moduleId) {
        console.log(`加载配置模块: ${moduleId}`);
        // TODO: 清空 #config-editor 内容，并根据 module ID 渲染对应的表单结构。
        const editor = document.getElementById('data-form');
        if (editor) {
            // TODO: 这里的逻辑需要高度依赖数据模型来动态生成输入控件（下拉框、开关等）。
        }

        // 触发加载配置的异步流程，该流程必须使用 POST 请求。
        fetchConfigData(moduleId);
    }
    
    /**
     * 通过模拟或实际 POST 请求获取配置数据。
     * @param {string} moduleId - 当前模块ID
     */
    async function fetchConfigData(moduleId) {
        const endpoint = `${API_ENDPOINT}?module=${moduleId}`;
        console.log(`正在使用POST请求读取配置：${endpoint}`);

        try {
            // ！！！核心要求：必须使用 POST 请求获取数据 !!!
            const response = await fetch(endpoint, {
                method: 'POST', 
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({ action: 'read_config', module: moduleId })
            });

            if (!response.ok) throw new Error('配置读取失败');
            const data = await response.json();

            // TODO: 将接收到的 data 结构化，并调用渲染器来生成 DOM 控件。
            renderConfigFields(data);

        } catch (error) {
            console.error("无法加载配置:", error);
            document.getElementById('config-editor').innerHTML = `<p style="color: red;">${__('error_message', '未能连接到NekoUI服务，请检查网络或后台进程。')}</p>`;
        }
    }

    // ================================================
    // 4. 功能性编辑器 (Advanced Editors)
    // ================================================

    /**
     * 处理 Canvas 可视化预览逻辑。
     * @param {string} elementId - 当前编辑的元素ID（如 screen_element）
     */
    function handleCanvasPreview(elementId) {
        const canvas = document.getElementById('canvas-preview');
        if (!canvas) return;

        const ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        // TODO: 根据元素ID和配置数据，绘制模拟的UI草图。
        console.log(`[预览]: 在画布上重绘 ${elementId} 的可视化效果...`);
    }

    /**
     * 处理图片加载预览逻辑。
     * @param {string} path - 图片路径 (assets|file)
     */
    function handleImagePreview(path, type) {
        console.log(`[图片加载]: 尝试加载图片 ${type}:${path}`);
        const img = new Image();
        // 注意：这需要浏览器环境支持的资源加载机制，这里仅作逻辑展示。
        img.onload = () => {
            // TODO: 将图片绘制到指定的预览元素中，而不是 Canvas（如果只是显示静态图）
            console.log("图片加载成功，可以用于预览。");
        };
        img.onerror = () => {
            alert(`无法加载图片：路径错误或资源不可用 (${path})`);
        };
        // 实际应用中，URL会是 /image?type=${type}&path=${encoded_path}
        img.src = `/image?type=${type}&path=${encodeURIComponent(path)}`;
    }

    // ================================================
    // 5. 事件监听器绑定 (Event Listeners)
    // ================================================

    function setupEventListeners() {
        // 模块侧边栏点击事件
        document.querySelector('.sidebar ul').addEventListener('click', (e) => {
            const module = e.target.getAttribute('data-module');
            if (module) {
                loadConfigModule(module);
            }
        });

        // 主题切换按钮监听器
        document.getElementById('theme-toggle').addEventListener('click', () => {
            // TODO: 切换 currentTheme 状态，并更新 body 的 data-theme 属性。
            const body = document.body;
            let newTheme = body.getAttribute('data-theme') === 'light' ? 'dark' : 'light';
            body.setAttribute('data-theme', newTheme);
            currentTheme = newTheme;
        });

        // 保存按钮监听器 (POST请求)
        document.getElementById('save-config').addEventListener('click', () => {
            const formData = collectFormData(); // 收集当前表单数据
            if (formData.length === 0) return alert("无数据可保存。");

            fetch(API_ENDPOINT, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({ action: 'save_config', payload: formData })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('配置保存成功!');
                } else {
                    alert(`保存失败：${data.message || '未知错误'}`);
                }
            })
            .catch(error => console.error("提交到服务器时出错:", error));
        });
    }

    // --- 辅助函数占位符 ---
    function collectFormData() {
        // TODO: 从 DOM 中收集所有 form-group 的值，生成一个包含key/value的数组或对象。
        return []; // 示例返回空数组
    }

    /**
     * 核心初始化函数：调用所有子系统设置监听器和初始状态。
     */
    setupEventListeners();
    initializeTheme();

    // 模拟模块加载：默认加载通用配置
    loadConfigModule('general');
});