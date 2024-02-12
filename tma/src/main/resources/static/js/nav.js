const sidebar = document.querySelector('.sidebar');
sidebar.querySelector('.blocker').onclick = hide;

const open = document.querySelector('.show-nav-btn');
const close = document.querySelector('.hide-nav-btn');

function show() { // swipe right
    sidebar.classList.remove('hidden');
    sidebar.classList.add('flex');
    sidebar.classList.add('lg:flex');

    close.classList.remove('hidden');
    close.classList.remove('lg:hidden');
    open.classList.add('hidden');
    open.classList.remove('lg:hidden');


    document.body.style.overflow = 'hidden';
    console.log("SHOW");

}
function hide() { // by blocker click, swipe left, or url change
    sidebar.classList.remove('flex');
    sidebar.classList.remove('lg:flex');
    sidebar.classList.add('hidden');

    open.classList.remove('hidden');
    open.classList.remove('lg:hidden');
    close.classList.add('hidden');
    close.classList.add('lg:hidden');


    document.body.style.overflow = '';
    console.log("HIDE");
}
function toggle() {
    sidebar.classList.contains('flex') || sidebar.classList.contains('lg:flex') ? hide() : show();
}