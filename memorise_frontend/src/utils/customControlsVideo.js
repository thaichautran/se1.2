const video = document.querySelector("video");
const playPauseButton = document.querySelector(".play-pause-button");
const timeDisplay = document.querySelector(".time-display");

playPauseButton.addEventListener("click", togglePlayPause);
video.addEventListener("timeupdate", updateTimeDisplay);

function togglePlayPause() {
  if (video.paused) {
    video.play();
    playPauseButton.style.backgroundImage =
      'url("../../assets/image/play-button-icon-white-8.jpg")';
  } else {
    video.pause();
    playPauseButton.style.backgroundImage =
      'url("../../assets/image/pause-white-button.png")';
  }
}

function updateTimeDisplay() {
  const currentTime = formatTime(video.currentTime);
  const duration = formatTime(video.duration);
  timeDisplay.textContent = `${currentTime} / ${duration}`;
}

function formatTime(seconds) {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = Math.floor(seconds % 60);
  return `${minutes}:${remainingSeconds < 10 ? "0" : ""}${remainingSeconds}`;
}
