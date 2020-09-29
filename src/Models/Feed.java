/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Calendar;
import rss.resources.app.Models.FeedContract;
import rss.resources.app.Models.FeedItemContract;
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
     * Método que retorna o {@link Feed#title} do {@link Feed}
     *
     * @return {@link Feed#title}
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Método responsável pela substituição do {@link Feed#title} de
     * {@link Feed}
     *
     * @param title substitui o título de {@link Feed}
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Método que retorna a {@link Feed#description} do {@link Feed}
     *
     * @return {@link Feed#description}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Método responsável pela substituição do {@link Feed#description} de
     * {@link Feed}
     *
     * @param description substitui a descrição de {@link Feed}
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Método que retorna a {@link Feed#language} do {@link Feed}
     *
     * @return {@link Feed#language}
     */
    @Override
    public String getLanguage() {
        return this.language;
    }

    /**
     * Método responsável pela substituição do {@link Feed#language} de
     * {@link Feed}
     *
     * @param language substitui o idioma de {@link Feed}
     */
    @Override
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Método que retorna a {@link Feed#buildDate} do {@link Feed}
     *
     * @return {@link Feed#buildDate}
     */
    @Override
    public Calendar getBuildDate() {
        return this.buildDate;
    }

    /**
     * Método responsável pela substituição do {@link Feed#buildDate} de
     * {@link Feed}
     *
     * @param buildDate substitui a data de publicação de {@link Feed}
     */
    @Override
    public void setBuildDate(Calendar buildDate) {
        this.buildDate = buildDate;
    }

    /**
     * Método que retorna {@link Feed#numberCategories} do {@link Feed}
     *
     * @return {@link Feed#numberCategories}
     */
    @Override
    public int numberCategories() {
        return this.numberCategories;
    }

    /**
     * Método que retorna {@link Feed#numberItems} do {@link Feed}
     *
     * @return {@link Feed#numberItems}
     */
    @Override
    public int numberItems() {
        return this.numberItems;
    }

    /**
     * Método responsável pela adição de um {@link FeedItem} ao array
     * {@link Feed#items}
     *
     * @param title título do {@link FeedItem}
     *
     * @param contentURL url do {@link FeedItem}
     *
     * @param description descrição do {@link FeedItem}
     *
     * @param publicationDate data de publicação do {@link FeedItem}
     *
     * @param category categoria do {@link FeedItem}
     *
     * @param author autor do {@link FeedItem}
     *
     * @return sucesso/insucesso da operação
     */
    @Override
    public boolean addItem(String title, String contentURL, String description,
            Calendar publicationDate, String category, String author) {

        if (title == null || contentURL == null || description == null /*|| publicationDate == null*/
                || category == null /*|| author == null*/) {
            throw new NullPointerException("Não é possível adicionar um item (Uma ou mais variáveis são nula(s))");
        } else if (this.numberItems == MAX_ITEMS) {
            throw new ArrayIndexOutOfBoundsException("Não é possível adicionar um item (Array cheio)");
        } else {
            FeedItem itemTest = new FeedItem(title, contentURL, description, publicationDate, author);
//FeedItem itemTest = new FeedItem(title, description, publicationDate, this.ItemID++, contentURL, author);
            for (int i = 0; i < this.items.length; i++) {
                if (this.items[i] == null) {
                    this.items[i] = itemTest;
                    this.items[i].addCategory(category);
                    this.numberItems++;
                    System.out.println("Item adicionado com sucesso");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método responsável por obter um {@link FeedItem} no array de
     * {@link Feed#items} dada a sua posição no array
     *
     * @param i posição do {@link FeedItem}
     *
     * @return {@link FeedItem} na posição dada
     *
     * @throws ObjectmanagementException exceção lançada caso o {@link FeedItem}
     * não exista na posição dada
     */
    @Override
    public FeedItemContract getItem(int i) throws ObjectmanagementException {

        if (this.items[i] == null) {
            throw new ObjectmanagementException("Item não existe na posição dada");
        } else if (i < 0 || i > this.items.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Posição inválida");
        }
        //System.out.println("O item na posição " + i + " é : "+this.items[i]);
        return this.items[i];
    }

    /**
     * Método responsável por adicionar uma categoria ao array de
     * {@link Feed#categories}
     *
     * @param category a ser adicionada
     *
     * @return sucesso/insucesso da operação
     */
    @Override
    public boolean addCategory(String category) {
        if (category == null) {
            throw new NullPointerException("Não é possível adicionar a categoria no feed (Categoria nula)");
        } else if (this.numberCategories == MAX_CATEGORIES) {
            throw new ArrayIndexOutOfBoundsException("Não é possível adicionar uma categoria no feed (Array cheio)");
        } else {
            for (int i = 0; i < this.categories.length; i++) {
                if (this.categories[i] == null) {
                    this.categories[i] = category;
                    this.numberCategories++;
                    System.out.println("Categoria adicionada.");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método responsável por obter uma categoria no array de
     * {@link Feed#categories} dada a sua posição no array
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
            throw new ObjectmanagementException("Posição inválida");
        }
        return this.categories[i];
    }

    /**
     * Representação textual de {@link Feed}
     *
     * @return informação de {@link Feed}
     */
    //buildDate.get(GregorianCalendar.DAY_OF_MONTH) + "/" + buildDate.get(GregorianCalendar.MONTH) + "/" + buildDate.get(GregorianCalendar.YEAR)
    @Override
    public String toString() {
        String string = "Feed{" + "id: " + getID() + ", title: " + title + ", descrição: " + description + ", idioma: " + language
                + ", data de publicação do feed: " + (buildDate == null ? "N/D" : buildDate.getTime()) + " " + super.toString() + "\n" + "Items do feed: {" + "\n";
        if (this.numberItems == 0) {
            System.out.println("Feed sem items");
        }
        int i = 0;
        while (i < this.numberItems) {
            string += "\t" + this.items[i].toString() + "\n";
            i++;
        }
        String string2 = "\n" + "Categorias do feed: {" + "\n";

        int j = 0;
        while (j < this.numberCategories) {
            string2 += "\t" + this.categories[j] + "\n";
            j++;
        }
        return string + '}' + string2 + '}';
    }

}
