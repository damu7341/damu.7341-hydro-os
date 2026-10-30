// HYDRO OS S1
// Main JavaScript File


// DIGITAL CLOCK

function showTime() {

    let currentTime = new Date();

    document.getElementById("clock").textContent =
        currentTime.toLocaleTimeString();

    document.getElementById("date").textContent =
        currentTime.toDateString();
}

showTime();

setInterval(showTime, 1000);


// CALCULATOR

let display = document.getElementById("display");

let calculator = document.getElementById(
    "calculator-buttons"
);

let buttons = [
    "7", "8", "9", "/",
    "4", "5", "6", "*",
    "1", "2", "3", "-",
    "0", ".", "C", "+",
    "="
];

buttons.forEach(function(value) {

    let button = document.createElement("button");

    button.textContent = value;

    button.onclick = function() {

        if (value === "C") {

            display.value = "";

        }

        else if (value === "=") {

            try {

                if (!/^[0-9+\-*/.() ]+$/.test(display.value)) {
                    throw new Error("Invalid input");
                }

                let answer = Function(
                    '"use strict"; return (' + display.value + ')'
                )();

                if (!Number.isFinite(answer)) {
                    throw new Error("Invalid calculation");
                }

                display.value = answer;

            }

            catch {

                display.value = "Error";

            }

        }

        else {

            display.value += value;

        }

    };

    calculator.appendChild(button);

});


// CALENDAR

function showCalendar() {

    let today = new Date();

    let calendar = document.getElementById("calendar");

    let year = today.getFullYear();

    let month = today.getMonth();

    calendar.innerHTML = "";

    let heading = document.createElement("h3");

    heading.textContent = today.toLocaleString(
        "default",
        { month: "long" }
    ) + " " + year;

    calendar.appendChild(heading);

    let grid = document.createElement("div");

    grid.style.display = "grid";

    grid.style.gridTemplateColumns =
        "repeat(7, 1fr)";

    grid.style.gap = "5px";

    let weekDays = [
        "Sun", "Mon", "Tue", "Wed",
        "Thu", "Fri", "Sat"
    ];

    weekDays.forEach(function(day) {

        let cell = document.createElement("div");

        cell.textContent = day;

        grid.appendChild(cell);

    });

    let firstDay = new Date(
        year, month, 1
    ).getDay();

    let totalDays = new Date(
        year, month + 1, 0
    ).getDate();

    for (let i = 0; i < firstDay; i++) {

        grid.appendChild(document.createElement("div"));

    }

    for (let day = 1; day <= totalDays; day++) {

        let cell = document.createElement("div");

        cell.textContent = day;

        if (day === today.getDate()) {

            cell.style.background = "#008fc9";

            cell.style.borderRadius = "5px";

        }

        grid.appendChild(cell);

    }

    calendar.appendChild(grid);

}

showCalendar();


// HYDRO TERMINAL

let terminal = document.getElementById(
    "terminal-output"
);

let commandInput = document.getElementById(
    "terminal-command"
);

function runTerminal() {

    let command = commandInput.value
        .trim()
        .toLowerCase();

    let result = "";

    if (command === "help") {

        result = "Commands: help, time, date, about, hello, clear";

    }

    else if (command === "time") {

        result = new Date().toLocaleTimeString();

    }

    else if (command === "date") {

        result = new Date().toDateString();

    }

    else if (command === "about") {

        result = "HYDRO OS S1 - Space Desktop Edition";

    }

    else if (command === "hello") {

        result = "Welcome to HYDRO OS!";

    }

    else if (command === "clear") {

        terminal.textContent = "";

        commandInput.value = "";

        return;

    }

    else if (command === "") {

        return;

    }

    else {

        result = "Command not found. Type help.";

    }

    let line = document.createElement("p");

    line.textContent = "> " + command + " : " + result;

    terminal.appendChild(line);

    commandInput.value = "";

}


// ENTER KEY SUPPORT

commandInput.addEventListener("keydown", function(event) {

    if (event.key === "Enter") {

        runTerminal();

    }

});
