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
public class AssignedTasksReportService {

    @Autowired
    private TaskCardService taskCardService;
    private List<TaskCard> tasks;

    public String exportAssingedTaskReport(String reportFormat , Integer id) throws JRException, IOException {

        String pathName = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                         .replace("target\\test-classes", "") + "src\\main\\resources\\report\\";

        String path = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                       + "src\\main\\resources\\static\\Exported-Reports";

        File file = ResourceUtils.getFile(pathName + "AssignedTask.jrxml");

        this.tasks= this.taskCardService.reportAssignedTasks(id);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        String exportedFile = null;

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.tasks);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Admin");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            exportedFile = "\\assignedTasks" + LocalDate.now() + LocalDateTime.now().getHour() + "hrs"
                           + LocalDateTime.now().getMinute() + "minutes" + ".html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + exportedFile );
        }

        if (reportFormat.equalsIgnoreCase("pdf")) {
            exportedFile = "\\assignedTasks" + LocalDate.now() + LocalDateTime.now().getHour() + "hrs"
                           + LocalDateTime.now().getMinute() + "minutes" + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + exportedFile);
        }

        if (reportFormat.equalsIgnoreCase("excel")) {
            exportedFile = "\\assignedTasks" + LocalDate.now() + LocalDateTime.now().getHour() + "hrs"
                            + LocalDateTime.now().getMinute() + "minutes" + ".xlsx";
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            configuration.setDetectCellType(true);
            exporter.setConfiguration(configuration);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path + exportedFile));
            exporter.exportReport();
        }
        return exportedFile;
    }

}
