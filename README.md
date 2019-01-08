# news-scraper-service

<h2>Dependencies</h2>

Download the following jars and add them to your projects classpath.

- gson: <http://www.java2s.com/Code/Jar/g/Downloadgson222jar.htm>
- jsoup: <https://jsoup.org/packages/jsoup-1.11.3.jar>
- jersyjars: <http://www.java2s.com/Code/Jar/j/Downloadjerseyjar.htm>

<h2>How to use scraper</h2>

- Open the <b>\src\java\application.properties</b> file and change the property 'file' accordingly. The scraper will use this file to store scrapped data in JSON format and the service will use this file to read data.

- Scrap the data as and when needed by running the main class: <b>com.news.scraper.ScrapStarter</b>

<h2>How to use service</h2>

- Start the service after scraping is complete.

- Get all authors: <b>/service/authors</b>

- Search authors: <b>/service/authors/{search-text}</b>

- Get all articles: <b>/service/articles</b>

- Search articles by name: <b>/service/articles/byauthor/{search-text}</b>

- Search articles by name: <b>/service/articles/bytitle/{search-text}</b>

- Search articles by name: <b>/service/articles/bydescription/{search-text}</b>
