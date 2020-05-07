import os
import platform
import logging
from logging import debug, info, warning, error, critical
from dataclasses import dataclass


def running_on_ci():
    return True if os.getenv("GITHUB_ACTIONS") == "true" else False

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
    ffmpeg_fmt_system_audio: str = ""
    terminal_program: str = ""

    # If the main mission ID is set to 2 (USAR Singleplayer), then disable the
    # tutorial mission, since it is a tutorial for the Zombie invasion mission.
    def __post_init__(self):
        self.configure_ci()
        if self.main_mission == 2:
            self.do_tutorial_mission = False
        if platform.system() == "Darwin":
            info("macOS detected.")
            macos_minor_version = int(platform.mac_ver()[0].split(".")[1])

            if macos_minor_version >= 15:
                self.enable_system_audio_recording = True
                self.ffmpeg_fmt_system_audio = "avfoundation"
            else:
                self.enable_system_audio_recording = False

            if not running_on_ci():
                if os.getenv("TERM_PROGRAM") == "iTerm.app":
                    self.terminal_program = "iTerm"
                else:
                    self.terminal_program = "Terminal"

    def configure_ci(self):
        if running_on_ci():
            self.time_limit = 1
            self.do_tutorial_mission = False
            self.main_mission="external/malmo/sample_missions/default_flat_1.xml"
            self.enable_ffmpeg = False
            self.enable_file_upload = False


if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO)
    Session()
