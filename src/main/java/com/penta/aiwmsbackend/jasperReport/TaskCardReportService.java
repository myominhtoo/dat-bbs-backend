package com.penta.aiwmsbackend.jasperReport;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.service.TaskCardService;

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
public class TaskCardReportService {

    @Autowired
    private TaskCardService taskCardService;

    private List<TaskCard> tList;

    public String exportTaskReport(String reportFormat) throws JRException, IOException {

        String pathName = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                .replace("target\\test-classes", "") + "src\\main\\resources\\report\\";

        // List<TaskCard> reportTaskCards = taskCardService.reportTaskCards(boardId);
        // List<TaskCard> reportTaskCards = taskCardService.reportTaskCards();

        String path = "D:\\Project";

        File file = ResourceUtils.getFile(pathName + "taskcardReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.tList);

        // System.out.println(tList);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Admin");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\taskCard.pdf");
        }

        if (reportFormat.equalsIgnoreCase("excel")) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            configuration.setDetectCellType(true);
            exporter.setConfiguration(configuration);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path + "\\taskCard.xlsx"));
            exporter.exportReport();
        }
        return "report generated in path " + path;
    }

    public void gerReportTaskCard(Integer id) {
        this.tList = this.taskCardService.reportTaskCards(id);
    }

}
