package arranchamento.util;

import arranchamento.util.ConexaoBanco;
import arranchamento.util.DateUtil;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

public class ExportadorXLSX {

    public static boolean exportarParaXLSX(String nomeArquivo, int turma, int pelotao, String dataInicio, String dataFim) {
        String sql = "SELECT usr.nome_de_guerra AS NOME_DE_GUERRA, ref.tipo AS REFEICAO, ref.data AS DATA\n" +
                "FROM arranchamentos arr\n" +
                "JOIN usuarios usr ON arr.usuario_id = usr.id\n" +
                "JOIN refeicoes ref ON arr.refeicao_id = ref.id\n" +
                "WHERE usr.turma = ? AND usr.pelotao = ? AND ref.data >= ? AND ref.data <= ?";
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

            pstmt.setInt(1, turma);
            pstmt.setInt(2, pelotao);
            pstmt.setDate(3, DateUtil.convertStringToSqlDate(dataInicio));
            pstmt.setDate(4, DateUtil.convertStringToSqlDate(dataFim));

            ResultSet rs = pstmt.executeQuery();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Dados");

            ResultSetMetaData metaData = rs.getMetaData();
            int numColunas = metaData.getColumnCount();

            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= numColunas; i++) {
                Cell cell = headerRow.createCell(i - 1);
                cell.setCellValue(metaData.getColumnName(i));
            }

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= numColunas; i++) {
                    Cell cell = row.createCell(i - 1);
                    cell.setCellValue(rs.getString(i));
                }
            }

            FileOutputStream fileOut = new FileOutputStream(nomeArquivo);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();


            System.out.println("Dados exportados com sucesso para " + nomeArquivo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

        public static boolean exportantigo(String nomeArquivo, int turma, int pelotao, String dataInicio, String dataFim) {
            String sql = "SELECT usr.nome_de_guerra AS NOME_DE_GUERRA, ref.tipo AS REFEICAO, ref.data AS DATA\n" +
                    "FROM arranchamentos arr\n" +
                    "JOIN usuarios usr ON arr.usuario_id = usr.id\n" +
                    "JOIN refeicoes ref ON arr.refeicao_id = ref.id\n" +
                    "WHERE usr.turma = ? AND usr.pelotao = ? AND ref.data >= ? AND ref.data <= ?";
            try (Connection conexao = ConexaoBanco.obterConexao();
                 PreparedStatement pstmt = conexao.prepareStatement(sql)) {

                pstmt.setInt(1, turma);
                pstmt.setInt(2, pelotao);
                pstmt.setDate(3, DateUtil.convertStringToSqlDate(dataInicio));
                pstmt.setDate(4, DateUtil.convertStringToSqlDate(dataFim));

                nomeArquivo = nomeArquivo + "("+DateUtil.convertStringToSqlDate(dataInicio)+"&"+DateUtil.convertStringToSqlDate(dataFim)+").xlsx";
                System.out.println(nomeArquivo);

                ResultSet resultSet = pstmt.executeQuery();

                Map<String, Map<String, List<String>>> dados = new HashMap<>();

                while (resultSet.next()) {
                    String nome = resultSet.getString("nome_de_guerra");
                    String atributo = resultSet.getString("refeicao");
                    String data = resultSet.getString("data");

                    dados.putIfAbsent(nome, new HashMap<>());
                    Map<String, List<String>> dataMap = dados.get(nome);
                    dataMap.putIfAbsent(data, new ArrayList<>());
                    dataMap.get(data).add(atributo);
                }

                Workbook workbook = new XSSFWorkbook();
                LocalDate today = LocalDate.now();
                int year = today.getYear();
                int ano = year % 100 +5 - turma;
                Sheet sheet = workbook.createSheet(ano+"º Ano - "+ pelotao+"º Pelotão");

                Font font = workbook.createFont();
                font.setBold(true);

                // Aplica o estilo de fonte à célula
                CellStyle style = workbook.createCellStyle();
                style.setFont(font);

                int rowNum = 0;
                Row headerRow = sheet.createRow(rowNum++);
                Cell cell = headerRow.createCell(0);
                cell.setCellValue("NOME DE GUERRA");
                cell.setCellStyle(style);


                Set<String> datas = new TreeSet<>();
                for (Map<String, List<String>> dataMap : dados.values()) {
                    datas.addAll(dataMap.keySet());
                }

                int colNum = 1;
                for (String data : datas) {
                    headerRow.createCell(colNum++).setCellValue(data);
                }

                for (Map.Entry<String, Map<String, List<String>>> entry : dados.entrySet()) {
                    String nome = entry.getKey();
                    Map<String, List<String>> dataMap = entry.getValue();

                    Row dataRow = sheet.createRow(rowNum++);
                    dataRow.createCell(0).setCellValue(nome);

                    colNum = 1;
                    for (String data : datas) {
                        List<String> atributos = dataMap.getOrDefault(data, Collections.emptyList());
                        String atributosConcatenados = String.join(", ", atributos);
                        dataRow.createCell(colNum++).setCellValue(atributosConcatenados);
                    }
                }

                for (int i=0; i<=colNum; i++){
                    sheet.autoSizeColumn(i);
                }

                FileOutputStream fileOut = new FileOutputStream(nomeArquivo);
                workbook.write(fileOut);
                fileOut.close();
                workbook.close();

                System.out.println("Dados exportados com sucesso para " + nomeArquivo);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }


    public static boolean exportarFormatado(String nomeArquivo, int turma, int pelotao, String dataInicio, String dataFim) {
        String sql = "SELECT usr.nome_de_guerra AS NOME_DE_GUERRA, ref.tipo AS REFEICAO, ref.data AS DATA\n" +
                "FROM arranchamentos arr\n" +
                "JOIN usuarios usr ON arr.usuario_id = usr.id\n" +
                "JOIN refeicoes ref ON arr.refeicao_id = ref.id\n" +
                "WHERE usr.turma = ? AND usr.pelotao = ? AND ref.data >= ? AND ref.data <= ?";
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, turma);
            pstmt.setInt(2, pelotao);
            pstmt.setDate(3, DateUtil.convertStringToSqlDate(dataInicio));
            pstmt.setDate(4, DateUtil.convertStringToSqlDate(dataFim));

            nomeArquivo = nomeArquivo + "("+DateUtil.convertStringToSqlDate(dataInicio)+"&"+DateUtil.convertStringToSqlDate(dataFim)+").xlsx";
            System.out.println(nomeArquivo);

            ResultSet resultSet = pstmt.executeQuery();

            Map<String, Map<String, List<String>>> dados = new HashMap<>();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome_de_guerra");
                String atributo = resultSet.getString("refeicao");
                String data = resultSet.getString("data");

                dados.putIfAbsent(nome, new HashMap<>());
                Map<String, List<String>> dataMap = dados.get(nome);
                dataMap.putIfAbsent(data, new ArrayList<>());
                dataMap.get(data).add(atributo);
            }

            Workbook workbook = new XSSFWorkbook();
            LocalDate today = LocalDate.now();
            int year = today.getYear();
            int ano = year % 100 +5 - turma;
            Sheet sheet = workbook.createSheet(ano+"º Ano - "+ pelotao+"º Pelotão");

            Font font = workbook.createFont();
            font.setBold(true);

            // Aplica o estilo de fonte à célula
            CellStyle style = workbook.createCellStyle();
            style.setFont(font);

            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            Cell cell = headerRow.createCell(0);
            cell.setCellValue("NOME DE GUERRA");
            cell.setCellStyle(style);


            Set<String> datas = new TreeSet<>();
            for (Map<String, List<String>> dataMap : dados.values()) {
                datas.addAll(dataMap.keySet());
            }

            int colNum = 1;
            for (String data : datas) {
                headerRow.createCell(colNum++).setCellValue(data+" cafe");
                headerRow.createCell(colNum++).setCellValue(data+" almoco");
                headerRow.createCell(colNum++).setCellValue(data+" janta");
                headerRow.createCell(colNum++).setCellValue(data+" ceia");
            }

            for (Map.Entry<String, Map<String, List<String>>> entry : dados.entrySet()) {
                String nome = entry.getKey();
                Map<String, List<String>> dataMap = entry.getValue();

                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(nome);

                colNum = 1;
                for (String data : datas) {
                    List<String> atributos = dataMap.getOrDefault(data, Collections.emptyList());
                    for(String atributo : atributos){
                        if(Objects.equals(atributo, "cafe")){
                            dataRow.createCell(colNum).setCellValue("X");
                        } else if(Objects.equals(atributo, "almoco")){
                            dataRow.createCell(colNum+1).setCellValue("X");
                        } else if(Objects.equals(atributo, "janta")){
                            dataRow.createCell(colNum+2).setCellValue("X");
                        } else if(Objects.equals(atributo, "ceia")){
                            dataRow.createCell(colNum+3).setCellValue("X");
                        }
                    }
                    colNum=colNum+4;
                }
            }

            for (int i=0; i<=colNum; i++){
                sheet.autoSizeColumn(i);
            }

            FileOutputStream fileOut = new FileOutputStream(nomeArquivo);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("Dados exportados com sucesso para " + nomeArquivo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        exportarFormatado(System.getProperty("user.home") + "/Downloads/" + "/DADOS",25,2, "06/05/2024", "10/05/2024");
    }
}
