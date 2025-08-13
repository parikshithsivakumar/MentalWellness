const circleProgress = document.querySelector(".circle-progress");
const numberOfBreaths = document.querySelector(".breath-input");
const start = document.querySelector(".start");
const instructions = document.querySelector(".instructions");
const breathsText = document.querySelector(".breaths-text");
let breathsLeft = 3;

// Watching for selected breaths from user
numberOfBreaths.addEventListener("change", () => {
  breathsLeft = numberOfBreaths.value;
  breathsText.innerText = breathsLeft;
});

// Grow/Shrink Circle
const growCircle = () => {
  circleProgress.classList.add("circle-grow");
  setTimeout(() => {
    circleProgress.classList.remove("circle-grow");
  }, 8000);
};

// Breathing Instructions
const breathTextUpdate = () => {
  breathsLeft--;
  breathsText.innerText = breathsLeft;
  instructions.innerText = "Breath in";
  setTimeout(() => {
    instructions.innerText = "Hold Breath";
    setTimeout(() => {
      instructions.innerText = "Exhale Breath Slowly";
    }, 4000);
  }, 4000);
};

// Breathing App Function
const breathingApp = () => {
  const breathingAnimtaion = setInterval(() => {
    if (breathsLeft === 0) {
      clearInterval(breathingAnimtaion);
      instructions.innerText = "Breathing session completed. Click 'Begin' to start another session!";
      start.classList.remove("button-inactive");
      breathsLeft = numberOfBreaths.value;
      breathsText.innerText = breathsLeft;
      return;
    }
    growCircle();
    breathTextUpdate();
  }, 12000);
};

// Start Breathing
start.addEventListener("click", () => {
  start.classList.add("button-inactive");
  instructions.innerText = "Get relaxed, and ready to begin breathing";
  setTimeout(() => {
    instructions.innerText = "Breathing is about to begin...";
    setTimeout(() => {
      breathingApp();
      growCircle();
      breathTextUpdate();
    }, 2000);
  }, 2000);
});

let timer;
      let timerContainer = document.getElementById("timer-container");
      let timerDisplay = document.getElementById("timer");
      let pointer = document.getElementById("pointer");
      let ringSound = document.getElementById("ringSound");

      function startTimer(duration) {
        let timeLeft = duration;

        function updateTimer() {
          let minutes = Math.floor(timeLeft / 60);
          let seconds = timeLeft % 60;

          timerDisplay.textContent = `${String(minutes).padStart(
            2,
            "0"
          )}:${String(seconds).padStart(2, "0")}`;

          let progress = 100 - (timeLeft / duration) * 100;
          let timerColor = progress < 50 ? "#4CAF50" : "#FF5722";

          timerContainer.style.background = `conic-gradient(${timerColor} ${progress}%, #f2f2f2 ${progress}% 100%)`;

          // Update pointer position
          let pointerRotation = 360 - progress * 3.6; // Convert percentage to degrees
          pointer.style.transform = `rotate(${pointerRotation}deg)`;

          if (timeLeft === 0) {
            clearInterval(timer);
            timerDisplay.textContent = "Time's  up!";
            ringSound.play();
          }

          timeLeft--;
        }

        // Call updateTimer every second
        updateTimer();
        timer = setInterval(updateTimer, 1000);
      }