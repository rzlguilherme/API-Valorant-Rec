package com.example.apivalorant;

public class Agente {

    private String CodAgente;
    private String FotoAgente;
    private String NomeAgente;
    private String HabiPrima;
    private String HabiSecun;
    private String Granada;
    private String Ultimate;
    private String Descricao;

    public Agente(String CodAgente, String FotoAgente, String NomeAgente, String HabiPrima, String HabiSecun, String Granada, String Ultimate, String Descricao){
        this.CodAgente = CodAgente;
        this.FotoAgente = FotoAgente;
        this.NomeAgente = NomeAgente;
        this.HabiPrima = HabiPrima;
        this.HabiSecun = HabiSecun;
        this.Granada = Granada;
        this.Ultimate = Ultimate;
        this.Descricao = Descricao;

    }

    public String getFotoAgente(){return FotoAgente;}
    public String getNomeAgente(){return NomeAgente;}
    public String getCodAgente(){return CodAgente;}
}
