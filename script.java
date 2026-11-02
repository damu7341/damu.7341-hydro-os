// Hydro S - Space OS - Fixed for buttons
window.addEventListener('DOMContentLoaded', function(){

// visits
let v = localStorage.getItem('hydroVisits');
v = v ? parseInt(v)+1 : 1;
localStorage.setItem('hydroVisits', v);
document.getElementById('visitCount').innerText = 'Visits:'+v;

// time
function updateTime(){
  let n = new Date();
  let clock = document.getElementById('clockDisplay');
  let top = document.getElementById('topTime');
  let date = document.getElementById('dateDisplay');
  if(clock) clock.innerText = n.toLocaleTimeString();
  if(top) top.innerText = n.toLocaleTimeString().slice(0,5);
  if(date) date.innerText = n.toDateString();
}
setInterval(updateTime,1000);
updateTime();

// calendar
function buildCal(){
  let n = new Date();
  let y = n.getFullYear();
  let m = n.getMonth();
  let first = new Date(y,m,1).getDay();
  let total = new Date(y,m+1,0).getDate();
  let title = document.getElementById('calTitle');
  if(title) title.innerText = n.toLocaleString('default',{month:'long',year:'numeric'});
  let g = document.getElementById('calGrid');
  if(!g) return;
  g.innerHTML='';
  let names = ['Su','Mo','Tu','We','Th','Fr','Sa'];
  for(let x of names){ let d=document.createElement('div'); d.className='day-name'; d.innerText=x; g.appendChild(d); }
  for(let i=0;i<first;i++){ g.appendChild(document.createElement('div')); }
  for(let i=1;i<=total;i++){
    let d=document.createElement('div'); d.className='day'; d.innerText=i;
    if(i==n.getDate()){ d.style.background='#302b63'; d.style.fontWeight='bold'; }
    g.appendChild(d);
  }
}
buildCal();

// WINDOW FUNCTIONS - made global so buttons can find them
window.toggleWin = function(id){ 
  let el = document.getElementById(id);
  if(el) el.classList.toggle('active'); 
}
window.closeWin = function(id){ 
  let el = document.getElementById(id);
  if(el) el.classList.remove('active'); 
}

// draggable
function drag(id){
  let win = document.getElementById(id);
  if(!win) return;
  let head = win.querySelector('.win-header');
  if(!head) return;
  let down=false, sx, sy, sl, st;
  head.onmousedown = function(e){ down=true; sx=e.clientX; sy=e.clientY; sl=win.offsetLeft; st=win.offsetTop; }
  document.onmouseup = function(){ down=false; }
  document.onmousemove = function(e){
    if(!down) return;
    win.style.left = (sl + e.clientX - sx) + 'px';
    win.style.top = (st + e.clientY - sy) + 'px';
  }
}
drag('clockWin'); drag('calWin'); drag('calcWin'); drag('notesWin'); drag('termWin');

// wallpaper
let walls = [
  'https://images.unsplash.com/photo-1531306728370-e2ebd9d7bb99?q=80&w=1974',
  'https://images.unsplash.com/photo-1451187580459-43490279c0fa?q=80&w=2072',
  'https://images.unsplash.com/photo-1462331940025-496dfbfc7564?q=80&w=2011'
];
let wi=0;
window.changeWall = function(){
  wi++; if(wi>=walls.length) wi=0;
  document.body.style.backgroundImage = "url('"+walls[wi]+"')";
}

// calculator
let calcVal = '';
window.appendCalc = function(c){
  calcVal += c;
  document.getElementById('calcDisplay').value = calcVal;
}
window.clearCalc = function(){
  calcVal=''; document.getElementById('calcDisplay').value='';
}
window.backCalc = function(){
  calcVal = calcVal.slice(0,-1);
  document.getElementById('calcDisplay').value = calcVal;
}
window.doCalc = function(){
  try{
    if(calcVal.includes('%')){
      calcVal = calcVal.replace('%','/100');
    }
    let ans = eval(calcVal);
    document.getElementById('calcDisplay').value = ans;
    calcVal = ans.toString();
  }catch{
    document.getElementById('calcDisplay').value = 'Error';
    calcVal='';
  }
}

// notes
window.saveNotes = function(){
  localStorage.setItem('myNotes', document.getElementById('notesArea').value);
  alert('saved');
}
let saved = localStorage.getItem('myNotes');
if(saved) {
  let area = document.getElementById('notesArea');
  if(area) area.value = saved;
}

// battery
if(navigator.getBattery){
  navigator.getBattery().then(function(b){
    let bat = document.getElementById('bat');
    if(bat) bat.innerText = Math.round(b.level*100)+'%';
  });
}

// quote
let qs = ['Space is infinite', 'We are made of stardust', 'Hydro S launching...'];
let q = document.getElementById('quote');
if(q) q.innerText = qs[Math.floor(Math.random()*qs.length)];

// terminal
let inp = document.getElementById('termInput');
let tBody = document.getElementById('termBody');
if(inp){
  inp.addEventListener('keydown', function(e){
    if(e.key=='Enter'){
      let raw = this.value;
      let cmd = raw.toLowerCase().trim();
      let d = document.createElement('div');
      d.innerHTML = '> '+raw+'<br>';
      if(cmd=='help') d.innerHTML+='help, time, date, clear, echo, hydro, calc, wallpaper, visits';
      else if(cmd=='time') d.innerHTML+=new Date().toLocaleTimeString();
      else if(cmd=='date') d.innerHTML+=new Date().toDateString();
      else if(cmd=='wallpaper'){ window.changeWall(); d.innerHTML+='wallpaper changed'; }
      else if(cmd=='clear'){ tBody.innerHTML=''; this.value=''; return; }
      else if(cmd.startsWith('echo ')) d.innerHTML+=raw.slice(5);
      else if(cmd=='hydro') d.innerHTML+='Hydro S Space OS v1.2';
      else if(cmd=='visits') d.innerHTML+='visits: '+v;
      else if(cmd=='calc') { window.toggleWin('calcWin'); d.innerHTML+='opened calculator'; }
      else d.innerHTML+='unknown: '+cmd;
      d.innerHTML+='<br><br>';
      tBody.appendChild(d);
      tBody.scrollTop = tBody.scrollHeight;
      this.value='';
    }
  });
}

}); // end DOMContentLoaded
