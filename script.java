<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Hydro S - Space OS</title>
<style>
* { margin:0; padding:0; box-sizing:border-box; font-family: Segoe UI, sans-serif; }
body {
  background: url('https://images.unsplash.com/photo-1531306728370-e2ebd9d7bb99?q=80&w=1974') no-repeat center center fixed;
  background-size: cover;
  height:100vh; color:white; overflow:hidden;
}
.topbar { height:40px; background:rgba(0,0,0,0.7); display:flex; justify-content:space-between; align-items:center; padding:0 15px; position:relative; z-index:100; }
.desktop { position:relative; width:100%; height:calc(100vh - 100px); padding:20px; }

/* WIDGET STYLE */
.widget {
  position:absolute; background:rgba(30,30,30,0.85); backdrop-filter:blur(15px);
  border-radius:16px; width:300px; box-shadow:0 10px 30px rgba(0,0,0,0.6);
  border:1px solid rgba(255,255,255,0.1); display:flex; flex-direction:column;
}
.widget-header { background:rgba(255,255,255,0.1); padding:12px 15px; display:flex; justify-content:space-between; border-radius:16px 16px 0 0; cursor:move; font-weight:bold; }
.widget-body { padding:15px; }
.widget-close { cursor:pointer; width:20px; height:20px; background:#ff5f56; border-radius:50%; display:flex; align-items:center; justify-content:center; font-size:12px; color:black; }

#clockWidget { top:20px; left:20px; }
#calWidget { top:20px; right:20px; width:320px; }
#calcWidget { top:220px; left:20px; width:280px; }
#notesWidget { top:220px; left:350px; }
#termWidget { bottom:20px; left:50%; transform:translateX(-50%); width:500px; }

#clockDisplay { font-size:38px; text-align:center; font-weight:bold; letter-spacing:2px; }
#dateDisplay { text-align:center; opacity:0.7; margin-top:5px; }
.calendar-grid { display:grid; grid-template-columns:repeat(7,1fr); gap:4px; margin-top:10px; }
.day-name { opacity:0.5; font-size:11px; text-align:center; }
.day { text-align:center; padding:6px; border-radius:6px; font-size:13px; background:rgba(255,255,255,0.05); }
.today { background:#302b63 !important; font-weight:bold; }

.calc-display { width:100%; height:50px; background:black; color:white; border:none; text-align:right; padding:10px; font-size:22px; border-radius:8px; margin-bottom:10px; }
.calc-keys { display:grid; grid-template-columns:repeat(4,1fr); gap:8px; }
.calc-keys button { padding:14px; border:none; border-radius:10px; background:#333; color:white; cursor:pointer; font-size:16px; }
.calc-keys button:active { transform:scale(0.95); }
.calc-keys button.op { background:#ff9500; }
.calc-keys button.equal { background:#302b63; }

.taskbar { position:fixed; bottom:15px; left:50%; transform:translateX(-50%); background:rgba(255,255,255,0.2); backdrop-filter:blur(20px); padding:12px 18px; border-radius:30px; display:flex; gap:10px; z-index:200; }
.task-btn { background:rgba(255,255,255,0.25); border:none; color:white; padding:10px 16px; border-radius:20px; cursor:pointer; font-size:13px; }
.task-btn:hover { background:white; color:black; }

#termBody { background:black; color:#00ff88; height:180px; padding:10px; overflow-y:auto; font-family:monospace; font-size:12px; border-radius:0 0 16px 16px; }
#termInput { background:transparent; border:none; color:white; flex:1; outline:none; font-family:monospace; width:100%; }
#notesArea { width:100%; height:90px; background:#111; color:white; border:1px solid #333; padding:10px; border-radius:8px; resize:none; }
.hidden { display:none !important; }
</style>
</head>
<body>

<div class="topbar">
  <div>Hydro S - Space OS</div>
  <div style="display:flex; gap:15px;"><span id="bat">--%</span><span id="visitCount"></span><span id="topTime"></span></div>
</div>

<div class="desktop">
  <!-- CLOCK WIDGET -->
  <div class="widget" id="clockWidget">
    <div class="widget-header"><span>🕒 Clock</span><div class="widget-close" data-close="clockWidget">x</div></div>
    <div class="widget-body"><div id="clockDisplay">00:00:00</div><div id="dateDisplay"></div></div>
  </div>

  <!-- CALENDAR WIDGET -->
  <div class="widget" id="calWidget">
    <div class="widget-header"><span>📅 Calendar</span><div class="widget-close" data-close="calWidget">x</div></div>
    <div class="widget-body"><div id="calTitle" style="text-align:center; font-weight:bold; margin-bottom:10px;"></div><div class="calendar-grid" id="calGrid"></div></div>
  </div>

  <!-- CALCULATOR WIDGET - FULLY WORKING -->
  <div class="widget" id="calcWidget">
    <div class="widget-header"><span>🧮 Calculator</span><div class="widget-close" data-close="calcWidget">x</div></div>
    <div class="widget-body">
      <input type="text" class="calc-display" id="calcDisplay" readonly placeholder="0">
      <div class="calc-keys">
        <button data-calc="clear">C</button>
        <button data-calc="/">/</button>
        <button data-calc="*">*</button>
        <button data-calc="back">⌫</button>
        <button data-calc="7">7</button>
        <button data-calc="8">8</button>
        <button data-calc="9">9</button>
        <button data-calc="-" class="op">-</button>
        <button data-calc="4">4</button>
        <button data-calc="5">5</button>
        <button data-calc="6">6</button>
        <button data-calc="+" class="op">+</button>
        <button data-calc="1">1</button>
        <button data-calc="2">2</button>
        <button data-calc="3">3</button>
        <button data-calc="=" class="equal">=</button>
        <button data-calc="0">0</button>
        <button data-calc="00">00</button>
        <button data-calc=".">.</button>
        <button data-calc="%">%</button>
      </div>
    </div>
  </div>

  <!-- NOTES WIDGET -->
  <div class="widget" id="notesWidget">
    <div class="widget-header"><span>📝 Notes</span><div class="widget-close" data-close="notesWidget">x</div></div>
    <div class="widget-body">
      <textarea id="notesArea" placeholder="Space notes..."></textarea>
      <button class="task-btn" id="saveBtn" style="margin-top:10px; width:100%;">Save Notes</button>
      <div id="quote" style="margin-top:10px; font-size:11px; opacity:0.6; text-align:center;"></div>
    </div>
  </div>

  <!-- TERMINAL WIDGET -->
  <div class="widget" id="termWidget">
    <div class="widget-header"><span>💻 Terminal</span><div class="widget-close" data-close="termWidget">x</div></div>
    <div id="termBody">Welcome to Hydro S Space OS<br>Type help and press Enter<br><br></div>
    <div style="display:flex; background:black; padding:8px 12px; border-radius:0 0 16px 16px;"><span style="color:#00ff88; margin-right:8px;">></span><input type="text" id="termInput" placeholder="type command..."></div>
  </div>
</div>

<div class="taskbar">
  <button class="task-btn" data-widget="clockWidget">Clock</button>
  <button class="task-btn" data-widget="calWidget">Calendar</button>
  <button class="task-btn" data-widget="calcWidget">Calculator</button>
  <button class="task-btn" data-widget="notesWidget">Notes</button>
  <button class="task-btn" data-widget="termWidget">Terminal</button>
  <button class="task-btn" id="wallBtn">Wallpaper</button>
</div>

<script>
// ALL JS INSIDE ONE FILE - NO LINKING ISSUE
document.addEventListener('DOMContentLoaded', () => {
  // visits
  let v = localStorage.getItem('hydroVisits');
  v = v ? parseInt(v)+1 : 1;
  localStorage.setItem('hydroVisits', v);
  document.getElementById('visitCount').textContent = 'Visits:'+v;

  // clock
  function updateTime(){
    let n = new Date();
    document.getElementById('clockDisplay').textContent = n.toLocaleTimeString();
    document.getElementById('topTime').textContent = n.toLocaleTimeString().slice(0,5);
    document.getElementById('dateDisplay').textContent = n.toDateString();
  }
  setInterval(updateTime,1000); updateTime();

  // calendar
  function buildCal(){
    let n = new Date(); let y=n.getFullYear(), m=n.getMonth();
    let first=new Date(y,m,1).getDay(), total=new Date(y,m+1,0).getDate();
    document.getElementById('calTitle').textContent = n.toLocaleString('default',{month:'long',year:'numeric'});
    let g=document.getElementById('calGrid'); g.innerHTML='';
    ['Su','Mo','Tu','We','Th','Fr','Sa'].forEach(x=>{
      let d=document.createElement('div'); d.className='day-name'; d.textContent=x; g.appendChild(d);
    });
    for(let i=0;i<first;i++) g.appendChild(document.createElement('div'));
    for(let i=1;i<=total;i++){
      let d=document.createElement('div'); d.className='day'; if(i==n.getDate()) d.classList.add('today'); d.textContent=i; g.appendChild(d);
    }
  }
  buildCal();

  // WIDGET TOGGLE - THIS FIXES BUTTONS
  document.querySelectorAll('[data-widget]').forEach(btn=>{
    btn.addEventListener('click', ()=>{
      let id = btn.getAttribute('data-widget');
      document.getElementById(id).classList.toggle('hidden');
    });
  });
  document.querySelectorAll('.widget-close').forEach(c=>{
    c.addEventListener('click', ()=>{
      document.getElementById(c.getAttribute('data-close')).classList.add('hidden');
    });
  });

  // draggable
  document.querySelectorAll('.widget').forEach(win=>{
    let head=win.querySelector('.widget-header'); let down=false,sx,sy,sl,st;
    head.addEventListener('mousedown', e=>{ down=true; sx=e.clientX; sy=e.clientY; sl=win.offsetLeft; st=win.offsetTop; });
    document.addEventListener('mouseup', ()=>down=false);
    document.addEventListener('mousemove', e=>{
      if(!down) return; win.style.left=(sl+e.clientX-sx)+'px'; win.style.top=(st+e.clientY-sy)+'px';
    });
  });

  // wallpaper
  let walls=['https://images.unsplash.com/photo-1531306728370-e2ebd9d7bb99?q=80&w=1974','https://images.unsplash.com/photo-1451187580459-43490279c0fa?q=80&w=2072','https://images.unsplash.com/photo-1462331940025-496dfbfc7564?q=80&w=2011']; let wi=0;
  document.getElementById('wallBtn').addEventListener('click', ()=>{
    wi=(wi+1)%walls.length; document.body.style.backgroundImage="url('"+walls[wi]+"')";
  });

  // CALCULATOR LOGIC
  let calcVal=''; let disp=document.getElementById('calcDisplay');
  document.querySelectorAll('[data-calc]').forEach(b=>{
    b.addEventListener('click', ()=>{
      let c=b.getAttribute('data-calc');
      if(c=='clear'){ calcVal=''; disp.value=''; }
      else if(c=='back'){ calcVal=calcVal.slice(0,-1); disp.value=calcVal; }
      else if(c=='='){
        try{ let v=calcVal.replace('%','/100'); let ans=eval(v); disp.value=ans; calcVal=ans.toString(); }
        catch{ disp.value='Error'; calcVal=''; }
      } else { calcVal+=c; disp.value=calcVal; }
    });
  });

  // notes
  document.getElementById('saveBtn').addEventListener('click', ()=>{
    localStorage.setItem('myNotes', document.getElementById('notesArea').value); alert('Notes saved!');
  });
  let saved=localStorage.getItem('myNotes'); if(saved) document.getElementById('notesArea').value=saved;
  let qs=['Space is infinite','We are stardust','Hydro S launching']; document.getElementById('quote').textContent=qs[Math.floor(Math.random()*qs.length)];

  // terminal
  let inp=document.getElementById('termInput'), tBody=document.getElementById('termBody');
  inp.addEventListener('keydown', e=>{
    if(e.key=='Enter'){
      let raw=inp.value, cmd=raw.toLowerCase().trim(); let d=document.createElement('div');
      d.innerHTML='> '+raw+'<br>';
      if(cmd=='help') d.innerHTML+='help, time, date, clear, wallpaper, calc, visits, hydro';
      else if(cmd=='time') d.innerHTML+=new Date().toLocaleTimeString();
      else if(cmd=='date') d.innerHTML+=new Date().toDateString();
      else if(cmd=='wallpaper'){ document.getElementById('wallBtn').click(); d.innerHTML+='wallpaper changed'; }
      else if(cmd=='clear'){ tBody.innerHTML=''; inp.value=''; return; }
      else if(cmd.startsWith('echo ')) d.innerHTML+=raw.slice(5);
      else if(cmd=='calc'){ document.getElementById('calcWidget').classList.remove('hidden'); d.innerHTML+='calculator opened'; }
      else d.innerHTML+='unknown: '+cmd;
      d.innerHTML+='<br><br>'; tBody.appendChild(d); tBody.scrollTop=tBody.scrollHeight; inp.value='';
    }
  });

  if(navigator.getBattery){ navigator.getBattery().then(b=>{ document.getElementById('bat').textContent=Math.round(b.level*100)+'%'; }); }
});
</script>
</body>
</html>
