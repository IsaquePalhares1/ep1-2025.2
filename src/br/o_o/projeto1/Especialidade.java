package br.o_o.projeto1;

public enum Especialidade {


    PADRAO,
    CARDIOLOGIA,
    DERMATOLOGIA,
    PEDIATRIA,
    ORTOPEDIA,
    NEUROLOGIA,
    GINECOLOGIA,
    PSIQUIATRIA,
    OFTALMOLOGIA;



    /* TRANFORMAR ESPECIALIDADE EM STRING */
    public static Especialidade parseEspecialidade(String s) {
        return Especialidade.valueOf(s.toUpperCase());
    }
}

