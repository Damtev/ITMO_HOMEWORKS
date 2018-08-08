package crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.io.*;
import java.util.regex.*;

public class SimpleWebCrawler implements WebCrawler {

    private HashMap<String, Page> Pages;
    private HashMap<String, Image> Images;
    private Downloader downloader;

    SimpleWebCrawler(final Downloader downloader) throws IOException {
        this.Pages = new HashMap<>();
        this.Images = new HashMap<>();
        this.downloader = downloader;
    }

    public Page crawl(String url, int depth) {
        if (depth == 0) {
            return new Page(url, "");
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(downloader.download(url)))) {
            Pattern pattern = Pattern.compile("<title>.*</title>"); //ищем содержимое тайтла
            Page page = null;
            String title;
            StringBuilder temp = new StringBuilder("");
            while (br.ready()) {
                temp.append(br.readLine());
            }
            String line = replaceWaste(temp.toString().replaceAll("<!-- .*? -->", "")); //убираем комментарии
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String titleGroup = matcher.group();
                title = titleGroup.substring(7, titleGroup.length() - 8); //убираем теги тайтла
                page = new Page(url, title);
            }
            if (!Pages.containsKey(url)) {
                Pages.put(url, page);
            }
            parseLink(line, page, depth);
            parseImage(line, page);
            return page;
        } catch (IOException e) {
            return new Page(url, "");
        }
    }

    private void parseLink(String line, Page page, int depth) {
        Pattern pattern = Pattern.compile("<a.*?>"); //ищем все внутри тега <a...>
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String link = findingLink(matcher.group(), page);
            if (link == null) {
                continue;
            }
            Page curPage = null;
            if (Pages.containsKey(link)) {
                curPage = Pages.get(link);
            } else {
                curPage = crawl(link, depth - 1); //переходи по линку с меньшей глубиной
                if (curPage.getUrl().equals(page.getUrl())) {
                    curPage = page;
                }
                Pages.put(curPage.getUrl(), curPage);
            }
            page.addLink(curPage);
        }
    }

    private String findingLink(String bigLink, Page page) {
        Pattern pattern = Pattern.compile("href\\p{Space}*=\\p{Space}*\".*?\""); //ищем все внутри href'а, кроме пробельного символа
        Matcher matcher = pattern.matcher(bigLink);
        if (matcher.find()) {
            String href = matcher.group();
            Pattern linkPattern = Pattern.compile("\".*\""); //собираем ссылку
            Matcher linkMatcher = linkPattern.matcher(href);
            String link = "";
            if (linkMatcher.find()) {
                link = linkMatcher.group();
                link = link.substring(1, link.length() - 1).trim(); //убираем кавычки
            }
            return toAbsoluteLink(link, page);
        }
        return null;
    }

    private String toAbsoluteLink(String relativeLink, Page page) {
        URL url;
        URL wholeLink = null;
        try {
            url = new URL(page.getUrl());
            wholeLink = new URL(url, relativeLink);
        } catch (MalformedURLException error) {
            System.out.println("URL hasn't been found");
        }
        return wholeLink.toString().replaceAll("#.*", "");
    }

    private void parseImage(String line, Page page) throws IOException {
        Pattern pattern = Pattern.compile("<img.*?>"); //собираем все из тега <img...>
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            Image image = downloadImage(matcher.group(), page);
            if (!Images.containsKey(image.getFile())) {
                Images.put(image.getFile(), image);
            } else {
                image = Images.get(image.getFile());
            }
            page.addImage(image);
        }
    }

    private Image downloadImage(String url, Page page) throws IOException {
        Pattern pattern = Pattern.compile("src *= *\".*?\""); //берем все после src
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String temp = matcher.group();
            Pattern linkPattern = Pattern.compile("\".*\""); //собираем ссылку
            Matcher linkMatcher = linkPattern.matcher(temp);
            String link = "";
            if (linkMatcher.find()) {
                link = linkMatcher.group();
                link = link.substring(1, link.length() - 1).trim();
            }
            String imgLink = toAbsoluteLink(link, page);
            String imgFile = CachingDownloader.encode(imgLink);
            Downloader imgDownloader = new CachingDownloader
                    (new ZipDownloader("base_cache.zip"), "_image"); //сохраняем картинку
            imgDownloader.download(imgLink);
            return new Image(imgLink, String.format("_image\\%s", imgFile)); //кладем картинку в мапу с путем
        }
        return null;
    }

    private static String replaceWaste(String s) {
        return s.replaceAll("(&lt;?)", "<")
                .replaceAll("(&gt;?)", ">")
                .replaceAll("(&amp;?)", "&")
                .replaceAll("&mdash;", "\u2014")
                .replaceAll("&nbsp;", "\u00A0")
                .replaceAll("\n", "");
    }

}