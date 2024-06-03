---

# 📒 Notes App

## 📖 Description

The **Notes App** is an Android application that allows users to create, update, and delete notes. Each note consists of a title, content, the time it was created, and the time it was last modified. The notes are stored in an SQLite database and are sorted by the most recently modified notes first.

## ✨ Features

- 📝 **Create Notes**: Users can create new notes with a title and content.
- ✏️ **Update Notes**: Users can update existing notes. The modified time is automatically updated.
- 🗑️ **Delete Notes**: Users can delete notes they no longer need.
- ⏰ **Time Tracking**: Each note records the time it was created and last modified.
- 📅 **Sorted Notes**: Notes are displayed in order of the most recently modified.

## 🚀 Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/FSfarhaan/Notes-App.git
    ```
2. **Open the project in Android Studio**:
    - Open Android Studio
    - Click on `File -> Open`
    - Select the cloned repository folder

3. **Build and Run the app**:
    - Connect your Android device or start an emulator
    - Click on the `Run` button in Android Studio

## 🛠️ Usage

1. **Creating a Note**:
    - Open the app and click on the `➕` button.
    - Enter a title and content for your note and click `✅`.

2. **Updating a Note**:
    - Tap on an existing note to open it.
    - Make changes to the title or content and click `✅`.

3. **Deleting a Note**:
    - Click on the delete icon or Long press a note.

## 🧩 Code Overview

### Main Components

- **MainActivity**: The main activity that displays the list of notes.
- **EditAcitivity**: The activity used to create or edit a note.
- **DbHelper**: A helper class for managing SQLite database operations.

### Database

- **SQLite**: The app uses SQLite for storing notes.
- **DbHelper.java**: Manages database creation, version management, and CRUD operations.

## 🤝 Contributing

Contributions are welcome! Please create an issue or submit a pull request with your improvements.

## 📸 Screenshots

<!-- Add screenshots of your app here. Example: -->
![Screenshot1](screenshots/screenshot5.jpeg)

## 📬 Contact

If you have any questions or suggestions, feel free to contact me at [farhaan8d@gmail.com](mailto:farhaan8d@gmail.com).
or connect me on https://www.linkedin.com/in/farhaan-shaikh-422301252/

---
