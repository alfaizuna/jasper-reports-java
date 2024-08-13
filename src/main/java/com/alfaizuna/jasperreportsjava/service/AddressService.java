package com.alfaizuna.jasperreportsjava.service;

import com.alfaizuna.jasperreportsjava.entity.Address;
import com.alfaizuna.jasperreportsjava.repository.AddressRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressService {

    private final AddressRepository repository;

    public AddressService(AddressRepository addressRepository) {
        this.repository = addressRepository;
    }

    public void exportJasperReport(HttpServletResponse response) throws JRException, IOException {
        List<Address> address = repository.findAll();
        //Get file and compile it
        File file = ResourceUtils.getFile("classpath:templates/Address_01.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(address);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Simplifying Tech");
        //Fill Jasper report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        //Export report
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }
}
