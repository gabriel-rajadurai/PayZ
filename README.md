**Project Structure**

3 modules
 - PayZ app module
 - Analytics app module
 - Analytics Interface module

**[PayZ app module](https://github.com/gabriel-rajadurai/PayZ-Interview-Assignment-/tree/main/app)**

This is the client app, and binds to a remote service in the PayZ app, and fetches System metrics before and post a transaction. It also has a WorkManager to periodically fetch the metrics as part of Scheduled maintainenance.

**[Analytics app module](https://github.com/gabriel-rajadurai/PayZ-Interview-Assignment-/tree/main/analytics)**

This is the server app. It exposes AnalyticsService, and returns the AIDL implementation when bound by a client app.

**[Analytics Interface module](https://github.com/gabriel-rajadurai/PayZ-Interview-Assignment-/tree/main/analyticsInterface)**

The Analytics Interface module contains the AIDL interfaces and data classes. This module is dependend upon by both the client app (PayZ) and the server app (Analytics). This helps avoid having to copy the AIDL files into both the server and client app, and also helps maintaining a consistent code for both the apps.

Here is basic sequence diagram explaining the flow

<br><img src = "https://github.com/gabriel-rajadurai/PayZ-Interview-Assignment-/blob/main/sequence-diagram.png"/>
