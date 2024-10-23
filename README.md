SuperBrowser - Android Application

Overview

SuperBrowser is an Android-based web browser application designed to enhance the mobile browsing experience. The app includes features for managing bookmarks, browsing multiple pages, and leveraging a simple and user-friendly interface for navigating the web.

Key Features

    •    Bookmark Management: Users can create, view, and manage bookmarks easily, ensuring quick access to frequently visited websites.
    •    Multi-Page Browsing: The app allows users to open and switch between multiple pages, providing a tab-like experience for better multitasking.
    •    Bookmark Activity: A dedicated activity for managing bookmarks, including listing, adding, and removing bookmarks.
    •    Browser Control: Intuitive browser controls for navigating, refreshing, and controlling the web view.
    •    Modular UI: Components such as PageListFragment and PageViewerFragment enhance the user experience by managing page lists and views dynamically.
    •    Android Standards: The app follows Android best practices, including proper manifest management, resource handling, and optimized Gradle configuration.

Structure

        Main Activities:
            • BookmarkActivity.kt: Manage the bookmarks in a user-friendly interface.
            • BrowserActivity.kt: Handles the core browser functionality, allowing users to browse websites, manage tabs, and perform common web browsing actions.
    
        Fragments:
            • PageListFragment.kt: Displays a list of pages (similar to tabs).
            • PageViewerFragment.kt: Renders the content of the web pages being viewed.
        
        ViewModel:
            • BrowserViewModel.kt: Provides the necessary data and business logic for handling browser-related functions like managing bookmarks and page lists.
                
        Tests:
            • ExampleInstrumentedTest.kt: Includes basic instrumented tests for verifying app functionality on Android devices.
            • ExampleUnitTest.kt: Provides unit tests for core logic components.

Requirements

    •    Android SDK: Target SDK version 30 or above.
    •    Gradle: Uses Gradle for project configuration and dependency management.
    •    Kotlin: Core development language for Android components.

