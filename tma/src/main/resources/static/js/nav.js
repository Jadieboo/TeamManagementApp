

const sidebar = document.getElementById('sidebar');
const navElements = sidebar.querySelectorAll('button, [href], input, select, textarea, [tabindex]');
const showNavBtn = document.getElementById('show-nav-button');
const hideNavBtn = document.getElementById('hide-nav-button');
let removeFocusTrap = null;


function show() {
    sidebar.classList.remove('sidebar-hidden');
    sidebar.classList.add('sidebar-flex');
    showNavBtn.classList.add('hidden');
    hideNavBtn.classList.remove('hidden');

    setTabIndex(navElements, 0);

    if (window.innerWidth < 1024) {
        document.body.classList.add('no-scroll');

        if (!removeFocusTrap) {
            removeFocusTrap = focusTrap(sidebar);
        }
    }

}

function hide() {
    setTabIndex(navElements, -1);
    sidebar.classList.add('sidebar-hidden');
    sidebar.classList.remove('sidebar-flex');
    showNavBtn.classList.remove('hidden');
    hideNavBtn.classList.add('hidden');

    if (removeFocusTrap) {
        removeFocusTrap();
        removeFocusTrap = null;
        showNavBtn.focus();
    }

    if (window.innerWidth < 1024) {
        document.body.classList.remove('no-scroll');
    }

}

function toggle() {
    if (sidebar.classList.contains('sidebar-hidden')) {
        show();
    } else {
        hide();
    }
}

function setSidebarClass() {
    if (window.innerWidth >= 1024) {
            show();
    } else {
            hide();
    }
}

function setTabIndex(elements, value) {
    elements.forEach(element => element.tabIndex = value);
}

window.addEventListener('DOMContentLoaded', () => {
  document.body.classList.add('js-loaded');
  setSidebarClass();
});

window.addEventListener('resize', setSidebarClass);

function focusTrap(element) {

    const focusableElements = Array.from(element.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'));

    if (focusableElements.length === 0) return () => {};

    const firstElement = focusableElements[0];
    const lastElement = focusableElements[focusableElements.length - 1];

    setTimeout(() => {
        firstElement.focus();
    }, 0);


    function handleKeydown(e) {

        if (e.key === 'Escape') {
            hide();
            return;
        }

        if (e.key !== 'Tab') return;

        if (e.shiftKey) {
            if (document.activeElement === firstElement) {
                e.preventDefault();
                lastElement.focus();
            }
        } else {
            if (document.activeElement === lastElement) {
                e.preventDefault();
                firstElement.focus();
            }
        }
    }

    element.addEventListener('keydown', handleKeydown);

    return () => {
        element.removeEventListener('keydown', handleKeydown);
    };
}
