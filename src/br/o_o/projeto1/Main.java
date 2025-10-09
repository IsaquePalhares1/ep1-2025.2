package br.o_o.projeto1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {

        /* BOAS VINDAS */
        System.out.println("**************************************************");
        System.out.println("Bem-Vindo ao Menu de Gerenciamento Hospitalar");
        System.out.println("**************************************************\n");
        Thread.sleep(1000);


        /* MENU INICIAL */
        int opcao;
        boolean isRunning = true;

        while (isRunning) {

            do {

                System.out.println("1 - Cadastrar Paciente\n" +
                        "2 - Cadastrar Paciente com Plano de Saúde\n" +
                        "3 - Cadastrar Médico\n" +
                        "4 - Agendar Consulta\n" +
                        "5 - Internar Paciente\n" +
                        "6 - Adicionar Plano de Saúde\n" +
                        "7 - Concluir Consulta\n" +
                        "8 - Encerrar Internação\n" +
                        "9 - Olhar Relatórios\n" +
                        "10 - Sair\n");
                System.out.println("****************************");
                System.out.print("Escolha uma das opções: ");
                opcao = scanner.nextInt();
                scanner.nextLine();
                System.out.println("****************************");
                System.out.println();



            } while (opcao < 1 || opcao > 10);

            switch (opcao) {
                case 1:
                    Thread.sleep(1000);
                    System.out.println("=============================");
                    System.out.println("Cadastrar Paciente");
                    System.out.println("=============================");
                    Thread.sleep(2000);
                    System.out.println();
                    Paciente paciente = new Paciente();
                    paciente.cadastrarPaciente(scanner);
                    break;


                case 2:
                    Thread.sleep(1000);
                    System.out.println("=============================");
                    System.out.println("Cadastrar Paciente com Plano de Saúde");
                    System.out.println("=============================");
                    Thread.sleep(2000);
                    System.out.println();
                    PacientePlano pacientePlano = new PacientePlano();
                    pacientePlano.cadastrarPacientePlano(scanner);
                    break;


                case 3:
                    Thread.sleep(1000);
                    System.out.println("=============================");
                    System.out.println("Cadastrar Médico");
                    System.out.println("=============================");
                    Thread.sleep(2000);
                    System.out.println();
                    Medico medico = new Medico();
                    medico.cadastrarMedico(scanner);
                    break;


                case 4:
                    Thread.sleep(1000);
                    System.out.println("=============================");
                    System.out.println("Agendar Consulta");
                    System.out.println("=============================");
                    Thread.sleep(2000);
                    System.out.println();
                    Consulta consulta = new Consulta();
                    consulta.agendarConsulta(scanner);
                    break;


                case 5:
                    Thread.sleep(1000);
                    System.out.println("=============================");
                    System.out.println("Internar Paciente");
                    System.out.println("=============================");
                    Thread.sleep(2000);
                    System.out.println();
                    Internacao internacao = new Internacao();
                    internacao.marcarInternacao(scanner);
                    break;


                case 6:
                    Thread.sleep(1000);
                    System.out.println("=============================");
                    System.out.println("Adicionar Plano de Saúde");
                    System.out.println("=============================");
                    Thread.sleep(2000);
                    System.out.println();
                    PlanoSaude planoSaude = new PlanoSaude();
                    planoSaude.cadastrarPlano(scanner);
                    break;


                case 7:
                    Thread.sleep(1000);
                    System.out.println("=============================");
                    System.out.println("Concluir Consulta");
                    System.out.println("=============================");
                    Thread.sleep(2000);
                    System.out.println();
                    Consulta consulta1 = new Consulta();
                    ArrayList<Consulta> consultas = consulta1.agendaDeConsultas();
                    consulta1.concluirConsulta(scanner, consultas);
                    break;


                case 8:
                    Thread.sleep(1000);
                    System.out.println("=============================");
                    System.out.println("Encerrar Internação");
                    System.out.println("=============================");
                    Thread.sleep(2000);
                    System.out.println();
                    Internacao internacao1 = new Internacao();
                    ArrayList<Internacao> internacoes = internacao1.historicoDeInternacoes();
                    internacao1.cancelarInternacao(scanner, internacoes);
                    break;


                case 9:
                    Thread.sleep(1000);
                    System.out.println("=============================");
                    System.out.println("Olhar Relatórios");
                    System.out.println("=============================");
                    Thread.sleep(2000);
                    System.out.println();
                    Relatorios relatorios = new Relatorios();
                    relatorios.relatorioPacientes();
                    relatorios.relatorioMedicos();
                    relatorios.relatorioConsultas();
                    relatorios.relatorioPacientesInternados();
                    relatorios.relatorioEstatisticas();
                    break;


                case 10:
                    Thread.sleep(1000);
                    System.out.println("=============================");
                    System.out.println("Saindo ...");
                    System.out.println("=============================");
                    isRunning = false;
                    break;



            }

            opcao = 0;
        }

        scanner.close();
    }
}
