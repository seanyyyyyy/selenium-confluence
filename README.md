
Automation testing:
- value, coverage, maintainability and robustness
- setting restrictions for different users
- different browsers
- assertion messages helpful
- test names good
- consistently good locators
- don't assume test data exists


Implementation notes:
- Page object and locator syntax I followed the official Selenium guidelines
  https://www.selenium.dev/documentation/en/guidelines_and_recommendations/page_object_models/
  
- Chose to use selenium-jupiter as opposed to normal because it had some interesting features I wanted to try out, e.g. tight integration with junit5, out-of-the-box different browsers, android devices and docker containers
  https://bonigarcia.github.io/selenium-jupiter/

Issues:
- inconsistent tagging: some are 'data-test-id', some are 'data-testid'
  
