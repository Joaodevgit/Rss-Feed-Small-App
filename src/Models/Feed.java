package Models;

import java.util.Calendar;
import rss.resources.app.Models.FeedContract;
import rss.resources.app.Models.FeedItemContract;
import rss.resources.app.exceptions.FeedException;
import rss.resources.app.exceptions.ObjectmanagementException;

/**
 * @author João Pereira
 */
public class Feed extends FeedSource implements FeedContract {

    private final int MAX_ITEMS = 1000;
    private final int MAX_CATEGORIES = 10;
    private String title;
    private String description;
    private String language;
    private Calendar buildDate;
    private int numberCategories;
    private int numberItems;
    private FeedItem[] items;
    private String[] categories;

    public Feed(String url) throws FeedException {
        super(url);
        this.numberCategories = 0;
        this.numberItems = 0;
        this.items = new FeedItem[MAX_ITEMS];
        this.categories = new String[MAX_CATEGORIES];
    }

    public Feed(String title, String description, Calendar buildDate, String url) throws FeedException {
        super(url);
        this.title = title;
        this.description = description;
        this.buildDate = buildDate;
        this.numberCategories = 0;
        this.numberItems = 0;
        this.items = new FeedItem[MAX_ITEMS];
        this.categories = new String[MAX_CATEGORIES];
    }

    public Feed(String title, String description, String language, Calendar buildDate,
            String url) throws FeedException {
        super(url);
        this.title = title;
        this.description = description;
        this.language = language;
        this.buildDate = buildDate;
        this.numberCategories = 0;
        this.numberItems = 0;
        this.items = new FeedItem[MAX_ITEMS];
        this.categories = new String[MAX_CATEGORIES];
    }

    /**
     * Method that returns {@link Feed #title} of {@link Feed}
     *
     * @return {@link Feed#title}
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Method responsible for defining {@link Feed #title} of {@link Feed}
     *
     * @param title new title of {@link Feed}
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method that returns {@link Feed #description} of {@link Feed}
     *
     * @return {@link Feed#description}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Method responsible for defining {@link Feed #description} of {@link Feed}
     *
     * @param description new description of {@link Feed}
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method that returns {@link Feed #language} for {@link Feed}
     *
     * @return {@link Feed#language}
     */
    @Override
    public String getLanguage() {
        return this.language;
    }

    /**
     * Method responsible for defining {@link Feed #language} of {@link Feed}
     *
     * @param language new language of {@link Feed}
     */
    @Override
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Method that returns {@link Feed #buildDate} from {@link Feed}
     *
     * @return {@link Feed#buildDate}
     */
    @Override
    public Calendar getBuildDate() {
        return this.buildDate;
    }

    /**
     * Method responsible for defining {@link Feed #buildDate} of {@link Feed}
     *
     * @param buildDate new publication date of {@link Feed}
     */
    @Override
    public void setBuildDate(Calendar buildDate) {
        this.buildDate = buildDate;
    }

    /**
     * Method that returns {@link Feed #numberCategories} of {@link Feed}
     *
     * @return {@link Feed#numberCategories}
     */
    @Override
    public int numberCategories() {
        return this.numberCategories;
    }

    /**
     * Method that returns {@link Feed #numberItems} of {@link Feed}
     *
     * @return {@link Feed#numberItems}
     */
    @Override
    public int numberItems() {
        return this.numberItems;
    }

    /**
     * Method responsible for adding an {@link FeedItem} to the array
     * {@link Feed #items}
     *
     * @param title title of {@link FeedItem}
     *
     * @param contentURL url of {@link FeedItem}
     *
     * @param description description of {@link FeedItem}
     *
     * @param publicationDate publication date of {@link FeedItem}
     *
     * @param category category of {@link FeedItem}
     *
     * @param author author of {@link FeedItem}
     *
     * @return success/failure of the operation
     */
    @Override
    public boolean addItem(String title, String contentURL, String description,
            Calendar publicationDate, String category, String author) {

        if (title == null || contentURL == null || description == null
                || category == null) {
            throw new NullPointerException("Cannot add an item (One or more variables are null)");
        } else if (this.numberItems == MAX_ITEMS) {
            throw new ArrayIndexOutOfBoundsException("Cannot add an item (Array full)");
        } else {
            FeedItem itemTest = new FeedItem(title, contentURL, description, publicationDate, author);
            for (int i = 0; i < this.items.length; i++) {
                if (this.items[i] == null) {
                    this.items[i] = itemTest;
                    this.items[i].addCategory(category);
                    this.numberItems++;
                    System.out.println("Item added successfully");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method responsible for obtaining an {@link FeedItem} in the
     * {@link Feed #items} array given its position in the array
     *
     * @param i position of {@link FeedItem}
     *
     * @return {@link FeedItem} in the given position
     *
     * @throws ObjectmanagementException exception thrown if {@link FeedItem}
     * does not exist in the position given
     */
    @Override
    public FeedItemContract getItem(int i) throws ObjectmanagementException {

        if (this.items[i] == null) {
            throw new ObjectmanagementException("Item does not exist in the given position");
        } else if (i < 0 || i > this.items.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Invalid position");
        }
        return this.items[i];
    }

    /**
     * Method responsible for adding a category to the {@link Feed #categories}
     * array
     *
     * @param category category to be added
     *
     * @return success/failure of the operation
     */
    @Override
    public boolean addCategory(String category) {
        if (category == null) {
            throw new NullPointerException("Cannot add category to feed (Category null)");
        } else if (this.numberCategories == MAX_CATEGORIES) {
            throw new ArrayIndexOutOfBoundsException("Cannot add a category to the feed (Array full)");
        } else {
            for (int i = 0; i < this.categories.length; i++) {
                if (this.categories[i] == null) {
                    this.categories[i] = category;
                    this.numberCategories++;
                    System.out.println("Category added.");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method responsible for obtaining a category in the array of
     * {@link Feed #categories} given its position in the array
     *
     * @param i category position
     *
     * @return category in the given position
     *
     * @throws ObjectmanagementException exception thrown if the category does
     * not exist in the position given
     */
    @Override
    public String getCategory(int i) throws ObjectmanagementException {
        if (this.categories[i] == null) {
            throw new ObjectmanagementException("Category does not exist.");
        } else if (i < 0 || i > this.categories.length - 1) {
            throw new ObjectmanagementException("Invalid position");
        }
        return this.categories[i];
    }

    /**
     * Textual representation of {@link Feed}
     *
     * @return {@link Feed} information
     */
    @Override
    public String toString() {
        String string = "Feed{" + "id: " + getID() + ", title: " + title + ", description: " + description + ", language: " + language
                + ", feed publication date: " + (buildDate == null ? "N/D" : buildDate.getTime()) + " " + super.toString() + "\n" + "Feed items: {" + "\n";
        if (this.numberItems == 0) {
            System.out.println("Feed without items");
        }
        int i = 0;
        while (i < this.numberItems) {
            string += "\t" + this.items[i].toString() + "\n";
            i++;
        }
        String string2 = "\n" + "Feed categories: {" + "\n";

        int j = 0;
        while (j < this.numberCategories) {
            string2 += "\t" + this.categories[j] + "\n";
            j++;
        }
        return string + '}' + string2 + '}';
    }
}
