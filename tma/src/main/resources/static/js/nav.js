

const sidebar = document.getElementById('sidebar');

function show() {
    sidebar.classList.remove('sidebar-hidden');
    sidebar.classList.add('sidebar-flex');

    if (window.innerWidth < 1024) {
        document.body.classList.add('no-scroll');
    }

}

function hide() {
    sidebar.classList.add('sidebar-hidden');
    sidebar.classList.remove('sidebar-flex');

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

window.addEventListener('DOMContentLoaded', () => {
  document.body.classList.add('js-loaded');
  setSidebarClass();
});

window.addEventListener('resize', setSidebarClass);

// Trap Focus for mobile nav
//TODO: test this properly
document.addEventListener('keydown', function (e) {
  if (!sidebar.classList.contains('sidebar-flex')) return;

  const focusableElements = sidebar.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])');
  const first = focusableElements[0];
  const last = focusableElements[focusableElements.length - 1];

  if (e.key === 'Tab') {
    if (e.shiftKey && document.activeElement === first) {
      e.preventDefault();
      last.focus();
    } else if (!e.shiftKey && document.activeElement === last) {
      e.preventDefault();
      first.focus();
    }
  }

  if (e.key === 'Escape') {
    hide();
  }
});


