let timer;
      let timerDisplay = document.getElementById("timer");
      let ringSound = document.getElementById("ringSound");

      // Start custom timer from user input
      function startCustomTimer() {
        const minutes = parseInt(document.getElementById("minutes").value) || 0;
        const seconds = parseInt(document.getElementById("seconds").value) || 0;
        const totalSeconds = minutes * 60 + seconds;

        if (totalSeconds === 0) {
          alert("Please enter a valid duration");
          return;
        }

        startTimer(totalSeconds);
        toggleButtons(true);
      }

      function toggleButtons(isRunning) {
        document.getElementById("startBtn").style.display = isRunning ? "none" : "flex";
        document.getElementById("pauseBtn").style.display = isRunning ? "flex" : "none";
        document.getElementById("resumeBtn").style.display = "none";
        document.getElementById("stopBtn").style.display = isRunning ? "flex" : "none";
        document.getElementById("minutes").disabled = isRunning;
        document.getElementById("seconds").disabled = isRunning;
      }

      function startTimer(duration) {
        let timeLeft = duration;

        function updateTimer() {
          let minutes = Math.floor(timeLeft / 60);
          let seconds = timeLeft % 60;

          timerDisplay.textContent = `${String(minutes).padStart(
            2,
            "0"
          )}:${String(seconds).padStart(2, "0")}`;

          if (timeLeft === 0) {
            clearInterval(timer);
            timerDisplay.textContent = "Time's up!";
            ringSound.play();
            toggleButtons(false);
          }

          timeLeft--;
        }

        // Call updateTimer every second
        updateTimer();
        timer = setInterval(updateTimer, 1000);
      }

      function pauseTimer() {
        clearInterval(timer);
        document.getElementById("pauseBtn").style.display = "none";
        document.getElementById("resumeBtn").style.display = "flex";
      }

      function resumeTimer() {
        const timeString = timerDisplay.textContent.split(":");
        const minutes = parseInt(timeString[0]);
        const seconds = parseInt(timeString[1]);
        const totalSeconds = minutes * 60 + seconds;

        if (totalSeconds > 0) {
          startTimer(totalSeconds);
          document.getElementById("pauseBtn").style.display = "flex";
          document.getElementById("resumeBtn").style.display = "none";
        }
      }

      function stopTimer() {
        clearInterval(timer);
        timerDisplay.textContent = "00:00";
        toggleButtons(false);
      }