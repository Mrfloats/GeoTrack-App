# Demo Video Script for GeoTrack

Use an Android device (or Android Emulator) to record this 3-5 minute video.

## 1. Introduction (0:00 - 0:30)
- Introduce yourself and the assignment.
- Open the app. Mention the initial permissions prompt. Point out the bottom navigation with "Map" and "Locations" tabs.

## 2. Adding a Location (0:30 - 1:30)
- Ensure your physical location (blue dot) is visible.
- Long-press anywhere on the map or go to the Locations tab and press the **Add (+)** floating action button.
- Give the location a name (e.g., "Library" or "My Favorite Cafe") and save it.
- **Explain:** "Under the hood, this uses Room Database to store the coordinates and name."

## 3. Geo-Fencing Notification (1:30 - 2:30)
- Explain that saving the location also added a 200m Geofence around it using Google's GeofencingClient.
- **If using an Emulator:** Open the Extended Controls (`...` button) -> Location -> change your coordinates to be exactly the location you just saved.
- **If using a physical device:** Walk towards the location (or drop a pin where you currently are standing).
- Wait a few seconds for the notification to pop up on your screen.
- Pull down the notification shade and show the "Geofence Triggered" alert.

## 4. Navigation (2:30 - 3:30)
- Navigate to the "Locations" tab containing your saved points.
- Tap on your newly saved location in the list.
- Show that it smoothly launches the Google Maps app with "Live Directions" from your current position to the destination.

## 5. Conclusion (3:30 - End)
- Briefly wrap up by mentioning the MVVM architecture and API (FusedLocationProviderClient) powers that make this seamless.
