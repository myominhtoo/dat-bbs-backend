package com.penta.aiwmsbackend.jasperReport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.util.StreamReaderDelegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.service.BoardService;

import net.bytebuddy.utility.nullability.NeverNull.ByDefault;

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

    public String exportBoardReport(String reportFormat, HttpServletResponse response) throws JRException, IOException {

        String filePath = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                + "src\\main\\resources\\report\\";

        List<Board> board = boardService.reportBoard();

        String path = "D:\\project";
        File file = ResourceUtils.getFile(filePath + "board.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(board);
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("createdBy", "Admin");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\board.pdf");
        }
        if (reportFormat.equalsIgnoreCase("excel")) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path + "\\board.xlsx"));

            SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
            config.setOnePagePerSheet(true);
            config.setDetectCellType(true);
            exporter.setConfiguration(config);
            exporter.exportReport();
        }
        return "report generated in path " + path;
    }

}