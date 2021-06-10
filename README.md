Implementation notes:
- Page object and locator syntax I followed the official Selenium guidelines
  https://www.selenium.dev/documentation/en/guidelines_and_recommendations/page_object_models/
  
- Wanted to try out selenium-jupiter instead of normal because it had some interesting features: 
  tight integration with junit5, out-of-the-box cross browser, android devices and docker containers
  https://bonigarcia.github.io/selenium-jupiter/

Issues:
- inconsistent tagging: some are 'data-test-id', some are 'data-testid'
- all the dropdown fields in the Restrictions modal have no unique ID or tags
  (all just <div class="css-4avucx-control">) which makes it more difficult to 
  test them individually.

  
