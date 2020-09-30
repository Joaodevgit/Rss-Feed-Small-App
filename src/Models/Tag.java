package Models;

import rss.resources.app.Models.TagContract;

/**
 * @author João Pereira
 */
public class Tag implements TagContract {

    private int id;
    private String name;

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Method that returns {@link Tag #id} of {@link Tag}
     *
     * @return id of {@link Tag}
     */
    @Override
    public int getID() {
        return this.id;
    }

    /**
     * Method responsible for defining the {@link Tag #id} of {@link Tag}
     *
     * @param id new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method that returns the {@link Tag #name} of {@link Tag}
     *
     * @return {@link Tag#name}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Method responsible for defining {@link Tag #name} of {@link Tag}
     *
     * @param name new {@link Tag#name}
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    /**
     * Textual representation of {@link Tag}
     *
     * @return {@link Tag} information
     */
    @Override
    public String toString() {
        return "Tag{" + "id:" + id + ", name:" + name + '}';
    }
}
