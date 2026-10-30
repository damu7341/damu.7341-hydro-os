const windows = document.querySelectorAll(".window");
const appButtons = document.querySelectorAll("[data-app]");
const startButton = document.getElementById("startButton");
const startMenu = document.getElementById("startMenu");

function openApp(appName) {
  windows.forEach(function(win) {
    win.style.display = "none";
  });
  var selected = document.getElementById(appName);
  if (selected) {
    selected.style.display = "block";
  }
  startMenu.style.display = "none";
}

document.querySelectorAll(".close-btn").forEach(function(btn) {
  btn.addEventListener("click", function() {
    btn.closest(".window").style.display = "none";
  });
});

appButtons.forEach(function(btn) {
  btn.addEventListener("click", function() {
    openApp(btn.dataset.app);
  });
});

startButton.addEventListener("click", function() {
  if (startMenu.style.display === "block") {
    startMenu.style.display = "none";
  } else {
    startMenu.style.display = "block";
  }
});

function updateClock() {
  var now = new Date();
  var time = now.toLocaleTimeString();
  var shortTime = now.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"});
  document.getElementById("topClock").textContent = time;
  document.getElementById("bigClock").textContent = time;
  document.getElementById("taskbarClock").textContent = shortTime;
  document.getElementById("todayDate").textContent = now.toDateString();
}
updateClock();
setInterval(updateClock, 1000);

var calendarDate = new Date();

function drawCalendar() {
  var year = calendarDate.getFullYear();
  var month = calendarDate.getMonth();
  var firstDay = new Date(year, month, 1).getDay();
  var totalDays = new Date(year, month + 1, 0).getDate();
  var monthName = document.getElementById("monthName");
  var calendarGrid = document.getElementById("calendarGrid");
  monthName.textContent = calendarDate.toLocaleDateString("en-US", {month: "long", year: "numeric"});
  calendarGrid.innerHTML = "";
  var weekdays = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
  weekdays.forEach(function(day) {
    var cell = document.createElement("div");
    cell.textContent = day;
    calendarGrid.appendChild(cell);
  });
  for (var i = 0; i < firstDay; i++) {
    calendarGrid.appendChild(document.createElement("div"));
  }
  for (var day = 1; day <= totalDays; day++) {
    var cell = document.createElement("div");
    cell.textContent = day;
    var today = new Date();
    if (day === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
      cell.classList.add("today");
    }
    calendarGrid.appendChild(cell);
  }
}

document.getElementById("previousMonth").addEventListener("click", function() {
  calendarDate.setMonth(calendarDate.getMonth() - 1);
  drawCalendar();
});
document.getElementById("nextMonth").addEventListener("click", function() {
  calendarDate.setMonth(calendarDate.getMonth() + 1);
  drawCalendar();
});
drawCalendar();

var terminalInput = document.getElementById("terminalCommand");
var terminalOutput = document.getElementById("terminalOutput");

function runCommand(command) {
  var typed = document.createElement("p");
  typed.textContent = "hydro> " + command;
  terminalOutput.appendChild(typed);
  var answer = document.createElement("p");
  var cmd = command.toLowerCase().trim();
  if (cmd === "help") {
    answer.textContent = "Commands: help, date, time, about, clear";
  } else if (cmd === "date") {
    answer.textContent = new Date().toDateString();
  } else if (cmd === "time") {
    answer.textContent = new Date().toLocaleTimeString();
  } else if (cmd === "about") {
    answer.textContent = "Welcome to Hydro OS - Space Desktop Project.";
  } else if (cmd === "clear") {
    terminalOutput.innerHTML = "";
    return;
  } else if (cmd === "") {
    return;
  } else {
    answer.textContent = "Command not found. Type help.";
  }
  terminalOutput.appendChild(answer);
}

terminalInput.addEventListener("keydown", function(event) {
  if (event.key === "Enter") {
    runCommand(terminalInput.value);
    terminalInput.value = "";
    terminalOutput.scrollTop = terminalOutput.scrollHeight;
  }
});

document.getElementById("wallpaperPicker").addEventListener("change", function(event) {
  var file = event.target.files[0];
  if (file && file.type.startsWith("image/")) {
    var imageURL = URL.createObjectURL(file);
    document.querySelector(".desktop").style.backgroundImage = "linear-gradient(rgba(3,8,30,.2), rgba(3,8,30,.4)), url(" + imageURL + ")";
  }
});

document.addEventListener("keydown", function(event) {
  if (event.ctrlKey && event.altKey && event.key.toLowerCase() === "t") {
    openApp("terminal");
  }
  if (event.key === "Escape") {
    windows.forEach(function(win) {
      win.style.display = "none";
    });
    startMenu.style.display = "none";
  }
});
