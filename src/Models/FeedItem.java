/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.File;
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

/*
* Nome: João Tiago Moreira Pereira
* Número: 8170202
* Turma: LEI1T2
*
* Nome: José Miguel Araújo de Carvalho
* Número: 8150146
* Turma: LEI1T2
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
     * Método que retorna o {@link FeedItem#itemID} do {@link FeedItem}
     *
     * @return {@link FeedItem#itemID}
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * Método responsável pela substituição do {@link FeedItem#itemID} de
     * {@link FeedItem}
     *
     * @param itemID substitui o id do item de {@link FeedItem}
     */
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    /**
     * Método que retorna o {@link FeedItem#title} do {@link FeedItem}
     *
     * @return {@link FeedItem#title}
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Método responsável pela substituição do {@link FeedItem#title} de
     * {@link FeedItem}
     *
     * @param title substitui o título de {@link FeedItem}
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Método que retorna o {@link FeedItem#contentUrl} do {@link FeedItem}
     *
     * @return {@link FeedItem#contentUrl}
     */
    @Override
    public String getContentURL() {
        return this.contentUrl;
    }

    /**
     * Método responsável pela substituição e validação do url
     *
     * @param contentUrl {@link FeedItem#contentUrl} de {@link FeedItem}
     * @throws FeedException exceção lançada caso o url não seja válido
     */
    @Override
    public void setContentURL(String contentUrl) throws FeedException {
        String http = "http://";
        String https = "https://";
        if (!(contentUrl.startsWith(http)) && !(contentUrl.startsWith(https))) {
            throw new FeedException("URL inválido");
        }
        this.contentUrl = contentUrl;
    }

    /**
     * Método que retorna a {@link FeedItem#description} do {@link FeedItem}
     *
     * @return {@link FeedItem#description}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Método responsável pela substituição da {@link FeedItem#description} de
     * {@link FeedItem}
     *
     * @param description substitui a descrição de {@link FeedItem}
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Método responsável por adicionar uma categoria ao array de
     * {@link FeedItem#categories}
     *
     * @param category a ser adicionada
     *
     * @return sucesso/insucesso da operação
     */
    @Override
    public boolean addCategory(String category) {
        if (category == null) {
            throw new NullPointerException("Não é possível adicionar a categoria no item (Categoria nula)");
        } else if (this.numberCategories == MAX_CATEGORIES) {
            throw new ArrayIndexOutOfBoundsException("Não é possível adicionar uma categoria no item (Array cheio)");
        } else {
            /*
             fiz este ciclo for caso seja necessário acrescentar um método que  
             a remova as categorias de um feedItem
             */
            for (int i = 0; i < this.categories.length; i++) {
                if (this.categories[i] == null) {
                    this.categories[i] = category;
                    this.numberCategories++;
                    System.out.println("Categoria do item adicionada.");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método responsável por obter uma categoria no array de
     * {@link FeedItem#categories} dada a sua posição no array
     *
     * @param i posição da categoria
     *
     * @return categoria na posição dada
     *
     * @throws ObjectmanagementException exceção lançada caso a categoria não
     * exista na posição dada
     */
    @Override
    public String getCategory(int i) throws ObjectmanagementException {
        if (this.categories[i] == null) {
            throw new ObjectmanagementException("Categoria não existente.");
        } else if (i < 0 || i > this.categories.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Posição inválida");
        }
        return this.categories[i];
    }

    /**
     * Método que retorna a {@link FeedItem#numberCategories} do
     * {@link FeedItem}
     *
     * @return {@link FeedItem#numberCategories}
     */
    @Override
    public int numberCategories() {
        return this.numberCategories;
    }

    /**
     * Método que retorna a {@link FeedItem#publicationDate} do {@link FeedItem}
     *
     * @return {@link FeedItem#publicationDate}
     */
    @Override
    public Calendar getPublicationDate() {
        return this.publicationDate;
    }

    /**
     * Método responsável pela substituição da {@link FeedItem#publicationDate}
     * de {@link FeedItem}
     *
     * @param publicationDate substitui a data de publicação de {@link FeedItem}
     */
    @Override
    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Método que retorna o {@link FeedItem#author} do {@link FeedItem}
     *
     * @return {@link FeedItem#author }
     */
    @Override
    public String getAuthor() {
        return this.author;
    }

    /**
     * Método responsável pela substituição do {@link FeedItem#author} de
     * {@link FeedItem}
     *
     * @param author substitui o autor de {@link FeedItem}
     */
    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Método que retorna a {@link FeedItem#numberTags} do {@link FeedItem}
     *
     * @return {@link FeedItem#numberTags}
     */
    @Override
    public int numberTags() {
        return this.numberTags;
    }

    public TagContract setTag(int i, String tag) {

        if (i < 0 || i > this.tags.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Posição inválida");
        } else if (tag == null) {
            throw new NullPointerException("Tag nula");
        }

        this.tags[i].setName(tag);

        return this.tags[i];
    }

    /**
     * Adiciona uma {@link Tag} tendo em conta as condições do array
     * {@link FeedItem#tags} ( se está cheio, se o nome da tag já existe ou se a
     * tag a ser adicionada tem valor null
     *
     * @param tagName {@link Tag#name}
     *
     * @return sucesso/insucesso da operação
     *
     * @throws FeedException se o nome da tag já existe no array
     */
    @Override
    public boolean addTag(String tagName) throws FeedException {

        if (tagName == null) {
            throw new NullPointerException("Não é possível adicionar tag (nome da tag nula)");
        } else if (hasTag(tagName)) {
            throw new FeedException("Não é possível adicionar tag (tag já existente)");
        } else if (numberTags == MAX_TAGS) {
            throw new ArrayIndexOutOfBoundsException("Não é possível adicionar tag (array de tags cheio)");
        } else {
            Tag temp_tag = new Tag(this.tagID++, tagName);
            this.tags[this.numberTags] = temp_tag;
            this.numberTags++;
            System.out.println("Tag adicionada com sucesso");
            return true;
        }
    }

    /**
     * Remove uma {@link Tag} no array {@link FeedItem#tags} dado o seu
     * {@link Tag#id}
     *
     * @param id da {@link Tag} a ser removida
     *
     * @return sucesso/insucesso da operação
     *
     * @throws ObjectmanagementException {@link Tag} a ser removida for nula
     */
    @Override
    public boolean removeTag(int id) throws ObjectmanagementException {

        if (numberTags == 0) {
            throw new NullPointerException("Não é possível remover o feed)");
        } else {
            int pos = findTagByID(id);
            if (pos == -1) {
                throw new ObjectmanagementException("Não é possível remover o feed (Feed não encontrado)");
            } else {
                while (pos < this.tags.length - 1) {
                    this.tags[pos] = this.tags[pos + 1];
                    pos++;
                }
                this.tags[numberTags - 1] = null;
                this.numberTags--;
                System.out.println("Tag removida com sucesso");
                return true;
            }
        }
    }

    /**
     * Método responsável por obter uma {@link Tag} no array de
     * {@link FeedItem#tags} dada a sua posição no array
     *
     * @param i posição da {@link Tag}
     *
     * @return sucesso/insucesso da operação
     *
     * @throws ObjectmanagementException exceção lançada caso a {@link Tag} não
     * exista na posição dada
     */
    @Override
    public TagContract getTag(int i) throws ObjectmanagementException {

        if (this.tags[i] == null) {
            throw new ObjectmanagementException("Tag não existe na posição dada");
        } else if (i < 0 || i > this.tags.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Posição inválida");
        }
        return this.tags[i];
    }

    /**
     * Método responsável por escrever/armazenar um {@link FeedItem} para
     * ficheiro em formato JSON ( cada item é armazenado num ficheiro à parte ,
     * ou seja 10 items 10 ficheiros)
     *
     */
    @Override
    public void saveItem() {
//        try {
//            Files.write(Paths.get("dataItem/item_" + this.itemID + ".gson.json"), new Gson().toJson(this).getBytes("UTF-8"));
//        } catch (Exception ex) {
//            Logger.getLogger(FeedItem.class.getName()).log(Level.SEVERE, null, ex);
//        }

        JSONObject item = new JSONObject();

        item.put("title", getTitle());
        item.put("author", getAuthor());
        item.put("description", getDescription());
        //item.put("publication_date", getPublicationDate().getTime().toString()); //Problemas em apresentar a data
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
        //File data = new File("dataItems"); // aceder/ler diretório data
        //File[] ficheiros = data.listFiles();
        try (FileWriter file = new FileWriter("dataItems/item_" + this.itemID/*ficheiros.length*/ + ".json")) {
            file.write(item.toJSONString());
            //file.flush();
            System.out.println("ITEM GUARDADO EM FICHEIRO");
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(FeedItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método responsável por verificar se a {@link Tag} existe no array
     * {@link FeedItem#tags} ou não
     *
     * @param tag {@link Tag} a ser encontrada
     *
     * @return sucesso/insucesso da operação
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
     * Método responsável por encontrar uma {@link Tag} no array de
     * {@link FeedItem#tags} dado o seu id
     *
     * @param id da {@link Tag} a ser encontrada
     *
     * @return posição da {@link Tag} no array {@link FeedItem#tags}
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
     * Representação textual de {@link FeedItem}
     *
     * @return informação de {@link FeedItem}
     */
    @Override
    public String toString() {

        String string = "FeedItem{" + "id: " + itemID + ", título: " + title + ", url: " + contentUrl + ", descrição: "
                + description + ", itemPublicationDate: " + (publicationDate == null ? "N/D" : publicationDate.getTime()) + ", autor: "
                + author + ", numberTags: " + numberTags + "\n" + "Tags do item: {" + "\n";
        int i = 0;
        while (i < this.numberTags) {
            string += "\t" + this.tags[i].toString() + "\n";
            i++;
        }

        String string2 = "\n" + "Categorias do item: {" + "\n";

        int j = 0;
        while (j < this.numberCategories) {
            string2 += "\t" + this.categories[j] + "\n";
            j++;
        }

        return string + '}' + string2 + '}';

    }
}
