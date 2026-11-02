// Hydro S - Fixed JS - Space Edition

// Top Bar Time
function updateTime() {
    const now = new Date();
    const clockDisplay = document.getElementById('clockDisplay');
    const topTime = document.getElementById('topTime');
    const dateDisplay = document.getElementById('dateDisplay');
    
    if (clockDisplay) clockDisplay.innerText = now.toLocaleTimeString();
    if (topTime) topTime.innerText = now.toLocaleTimeString().slice(0, 5);
    if (dateDisplay) dateDisplay.innerText = now.toDateString();
}

// Calendar - Fixed
function buildCalendar() {
    const now = new Date();
    const year = now.getFullYear();
    const month = now.getMonth();
    const firstDay = new Date(year, month, 1).getDay();
    const days = new Date(year, month + 1, 0).getDate();

    const title = document.getElementById('calTitle');
    const grid = document.getElementById('calGrid');
    if (!title || !grid) return;

    title.innerText = now.toLocaleString('default', { month: 'long', year: 'numeric' });
    grid.innerHTML = '';

    ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'].forEach(n => {
        let d = document.createElement('div');
        d.className = 'day-name';
        d.innerText = n;
        grid.appendChild(d);
    });

    for (let i = 0; i < firstDay; i++) {
        let d = document.createElement('div');
        d.className = 'day';
        grid.appendChild(d);
    }

    for (let i = 1; i <= days; i++) {
        let d = document.createElement('div');
        d.className = 'day';
        d.innerText = i;
        if (i === now.getDate()) d.classList.add('today');
        grid.appendChild(d);
    }
}

// Window Controls - Fixed
function toggleWin(id) {
    const win = document.getElementById(id);
    if (win) win.classList.toggle('active');
}

function closeWin(id) {
    const win = document.getElementById(id);
    if (win) win.classList.remove('active');
}

// Visits Counter - Fixed with localStorage
let visits = localStorage.getItem('hydroVisits');
visits = visits ? parseInt(visits) + 1 : 1;
localStorage.setItem('hydroVisits', visits);
const visitEl = document.getElementById('visitCount');
if (visitEl) visitEl.innerText = 'Visits: ' + visits;

// Terminal - Fixed
const termInput = document.getElementById('termInput');
const termBody = document.getElementById('termBody');

if (termInput && termBody) {
    termInput.addEventListener('keydown', function (e) {
        if (e.key === 'Enter') {
            const raw = this.value.trim();
            if (raw === '') return;
            
            const cmd = raw.toLowerCase();
            let out = document.createElement('div');
            out.innerHTML = '> ' + raw + '<br>';
            
            const now = new Date();
            if (cmd === 'help') {
                out.innerHTML += 'Commands: help, time, date, clear, echo [text], hydro, visits';
            } else if (cmd === 'time') {
                out.innerHTML += 'Local Time: ' + now.toLocaleTimeString();
            } else if (cmd === 'date') {
                out.innerHTML += 'Local Date: ' + now.toLocaleDateString();
            } else if (cmd === 'clear') {
                termBody.innerHTML = '';
                this.value = '';
                return;
            } else if (cmd.startsWith('echo ')) {
                out.innerHTML += raw.substring(5);
            } else if (cmd === 'hydro') {
                out.innerHTML += 'Hydro S v1.0 - Space Edition - All systems nominal';
            } else if (cmd === 'visits') {
                out.innerHTML += 'Total visits: ' + visits;
            } else {
                out.innerHTML += 'Unknown command: ' + cmd + " (type 'help')";
            }
            
            out.innerHTML += '<br><br>';
            termBody.appendChild(out);
            termBody.scrollTop = termBody.scrollHeight;
            this.value = '';
        }
    });
}

// Init - Fixed
setInterval(updateTime, 1000);
updateTime();
buildCalendar();
