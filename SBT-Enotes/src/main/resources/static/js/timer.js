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