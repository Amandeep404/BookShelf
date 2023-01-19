# BookShelf
A simple easy to use app for buying Computer Science related Books.

## App Structure 

**BookShelf** is an Android App build using `Kotlin` and its UI is made using `XML`. The App is using the `MVVM Architecture` so that it is easy to make new changes to the app in the future.
* Splash Screen - For the Splash Screen I have used the new [Splash Screen Api](https://developer.android.com/develop/ui/views/launch/splash-screen) provided by the Android team.
* Api used - I have used [IT Bookstore API](https://api.itbook.store/) for retrieving books data with the data help of `Retrofit` client for parsing `JSON` response.
* Saving data - User can bookmark the books which he/she like or want to buy later. This data is saved in the app's internal storage using the `ROOM library`
so that the user can view the saved books in **Offline-mode**.
* App navigation - I have used `Navigation-components` for easily navigating from one fragment to the other without writting the same boilerplate code again and again.
* Searching - I have implemented the searching of books in the same fragment to challenge my own Self and make the app more user friendly. I have to update the `RecyclerView` according to the search-bar response and show the corresponding result. 


## Screen-Shots

<img src="https://user-images.githubusercontent.com/107784525/213380361-7ed3f750-a8b7-4aad-8578-385d7f8ca171.png" alt="drawing" style="width:200px"/>   <img src="https://user-images.githubusercontent.com/107784525/213380548-47787b99-18c4-4ea2-95b5-b29b1378ba9f.png" style="width:200px"/>   <img src="https://user-images.githubusercontent.com/107784525/213380692-0dc48dce-d2c6-4b28-b2ee-89a42c535cc7.png" style="width:200px"/>

<img src="https://user-images.githubusercontent.com/107784525/213381028-40b55d02-7893-451d-85be-c5902bd0123a.png" style="width:200px"/>   <img src="https://user-images.githubusercontent.com/107784525/213380819-eb0da7b3-7809-4d0b-bb01-f901b01014f6.png" style="width:200px"/> 












