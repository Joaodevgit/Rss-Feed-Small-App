package Models;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rss.resources.app.Models.FeedItemContract;
import rss.resources.app.Models.TagContract;
import rss.resources.app.exceptions.FeedException;
import rss.resources.app.exceptions.ObjectmanagementException;

/**
 * @author João Pereira
 */
public class FeedItem implements FeedItemContract {

    private final int MAX_TAGS = 5;
    private final int MAX_CATEGORIES = 10;

    private int itemID;
    private String title;
    private String contentUrl;
    private String description;
    private Calendar publicationDate;
    private String author;
    private String[] categories;
    private int numberCategories;
    private Tag[] tags;
    private int numberTags;
    private static int cont = 0;
    private static int contItem = 0;
    private static int tagID = 0;

    public FeedItem() {
        this.tags = new Tag[MAX_TAGS];
    }

    public FeedItem(String title, String contentUrl, String description, Calendar publicationDate,
            String author) {
        this.itemID = contItem++;
        this.title = title;
        try {
            setContentURL(contentUrl);
        } catch (FeedException ex) {
            System.out.println(ex.getMessage());
        }
        this.description = description;
        this.categories = new String[MAX_CATEGORIES];
        this.numberCategories = 0;
        this.publicationDate = publicationDate;
        this.author = author;
        this.numberTags = 0;
        this.tags = new Tag[MAX_TAGS];
    }

    /**
     * Method that returns {@link FeedItem #itemID} of {@link FeedItem}
     *
     * @return {@link FeedItem#itemID}
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * Method responsible for defining {@link FeedItem #itemID} of
     * {@link FeedItem}
     *
     * @param itemID new itemID of {@link FeedItem}
     */
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    /**
     * Method that returns {@link FeedItem #title} of {@link FeedItem}
     *
     * @return {@link FeedItem#title}
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Method responsible for defining {@link FeedItem #title} of
     * {@link FeedItem}
     *
     * @param title new title of {@link FeedItem}
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method that returns {@link FeedItem #contentUrl} of {@link FeedItem}
     *
     * @return {@link FeedItem#contentUrl}
     */
    @Override
    public String getContentURL() {
        return this.contentUrl;
    }

    /**
     * Method responsible for defining and validating the url
     *
     * @param contentUrl {@link FeedItem#contentUrl} of {@link FeedItem}
     * @throws FeedException exception thrown if the url is not valid
     */
    @Override
    public void setContentURL(String contentUrl) throws FeedException {
        String http = "http://";
        String https = "https://";
        if (!(contentUrl.startsWith(http)) && !(contentUrl.startsWith(https))) {
            throw new FeedException("Invalid URL");
        }
        this.contentUrl = contentUrl;
    }

    /**
     * Method that returns {@link FeedItem #description} of {@link FeedItem}
     *
     * @return {@link FeedItem#description}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Method responsible for defining {@link FeedItem #description} of
     * {@link FeedItem}
     *
     * @param description new description of {@link FeedItem}
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method responsible for adding a category to the
     * {@link FeedItem #categories} array
     *
     * @param category to be added
     *
     * @return success/failure of the operation
     */
    @Override
    public boolean addCategory(String category) {
        if (category == null) {
            throw new NullPointerException("Cannot add category to item (Category null)");
        } else if (this.numberCategories == MAX_CATEGORIES) {
            throw new ArrayIndexOutOfBoundsException("Cannot add a category to the item (Array full)");
        } else {
            /**
             * This for loop was made in case of being necessary adding a method
             * that removes the categories from a feedItem
             */
            for (int i = 0; i < this.categories.length; i++) {
                if (this.categories[i] == null) {
                    this.categories[i] = category;
                    this.numberCategories++;
                    System.out.println("Item category added.");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method responsible for obtaining a category in the
     * {@link FeedItem #categories} array given its position in the array
     *
     * @param i category postion
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
            throw new ArrayIndexOutOfBoundsException("Invalid position");
        }
        return this.categories[i];
    }

    /**
     * Method that returns {@link FeedItem #numberCategories} of
     * {@link FeedItem}
     *
     * @return {@link FeedItem#numberCategories}
     */
    @Override
    public int numberCategories() {
        return this.numberCategories;
    }

    /**
     * Method that returns {@link FeedItem #publicationDate} of {@link FeedItem}
     *
     * @return {@link FeedItem#publicationDate}
     */
    @Override
    public Calendar getPublicationDate() {
        return this.publicationDate;
    }

    /**
     * Method responsible for defining {@link FeedItem #publicationDate} of
     * {@link FeedItem}
     *
     * @param publicationDate new publication date of {@link FeedItem}
     */
    @Override
    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Method that returns {@link FeedItem author} of {@link FeedItem}
     *
     * @return {@link FeedItem#author }
     */
    @Override
    public String getAuthor() {
        return this.author;
    }

    /**
     * Method responsible for defining {@link FeedItem #author} of
     * {@link FeedItem}
     *
     * @param author new author of {@link FeedItem}
     */
    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Method that returns {@link FeedItem #numberTags} of {@link FeedItem}
     *
     * @return {@link FeedItem#numberTags}
     */
    @Override
    public int numberTags() {
        return this.numberTags;
    }

    public TagContract setTag(int i, String tag) {

        if (i < 0 || i > this.tags.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Invalid position");
        } else if (tag == null) {
            throw new NullPointerException("Null tag");
        }

        this.tags[i].setName(tag);

        return this.tags[i];
    }

    /**
     * Add an {@link Tag} taking into account the conditions of the array
     * {@link FeedItem #tags} (if it is full, if the tag name already exists or
     * if the tag to be added has a null value)
     *
     * @param tagName {@link Tag#name}
     *
     * @return success/failure of the operation
     *
     * @throws FeedException if the tag name already exists in the array
     */
    @Override
    public boolean addTag(String tagName) throws FeedException {

        if (tagName == null) {
            throw new NullPointerException("Unable to add tag (null tag name)");
        } else if (hasTag(tagName)) {
            throw new FeedException("Unable to add tag (existing tag)");
        } else if (numberTags == MAX_TAGS) {
            throw new ArrayIndexOutOfBoundsException("Unable to add tag (full tag array)");
        } else {
            Tag temp_tag = new Tag(this.tagID++, tagName);
            this.tags[this.numberTags] = temp_tag;
            this.numberTags++;
            System.out.println("Tag successfully added");
            return true;
        }
    }

    /**
     * Removes an {@link Tag} in the {@link FeedItem #tags} array given its
     * {@link Tag #id}
     *
     * @param id of {@link Tag} to be removed
     *
     * @return success/failure of the operation
     *
     * @throws ObjectmanagementException exception thrown if the {@link Tag} to
     * be removed is null
     */
    @Override
    public boolean removeTag(int id) throws ObjectmanagementException {

        if (numberTags == 0) {
            throw new NullPointerException("Unable to remove the feed");
        } else {
            int pos = findTagByID(id);
            if (pos == -1) {
                throw new ObjectmanagementException("Unable to remove feed (Feed not found)");
            } else {
                while (pos < this.tags.length - 1) {
                    this.tags[pos] = this.tags[pos + 1];
                    pos++;
                }
                this.tags[numberTags - 1] = null;
                this.numberTags--;
                System.out.println("Tag removed successfully");
                return true;
            }
        }
    }

    /**
     * Method responsible for obtaining an {@link Tag} in the array of
     * {@link FeedItem #tags} given its position in the array
     *
     * @param i position of {@link Tag}
     *
     * @return success/failure of the operation
     *
     * @throws ObjectmanagementException exception thrown if {@link Tag} does
     * not exist in the position given
     */
    @Override
    public TagContract getTag(int i) throws ObjectmanagementException {

        if (this.tags[i] == null) {
            throw new ObjectmanagementException("Tag does not exist in the given position");
        } else if (i < 0 || i > this.tags.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Invalid position");
        }
        return this.tags[i];
    }

    /**
     * Method responsible for writing/storing a {@link FeedItem} to file in JSON
     * format (each item is stored in a separate file, that is, 10 items 10
     * files)
     */
    @Override
    public void saveItem() {
        JSONObject item = new JSONObject();

        item.put("title", getTitle());
        item.put("author", getAuthor());
        item.put("description", getDescription());
        item.put("url", getContentURL());

        JSONArray tagsFile = new JSONArray();
        for (int i = 0; i < this.numberTags; i++) {
            if (this.tags[i] != null) {
                JSONObject tagDetails = new JSONObject();
                tagDetails.put("id", this.tags[i].getID());
                tagDetails.put("name", this.tags[i].getName());
                tagsFile.add(tagDetails);
            }
        }
        item.put("tags", tagsFile);

        JSONArray categoriesFile = new JSONArray();
        for (int i = 0; i < this.numberCategories; i++) {
            if (this.categories[i] != null) {
                categoriesFile.add(this.categories[i]);
            }
        }

        item.put("categories", categoriesFile);
        try (FileWriter file = new FileWriter("dataItems/item_" + this.itemID + ".json")) {
            file.write(item.toJSONString());
            System.out.println("ITEM SAVED IN FILE");
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(FeedItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method responsible for checking whether the {@link Tag} exists in the
     * {@link FeedItem #tags} array or not
     *
     * @param tag {@link Tag} to be found
     *
     * @return success/failure of the operation
     */
    public boolean hasTag(String tag) {

        for (Tag t : this.tags) {
            if (t != null && tag.equals(t.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method responsible for finding an {@link Tag} in the array of
     * {@link FeedItem #tags} given its id
     *
     * @param id of {@link Tag} to be found
     *
     * @return {@link Tag} position in array {@link FeedItem #tags}
     */
    public int findTagByID(int id) {

        int pos = -1;

        for (int i = 0; i < this.tags.length; i++) {
            if (this.tags[i] != null && this.tags[i].getID() == id) {
                pos = i;
                return pos;
            }
        }
        return pos;
    }

    /**
     * Textual representation of {@link FeedItem}
     *
     * @return {@link FeedItem} information
     */
    @Override
    public String toString() {

        String string = "FeedItem{" + "id: " + itemID + ", title: " + title + ", url: " + contentUrl + ", description: "
                + description + ", itemPublicationDate: " + (publicationDate == null ? "N/D" : publicationDate.getTime()) + ", author: "
                + author + ", numberTags: " + numberTags + "\n" + "Item tags: {" + "\n";
        int i = 0;
        while (i < this.numberTags) {
            string += "\t" + this.tags[i].toString() + "\n";
            i++;
        }

        String string2 = "\n" + "Item categories: {" + "\n";

        int j = 0;
        while (j < this.numberCategories) {
            string2 += "\t" + this.categories[j] + "\n";
            j++;
        }

        return string + '}' + string2 + '}';
    }
}
