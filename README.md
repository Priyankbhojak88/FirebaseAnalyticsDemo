
Firebase Analytics - All In One
Date: July 14 2016
Overview

Hello friends this blog is for Firebase Analytics for it’s different features. Here I have explain step by step features of firebase analytics with examples.

You can find details on below topics from this blog,

Analytics - Google Dashboard,Events,Audiences,Attribution,Funnels,Cohorts,User Properties.
Authentication - Users,Sign-In Methods,Email templates
Storage
Hosting
Remote Config
Test Lab for Android
Crash Reporting
Notifications
Dynamic Links
AdMob
Getting started
Pre-requisites
Android Studio 1.5 or above
Google Play Services 9.0.2 or above.
Add Firebase to your app
Go to Firebase console and click on “CREATE NEW PROJECT”. Fill the “Project Name” and Select Country, then click on “CREATE PROJECT”


Click on “Add Firebase to your Android app”


Enter Package name and Debug signing certificate SHA-1(Optional) and click on “Add app” button


Google-services.json file will be downloaded. Go to Android Studio and Switch to the Project view in Android Studio to see your project root directory. Move the google-services.json file you just downloaded into your Android app module root directory.

The Google services plugin for Gradle loads the google-services.json file you just downloaded. Modify your build.gradle files to use the plugin.
Add the following lines, to your Project-level build.gradle:
buildscript {
    dependencies {
    // Add this line
    classpath 'com.google.gms:google-services:3.0.0'
  }
}

Add the following lines to the bottom of your App-level build.gradle:
// Add to the bottom of the file
apply plugin: 'com.google.gms.google-services'
Add the following dependencies, to your App-level build.gradle
compile 'com.google.firebase:firebase-crash:9.0.2'
Finally, press "Sync now"
Code
In demo you will find build.gradle dependencies which i used for different features.
dependencies {
   compile fileTree(dir: 'libs', include: ['*.jar'])
   testCompile 'junit:junit:4.12'
   compile 'com.android.support:appcompat-v7:24.0.0'
   // To Connect with Firebase Analytics
   compile 'com.google.firebase:firebase-analytics:9.2.0'
   // no inspection Gradle Compatible
   compile 'com.google.android.gms:play-services:9.2.0'
   // To get ads details
   compile 'com.google.android.gms:play-services-ads:9.2.0'
   // To get crash report
   compile 'com.google.firebase:firebase-crash:9.0.0'
   // To get details of App invite
   compile 'com.google.android.gms:play-services-appinvite:9.2.0'
   // To get push notifications
   compile 'com.google.firebase:firebase-messaging:9.2.0'
   // For User Authentication
   compile 'com.google.firebase:firebase-auth:9.0.2'
   // For Firebase Remote Config
   compile 'com.google.firebase:firebase-config:9.0.0'
}

Let’s start with Analytics

It is a free app measurement tool which provided details related to user engagement and activities, It is very useful measurement solution to check application performance.

The good thing about this tool is it is free and unlimited.it has capebility to generate reports which can be very useful to check application behaviour.on basis of that report we can take decisions for better performance and optimization.   

After adding analytics SDK with your app. You are able to view data within 24 hours.You need to follow this steps:

Firebase console > Select your project  > Select your app >That's it you are now in console. 

Console has different sections which are as below.

Google Dashboard 
It displays application data in different tabular and graphical representation.some of the example screenshot are at bellow.








^ Active Users ^



^ User Engagement  ^


^ Device Model & OS Versions ^



^ Location & Country/Region ^




Events 
Next tab of the Analytics is event in that there will be full list of events that we are using in application. It has setting to enable and disable events.It also provides details for how many time perticuler event get fired with users number.


^ Event list ^

Audiences  
It has group groups of users with common attributes.we can create Audiences to target specific users.It has diffrent criteria. 


 ^ Create Audience ^



  ^ Audience List ^

Attribution 
Attribution tab shows how many conversion events were driven by each source and ad network.It is basically useful to check which part is most effective and to know where your users came from.


 ^ Attribution list ^
Funnels
Funnels basically use to calculate completion rate between two events.so with use of this we can track the specific stage that completed by user or not.

Here I have created one funnel with name Letsnurture with three events  notification_foreground,notification_received,notification_open and here is the result shown in below screenshots. 


^ Create funnels ^

^ Funnels list ^

^ Funnel Detail ^
Cohorts
This feature gives detail about percentage of returning users in graphical view.in graph each row represents a cohort.Last row represents the most recent cohort. first row represents the earliest cohort.If the returning user's percentage goes high shades become dark and if it goes down shades become lighter. 


^ Example Cohorts ^

User properties 
This feature is use to create custom properties to track it. User can create their own property and focus on that created property. In android there is a method setUserProperty where they can pass their own property and then just call logEvent thats it.it will start tracking for that property. 
Authentication - Users,Sign-In Methods,Email templates
Authentication 


What’s inside code?

In demo code there are different methods which are used in Firebase Analytics.In my demo code there is FirebaseAnalyticsActivity  class Which contains all the details related to diffrent Analytics features.Some of the use full methods are mention at below.
Get the sample code
Clone or Download the GitHub repository from below link :
https://github.com/Priyankbhojak88/FirebaseAnalyticsDemo


