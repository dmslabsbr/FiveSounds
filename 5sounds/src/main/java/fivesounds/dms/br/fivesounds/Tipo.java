package fivesounds.dms.br.fivesounds;

/**
 * Created by dms on 01/05/16.
 */
public class Tipo {

    private String id;
    private String tipo;
    private String imagem;

    /**
     * Construtor da classe
     * @param id
     * @param tipo
     * @param imagem
     */
    public Tipo(String id, String tipo, String imagem) {
        this.id = id;
        this.tipo = tipo;
        this.imagem = imagem;
    }

    /**
     * Retorna o tipo
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * retorna o nome da imagem
     * @return
     */
    public String getImagem() {
        return imagem;
    }

    /**
     * Retorna o id
     * @return
     */
    public String getId() {
        return id;
    }
}
