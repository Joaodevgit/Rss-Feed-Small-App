/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/*
* Nome: João Tiago Moreira Pereira
* Número: 8170202
* Turma: LEI1T2
*
* Nome: José Miguel Araújo de Carvalho
* Número: 8150146
* Turma: LEI1T2
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
     * Método que retorna o {@link App#numberGroups} de {@link App}
     *
     * @return {@link App#numberGroups}
     */
    @Override
    public int numberGroups() {
        return this.numberGroups;
    }

    /**
     * Método que retorna o array {@link App#groups} de {@link App}
     *
     * @return array {@link App#groups}
     */
    @Override
    public FeedGroupContract[] getAllGroups() {
        return this.groups;
    }

    /**
     * Método responsável pela adição de um {@link FeedGroup} no array
     * {@link App#groups} , preservando a ordem de inserção
     *
     * @param title do {@link FeedGroup}
     *
     * @param description do {@link FeedGroup}
     *
     * @return sucesso/insucesso da operação
     */
    @Override
    public boolean addGroup(String title, String description) {

        if (title == null || description == null) {
            throw new NullPointerException("Não é possível adicionar um grupo de feeds (Uma ou mais variáveis são nula(s))");
        } else if (this.numberGroups == MAX_GROUPS) {
            throw new ArrayIndexOutOfBoundsException("Não é possível adicionar um grupo (Array cheio)");
        } else {
            FeedGroup temp_group = new FeedGroup(this.groupid++, title, description);

            for (int i = 0; i < this.groups.length; i++) {
                if (this.groups[i] == null) {
                    this.groups[i] = temp_group;
                    this.numberGroups++;
                    System.out.println("Grupo adicionado com sucesso");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método responsável pela remoção da primeira ocorrência de um
     * {@link FeedGroup} no array {@link App#groups}
     *
     * @param id do {@link FeedGroup} a ser removido
     *
     * @return sucesso/insucesso da operação
     *
     * @throws ObjectmanagementException exceção lançada caso o
     * {@link FeedGroup} não exista no array {@link App#groups}
     */
    @Override
    public boolean removeGroup(int id) throws ObjectmanagementException {

        int pos;

        if (this.numberGroups == 0) {
            throw new NullPointerException("Não é possível remover o grupo)");
        } else {
            if (findGroupByID(id) == -1) {
                throw new ObjectmanagementException("Não é possível remover o grupo de feeds (Grupo não encontrado)");
            } else {
                pos = findGroupByID(id);
                while (pos < this.groups.length - 1) {
                    this.groups[pos] = this.groups[pos + 1];
                    pos++;
                }
                this.groups[this.numberGroups - 1] = null;
                this.numberGroups--;
                System.out.println("Grupo de feeds removido com sucesso");
                return true;
            }
        }
    }

    /**
     * Método responsável por obter um {@link FeedGroup} no array de
     * {@link App#groups} dada a sua posição no array
     *
     * @param pos posição do {@link FeedGroup}
     *
     * @return {@link FeedGroup} na posição dada
     *
     * @throws ObjectmanagementException exceção lançada caso o
     * {@link FeedGroup} não exista na posição dada
     */
    @Override
    public FeedGroupContract getGroup(int pos) throws ObjectmanagementException {
        if (this.groups[pos] == null) {
            throw new ObjectmanagementException("Grupo não existe na posição dada");
        } else if (pos < 0 || pos > this.groups.length - 1) {
            throw new ArrayIndexOutOfBoundsException("Posição inválida");
        }

        return this.groups[pos];
    }

    /**
     * Método responsável por obter um {@link FeedGroup} no array de
     * {@link App#groups} dado o id do {@link FeedGroup}
     *
     * @param id do {@link FeedGroup}
     *
     * @return {@link FeedGroup} do id dado
     *
     * @throws ObjectmanagementException exceção lançada caso o
     * {@link FeedGroup} não exista com o id dado
     */
    @Override
    public FeedGroupContract getGroupByID(int id) throws ObjectmanagementException {
        for (int i = 0; i < this.groups.length; i++) {
            if (this.groups[i].getID() == id) {
                return this.groups[i];
            }
            if (i == MAX_GROUPS - 1) {
                throw new ObjectmanagementException("Grupo não existe na posição dada");
            }
        }
        return null;
    }

    /**
     * Método responsável por pesquisar por todos os {@link FeedItem} salvos que
     * contêm uma tag específica
     *
     * @param tag tag a ser procurada
     *
     * @return {@link FeedItem} da tag dada
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
     * Método responsável por escrever/armazenar os {@link App#groups} para
     * ficheiro em formato JSON
     *
     * @throws Exception exceção lançada quando existem problema ao nível do I/O
     * ou problemas de conversão que possam ocorrer
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
            System.out.println("Grupo GUARDADO EM FICHEIRO");
        } catch (Exception ex) {
            System.out.println("I/O problem");
        }

    }

    /**
     * Método responsável por ler/carregar os {@link App#groups} a partir de um
     * ficheiro em formato JSON
     *
     * @throws Exception exceção lançada quando existem problema ao nível do I/O
     * ou problemas de conversão que possam ocorrer
     */
    @Override
    public void loadGroups() throws Exception {
        JSONParser parser = new JSONParser();
        try {
            Object grupos = parser.parse(new FileReader("dataGroups/grupos.json"));
            JSONObject jsonObject = (JSONObject) grupos;

            JSONArray arrayGrupos = (JSONArray) jsonObject.get("group");
            System.out.println("Grupo(s) carregado(s) a partir do ficheiro JSON: ");

            for (Object gr : arrayGrupos) {
                System.out.println(gr.toString());
            }

        } catch (IOException | ParseException ex) {
            System.out.println("I/O problem");
        }
    }

    /**
     * Retorna o número de {@link FeedItem} armazenados em {@link App}
     *
     * @return array {@link FeedItem}
     */
    @Override
    public FeedItem[] getAllSavedItems() {

        FeedItem[] feedItems = new FeedItem[100];
        int i = 0;
        File data = new File("dataItems"); // aceder/ler diretório data

        File[] ficheiros = data.listFiles(); // lista todos os ficheiros dentro do diretório

        for (File ficheiro : ficheiros) {
            if (ficheiro.getName().startsWith("item_") && ficheiro.getName().endsWith(".json")) { // filtrar todos os ficheiros que são feed items
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
     * Método que procura/obtém um {@link FeedItem} salvo nos ficheiros
     *
     * @param ficheiro onde se encontra o item a ser lido
     *
     * @return o {@link FeedItem} carregado
     */
    private FeedItem getSavedItem(File ficheiro) {
        try {
            FeedItem feedItem = new FeedItem();

            FileReader fr = new FileReader(ficheiro);

            JSONObject j = (JSONObject) new JSONParser().parse(fr); // analisa o ficheiro em json e constrói o JSONObject 

            feedItem.setTitle((String) j.get("title"));
            feedItem.setAuthor((String) j.get("author"));
            feedItem.setDescription((String) j.get("description"));
            //feedItem.setPublicationDate((Calendar) j.get("publication_date")); Dá erro
            try {
                feedItem.setContentURL((String) j.get("url"));
            } catch (FeedException e) {
                System.out.println(e.getMessage());
            }

//            JSONArray tags = (JSONArray) j.get("tags");
//            int p = 0;
//            for (int t = 0; t < feedItem.numberTags(); t++) {
//                feedItem.setTag(t, (String) tags.get(p));
//                p++;
//            }
//            JSONArray categories = (JSONArray) j.get("categories");
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
     * Remove um determinado {@link FeedItem} armazenado em ficheiro JSON dada a
     * sua posição
     *
     * @param i posição do {@link FeedItem} a ser removido
     *
     * @return sucesso/insucesso da operação
     */
    @Override
    public boolean removeSavedItem(int i) {

        File data = new File("dataItems"); // aceder/ler diretório data

        File[] ficheiros = data.listFiles(); // lista todos os ficheiros dentro do diretório
        int n = -1;
        for (File ficheiro : ficheiros) {
            if (ficheiro.getName().startsWith("item_") && ficheiro.getName().endsWith(".json")) { // filtrar todos os ficheiros que são feed items
                n++;
                if (i == n) {
                    return ficheiro.delete();
                }
            }
        }

        return false;
    }

    /**
     * Método responsável por encontrar um {@link FeedGroup} no array de
     * {@link App#groups} dado o seu id
     *
     * @param id da {@link FeedGroup} a ser encontrado
     *
     * @return posição do {@link FeedGroup} no array {@link App#groups}
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
