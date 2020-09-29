/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import rss.resources.app.Models.FeedContract;
import rss.resources.app.Models.FeedGroupContract;
import rss.resources.app.Utils.RSSFeedParser;
import rss.resources.app.exceptions.FeedException;
import rss.resources.app.exceptions.GroupException;
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
     * Método que retorna o {@link FeedGroup#groupID} do {@link FeedGroup}
     *
     * @return {@link FeedGroup#groupID}
     */
    @Override
    public int getID() {
        return this.groupID;
    }

    /**
     * Método responsável pela substituição do {@link FeedGroup#groupID} de
     * {@link FeedGroup}
     *
     * @param groupID substitui o id de {@link FeedGroup}
     */
    private void setID(int groupID) {
        this.groupID = groupID;
    }

    /**
     * Método que retorna o {@link FeedGroup#groupTitle} do {@link FeedGroup}
     *
     * @return {@link FeedGroup#groupTitle}
     */
    @Override
    public String getTitle() {
        return this.groupTitle;
    }

    /**
     * Método responsável pela substituição do {@link FeedGroup#groupTitle} de
     * {@link FeedGroup}
     *
     * @param groupTitle substitui o título de {@link FeedGroup}
     */
    @Override
    public void setTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    /**
     * Método que retorna o {@link FeedGroup#groupDescription} do
     * {@link FeedGroup}
     *
     * @return {@link FeedGroup#groupDescription}
     */
    @Override
    public String getDescription() {
        return this.groupDescription;
    }

    /**
     * Método responsável pela substituição do
     * {@link FeedGroup#groupDescription} de {@link FeedGroup}
     *
     * @param groupDescription substitui a descrição de {@link FeedGroup}
     */
    @Override
    public void setDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    /**
     * Método que retorna o {@link FeedGroup#numberFeeds} do {@link FeedGroup}
     *
     * @return {@link FeedGroup#numberFeeds}
     */
    @Override
    public int numberFeeds() {
        return this.numberFeeds;
    }

    /**
     * Método responsável pela adição de um {@link FeedContract} no array
     * {@link FeedGroup} pelo seu url , preservando a ordem de inserção
     *
     * @param url a ser adicionado
     *
     * @return sucesso/insucesso da operação
     *
     * @throws GroupException exceção lançada caso o {@link FeedContract} já
     * exista no array {@link FeedGroup#feeds}
     */
    @Override
    public boolean addFeed(String url) throws GroupException {

        if (url == null) {
            throw new NullPointerException("Não é possível adicionar o feed(url nulo)");
        } else if (hasURL(url)) {
            throw new GroupException("Não é possível adicionar o feed(Feed já existente)");
        } else if (numberFeeds == MAX_FEEDS) {
            throw new ArrayIndexOutOfBoundsException("Não é possível adicionar o feed(Array cheio)");
        } else {
            try {
                Feed feedTest = new Feed(url);
                RSSFeedParser.readFeed(feedTest);
                //System.out.println(feedTest.toString());
                this.feeds[numberFeeds] = feedTest;
                this.numberFeeds++;
                System.out.println("Feed adicionado com sucesso");
                return true;
            } catch (FeedException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    /**
     * Método responsável pela adição de um {@link FeedContract} no array
     * {@link FeedGroup} , preservando a ordem de inserção
     *
     * @param newFeed {@link FeedContract} a ser adicionado no array
     * {@link FeedGroup#feeds}
     *
     * @return sucesso/insucesso da operação
     *
     * @throws GroupException exceção lançada caso o {@link FeedContract} já
     * exista no array {@link FeedGroup#feeds}
     */
    @Override
    public boolean addFeed(FeedContract newFeed) throws GroupException {

        if (newFeed == null) {
            throw new NullPointerException("Não é possível adicionar o feed(Feed nulo)");

        } else if (hasFeed(newFeed)) {
            throw new GroupException("Não é possível adicionar o feed(Feed já existente)");
        } else if (numberFeeds == MAX_FEEDS) {
            throw new ArrayIndexOutOfBoundsException("Não é possível adicionar o feed(Array cheio)");
        } else {
            this.feeds[this.numberFeeds] = newFeed;
            this.setID(groupID++);
            this.numberFeeds++;
            System.out.println("Feed adicionado com sucesso");
            return true;
        }
    }

    /**
     * Método responsável pela remoção do {@link FeedContract} no array
     * {@link FeedGroup#feeds}
     *
     * @param fc {@link FeedContract} a ser removido do array
     *
     * @return sucesso/insucesso da operação
     *
     * @throws ObjectmanagementException exceção lançada caso o
     * {@link FeedContract} não exista no array {@link FeedGroup#feeds}
     */
    @Override
    public boolean removeFeed(FeedContract fc) throws ObjectmanagementException {
        int pos;
        if (numberFeeds == 0) {
            throw new ObjectmanagementException("Não é possível remover o tag (Array vazio)");
        } else {
            if (findFeed(fc) == -1) {
                throw new ObjectmanagementException("Não é possível remover a tag (ID não encontrado)");
            } else {
                pos = findFeed(fc);
                while (pos < this.feeds.length - 1) {
                    this.feeds[pos] = this.feeds[pos + 1];
                    pos++;
                }
                this.feeds[numberFeeds - 1] = null;
                this.numberFeeds--;
                System.out.println("Feed removido com sucesso");
                return true;
            }
        }
    }

    /**
     * Método responsável por obter um {@link FeedContract} no array de
     * {@link FeedGroup#feeds} dada a sua posição no array
     *
     * @param i posição do {@link FeedContract}
     *
     * @return {@link FeedContract} na posição dada
     *
     * @throws ObjectmanagementException exceção lançada caso o
     * {@link FeedContract} não exista na posição dada
     */
    @Override
    public FeedContract getFeed(int i) throws ObjectmanagementException {
        if (this.feeds[i] == null) {
            throw new ObjectmanagementException("Feed não existe na posição dada");
        } else if (i < 0 || i > this.feeds.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Posição inválida");
        }

        return this.feeds[i];
    }

    /**
     * Método responsável por obter um {@link FeedContract} no array de
     * {@link FeedGroup#feeds} dado o id do {@link FeedContract}
     *
     * @param id do {@link FeedContract}
     *
     * @return {@link FeedContract} do id dado
     *
     * @throws ObjectmanagementException exceção lançada caso o
     * {@link FeedContract} não exista com o id dado
     */
    @Override
    public FeedContract getFeedByID(int id) throws ObjectmanagementException {
        int i = 0;
        while (i < this.numberFeeds && this.feeds[i].getID() != id) {
            i++;
        }
        if (i == this.numberFeeds) {
            throw new ObjectmanagementException("Feed não existe no id dado");
        }
        return this.feeds[i];
    }

    /**
     ** Retorna dados de cada feed, permitindo a instanciação de
     * {@link FeedContract} e os seus {@link FeedItem}
     */
    @Override
    public void getData() {
        for (int i = 0; i < this.numberFeeds; i++) {
            RSSFeedParser.readFeed(this.feeds[i]);
        }
    }

    /**
     * Representação textual de {@link FeedGroup}
     *
     * @return informação de {@link FeedGroup}
     */
    @Override
    public String toString() {
        String string = "FeedGroup{" + "id: " + groupID + ", título: " + groupTitle + ", descrição: "
                + groupDescription + "\n" + "Feeds do group: {" + "\n";
        int i = 0;
        while (i < this.numberFeeds) {
            string += "\t" + this.feeds[i].toString() + "\n";
            i++;
        }
        return string + '}';
    }

    /**
     * Método responsável por verificar se o {@link FeedContract} existe no
     * array {@link FeedGroup#feeds} ou não
     *
     * @param feed a ser procurado
     *
     * @return sucesso/insucesso da operação
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
     * Método responsável por verificar se o {@link FeedContract#getURL()}
     * existe no array {@link FeedGroup#feeds} ou não
     *
     * @param url a ser procurado
     *
     * @return sucesso/insucesso da operação
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
     * Método responsável por encontrar um {@link FeedContract} no array
     * {@link FeedGroup#feeds}
     *
     * @param feed a ser encontrado
     *
     * @return posição do {@link FeedContract} no array {@link FeedGroup#feeds},
     * caso a posição seja -1 o feed não foi encontrado
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
