# 🤖 AI & ML Welcome Application

A modern, interactive Android application built for **AI & Machine Learning engineering students**. This app replaces the basic "Welcome to Android" layout with a futuristic, immersive onboarding experience.

<p align="center">
  <img src="https://img.shields.io/badge/Language-Kotlin_100%25-7F52FF?logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Architecture-Clean_ViewBinding-4285F4" />
  <img src="https://img.shields.io/badge/UI_Style-Glassmorphism_|_Neumorphism-00BFA5" />
  <img src="https://img.shields.io/badge/API-26+-brightgreen" />
</p>

---

## ✨ Features

### 🌟 Immersive UI Morphism
- **Glassmorphism**: Info cards utilize frosted glass effects (transparency + blur + subtle borders).
- **Neumorphism**: Buttons feature soft-raised gradients that provide a physical feel.
- **Motion Morphism**: The background features "glow circles" that float gently, creating a dynamic, alive interface.

### 🧠 Interactive AI Content
- **Animated Typewriter**: The main title "Welcome to AI & ML Engineering" reveals itself character-by-character.
- **Auto-Cycling Messages**: Subtitles change every 3 seconds to inspire students:
  - *Build Intelligent Systems*
  - *Explore Neural Networks*
  - *Shape the Future with AI*
- **Dot Indicators**: Real-time visual tracking of currently active cycling messages.

### 🔘 User Interaction Logic
- **Expandable Info Card**: Tap the "About AI & ML" card to reveal a detailed overview with a smooth chevron rotation animation.
- **Dynamic Content Button**: The "Discover More" button allows users to manually cycle through AI themes with a spring press effect.
- **Mini Topic Tiles**: Clickable cards for Deep Learning, NLP, and Computer Vision with interactive scale animations.

---

## 🏗️ Technical Implementation

### Architecture
- **Language**: 100% Kotlin
- **Layout**: Android XML with Constraint/Linear combinations
- **View Binding**: Safe and efficient access to UI components
- **Animations**: A mix of XML animators and Programmatic `ObjectAnimator` for complexity.

### Project Structure
```
app/src/main/
├── java/com/mad/app/
│   └── MainActivity.kt            # Core logic & animation management
├── res/
│   ├── anim/                      # fade_in, fade_out, slide_up, pop_in
│   ├── drawable/                  # glass_card_bg, neu_button_bg, ambient glows
│   ├── layout/                    # activity_main.xml (the heart of the UI)
│   └── values/                    # AI palette colors, strings, dimens, themes
└── AndroidManifest.xml
```

---

## 🚀 Getting Started

1. **Clone** the project.
2. **Open** in Android Studio (Hedgehog or newer).
3. **Sync** Gradle to download dependencies (Lottie, Material 3, etc.).
4. **Run** on any device or emulator (API 26+).

---

Built with ❤️ for AI & ML Engineers.