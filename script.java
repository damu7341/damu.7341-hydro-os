// HYDRO OS - Main program

// Open an application
function openApp(appName) {
    let allWindows = document.querySelectorAll(".window");

    allWindows.forEach(function (win) {
        win.style.display = "none";
    });

    document.getElementById(appName).style.display = "block";

    document.getElementById("startMenu").style.display = "none";
}

// Close an application
function closeApp(appName) {
    document.getElementById(appName).style.display = "none";
}

// Open or close Start Menu
function toggleMenu() {
    let menu = document.getElementById("startMenu");

    if (menu.style.display === "block") {
        menu.style.display = "none";
    } else {
        menu.style.display = "block";
    }
}

// Digital clock
function updateClock() {
    let now = new Date();

    document.getElementById("clock").textContent =
        now.toLocaleTimeString();

    document.getElementById("bigClock").textContent =
        now.toLocaleTimeString();

    document.getElementById("bottomClock").textContent =
        now.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit"
        });

    document.getElementById("date").textContent =
        now.toDateString();
}

updateClock();
setInterval(updateClock, 1000);

// Calendar
function showCalendar() {
    let now = new Date();
    let year = now.getFullYear();
    let month = now.getMonth();

    let firstDay = new Date(year, month, 1).getDay();
    let days = new Date(year, month + 1, 0).getDate();

    let calendar = document.getElementById("calendar");

    document.getElementById("month").textContent =
        now.toLocaleDateString("en-US", {
            month: "long",
            year: "numeric"
        });

    calendar.innerHTML = "";

    let weekdays = ["S", "M", "T", "W", "T", "F", "S"];

    weekdays.forEach(function (day) {
        let cell = document.createElement("div");
        cell.textContent = day;
        calendar.appendChild(cell);
    });

    for (let i = 0; i < firstDay; i++) {
        calendar.appendChild(document.createElement("div"));
    }

    for (let day = 1; day <= days; day++) {
        let cell = document.createElement("div");
        cell.textContent = day;

        if (day === now.getDate()) {
            cell.classList.add("today");
        }

        calendar.appendChild(cell);
    }
}

showCalendar();

// Terminal commands
let commandBox = document.getElementById("command");
let output = document.getElementById("output");

commandBox.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
        let command = commandBox.value.toLowerCase().trim();

        let result = "";

        if (command === "help") {
            result = "Commands: help, date, time, about, clear";
        } else if (command === "date") {
            result = new Date().toDateString();
        } else if (command === "time") {
            result = new Date().toLocaleTimeString();
        } else if (command === "about") {
            result = "Welcome to Hydro OS!";
        } else if (command === "clear") {
            output.innerHTML = "";
            commandBox.value = "";
            return;
        } else {
            result = "Unknown command. Type help.";
        }

        let line = document.createElement("p");
        line.textContent = "hydro> " + command + " — " + result;

        output.appendChild(line);
        commandBox.value = "";
    }
});

// Escape key closes all open windows
document.addEventListener("keydown", function (event) {
    if (event.key === "Escape") {
        document.querySelectorAll(".window").forEach(function (win) {
            win.style.display = "none";
        });

        document.getElementById("startMenu").style.display = "none";
    }
});
