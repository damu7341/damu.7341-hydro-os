// Open and close window functions
function openWindow(windowId) {
    const win = document.getElementById(windowId);
    win.style.display = 'flex';
    win.style.zIndex = Date.now(); // Bring to front
}

function closeWindow(windowId) {
    const win = document.getElementById(windowId);
    win.style.display = 'none';
}

// Simple live clock in the taskbar
function updateClock() {
    const now = new Date();
    let hours = now.getHours();
    let minutes = now.getMinutes();
    hours = hours < 10 ? '0' + hours : hours;
    minutes = minutes < 10 ? '0' + minutes : minutes;
    document.getElementById('clock').innerText = `${hours}:${minutes}`;
}
setInterval(updateClock, 1000);
updateClock();

// Basic interactive terminal commands
function handleTerminal(event) {
    if (event.key === 'Enter') {
        const inputField = document.getElementById('terminal-input');
        const outputDiv = document.getElementById('terminal-output');
        const command = inputField.value.trim();

        outputDiv.innerHTML += `<div>guest@hydro-os:~$ ${command}</div>`;

        if (command === 'help') {
            outputDiv.innerHTML += `<div>Available commands: clear, about, date, echo [text]</div>`;
        } else if (command === 'clear') {
            outputDiv.innerHTML = '';
        } else if (command === 'about') {
            outputDiv.innerHTML += `<div>Hydro OS v1.0 - A custom web operating system project.</div>`;
        } else if (command === 'date') {
            outputDiv.innerHTML += `<div>${new Date()}</div>`;
        } else if (command.startsWith('echo ')) {
            outputDiv.innerHTML += `<div>${command.substring(5)}</div>`;
        } else if (command !== '') {
            outputDiv.innerHTML += `<div>Command not found: ${command}. Type 'help' for assistance.</div>`;
        }

        inputField.value = '';
        outputDiv.scrollTop = outputDiv.scrollHeight;
    }
}
