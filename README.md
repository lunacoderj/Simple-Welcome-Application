# 🌌 AI & ML Student Showcase Portal

A state-of-the-art Android application designed for **Artificial Intelligence & Machine Learning** students at **ANITS**. This app provides a seamless, high-performance experience to explore courses, department vision, and contact information with a futuristic UI.

---

## 📸 App Walkthrough

<p align="center">
  <img src="screenshots/home.png" width="22%" />
  <img src="screenshots/about.png" width="22%" />
  <img src="screenshots/courses.png" width="22%" />
  <img src="screenshots/contact.png" width="22%" />
</p>

---

## ✨ Cutting-Edge Features

### 🎨 Modern UI/UX
- **Jetpack Compose Native**: Built entirely with modern declarative UI for maximum fluidity.
- **Glassmorphism Design**: Frosted glass effects and translucent layers for a premium look.
- **Dynamic Animations**: Smooth transitions including `FadeIn`, `SlideIn`, and `ScaleIn` effects.
- **Deep Blue Theme**: A specialized high-contrast dark theme optimized for student focus.

### 📚 Course Exploration
- **Interactive Course Grid**: Browse through specialized tracks like Generative AI, NNDL, and NLP.
- **Real-time Image Loading**: Integration with **Coil** for high-resolution course imagery.
- **Contextual Notifications**: Integrated `Snackbar` feedback for course interactions.

### 🛠️ Robust Navigation
- **Persistent Bottom Bar**: Quick access to Home, About, Courses, and Contact.
- **Top-Level Logic**: Intelligent backstack management to ensure smooth page switching.
- **Floating Action Buttons**: Context-aware CTAs for quick inquiries.

---

## 🛠️ Technical Stack

- **Language**: 100% Kotlin
- **UI Framework**: Jetpack Compose
- **Navigation**: Jetpack Compose Navigation
- **Image Loading**: Coil
- **Animations**: Compose Animation API
- **Architecture**: Modern Android Best Practices

---

## 🏗️ Project Structure

```text
app/src/main/java/com/mad/app/
├── navigation/      # NavGraph and Top-level navigation logic
├── screens/         # Individual screen composables (Home, About, etc.)
├── ui/
│   ├── components/  # Reusable UI elements (GlassCard, CourseCard)
│   └── theme/       # Custom Color schemes, Typography, and Shapes
└── MainActivity.kt  # Root entry point with Scaffold container
```

---

## 🚀 Installation

1.  **Clone** the repository:
    ```bash
    git clone https://github.com/lunacoderj/Simple-Welcome-Application.git
    ```
2.  **Open** in Android Studio (Iguana or newer).
3.  **Sync** Gradle and **Run** on any device with API 26+.

---

Built with 💻 and ☕ for future AI Innovators at **ANITS**.
