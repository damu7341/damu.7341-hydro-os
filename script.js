// Hydro S - Space OS - script.js (JavaScript)

// ===== VISITS =====
let visits = localStorage.getItem('hv');
visits = visits ? parseInt(visits) + 1 : 1;
localStorage.setItem('hv', visits);
document.getElementById('visits').innerText = 'Visits:' + visits;

// ===== CLOCK WIDGET =====
function tick() {
  let d = new Date();
  document.getElementById('clock').innerText = d.toLocaleTimeString();
  document.getElementById('time').innerText = d.toLocaleTimeString().slice(0,5);
  document.getElementById('date').innerText = d.toDateString();
}
setInterval(tick, 1000);
tick();

// ===== CALENDAR WIDGET =====
function buildCal() {
  let n = new Date();
  document.getElementById('calTitle').innerText = n.toLocaleString('default', {month:'long', year:'numeric'});
  let g = document.getElementById('calGrid');
  g.innerHTML = '';
  let first = new Date(n.getFullYear(), n.getMonth(), 1).getDay();
  let total = new Date(n.getFullYear(), n.getMonth()+1, 0).getDate();
  for (let i = 0; i < first; i++) {
    g.appendChild(document.createElement('div'));
  }
  for (let i = 1; i <= total; i++) {
    let d = document.createElement('div');
    d.innerText = i;
    if (i == n.getDate()) {
      d.className = 'today';
    }
    g.appendChild(d);
  }
}
buildCal();

// ===== BUTTONS WORKING - TOGGLE WIDGET =====
function toggleW(id) {
  let e = document.getElementById(id);
  if (e.style.display === 'none') {
    e.style.display = 'block';
  } else {
    e.style.display = 'none';
  }
}

// ===== WALLPAPER CHANGER - SPACE THEME =====
let bgList = [
  'https://images.unsplash.com/photo-1531306728370-e2ebd9d7bb99?q=80&w=1974',
  'https://images.unsplash.com/photo-1451187580459-43490279c0fa?q=80&w=2072',
  'https://images.unsplash.com/photo-1462331940025-496dfbfc7564?q=80&w=2011'
];
let bgIndex = 0;
function changeBG() {
  bgIndex = (bgIndex + 1) % bgList.length;
  document.body.style.backgroundImage = "url('" + bgList[bgIndex] + "')";
}

// ===== CALCULATOR WIDGET - FULLY WORKING =====
let cv = '';
function cPress(v) {
  cv += v;
  document.getElementById('calcOut').value = cv;
}
function cClear() {
  cv = '';
  document.getElementById('calcOut').value = '';
}
function cBack() {
  cv = cv.slice(0, -1);
  document.getElementById('calcOut').value = cv;
}
function cEqual() {
  try {
    let s = cv.replace('%', '/100');
    let ans = eval(s);
    document.getElementById('calcOut').value = ans;
    cv = ans.toString();
  } catch {
    document.getElementById('calcOut').value = 'Error';
    cv = '';
  }
}

// ===== NOTES WIDGET =====
function saveN() {
  localStorage.setItem('myN', document.getElementById('notes').value);
  alert('Notes saved!');
}
let saved = localStorage.getItem('myN');
if (saved) {
  document.getElementById('notes').value = saved;
}
