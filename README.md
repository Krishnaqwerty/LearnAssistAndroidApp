# 🚀 LearnAssist – Your Personalized Learning Companion

LearnAssist is an AI-enhanced Android app designed to **identify slow learners** and **recommend personalized learning materials** based on individual quiz performances. Built with Firebase, Kotlin, and Web technologies, LearnAssist brings **smart education** right to your pocket.

---

## 📸 Preview

> 🎯 Personalized Dashboard → 📊 Quiz Insights → 📺 Smart Video Recommendations

(Include GIF or screenshots here for visual impact.)

---

## ✨ Key Features

- 👋 **Personalized Greeting**  
  Welcomes students with a modern UI and their email ID.

- 🧠 **Smart Quiz Analysis**  
  Tracks subject-wise quiz performance using Firebase Realtime Database.

- 🎓 **Performance-Based Recommendations**  
  Recommends one curated YouTube video per subject based on student performance.

- 📺 **In-App Fullscreen Video Playback**  
  Plays YouTube videos **within the app** in landscape fullscreen using WebView.

- ☁️ **Realtime Firebase Sync**  
  Reads and displays user-specific quiz data from Firebase in real time.

---

## 🔧 Tech Stack

| Technology | Role |
|------------|------|
| **Kotlin** | Android App Development |
| **Firebase Realtime Database** | Storing user quiz performance |
| **Firebase Auth** | User authentication (email-based) |
| **WebView** | Embeds YouTube video player |
| **Material UI** | Modern and responsive app design |
| **MVVM + ViewBinding** | Clean code architecture and safe UI references |

---

## 🧠 Recommendation Logic

> 🧮 **Performance = (score / total) × 100**

| Subject | Score | Total | Video Recommendation |
|---------|-------|-------|-----------------------|
| Math    | 3     | 5     | Video A |
| Physics | 1     | 5     | Video B |
| ...     | ...   | ...   | ...     |

Each subject is mapped to a YouTube video based on the quiz result, and dynamically displayed with thumbnail preview and click-to-play experience.

---

## 📁 Firebase Structure

```plaintext
users
└── UID (e.g., h1eReXYlK6efh8WGeLvlw7ODppc2)
    └── quizResults
        ├── math
        │   ├── score: 4
        │   ├── total: 5
        │   └── timestamp: <ms>
        ├── physics
        └── chemistry
