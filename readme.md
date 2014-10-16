# Acme Ad Manager Demo=

This is a demo application built on a J2EE stack with a strict TDD approach and conforming to RESTful principles. The app should be runnable from a local dev environment with minimal fuss. During development, I used embedded jetty for frictionless iteration.

The app includes two main pages which allow the management of Ads and Newspapers and their relationships. All unit and integration tests are passing as of V1.0

## Technologies used

## Web Stack
- Java 7
- Spring MVC
- jUnit
- Mockito
- Hamcrest
- Thymeleaf

## Dev

- Eclipse
- Maven
- HyperSQL 
- Embedded Jetty

## TDD Philosophy
TDD in a nutshell as followed during development of this project:

1. Write failing tests
2. Only write application code with the goal of passing tests
3. Once all tests are passing, add new features by repeating the cycle

Test coverate should be close to 100%.

Following TDD allowed me to focus on the problem domain up front without getting tangled up in the implementation. Having solid tests allowed for a level of confidence during ruthless refacting which would not have been possible without following strict TDD.

The tests in the project are divided into two groups:

- Model Tests: Although these are not "pure" unit tests, they focus stricly on the data layer. Mocks could be created to convert these to pure unit tests, but in their current form they also help test any issues outside the models.
- Controller Tests: Strictly speaking, these are written as Integration tests. Since the controllers in this project are very anemic, using Spring's MVC testing framework allows us to test end-to-end the entire MVC stack for each business function. In doing so we simultaneously test controllers, business logic, data access layer and views.


