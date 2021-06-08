document.addEventListener('DOMContentLoaded', function() {
    var sidenav = document.querySelectorAll('.sidenav');
    var instancesSideNav = M.Sidenav.init(sidenav, {});
    var modal = document.querySelectorAll('.modal');
    var instancesModal = M.Modal.init(modal, {opacity: 0.3});
});






// text input
// $(document).ready(function() {
//   $('input#input_text, textarea#textarea2').characterCounter();
// });