package Controllers;

import Models.FeedGroup;
import Models.FeedItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import rss.resources.app.Controllers.AppContract;
import rss.resources.app.Models.FeedGroupContract;
import rss.resources.app.Models.FeedItemContract;
import rss.resources.app.exceptions.FeedException;
import rss.resources.app.exceptions.ObjectmanagementException;

/**
 * @author João Pereira
 */
public class App implements AppContract {

    private final int MAX_GROUPS = 100;

    private FeedGroup[] groups;
    private int numberGroups;
    private int groupid = 0;

    public App() {
        this.groups = new FeedGroup[MAX_GROUPS];
        this.numberGroups = 0;
    }

    /**
     * Method that returns {@link App #numberGroups} of {@link App}
     *
     * @return {@link App#numberGroups}
     */
    @Override
    public int numberGroups() {
        return this.numberGroups;
    }

    /**
     * Method that returns the array {@link App #groups} of {@link App}
     *
     * @return array {@link App#groups}
     */
    @Override
    public FeedGroupContract[] getAllGroups() {
        return this.groups;
    }

    /**
     * Method responsible for adding an {@link FeedGroup} to the array
     * {@link App #groups}, preserving the insertion order
     *
     * @param title of {@link FeedGroup}
     *
     * @param description of {@link FeedGroup}
     *
     * @return success/failure of the operation
     */
    @Override
    public boolean addGroup(String title, String description) {

        if (title == null || description == null) {
            throw new NullPointerException("Unable to add a feed group (One or more variables are null)");
        } else if (this.numberGroups == MAX_GROUPS) {
            throw new ArrayIndexOutOfBoundsException("Unable to add a group (Array full)");
        } else {
            FeedGroup temp_group = new FeedGroup(this.groupid++, title, description);

            for (int i = 0; i < this.groups.length; i++) {
                if (this.groups[i] == null) {
                    this.groups[i] = temp_group;
                    this.numberGroups++;
                    System.out.println("Group added successfully");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method responsible for removing the first occurrence of an
     * {@link FeedGroup} in the {@link App #groups} array
     *
     * @param id id of {@link FeedGroup} to be removed
     *
     * @return success/failure of the operation
     *
     * @throws ObjectmanagementException exception thrown if {@link FeedGroup}
     * does not exist in the array {@link App #groups}
     */
    @Override
    public boolean removeGroup(int id) throws ObjectmanagementException {

        int pos;

        if (this.numberGroups == 0) {
            throw new NullPointerException("Cannot remove the group");
        } else {
            if (findGroupByID(id) == -1) {
                throw new ObjectmanagementException("Cannot remove feed group (Group not found)");
            } else {
                pos = findGroupByID(id);
                while (pos < this.groups.length - 1) {
                    this.groups[pos] = this.groups[pos + 1];
                    pos++;
                }
                this.groups[this.numberGroups - 1] = null;
                this.numberGroups--;
                System.out.println("Feed group removed successfully");
                return true;
            }
        }
    }

    /**
     * Method responsible for obtaining an {@link FeedGroup} in the array of
     * {@link App #groups} given its position in the array
     *
     * @param pos position of {@link FeedGroup}
     *
     * @return {@link FeedGroup} in the given position
     *
     * @throws ObjectmanagementException exception thrown if {@link FeedGroup}
     * does not exist in the position given
     */
    @Override
    public FeedGroupContract getGroup(int pos) throws ObjectmanagementException {
        if (this.groups[pos] == null) {
            throw new ObjectmanagementException("Group does not exist in the given position");
        } else if (pos < 0 || pos > this.groups.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Invalid Position");
        }

        return this.groups[pos];
    }

    /**
     * Method responsible for obtaining an {@link FeedGroup} in the array of
     * {@link App #groups} given the id of the {@link FeedGroup}
     *
     * @param id id of {@link FeedGroup}
     *
     * @return {@link FeedGroup} of the given id
     *
     * @throws ObjectmanagementException exception thrown if {@link FeedGroup}
     * does not exist with the given id
     */
    @Override
    public FeedGroupContract getGroupByID(int id) throws ObjectmanagementException {
        for (int i = 0; i < this.groups.length; i++) {
            if (this.groups[i].getID() == id) {
                return this.groups[i];
            }
            if (i == MAX_GROUPS - 1) {
                throw new ObjectmanagementException("Group does not exist in the given position");
            }
        }
        return null;
    }

    /**
     * Method responsible for searching all {@link FeedItem} saved that contain
     * a specific tag
     *
     * @param tag tag to be searched
     *
     * @return {@link FeedItem} of the given tag
     */
    @Override
    public FeedItemContract[] getItemsByTag(String tag) {

        try {
            int t = 0;
            FeedItemContract[] feedItems = new FeedItem[1000];
            for (int i = 0; i < this.numberGroups; i++) {
                for (int j = 0; j < this.groups[i].numberFeeds(); j++) {
                    for (int k = 0; k < this.groups[i].getFeed(j).numberItems(); k++) {
                        for (int l = 0; l < this.groups[i].getFeed(j).getItem(k).numberTags(); l++) {
                            if (this.groups[i].getFeed(j).getItem(k).getTag(l).getName().equals(tag)) {
                                feedItems[t] = this.groups[i].getFeed(j).getItem(k);
                                t++;
                            }
                        }
                    }
                }
            }

            FeedItemContract[] items = new FeedItemContract[t];
            for (int k = 0; k < t; k++) {
                items[k] = feedItems[k];
            }

            return items;
        } catch (ObjectmanagementException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Method responsible for writing/storing the {@link App #groups} to JSON
     * format file
     *
     * @throws Exception exception thrown when there are problems at the level
     * of I/O or conversion problems that may occur
     */
    @Override
    public void saveGroups() throws Exception {

        JSONArray grupos = new JSONArray();
        JSONObject group = new JSONObject();

        for (int i = 0; i < this.numberGroups; i++) {
            if (this.groups[i] != null) {
                JSONObject groupDetails = new JSONObject();
                groupDetails.put("id", this.groups[i].getID());
                groupDetails.put("title", this.groups[i].getTitle());
                groupDetails.put("description", this.groups[i].getDescription());
                grupos.add(groupDetails);
            }
        }
        group.put("group", grupos);

        try (FileWriter file = new FileWriter("dataGroups/grupos.json")) {

            file.write(group.toJSONString());
            file.flush();
            file.close();
            System.out.println("Group SAVED IN FILE");
        } catch (Exception ex) {
            System.out.println("I/O problem");
        }

    }

    /**
     * Method responsible for reading/loading the {@link App #groups} from a
     * file in JSON format
     *
     * @throws Exception exception thrown when there are problems at the level
     * of I/O or conversion problems that may occur
     */
    @Override
    public void loadGroups() throws Exception {
        JSONParser parser = new JSONParser();
        try {
            Object grupos = parser.parse(new FileReader("dataGroups/grupos.json"));
            JSONObject jsonObject = (JSONObject) grupos;

            JSONArray arrayGrupos = (JSONArray) jsonObject.get("group");
            System.out.println("Group(s) loaded from JSON file: ");

            for (Object gr : arrayGrupos) {
                System.out.println(gr.toString());
            }

        } catch (IOException | ParseException ex) {
            System.out.println("I/O problem");
        }
    }

    /**
     * Returns the number of {@link FeedItem} stored in {@link App}
     *
     * @return array {@link FeedItem}
     */
    @Override
    public FeedItem[] getAllSavedItems() {

        FeedItem[] feedItems = new FeedItem[100];
        int i = 0;
        File data = new File("dataItems"); // access/read data directory

        File[] ficheiros = data.listFiles(); // lists all files within the directory

        for (File ficheiro : ficheiros) {
            if (ficheiro.getName().startsWith("item_") && ficheiro.getName().endsWith(".json")) { // filter all files that are feed items
                feedItems[i] = getSavedItem(ficheiro);
                i++;
            }
        }

        FeedItem[] items = new FeedItem[i];
        for (int k = 0; k < i; k++) {
            items[k] = feedItems[k];
        }

        return items;
    }

    /**
     * Method that looks for/gets a {@link FeedItem} saved in files
     *
     * @param ficheiro where is the item to be read
     *
     * @return the {@link FeedItem} loaded
     */
    private FeedItem getSavedItem(File ficheiro) {
        try {
            FeedItem feedItem = new FeedItem();

            FileReader fr = new FileReader(ficheiro);

            JSONObject j = (JSONObject) new JSONParser().parse(fr); // parse the file in json and build the JSONObject 

            feedItem.setTitle((String) j.get("title"));
            feedItem.setAuthor((String) j.get("author"));
            feedItem.setDescription((String) j.get("description"));
            try {
                feedItem.setContentURL((String) j.get("url"));
            } catch (FeedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(j.toString());
            fr.close();
            return feedItem;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Removes a given {@link FeedItem} stored in a JSON file given its position
     *
     * @param i position of {@link FeedItem} to be removed
     *
     * @return success/failure of the operation
     */
    @Override
    public boolean removeSavedItem(int i) {

        File data = new File("dataItems"); // access/read data directory

        File[] ficheiros = data.listFiles(); // lists all files within the directory
        int n = -1;
        for (File ficheiro : ficheiros) {
            if (ficheiro.getName().startsWith("item_") && ficheiro.getName().endsWith(".json")) { // filter all files that are feed items
                n++;
                if (i == n) {
                    return ficheiro.delete();
                }
            }
        }

        return false;
    }

    /**
     * Method responsible for finding an {@link FeedGroup} in the array of {@link App # groups} given its id
     * 
     * @param id of {@link FeedGroup} to be found
     *
     * @return position of {@link FeedGroup} in the array{@link App#groups}
     */
    public int findGroupByID(int id) {

        int pos = -1;

        for (int i = 0; i < this.groups.length; i++) {
            if (this.groups[i] != null) {
                if (this.groups[i].getID() == id) {
                    pos = i;
                    return pos;
                }
            }
        }
        return pos;
    }

}
