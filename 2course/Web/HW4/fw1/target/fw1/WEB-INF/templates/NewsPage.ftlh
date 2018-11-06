<#import "common.ftl" as c>

<link rel="stylesheet" type="text/css" href="/css/addNews.css">

<@c.page>
    <form method="post" action="/news" id="submit-news-form">
        <input type="hidden" name="action" value="submit">
        <div class="title">
            <label for="news-text">News Text</label>
        </div>
        <textarea name="news-text" form="submit-news-form" title="News Text">Enter text</textarea>
        <input type="submit" value="Submit">
    </form>
</@c.page>