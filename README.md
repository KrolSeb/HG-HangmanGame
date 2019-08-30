<h1 align="center"> HG - Hangman Game </h1>
<p align="center">
    <img alt="HG" title="HG Logo" src="https://raw.githubusercontent.com/KrolSeb/HG-HangmanGame/master/app/src/main/res/drawable-xxxhdpi/logo_android.png" width="256" height="256">
</p>
<h4 align="center">
  Hangman game created with Android SDK, Java and Firebase.
</h4>

## Table of contents
* [General info](#general-info)
* [Screenshots](#screenshots)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Inspiration](#inspiration)
* [Contact](#contact)

## General info
Initially the project was realized as a part of team project classes at the university. In a four-person team we prepared application graphic design, scenarios and UML diagrams for classes included in a project.
Work progress diagrams from Redmine and files created during application design are available on [Google Drive](https://drive.google.com/open?id=1Ee-ChpDsyGX8qtNERCiygCPyAJGJafs7) (content in Polish language).

At this repo you can find a code for mobile version of application.

## Screenshots
<p align="middle">
  <img src="https://drive.google.com/uc?export=view&id=1ZIl44pBNAWNdXyX4CAE_cuuSnRicgaZB" width="33%" />
  <img src="https://drive.google.com/uc?export=view&id=1LrSLpQJLVRmk3mLICt1FqxV1DcPL0v7f" width="33%" /> 
  <img src="https://drive.google.com/uc?export=view&id=1MGdbKmPGQyrh3A42jYreCfBMlbXgRUYk" width="33%" />
  <img src="https://drive.google.com/uc?export=view&id=1dtvSs2sdlsJQNXxGnywtaiNaaowq_UqF" width="33%" />
    <img src="https://drive.google.com/uc?export=view&id=1zImzkM8unbxJTnNXStndwaMCsOf24pVv" width="33%" />
  <img src="https://drive.google.com/uc?export=view&id=1a4JXAwEAOReF3j4A70aC9zzLotrASZow" width="33%" />
  <img src="https://drive.google.com/uc?export=view&id=1Mds1GdhZLSANbZriBwh2IW2pesz01wkt" width="33%" />
    <img src="https://drive.google.com/uc?export=view&id=1KjEQuSHu30ZVlo60l6nrWgMuLmzHzrFp" width="33%" />
  <img src="https://drive.google.com/uc?export=view&id=1HmL-Q1Q6BmLPr8Z7MXtq1wr4a5aJf7vk" width="33%" />
  <img src="https://drive.google.com/uc?export=view&id=1Eea4wHFbu6Wa1ZCdB7-X8nu5yYKieq7m" width="33%" />
  <img src="https://drive.google.com/uc?export=view&id=1rcVMdMRhf3zBBKiRGMEdjKqzkBk9v11o" width="33%" />
  <img src="https://drive.google.com/uc?export=view&id=1FryNItXJHEyMHKjbHpuLE6fJK6VI8L32" width="33%" />
    <img src="https://drive.google.com/uc?export=view&id=1ftGLmTjgvX1zwa5VeDXMEPo1VziaSJFu" width="33%" />
  <img src="https://drive.google.com/uc?export=view&id=1lD_klZaW_YQ_jZ6YXKx1BE5W5O1gqKy1" width="33%" />
</p>


## Technologies
* Java 8
* Android SDK (API level 28 - Android 9.0)
* Firebase 16.0.x
* Gradle 5.1.1
* Butterknife 10.0.0
* Picasso 2.71828
* IDE - Android Studio version 3.x

## Setup
Application is available for devices with Android OS version 5.0 (Lollipop) and above.

Clone this repository via Android Studio startup menu. IDE should recognize all dependencies and download them automatically. Build project and run via default Android emulator.

## Features
List of features ready:
* creating new user accounts
* logging in and out from application
* changing user avatar and nickname
* changing word categories
* game algorithm (guessing words, drawing hangman elements, choosing letter from alphabet, showing appropriate information about game result)
* calculating user score after game and saving new state to database
* showing best user results and summary after game.

To-do list for future development:
* add English language support
* add more categories and words to database
* add button in game to inform user about word subcategory 
* implement change password service
* change rules used to calculate game points
* fix application bugs (especially in the case of poor internet connection).

## Status
The basic version of a project (allowing to play the game) has been **completed**. I want to come back to develop this project in a few months. During the break, maybe I will come up with ideas, what new could be added to this application.
  
Now I'm learning the basics of programming web applications in Java (Spring). 

## Inspiration
Thanks to [@martapiecko](https://github.com/martapiecko), [@mati6677](https://github.com/mati6677) and [@kusmierczykw](https://github.com/kusmierczykw) for working together on a project.
  
After finishing my studies I want to continue application  implementation. 
Primarily I want to improve my programming skills, Java knowledge and increase expercience in creating mobile apps. 

## Contact
Created by [@KrolSeb](https://krolseb.github.io/) - please feel free to contact me if you need any further information.

Credits to [@flynerdpl](https://www.flynerd.pl/) for the article about github README writing - it's my first "good quality" README on Github :).
