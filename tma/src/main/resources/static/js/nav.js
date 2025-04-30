

const sidebar = document.getElementById('sidebar');
const navElements = sidebar.querySelectorAll('button, [href], input, select, textarea, [tabindex]');
const navOpenIcon = document.getElementById('nav-open-icon');
const navClosedIcon = document.getElementById('nav-closed-icon');
const showNavBtn = document.getElementById('show-nav-button');
const hideNavBtn = document.getElementById('hide-nav-button');
var lemon = true;
let removeFocusTrap = null;


function show() {
    sidebar.classList.remove('sidebar-hidden');
    sidebar.classList.add('sidebar-flex');
    navClosedIcon.classList.remove('bg-white');
    setTabIndex(navElements, 0);
//    showNavBtn.classList.add('hidden');
//    hideNavBtn.classList.remove('hidden');

    if (window.innerWidth < 1024) {
        console.log('mobile nav open');
        document.body.classList.add('no-scroll');

        if (!removeFocusTrap) {
            removeFocusTrap = focusTrap(sidebar);
        }
//        lemon = false;
    } else {
//        lemon = true;
        console.log('desktop nav open');
    }

}

function hide() {
    setTabIndex(navElements, -1);
    sidebar.classList.add('sidebar-hidden');
    sidebar.classList.remove('sidebar-flex');
    if (!navClosedIcon.classList.contains('bg-white')) {
        navClosedIcon.classList.add('bg-white');
    }
//    showNavBtn.classList.remove('hidden');
//    hideNavBtn.classList.add('hidden');

    if (removeFocusTrap) {
        removeFocusTrap();
        removeFocusTrap = null;
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

//TODO do i need this
window.addEventListener('DOMContentLoaded', () => {
  document.body.classList.add('js-loaded');
  setSidebarClass();
});

window.addEventListener('resize', setSidebarClass);

function isMobileNavOpen() {
  return (
    window.innerWidth < 1024 &&
    !sidebar.classList.contains('sidebar-flex')
  );
}

//showNavBtn.addEventListener('click', focusTrap);
//showNavBtn.addEventListener('keydown', focusTrap);

function focusTrap(element) {

    const focusableElements = element.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])');

    if (focusableElements.length === 0) return () => {};

    const firstElement = focusableElements[0];
    const lastElement = focusableElements[focusableElements.length - 1];


    function handleKeydown(e) {

        if (e.key !== 'Tab') {
            if (e.key === 'Escape') {
                hide();
                return;
            }
            return;
        }

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
        console.log('remove event listener');
    };
}


// Trap Focus for mobile nav
//TODO: test this properly - on large screens your trapped and if you press escape the menu disappears
//showNavBtn.addEventListener('keydown', function (e) {
//    //TODO: logic is wrong check it - lemon theory works
//    //  if (!sidebar.classList.contains('sidebar-flex' || window.innerWidth >= 1024)) return;
//    if (e.key !== 'Space' || e.key !== 'Enter' || window.innerWidth >= 1024) {
//        console.log('skip focus trap');
//        return;
//    }
//    console.log(e);
//
//    const focusableElements = [
//      ...sidebar.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'),
//      hideNavBtn
//    ];
//    console.log(focusableElements);
//    const firstElement = focusableElements[0];
//    const lastElement = focusableElements[focusableElements.length - 1];
//
//    if (document.activeElement === lastElement && !e.shiftKey) {
//        e.preventDefault();
//        firstElement.focus();
//        console.log('last element and tab');
//        console.log('active element -> ' + document.activeElement);
//    }
//
//    if (document.activeElement === firstElement && e.shiftKey) {
//        e.preventDefault();
//        lastElement.focus();
//        console.log('first element and shift');
//        console.log('active element -> ' + document.activeElement);
//    }
//
//    if (e.key === 'Escape') {
//        hide();
//    }
//});