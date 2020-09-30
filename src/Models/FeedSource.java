package Models;

import java.net.MalformedURLException;
import java.net.URL;
import rss.resources.app.Models.FeedSourceContract;
import rss.resources.app.exceptions.FeedException;

/**
 * @author João Pereira
 */
public class FeedSource implements FeedSourceContract {

    private static int idContador = 0;

    private int id;
    private String url;

    public FeedSource(String url) throws FeedException {
        try {
            setURL(url);
            this.id = idContador++;
        } catch (FeedException ex) {
            System.out.println("Invalid Url");
            throw new FeedException();
        }
    }

    /**
     * Method that obtains the {@link FeedSource #id} of {@link FeedSource}
     *
     * @return {@link FeedSource#id}
     */
    @Override
    public int getID() {
        return this.id;
    }

    /**
     * Method that returns {@link FeedSource #url} of {@link FeedSource}
     *
     * @return Uniform Resource Locator of {@link FeedSource}
     */
    @Override
    public String getURL() {
        return this.url;
    }

    /**
     * Method responsible for validating the {@link FeedSource #url}
     *
     * @param url to be validated
     * @throws FeedException exception thrown if {@link FeedSource #url} is
     * invalid
     */
    @Override
    public void setURL(String url) throws FeedException {

        try {
            URL u = new URL(url);
            this.url = url;
        } catch (MalformedURLException ex) {
            throw new FeedException("Invalid URL");
        }
    }

    /**
     * Textual representation of {@link FeedSource}
     *
     * @return {@link FeedSource} information
     */
    @Override
    public String toString() {
        return "FeedSource{" + "id:" + id + ", url:" + url + '}';
    }

}
