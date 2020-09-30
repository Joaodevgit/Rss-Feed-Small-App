package Demo;

import Controllers.App;
import Models.FeedItem;
import Models.Feed;
import Models.FeedGroup;
import Models.FeedSource;
import java.util.Calendar;
import rss.resources.app.Views.ExecutionMode;
import rss.resources.app.Views.MainFrame;
import rss.resources.app.exceptions.FeedException;
import rss.resources.app.exceptions.GroupException;
import rss.resources.app.exceptions.ObjectmanagementException;

/**
 * @author João Pereira
 */
public class RSSDemo {

    public static void main(String[] args) {

        try {
            FeedSource feedSource1 = new FeedSource("https://www.jn.pt");
            System.out.println(feedSource1.toString());

            //                                     Title               ContentURL             Description      Publication Date       Author
            FeedItem item1 = new FeedItem("Golo logo no 1º minuto", "http://www.google.pt", "Na passada 2f...", Calendar.getInstance(), "Pedro");
            item1.addTag("Desporto");
            item1.addTag("Internacional");
            item1.addCategory("Público");
            item1.addCategory("Música");
            System.out.println(item1.getCategory(0));
            item1.addTag("Nacional");
            item1.addTag("Economia");
            item1.removeTag(1);
            item1.addTag("Saúde");
            System.out.println(item1.getTag(2));
            item1.addTag("Cultura");
            System.out.println("INFO ITEM: " + item1.toString());
            item1.saveItem();

            //                              Title                 ContentURL             Description           Publication Date      Author
            FeedItem item2 = new FeedItem("Terramoto em Felgueiras", "http://www.estg.ipp.pt", "Durante a madrugada...", Calendar.getInstance(), "José");
            item2.addTag("Últimas");
            item2.addTag("Internacional");
            item2.addCategory("Felgueiras regional");
            item2.saveItem();

            //                   Title Description Language    Build Date          url FeedSource                  
            Feed feed1 = new Feed("Bola", "Golo", "pt", Calendar.getInstance(), "http://feeds.jn.pt/JN-Ultimas");
            feed1.addItem("Pesquisa no Google", "https://www.google.pt/Pesquisa", "Um dia pesquisei no google..", Calendar.getInstance(), "Ciência", "Tim Berners Lee");
            feed1.addCategory("Geeks");
            feed1.addItem("Não vai querer acreditar", "https://www.google.pt/Pesquisa", "Atriz morre desamparada no palco", Calendar.getInstance(), "Cultura", "Cristina Ferreira");
            feed1.addItem("ESTG ajuda Microsoft", "https://www.estg.ipp.pt", "Um dia na microsoft corporation", Calendar.getInstance(), "Tecnologia", "Bill Gates");
            feed1.addItem("Criminoso em destaque", "https://www.jn.pt/", "Criminoso é apanhado em flagrante", Calendar.getInstance(), "Justiça", "Judite de Sousa");
            feed1.getItem(2);
            Feed feed3 = new Feed("Fotógrafo desleixa-se", "Fotografia", "pt", Calendar.getInstance(), "http://www.google.pt");

            System.out.println(feed1.getCategory(0));
            feed1.getItem(0).addTag("Tecnologia");
            System.out.println(feed1.toString());

            FeedGroup group1 = new FeedGroup("Jornal de Noticias", "Grupo motivado e focado");
            Feed feed2 = new Feed("http://feeds.jn.pt/JN-Ultimas");
            group1.addFeed(feed1);
            group1.addFeed(feed3);
            group1.getFeed(1);
            System.out.println(group1.getFeedByID(1));
            System.out.println(group1.toString());
            System.out.println("INFO GRUPO1:" + group1.toString());
            group1.removeFeed(feed1);
            System.out.println(group1.getFeed(0));

            try {

                App app = new App();

                app.addGroup("Notícias", "Grupo destinado a notícias");
                app.addGroup("Desporto", "Grupo destinado a notícias de desporto");
                app.addGroup("Internacional", "Grupo destinado a notícias internacionais");
                app.saveGroups();
                System.out.println(app.getGroup(1));
                System.out.println(app.getGroupByID(1));
                feed2.setTitle("Exemplo de feed");
                feed2.addItem("Totti anuncia adeus à Roma... 30 anos depois",
                        "https://www.jn.pt/desporto/interior/totti-anuncia-adeus-a-roma-30-anos-depois-11017581.html",
                        "O ex-jogador e a maior lenda do clube italiano demite-se do cargo de dirigente devido a problemas com a atual direção.",
                        Calendar.getInstance(), "Futebol", "Autor1");
                app.getGroup(0).addFeed(feed1);
                app.getGroup(0).addFeed(feed2);

                //                                   Title        contentURL              Description         Publication Date      Category     Author      
                app.getGroup(0).getFeed(0).addItem("Titulo", "http://www.google.pt", "Uma descrição aqui", Calendar.getInstance(), "Categoria", "Categoria");
                app.loadGroups();
                app.getGroup(0).addFeed("http://feeds.tsf.pt/TSF-Desporto");
                MainFrame gui = new MainFrame(app, ExecutionMode.DEVELOPMENT);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (FeedException | NullPointerException | ArrayIndexOutOfBoundsException | ObjectmanagementException | GroupException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
