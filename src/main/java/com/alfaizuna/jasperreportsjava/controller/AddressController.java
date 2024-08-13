package com.alfaizuna.jasperreportsjava.controller;

import com.alfaizuna.jasperreportsjava.entity.Address;
import com.alfaizuna.jasperreportsjava.repository.AddressRepository;
import com.alfaizuna.jasperreportsjava.service.AddressService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class AddressController {

    private final AddressService addressService;
    private final AddressRepository addressRepository;

    public AddressController(AddressService addressService, AddressRepository addressRepository) {
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }

    @GetMapping("/getAddress")
    public List<Address> getAddress() {
        List<Address> address = (List<Address>) addressRepository.findAll();
        return address;
    }

    @GetMapping("/jasperpdf/export")
    public void createPDF(HttpServletResponse response) throws IOException, JRException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        addressService.exportJasperReport(response);
    }
}
