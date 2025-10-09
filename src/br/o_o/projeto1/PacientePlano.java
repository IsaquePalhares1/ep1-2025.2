package br.o_o.projeto1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PacientePlano extends Paciente {


    /*    ATRIBUTOS    */
    private PlanoSaude planoSaude;


    /*  CONSTRUTORES */
    public PacientePlano(String nome, String cpf, int idade, ArrayList<String> histConsulta, ArrayList<String> histInternacao, PlanoSaude planoSaude) {
        super(nome, cpf, idade, histConsulta, histInternacao);
        this.planoSaude = planoSaude;
    }

    public PacientePlano() {
        super();
        this.planoSaude = new PlanoSaude();
    }



    /* MÉTODOS GET */
    public PlanoSaude getPlanoSaude() {
        return planoSaude;
    }


    /*  MÉTODOS SET  */
    public void setPlanoSaude(PlanoSaude planoSaude) {
        this.planoSaude = planoSaude;
    }


    /*  TO STRING */
    @Override
    public String toString(){
        return    super.toString() + "\n"
                + "Plano de Saúde: " + planoSaude;
    }



    /* MÉTODOS VALOR CONSULTA */
    public double valorConsulta(Medico medico, PlanoSaude planoSaude) {

        double custoBase = medico.getCustoConsulta();
        double valor = 0.0;

        switch (medico.getEspecialidade()) {


            case CARDIOLOGIA:
                valor = custoBase * (1 -(planoSaude.getDescontoCardiologia()*0.01));
                break;

            case DERMATOLOGIA:
                valor = custoBase * (1 -(planoSaude.getDescontoDermatologia()*0.01));
                break;

            case PEDIATRIA:
                valor = custoBase * (1 -(planoSaude.getDescontoPediatria()*0.01));
                break;

            case ORTOPEDIA:
                valor = custoBase * (1 -(planoSaude.getDescontoOrtopedia()*0.01));
                break;

            case NEUROLOGIA:
                valor = custoBase * (1 -(planoSaude.getDescontoNeurologia()*0.01));
                break;

            case GINECOLOGIA:
                valor = custoBase * (1 -(planoSaude.getDescontoGinecologia()*0.01));
                break;

            case PSIQUIATRIA:
                valor = custoBase * (1 -(planoSaude.getDescontoPsiquiatria()*0.01));
                break;

            case OFTALMOLOGIA:
                valor = custoBase * (1 -(planoSaude.getDescontoOftalmologia()*0.01));
                break;
        }

        if (getIdade() >= 60) {
            valor *= (1 - (planoSaude.getDescontoIdoso()*0.01));
            return valor;
        }
        else {
            valor = valor;
            return valor;
        }
    }

    /* METODO CADASTRAR PACIENTE PLANO */
    public void cadastrarPacientePlano(Scanner scanner) throws IOException, InterruptedException {


        PacientePlano pacientePlano = new PacientePlano();

        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();
        pacientePlano.setNome(nome);

        System.out.print("Cpf do paciente: ");
        String cpf = scanner.nextLine();
        pacientePlano.setCpf(cpf);

        System.out.print("Idade do paciente: ");
        int idade = scanner.nextInt();
        pacientePlano.setIdade(idade);
        scanner.nextLine();

        System.out.print("Plano de Saúde do paciente: ");
        String plano = scanner.nextLine();
        PlanoSaude planoSaude = new PlanoSaude(plano);
        pacientePlano.setPlanoSaude(planoSaude);;


        /*ADIÇAO NO ARQUIVO*/
        try(PrintWriter escrever = new PrintWriter(new FileWriter("cadastro_pacientes.txt", true))) {
            escrever.println(pacientePlano.getNome() + ";" +
                    pacientePlano.getCpf() + ";" +
                    pacientePlano.getIdade() + ";" +
                    pacientePlano.getPlanoSaude().getNome() + ";" +
                    String.join("|", pacientePlano.getHistConsultas()) + ";" +
                    String.join("|", pacientePlano.getHistInternacoes()));

        }
        System.out.println();
        Thread.sleep(1000);
        System.out.println("Paciente com Plano Cadastrado com sucesso!");
        System.out.println();
        Thread.sleep(2000);

    }

}