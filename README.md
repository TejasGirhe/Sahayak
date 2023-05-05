### This is my final year B.Tech. project 
### Introduction

In this project, we have developed an innovative educational tool that lever- ages the latest technologies to help children with reading disabilities develop critical reading skills. This android application uses augmented reality and artificial intelligence. This app offers a range of features that help children learn to read by providing them with interactive and engaging tools that enhance their learning experience.
The user can scan typed text from the app or type the text that they would like to process. The app provides synonyms and meanings of the word, help-ing to build their vocabulary and comprehension skills. In addition, the app identifies the part of speech for each word, helping children understand the grammatical structure of sentences.
To assist with reading fluency, the app also reads aloud the text, allowing children to listen to the words as they read along. This feature is particularly useful for children who struggle with reading, as it helps them develop their reading skills and build confidence in their abilities. Another feature of the app is the reading tracker, which helps children trace the text as it is read. This tool is particularly helpful for children with visual processing difficul- ties, as it enables them to focus on individual words and track their progress as they read. Images are generated for a word using AI, giving children a visual representation of the word they are learning.
Finally, the app includes an augmented reality component, allowing users to view 3D models of objects in their own space. This feature is particularly useful for children who learn best through visual and tactile experiences, as it provides a hands-on learning experience that enhances their understanding of the world around them.

### Project Design
The android application is developed on Android Studio using Java for front- end and back-end. The app interface is designed using Android XML layouts. The app uses a simple yet engaging UI suitable for young children. Camera is used to capture the typed text live and the camera preview is displayed in the SurfaceView and the Google Play Store’s Vision engine is attached to the camera source to detect text. The OCR (Optical Character Recognition) engine provided by Google Play Services for Android is to detect text from the camera source. The detected text is processed in TextRecognizer and dis- played in the app. Next, this processed text is taken to another page where it is read word by word at a slow rate using the TextToSpeech engine by Android, here the word being read also gets traced to keep track of the read- ing. The reading can be played, paused, forwarded, rewound, and replayed by navigating through the words. For every word being read, the synonyms, definitions, and part of speech is displayed by fetching this information from the Collegiate and Thesaurus API by breaking down its output.

Next, we generate images for the word as a prompt using an ML image generator created using Stable Diffusion Pipeline. This Python model uses a deep learning library called PyTorch to generate an image based on text input. The pre-trained model has been used to generate an image based on the text input by running it through a process called Stable Diffusion. This process essentially creates a gradual blending of pixels to generate an image. The PyTorch library has a class called StableDiffusionPipeline, specifically in the library’s ”Fairscale” module. This technology enables the creation of high-quality images from text prompts utilizing a method referred to as ”Stable Diffusion”. The process entails updating an image repeatedly over multiple time steps while incrementally introducing additional noise into it during each step. Gradually produced is a blurred depiction that can be refined and perfected for achieving exceptional clarity and finesse. Moreover, this very same named category known as StableDiffusionPipeline offers its own distinct mechanism useful for implementing the Stable Diffusion tech- nique that can be used with pre-trained models. It takes a text prompt as input and generates an image based on that prompt. This class has several configurable options that can be used to customize the generated images, such as the level of detail, the amount of noise to add, and the size of the image. Any number of images can be generated using this model. The im- ages are fetched by creating a FastAPI that gives image output for a given prompt. These images provide a further description of the word being read. These generated images are also saved in an online database (Firebase) for further use, if any.
An online visual dictionary consisting of 3D models of basic words for all alphabets, shapes, foods, instruments, animals, etc has been created. If the word being read is present in the visual dictionary, its 3D model is fetched and is displayed as augmented reality using Google’s ARCore and Sceneviewer. If the user’s phone doesn’t support AR, even then, the 3D model can be viewed

on the mobile screen. This gives a more real-world and tactile learning ex- perience. If the 3D model is not available for a certain word, the application can inform the user that the model is not currently available but will be added in the future. Additionally, the application can store the word and its demand in the back-end server. The demand for words without a 3D model can be tracked to identify which words are most in demand by users. Based on this data, the development team can prioritize the creation of 3D models for those words and update the visual dictionary accordingly.



### Overview

This is a camera app that continuously detects the objects (bounding boxes and
classes) in the frames seen by your device's back camera, with the option to use
a quantized
[MobileNet SSD](https://tfhub.dev/tensorflow/lite-model/ssd_mobilenet_v1/1/metadata/2),
[EfficientDet Lite 0](https://tfhub.dev/tensorflow/lite-model/efficientdet/lite0/detection/metadata/1),
[EfficientDet Lite1](https://tfhub.dev/tensorflow/lite-model/efficientdet/lite1/detection/metadata/1),
or
[EfficientDet Lite2](https://tfhub.dev/tensorflow/lite-model/efficientdet/lite2/detection/metadata/1)
model trained on the [COCO dataset](http://cocodataset.org/). These instructions
walk you through building and running the demo on an Android device.

The model files are downloaded via Gradle scripts when you build and run the
app. You don't need to do any steps to download TFLite models into the project
explicitly.

This application should be run on a physical Android device.

## Build the demo using Android Studio

### Prerequisites

*   The **[Android Studio](https://developer.android.com/studio/index.html)**
    IDE. This sample has been tested on Android Studio Bumblebee.

*   A physical Android device with a minimum OS version of SDK 24 (Android 7.0 -
    Nougat) with developer mode enabled. The process of enabling developer mode
    may vary by device.

### Building

*   Open Android Studio. From the Welcome screen, select Open an existing
    Android Studio project.

*   From the Open File or Project window that appears, navigate to and select
    the tensorflow-lite/examples/object_detection/android directory. Click OK.

*   If it asks you to do a Gradle Sync, click OK.

*   With your Android device connected to your computer and developer mode
    enabled, click on the green Run arrow in Android Studio.

### Models used

Downloading, extraction, and placing the models into the assets folder is
managed automatically by the download.gradle file.
