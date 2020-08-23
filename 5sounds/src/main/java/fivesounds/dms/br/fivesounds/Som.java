package fivesounds.dms.br.fivesounds;

/**
 * Created by dms on 30/04/16.
 */


import com.google.gson.annotations.SerializedName;


public class Som {


    @SerializedName("id")
    private String id;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("titulo")
    private String titulo;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("imagem")
    private String imagem;
    @SerializedName("som")
    private String som;

    /**
     * Contrutor da Classe
     * @param id
     * @param tipo
     * @param titulo
     * @param descricao
     * @param imagem
     * @param som
     */
    public Som(String id, String tipo, String titulo, String descricao, String imagem, String som) {
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
        this.som = som;
    }

    /**
     * Retorna o tipo do som
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Retorna o titulo do som
     * @return
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Retorna a descrição do som
     * @return
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * retorna o nome da imagem
     * @return
     */
    public String getImagem() {
        return imagem;
    }

    /**
     * Retorna o nome do som
     * @return
     */
    public String getSom() {
        return som;
    }

    /**
     * Retorna o id
     * @return
     */
    public String getId() {
        return id;
    }


}
