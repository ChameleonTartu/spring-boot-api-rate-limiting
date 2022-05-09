# spring-boot-api-rate-limiting
Show-case of Bucket4J usage for Spring Boot API rate limiting


<a href="https://www.buymeacoffee.com/chameleontartu"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&slug=chameleontartu&button_colour=40DCA5&font_colour=ffffff&font_family=Cookie&outline_colour=000000&coffee_colour=FFDD00"></a>

### Problem solved

This is example of fine-grained rate limiting of Spring Boot API.

There are many approaches to solve the same issue:
- Rate limit on a proxy level
- Not rate limit but scale during the load
- Create global rate-limiting on the application level
- Fine-grained rate limiting on the application level

Each of the approaches has their pros and cons, this demo shows the latest variant.


Possible use-case, API is front of another API, legacy system and requires endpoint limiting. 
For instance, some POST requests require not more than 1, 10, 100 requests per second and some GET requests not more than 1, 100, 10000 per second.


### Acknowledgement

[Haim Raman](https://stackoverflow.com/users/1625740/haim-raman) and [eol](https://stackoverflow.com/users/3761628/eol) for their answers on my [question](https://stackoverflow.com/questions/71763702/how-to-process-custom-annotation-in-spring-boot-with-kotlin/71868173#71868173).

Baeldung for the article about [Bucket4J](https://www.baeldung.com/spring-bucket4j).