# Base API
This is a sample akka-http api project. One can take reference from this project to create an application which requires to involve the following features.
</br>
i.    Akka-http endpoints </br>
ii.   Akka-http endpoints unit tests </br>
iii.  Slick settings for db </br>
iv.   Slick table mapping and queries </br>
v.    Unit tests of db functions with h2 </br>
vi.   Service layer </br>
vii.  Logging </br>


## How to test
`sbt clean compile test`

## How to run
`sbt run` </br>
This will run the server and look for actual postgres database, so make sure to have the postgres database setup already.
Otherwise if you want to run the api with h2 db, go to TestServer through the intellij and run the server.

