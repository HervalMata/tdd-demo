# Acme Ad Manager Demo

This demo application is built on a J2EE stack with a strict TDD approach and conforming to RESTful principles. The app should be runnable from a local dev environment with minimal fuss. During development, I used embedded jetty for frictionless iteration. Unit tests were always written first, followed by application code to make them pass.

The app includes two main pages which allow the management of Ads and Newspapers and their relationships. All unit and integration tests are passing as of V1.0

## Technologies used

## Web Stack
- **Java 7**
- **Spring MVC**: As the most popular J2EE framework, Spring provides one of the most accessible learning curves of its domain. The Spring project embraces unit testing and provides great facilities for unit testing integration, mock creation and advanced assertion libraries. 
- **Hibernate / JPA**: The standard ORM for J2EE.
- **jUnit**: Standard unit testing framework. Integrates very nicely with Eclipse IDE to enable true TDD development process.
- **Mockito**: Mock library to supplement jUnit.
- **Hamcrest**: Assertion library which provides a variety of matches, including xPath.
- **xPath**: Used to test views and templates by matching expectations against HTML content.
- **Thymeleaf**: Awesome XML based template engine. Templates remain valid HTML, even in uncompiled form, allowing for easy collaboration between designers and developers. It also provides layouts to minimize HTML duplication.

## Development
- **Eclipse**: The best open source IDE that exists :)
- **Maven**: Dependency management and build configuration.
- **HyperSQL**: Memory based database. Other DB backends should be 
- **Embedded Jetty**: allows us to deploy our app directly within Eclipse without the mess of WAR building or deployment to an external application server, saving tons of time.

## TDD Philosophy
TDD in a nutshell (as followed during development of this project):

1. Always start by writing **failing** tests
2. **Only** write application code with the goal of passing tests
3. Once all tests are passing, add new features by repeating the cycle

Test coverage should be close to 100%.

Following TDD allowed me to focus on the problem domain up front without getting tangled up in the implementation. Having solid tests allowed for a level of confidence during ruthless refacting which would not have been possible without following strict TDD. This approach provides a paradigm for building software which dramatically increase both app stability as well as developer producivity.

The tests in the project are divided into two groups:

- **Model Tests**: Although these are not "pure" unit tests, they do focus stricly on the data layer. Mocks could be created to convert these to pure unit tests, but in their current form they also help test any persistence issues outside the model classes.
- **Controller Tests**: Strictly speaking, these are written as Integration tests. Since the controllers in this project are very anemic, using Spring's MVC testing framework allows us to test end-to-end the entire MVC stack for each business function by faking web requests. In doing so we simultaneously test controllers, business logic, data access layer and views, getting a lot more bang for our buck.


