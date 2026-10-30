// HYDRO OS - Main JavaScript file

const windows = document.querySelectorAll(".window");
const appButtons = document.querySelectorAll("[data-app]");
const startButton = document.getElementById("startButton");
const startMenu = document.getElementById("startMenu");

// Open an application window
function openApp(appName) {
  windows.forEach(function (win) {
    win.style.display = "none";
  });

  const selectedWindow = document.getElementById(appName);

  if (selectedWindow) {
    selectedWindow.style.display = "block";
  }

  startMenu.style.display = "none";
}

// Close buttons
document.querySelectorAll(".close-btn").forEach(function (button) {
  button.addEventListener("click", function () {
    button.closest(".window").style.display = "none";
  });
});

// App icons and start menu buttons
appButtons.forEach(function (button) {
  button.addEventListener("click", function () {
    openApp(button.dataset.app);
  });
});

// Start menu
startButton.addEventListener("click", function () {
  startMenu.style.display =
    startMenu.style.display === "block" ? "none" : "block";
});

// Live clock
function updateClock() {
  const now = new Date();

  const time = now.toLocaleTimeString();
  const shortTime = now.toLocaleTimeString([], {
    hour: "2-digit",
    minute: "2-digit"
  });

  document.getElementById("topClock").textContent = time;
  document.getElementById("bigClock").textContent = time;
  document.getElementById("taskbarClock").textContent = shortTime;

  document.getElementById("todayDate").textContent =
    now.toDateString();
}

updateClock();
setInterval(updateClock, 1000);

// Calendar
let calendarDate = new Date();

function drawCalendar() {
  const year = calendarDate.getFullYear();
  const month = calendarDate.getMonth();

  const firstDay = new Date(year, month, 1).getDay();
  const totalDays = new Date(year, month + 1, 0).getDate();

  const monthName = document.getElementById("monthName");
  const calendarGrid = document.getElementById("calendarGrid");

  monthName.textContent = calendarDate.toLocaleDateString("en-US", {
    month: "long",
    year: "numeric"
  });

  calendarGrid.innerHTML = "";

  const weekdays = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

  weekdays.forEach(function (day) {
    const cell = document.createElement("div");
    cell.textContent = day;
    calendarGrid.appendChild(cell);
  });

  for (let i = 0; i < firstDay; i++) {
    calendarGrid.appendChild(document.createElement("div"));
  }

  for (let day = 1; day <= totalDays; day++) {
    const cell = document.createElement("div");
    cell.textContent = day;

    const today = new Date();

    if (
      day === today.getDate() &&
      month === today.getMonth() &&
      year === today.getFullYear()
    ) {
      cell.classList.add("today");
    }

    calendarGrid.appendChild(cell);
  }
}

document.getElementById("previousMonth").addEventListener("click", function () {
  calendarDate.setMonth(calendarDate.getMonth() - 1);
  drawCalendar();
});

document.getElementById("nextMonth").addEventListener("click", function () {
  calendarDate.setMonth(calendarDate.getMonth() + 1);
  drawCalendar();
});

drawCalendar();

// Simple terminal commands
const terminalInput = document.getElementById("terminalCommand");
const terminalOutput = document.getElementById("terminalOutput");

function runCommand(command) {
  const answer = document.createElement("p");
  const typed = document.createElement("p");

  typed.textContent = "hydro> " + command;
  terminalOutput.appendChild(typed);

  switch (command.toLowerCase().trim()) {
    case "help":
      answer.textContent =
        "Commands: help, date, time, about, clear";
      break;

    case "date":
      answer.textContent = new Date().toDateString();
      break;

    case "time":
      answer.textContent = new Date().toLocaleTimeString();
      break;

    case "about":
      answer.textContent = "Welcome to Hydro OS - Space Desktop Project.";
      break;

    case "clear":
      terminalOutput.innerHTML = "";
      return;

    case "":
      return;

    default:
      answer.textContent = "Command not found. Type help.";
  }

  terminalOutput.appendChild(answer);
}

terminalInput.addEventListener("keydown", function (event) {
  if (event.key === "Enter") {
    runCommand(terminalInput.value);
    terminalInput.value = "";
    terminalOutput.scrollTop = terminalOutput.scrollHeight;
  }
});

// Choose a wallpaper from your device
document.getElementById("wallpaperPicker").addEventListener(
  "change",
  function (event) {
    const file = event.target.files[0];

    if (file && file.type.startsWith("image/")) {
      const imageURL = URL.createObjectURL(file);
      document.querySelector(".desktop").style.backgroundImage =
        `linear-gradient(rgba(3,8,30,.2), rgba(3,8,30,.4)), url("${imageURL}")`;
    }
  }
);

// Keyboard shortcuts
document.addEventListener("keydown", function (event) {
  if (event.ctrlKey && event.altKey && event.key.toLowerCase() === "t") {
    openApp("terminal");
  }

  if (event.key === "Escape") {
    windows.forEach(function (win) {
      win.style.display = "none";
    });

    startMenu.style.display = "none";
  }
});
