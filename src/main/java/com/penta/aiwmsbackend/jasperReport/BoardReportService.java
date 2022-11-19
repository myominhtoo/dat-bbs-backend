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
public class BoardReportService {
    
    @Autowired
    private BoardService boardService;

    private List<Board> blist;

    public String exportBoardReport(String format) throws JRException, IOException {

        String filePath = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                + "src\\main\\resources\\report\\";
        String path = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                + "src\\main\\resources\\static\\Exported-Reports";

        // String path = "D:\\Penta\\JasperReport";
        File file = ResourceUtils.getFile(filePath + "board.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.blist);
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("createdBy", "Admin");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (format.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint,
                    path + "\\board" + LocalDate.now() + " " + LocalDateTime.now().getHour() + " hrs "
                            + LocalDateTime.now().getMinute() + " minutes " + ".html");
        }
        if (format.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    path + "\\board" + LocalDate.now() + " " + LocalDateTime.now().getHour() + " hrs "
                            + LocalDateTime.now().getMinute() + " minutes " + ".pdf");
        }
        if (format.equalsIgnoreCase("excel")) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
                    path + "\\board" + LocalDate.now() + " " + LocalDateTime.now().getHour() + " hrs "
                            + LocalDateTime.now().getMinute() + " minutes " + ".xlsx"));

            SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
            config.setOnePagePerSheet(true);
            config.setDetectCellType(true);
            exporter.setConfiguration(config);
            exporter.exportReport();
        }
        return "report generated in path " + path;
    }

    public void reportBoardList(Integer id) {
        this.blist = this.boardService.reportBoard(id);
    }

}
