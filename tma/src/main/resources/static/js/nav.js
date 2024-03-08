const sidebar = document.querySelector('.sidebar');
sidebar.querySelector('.blocker').onclick = hide;

const showNavBtn = document.querySelector('.show-nav-btn');
const hideNavBtn = document.querySelector('.hide-nav-btn');

function show() { // swipe right
    sidebar.classList.remove('hidden');
    sidebar.classList.remove('sidebar-hidden');
    sidebar.classList.add('sidebar-flex');

    hideNavBtn.classList.remove('hidden');
    showNavBtn.classList.add('hidden');


    // document.body.style.overflow = 'hidden';
    console.log("SHOW");

}
function hide() { // by blocker click, swipe left, or url change
    sidebar.classList.add('sidebar-hidden');
    sidebar.classList.remove('sidebar-flex');

    showNavBtn.classList.remove('hidden');
    hideNavBtn.classList.add('hidden');



    document.body.style.overflow = '';
    console.log("HIDE");
}
function toggle() {
    sidebar.classList.contains('hidden') || sidebar.classList.contains('sidebar-hidden') ? show() : hide();
}