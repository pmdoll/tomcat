import os
import platform
import logging
from subprocess import run, STDOUT, PIPE
from logging import debug, info, warning, error, critical
from dataclasses import dataclass


def running_on_ci():
    return True if os.getenv("GITHUB_ACTIONS") == "true" else False


class AVRecorder:

    ffmpeg_fmt_screen_video: str = ""
    ffmpeg_fmt_system_audio: str = ""
    ffmpeg_fmt_webcam: str = ""
    ffmpeg_fmt_microphone: str = ""

    enable_system_audio_recording: bool = True

    ffmpeg_input_device_screen_video: str = ""
    ffmpeg_input_device_webcam: str = ""
    ffmpeg_input_device_system_audio: str = ""
    ffmpeg_input_device_microphone: str = ""

    def __init__(self):
        if platform.system() == "Darwin":
            macos_minor_version = int(platform.mac_ver()[0].split(".")[1])

            if macos_minor_version >= 15:
                self.ffmpeg_fmt_system_audio = "avfoundation"
            else:
                self.enable_system_audio_recording = False
        self.configure_screen_capture()

    def configure_webcam(self):
        if platform.system() == "Darwin":
            # TODO implement checking for external display here.
            self.ffmpeg_fmt_webcam = "avfoundation"
            self.ffmpeg_input_device_webcam = "0:"
        else:
            self.ffmpeg_fmt_webcam = "v4l2"
            self.ffmpeg_input_device_webcam = "/dev/video0"

    def configure_microphone_recording(self):
        if platform.system() == "Darwin":
            # TODO implement checking for external display here.
            self.ffmpeg_fmt_microphone = "avfoundation"
            if self.enable_system_audio_recording:
                self.ffmpeg_input_device_system_audio = ":0"
                self.ffmpeg_input_device_microphone = ":1"
            else:
                self.ffmpeg_input_device_microphone = ":0"
        else:
            self.ffmpeg_fmt_microphone = "alsa"
            self.ffmpeg_input_device_microphone = "default"

    def configure_screen_capture(self):
        if platform.system() == "Darwin":
            p1 = run(
                [
                    "osascript",
                    "-e",
                    'tell application "Finder" to get bounds of window of desktop',
                ],
                stdout=PIPE,
                universal_newlines=True,
            )
            self.screen_dimensions = "x".join(p1.stdout.split(", ")[2:4])
        else:
            self.ffmpeg_fmt_screen_capture = "x11grab"
            self.screen_dimensions = run(
                "xdpyinfo | grep dimensions | awk '{print $2;}'",
                shell=True,
                stdout=PIPE,
                universal_newlines=True,
            ).stdout
            self.ffmpeg_input_device_screen_capture = ":0.0"


@dataclass
class Session:
    ffmpeg_enabled: bool = True
    main_mission = 1
    do_tutorial_mission: bool = True
    do_main_mission: bool = True
    enable_system_audio_recording: bool = True
    player_id: str = os.getenv("USER", "default_player_id")
    tmp_dir: str = f"/tmp/{os.getenv('USER')}/tomcat"
    time_limit: int = 600
    terminal_program: str = ""

    # If the main mission ID is set to 2 (USAR Singleplayer), then disable the
    # tutorial mission, since it is a tutorial for the Zombie invasion mission.
    def __post_init__(self):
        self.configure_ci()
        if self.main_mission == 2:
            self.do_tutorial_mission = False
        if platform.system() == "Darwin":
            info("macOS detected.")

            if not running_on_ci():
                if os.getenv("TERM_PROGRAM") == "iTerm.app":
                    self.terminal_program = "iTerm"
                else:
                    self.terminal_program = "Terminal"
        self.avrecorder = AVRecorder()

    def configure_ci(self):
        if running_on_ci():
            self.time_limit = 1
            self.do_tutorial_mission = False
            self.main_mission = (
                "external/malmo/sample_missions/default_flat_1.xml"
            )
            self.enable_ffmpeg = False
            self.enable_file_upload = False


if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO)
    Session()
