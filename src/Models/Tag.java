/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import rss.resources.app.Models.TagContract;

/*
* Nome: João Tiago Moreira Pereira
* Número: 8170202
* Turma: LEI1T2
*
* Nome: José Miguel Araújo de Carvalho
* Número: 8150146
* Turma: LEI1T2
 */
public class Tag implements TagContract {

    private int id;
    private String name;

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Método que retorna o {@link Tag#id} de {@link Tag}
     *
     * @return id de {@link Tag}
     */
    @Override
    public int getID() {
        return this.id;
    }

    /**
     * Método responsável pela substituição do {@link Tag#id} de {@link Tag}
     *
     * @param id a ser substituido
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método que retorna o {@link Tag#name} de {@link Tag}
     *
     * @return nome de {@link Tag}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Método responsável pela substituição do {@link Tag#name} de {@link Tag}
     *
     * @param name a ser substituido
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    /**
     * Método responsável pela impressão dos atributos de {@link Tag}
     *
     * @return dados de {@link Tag}
     */
    @Override
    public String toString() {
        return "Tag{" + "id:" + id + ", name:" + name + '}';
    }

}
