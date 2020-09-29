/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.net.MalformedURLException;
import java.net.URL;
import rss.resources.app.Models.FeedSourceContract;
import rss.resources.app.exceptions.FeedException;

/*
* Nome: João Tiago Moreira Pereira
* Número: 8170202
* Turma: LEI1T2
*
* Nome: José Miguel Araújo de Carvalho
* Número: 8150146
* Turma: LEI1T2
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
            System.out.println("Url inválido");
            throw new FeedException();
        }
    }

    /**
     * Método que obtém o {@link FeedSource#id} de {@link FeedSource}
     *
     * @return {@link FeedSource#id}
     */
    @Override
    public int getID() {
        return this.id;
    }

    /**
     * Método que retorna o {@link FeedSource#url} de {@link FeedSource}
     *
     * @return Uniform Resource Locator de {@link FeedSource}
     */
    @Override
    public String getURL() {
        return this.url;
    }

    /**
     * Método responsável pela validação do {@link FeedSource#url}
     *
     * @param url a ser validado
     * @throws FeedException exceção lançada caso o {@link FeedSource#url} seja
     * inválido
     */
    @Override
    public void setURL(String url) throws FeedException {

        try {
            URL u = new URL(url);
            this.url = url;
        } catch (MalformedURLException ex) {
            throw new FeedException("URL Invalida");
        }

//        String http = "http://";
//        String https = "https://";
//        if (!(url.startsWith(http)) && !(url.startsWith(https))) {
//            //throw new FeedException("erro url invaaalido");
//            url = "http://" + url;
//        }
//        this.url = url;
    }

    /**
     * Método responsável pela impressão dos atributos de {@link FeedSource}
     *
     * @return dados de {@link FeedSource}
     */
    @Override
    public String toString() {
        return "FeedSource{" + "id:" + id + ", url:" + url + '}';
    }

}
