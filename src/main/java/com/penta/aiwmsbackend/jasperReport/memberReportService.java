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

import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.service.UserService;

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
public class memberReportService {

    @Autowired
    private UserService userService;

    private List<User> members;

    public String exportReport(String reportFormat , Integer id)
            throws JRException, IOException {

        String path = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                        + "src\\main\\resources\\static\\Exported-Reports";

        String pathname = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                .replace("target\\test-classes", "") + "src\\main\\resources\\report\\";

        String exportedFile = null;
        File file = ResourceUtils.getFile(pathname + "memberReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        this.members = this.userService.getRpMember(id);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.members);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Admin");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            exportedFile =  "\\member" + LocalDate.now()+ LocalDateTime.now().getHour() + " hrs "
                            + LocalDateTime.now().getMinute() + "minutes" + ".html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint,
                    path + exportedFile);
        }

        if (reportFormat.equalsIgnoreCase("pdf")) {
            exportedFile =  "\\member" + LocalDate.now() + LocalDateTime.now().getHour() + " hrs "
                             + LocalDateTime.now().getMinute() + "minutes" + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint,path + exportedFile);
        }

        if (reportFormat.equalsIgnoreCase("excel")) {
            exportedFile =  "\\member" + LocalDate.now() + LocalDateTime.now().getHour() + " hrs "
                            + LocalDateTime.now().getMinute() + "minutes" + ".xlsx";
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            configuration.setDetectCellType(true);
            exporter.setConfiguration(configuration);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
                    path + exportedFile));

            // response.addHeader("Content-Disposition", "attachment; filename=user.xlsx;");
            exporter.exportReport();
        }
        return exportedFile;
    }

  

}
