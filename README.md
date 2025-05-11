# 🏥 **CureTrack** – *Integrated Hospital Capacity Management System*

**CureTrack** is an Android application built in **Java** using **Android Studio**, designed to help users locate hospitals, check bed availability, upload images via **Cloudinary**, and interact using a real-time **chat interface**. It features user profiles, location-aware search, and seamless navigation through a bottom navigation bar.

---

## ✨ **Key Features**

### 🔍 **Hospital Search**

* View nearby hospitals with real-time **bed availability**.
* Integrated **search bar** to filter hospitals by name.
* Tapable hospital images open detailed views.

### 📍 **Location Awareness**

* Auto-displays user’s **current city** (e.g., *Bhopal*).
* 📌 Location icon displayed for better UX.

### ☁️ **Image Upload with Cloudinary**

* 📷 Select images from gallery.
* Upload directly to **Cloudinary**.
* 📄 Uploaded image URL is logged for reference.

### 👤 **User Profile**

* View and update profile information.
* Profile data stored in **Firebase Realtime Database**.

### 🧭 **Bottom Navigation Bar**

* 🔙 Home | 📝 Record/Search | 🔔 Profile/Notifications
* Implemented using `BottomNavigationView`.

### 💬 **Real-Time Chat**

* Chat with other users or groups.
* Supports **media attachments** (images/docs/audio).
* Powered by **Firebase Realtime Database** & **Firebase Storage**.

---

## 📱 **Core Screens & Fragments**

* **HomeFragment** – Displays hospitals with clickable image buttons.
* **RecordFragment** – Search interface for finding hospitals.
* **NotificationFragment** – Placeholder for profile or future notifications.
* **PatienthomeFragment** – Hospital listings & lifecycle-aware UI updates.

---

## 🔗 **Firebase Integration**

* 🔐 **Authentication** – Login/Sign-up.
* 💾 **Realtime Database** – Stores hospital data, user profiles, and chat info.
* 🗂️ **Storage** – Stores attachments (images, docs, audio).

---

## ☁️ **Cloudinary Integration**

* Utilizes Cloudinary's **Java SDK**.
* Requires:

  * `cloud_name`
  * `api_key`
  * `api_secret`
* Image URL accessible through app logs.

#### Example Cloudinary Config:

```java
Map<String, String> config = new HashMap<>();
config.put("cloud_name", "your_cloud_name");
config.put("api_key", "your_api_key");
config.put("api_secret", "your_api_secret");
```

---

## 🖼️ **App Screenshots**

<p align="center">
  <img src="home.png" alt="Home Screen" width="250"/>
  <img src="aiims.png" alt="Search Screen" width="250"/>
  <img src="profile.png" alt="Profile Screen" width="250"/>
  <br/>
  <img src="hospital1.png" alt="Hospital View" width="250"/>
  <img src="hospital2.png" alt="Hospital View 2" width="250"/>
  <img src="appointment.png" alt="Appointment Booking" width="250"/>
</p>

---

## ⚙️ **Installation Guide (Android Studio)**

### 1. 📥 Install Android Studio

👉 [Download Android Studio](https://developer.android.com/studio)

### 2. 🚀 Clone the Repository

```bash
git clone https://github.com/Avichal2004/Cure-track.git
```

### 3. 📂 Open in Android Studio

* Open Android Studio
* Go to `File > Open` and select the cloned project folder

### 4. 📦 Sync Dependencies

* Android Studio will auto-sync Gradle files
* Click "Sync Now" if prompted

### 5. 📱 Set Up Device

* Create an emulator via `Tools > Device Manager`
* Or connect a physical device with **USB debugging** enabled

### 6. ▶️ Run the App

* Click the green **Run** button or press **Shift + F10**

---

## 🔐 **Demo Credentials**

### 🏥 Hospital Login

* **Email**: `bhailala1001@gmail.com`
* **Password**: `22222222`

### 👤 Patient Login

* **Email**: `avichalj48@gmail.com`
* **Password**: `33333333`

---
