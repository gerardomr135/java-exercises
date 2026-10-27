# Main functionality

Users who are interested in watching a movie, can query key information about them.

# High level view

Application provides web user interface.

1. Movie search
- User enters movie title search expression to find movies.
- The page displays list of movies matching the search expression.

Movie fields to be displayed:
- Title
- Year

Title is a hyperlink to movie details page.

2. Movie Details

The page display key information about the movie:
- Title
- Year
- Genre
- Directors
- Writers
- Actors
- Plot
- imdbRating

# Integrations

Application calls public movie query service via rest api to search movies and get movie information.

Base URL: https://api.imdbapi.dev

## Title Search

endpoint: /search/titles
HTTP method: GET
Query parameters:
- query: string, the search expression

Response: list of movies matching the search expression

## Movie Details

endpoint: /titles/{id}
HTTP method: GET
Path parameters:
- id: string, the movie id

# Architectural constraints
- java version 17
- Spring Framework, Spring Boot version 3.1.4
- maven for build
- web framework: Spring MVC
- view technology: Thymeleaf and Bootstrap
- persistent storage: N/A
