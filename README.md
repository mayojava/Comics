# Comics App
This is a sample app that uses the marvel API for to display top comics

# Architecture
It uses a reactive clean architecture where the locally stored data is the only source of truth. Subscription is
made to the stored data and when this data is updated, it automatically refreshes the UI. This was accomplished using
RxJava and Room.

The application is divided into 3 different layers:

* *App layer*: This is the presentation layer that contains the view and view models. `ViewModels` are used to
interact with the interactors that fetches and subscribes to data from the repository.

* *Domain Layer*: This contains the interactors for the application use cases. It also exposes interface that are used for
communication with the data layer. No android framework dependencies exists in this layer.

* Data layer: This layer contains both the reactive local store and a service to make remote api calls. It implements
interfaces from the domain layer to perform interactors use cases.

 # Libraries used
 * Android architecture components
 * RxJava 2
 * Room
 * Dagger 2
 * Auto Value
 * Retrofit
 * Okttp
 * Options
 * Picasso
 * Mockito
 * AssertJ

 # Todos:

 * Unit test with Espresso
 * Add shared element transition from list to details screen
 * Paginate result
 * Implement a dynamic programming (Knapsack) algorithm to compute maximum number to comics that can be bought without
 exceeding a budget.
