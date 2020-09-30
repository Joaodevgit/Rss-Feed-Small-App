package Models;

import rss.resources.app.Models.FeedContract;
import rss.resources.app.Models.FeedGroupContract;
import rss.resources.app.Utils.RSSFeedParser;
import rss.resources.app.exceptions.FeedException;
import rss.resources.app.exceptions.GroupException;
import rss.resources.app.exceptions.ObjectmanagementException;

/**
 * @author João Pereira
 */
public class FeedGroup implements FeedGroupContract {

    private final int MAX_FEEDS = 10;

    private int groupID;
    private String groupTitle;
    private String groupDescription;
    private int numberFeeds;
    private FeedContract[] feeds;

    public FeedGroup(int id, String groupTitle, String groupDescription) {
        this.groupID = id;
        this.groupTitle = groupTitle;
        this.groupDescription = groupDescription;
        this.numberFeeds = 0;
        this.feeds = new Models.Feed[MAX_FEEDS];
    }

    public FeedGroup(String groupTitle, String groupDescription) {
        this.groupID = 0;
        this.groupTitle = groupTitle;
        this.groupDescription = groupDescription;
        this.numberFeeds = 0;
        this.feeds = new Models.Feed[MAX_FEEDS];
    }

    /**
     * Method that returns {@link FeedGroup #groupID} of {@link FeedGroup}
     *
     * @return {@link FeedGroup#groupID}
     */
    @Override
    public int getID() {
        return this.groupID;
    }

    /**
     * Method responsible for defining {@link FeedGroup #groupID} of
     * {@link FeedGroup}
     *
     * @param groupID new groupID of {@link FeedGroup}
     */
    private void setID(int groupID) {
        this.groupID = groupID;
    }

    /**
     * Method that returns {@link FeedGroup #groupTitle} of {@link FeedGroup}
     *
     * @return {@link FeedGroup#groupTitle}
     */
    @Override
    public String getTitle() {
        return this.groupTitle;
    }

    /**
     * Method responsible for defining {@link FeedGroup #groupTitle} of
     * {@link FeedGroup}
     *
     * @param groupTitle new title of {@link FeedGroup}
     */
    @Override
    public void setTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    /**
     * Method that returns {@link FeedGroup #groupDescription} of
     * {@link FeedGroup}
     *
     * @return {@link FeedGroup#groupDescription}
     */
    @Override
    public String getDescription() {
        return this.groupDescription;
    }

    /**
     * Method responsible for defining {@link FeedGroup #groupDescription} of
     * {@link FeedGroup}
     *
     * @param groupDescription new description of {@link FeedGroup}
     */
    @Override
    public void setDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    /**
     * Method that returns {@link FeedGroup #numberFeeds} of {@link FeedGroup}
     *
     * @return {@link FeedGroup#numberFeeds}
     */
    @Override
    public int numberFeeds() {
        return this.numberFeeds;
    }

    /**
     * Method responsible for adding an {@link FeedContract} to the
     * {@link FeedGroup} array by its url, preserving the insertion order
     *
     * @param url url to be added
     *
     * @return success/failure of the operation
     *
     * @throws GroupException exception thrown if {@link FeedContract} already
     * exists in the array {@link FeedGroup #feeds}
     */
    @Override
    public boolean addFeed(String url) throws GroupException {

        if (url == null) {
            throw new NullPointerException("Unable to add feed (null url)");
        } else if (hasURL(url)) {
            throw new GroupException("Unable to add the feed (Feed already exists)");
        } else if (numberFeeds == MAX_FEEDS) {
            throw new ArrayIndexOutOfBoundsException("Unable to add the feed (Array full)");
        } else {
            try {
                Feed feedTest = new Feed(url);
                RSSFeedParser.readFeed(feedTest);
                this.feeds[numberFeeds] = feedTest;
                this.numberFeeds++;
                System.out.println("Feed added successfully");
                return true;
            } catch (FeedException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    /**
     * Method responsible for adding an {@link FeedContract} to the
     * {@link FeedGroup} array, preserving the insertion order
     *
     * @param newFeed {@link FeedContract} to be added to the array
     * {@link FeedGroup #feeds}
     *
     * @return success/failure of the operation
     *
     * @throws GroupException exception thrown if {@link FeedContract} already
     * exists in the array {@link FeedGroup #feeds}
     */
    @Override
    public boolean addFeed(FeedContract newFeed) throws GroupException {

        if (newFeed == null) {
            throw new NullPointerException("Unable to add feed (Null feed)");

        } else if (hasFeed(newFeed)) {
            throw new GroupException("Unable to add the feed (Feed already exists)");
        } else if (numberFeeds == MAX_FEEDS) {
            throw new ArrayIndexOutOfBoundsException("Unable to add the feed (Array full)");
        } else {
            this.feeds[this.numberFeeds] = newFeed;
            this.setID(groupID++);
            this.numberFeeds++;
            System.out.println("Feed added successfully");
            return true;
        }
    }

    /**
     * Method responsible for removing {@link FeedContract} in the
     * {@link FeedGroup #feeds} array
     *
     * @param fc {@link FeedContract} to be removid from the array
     *
     * @return success/failure of the operation
     *
     * @throws ObjectmanagementException exception thrown if
     * {@link FeedContract} does not exist in the array {@link FeedGroup #feeds}
     */
    @Override
    public boolean removeFeed(FeedContract fc) throws ObjectmanagementException {
        int pos;
        if (numberFeeds == 0) {
            throw new ObjectmanagementException("Unable to remove the tag (empty array)");
        } else {
            if (findFeed(fc) == -1) {
                throw new ObjectmanagementException("Unable to remove the tag (ID not found)");
            } else {
                pos = findFeed(fc);
                while (pos < this.feeds.length - 1) {
                    this.feeds[pos] = this.feeds[pos + 1];
                    pos++;
                }
                this.feeds[numberFeeds - 1] = null;
                this.numberFeeds--;
                System.out.println("Feed removed successfully");
                return true;
            }
        }
    }

    /**
     * Method responsible for obtaining a {@link FeedContract} in the array of
     * {@link FeedGroup #feeds} given its position in the array
     *
     * @param i position of {@link FeedContract}
     *
     * @return {@link FeedContract} in the given position
     *
     * @throws ObjectmanagementException exception thrown if
     * {@link FeedContract} does not exist in the given position
     */
    @Override
    public FeedContract getFeed(int i) throws ObjectmanagementException {
        if (this.feeds[i] == null) {
            throw new ObjectmanagementException("Feed does not exist at the given position");
        } else if (i < 0 || i > this.feeds.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Invalid position");
        }

        return this.feeds[i];
    }

    /**
     * Method responsible for obtaining an {@link FeedContract} in the
     * {@link FeedGroup #feeds} array given the {@link FeedContract} id
     *
     * @param id id of {@link FeedContract}
     *
     * @return {@link FeedContract} of the given id
     *
     * @throws ObjectmanagementException exception thrown if
     * {@link FeedContract} does not exist with the given id
     */
    @Override
    public FeedContract getFeedByID(int id) throws ObjectmanagementException {
        int i = 0;
        while (i < this.numberFeeds && this.feeds[i].getID() != id) {
            i++;
        }
        if (i == this.numberFeeds) {
            throw new ObjectmanagementException("Feed does not exist in the given id");
        }
        return this.feeds[i];
    }

    /**
     * Returns data for each feed, allowing instantiation of
     * {@link FeedContract} and its {@link FeedItem}
     */
    @Override
    public void getData() {
        for (int i = 0; i < this.numberFeeds; i++) {
            RSSFeedParser.readFeed(this.feeds[i]);
        }
    }

    /**
     * Textual representation of {@link FeedGroup}
     *
     * @return {@link FeedGroup} information
     */
    @Override
    public String toString() {
        String string = "FeedGroup{" + "id: " + groupID + ", title: " + groupTitle + ", description: "
                + groupDescription + "\n" + "Group feeds: {" + "\n";
        int i = 0;
        while (i < this.numberFeeds) {
            string += "\t" + this.feeds[i].toString() + "\n";
            i++;
        }
        return string + '}';
    }

    /**
     * Method responsible for checking whether {@link FeedContract} exists in
     * the {@link FeedGroup #feeds} array or not
     *
     * @param feed to be sought
     *
     * @return success/failure of the operation
     */
    public boolean hasFeed(FeedContract feed) {

        for (FeedContract f : this.feeds) {
            if (f != null) {
                if (f.equals(feed)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method responsible for checking whether {@link FeedContract #getURL ()}
     * exists in the {@link FeedGroup #feeds} array or not
     *
     * @param url url to be sought
     *
     * @return success/failure of the operation
     */
    public boolean hasURL(String url) {

        for (FeedContract f : this.feeds) {
            if (f != null && f.getURL().equals(url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method responsible for finding an {@link FeedContract} in the array
     * {@link FeedGroup #feeds}
     *
     * @param feed to be found
     *
     * @return {@link FeedContract} position in the array
     * {@link FeedGroup #feeds}, if the position is -1 the feed was not found
     */
    public int findFeed(FeedContract feed) {

        int pos = -1;

        for (int i = 0; i < this.feeds.length; i++) {
            if (this.feeds[i] != null && this.feeds[i].equals(feed)) {
                pos = i;
                return pos;
            }
        }
        return pos;
    }
}
