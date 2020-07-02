#pragma once

#include <SequenceCapture.h>
#include <VisualizationUtils.h>
#include <Visualizer.h>

namespace tomcat {

    class WebcamSensor {
      public:
        WebcamSensor()
            : visualizer(true, false, false, false),
              det_parameters(this->arguments) {
        }

        void initialize();
        void get_observation();

      private:
        std::vector<std::string> arguments = {"-device", "0", "-au_static"};
        Utilities::Visualizer visualizer;
        cv::Mat rgb_image;
        Utilities::SequenceCapture sequence_reader;
        LandmarkDetector::CLNF face_model;
        LandmarkDetector::FaceModelParameters det_parameters;
        Utilities::FpsTracker fps_tracker;
        cv::Mat_<uchar> grayscale_image;
    };

} // namespace tomcat