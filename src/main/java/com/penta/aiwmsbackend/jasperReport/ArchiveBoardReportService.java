package com.penta.aiwmsbackend.jasperReport;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.service.BoardService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class ArchiveBoardReportService {

        @Autowired
        private BoardService boardService;

        private List<Board> blist;

        public String archiveBoardReport(String format, Integer id) throws JRException, IOException {

                String filePath = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                                + "src\\main\\resources\\report\\";

                String path = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                                + "src\\main\\resources\\static\\Exported-Reports";
                String exportedFile = null;
                this.blist = this.boardService.reportArchiveBoard(id);
                File file = ResourceUtils.getFile(filePath + "board.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.blist);
                Map<String, Object> parameters = new HashMap<>();

                parameters.put("createdBy", "Admin");
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                if (format.equalsIgnoreCase("html")) {
                        exportedFile = "\\archive-boards" + LocalDate.now() + LocalDateTime.now().getHour() + "hrs"
                                        + LocalDateTime.now().getMinute() + "minutes" + ".html";
                        JasperExportManager.exportReportToHtmlFile(jasperPrint,
                                        path + exportedFile);
                }
                if (format.equalsIgnoreCase("pdf")) {
                        exportedFile = "\\archive-boards" + LocalDate.now() + LocalDateTime.now().getHour() + "hrs"
                                        + LocalDateTime.now().getMinute() + "minutes" + ".pdf";
                        JasperExportManager.exportReportToPdfFile(jasperPrint,
                                        path + exportedFile);
                }
                if (format.equalsIgnoreCase("excel")) {
                        exportedFile = "\\archive-boards" + LocalDate.now() + LocalDateTime.now().getHour() + "hrs"
                                        + LocalDateTime.now().getMinute() + "minutes" + ".xlsx";
                        JRXlsxExporter exporter = new JRXlsxExporter();
                        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
                                        path + exportedFile));

                        SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
                        config.setOnePagePerSheet(true);
                        config.setDetectCellType(true);
                        exporter.setConfiguration(config);
                        exporter.exportReport();
                }
                return exportedFile;

        }

        // public void reportBoardList(Integer id) {
        // this.blist = this.boardService.showdeletedBoards(id);
        // }

}
