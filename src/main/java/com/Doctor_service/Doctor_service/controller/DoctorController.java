package com.Doctor_service.Doctor_service.controller;



import  com.Doctor_service.Doctor_service.model.Doctor;
import com.Doctor_service.Doctor_service.model.User;
import  com.Doctor_service.Doctor_service.service.DoctorService;
import  com.Doctor_service.Doctor_service.repositery.DoctorRepositery;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://127.0.0.1:5500") // Adjust based on your frontend origin
@RestController
@RequestMapping("/doctors")
public class DoctorController {


    @Autowired
    private DoctorService  doctorService;


    @Autowired
    private  final DoctorRepositery doctorRepositery;


    public DoctorController(DoctorRepositery doctorRepositery) {
        this.doctorRepositery = doctorRepositery;
    }


    @PostMapping("/add")
    public  String addDoctor(@RequestBody Doctor doctor){
        doctorService.createDoctor(doctor);
        return "doctor added succesfully";
    }

    @GetMapping("/validate/{doctorId}")
    public  ResponseEntity<DoctorResponse> validateDoctor(@PathVariable Integer doctorId){
        Optional<Doctor> doctorOptional=doctorService.getDoctorById(doctorId);

        if (doctorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Doctor doctor=doctorOptional.get();

        return  ResponseEntity.ok(new DoctorResponse(
                doctor.getDoctor_Id(),
                doctor.getFirstname(),
                doctor.getLastname(),
                doctor.getEmial(),
                doctor.getPhonenumber(),
                doctor.getSpecilization(),
                doctor.getExperience(),
                doctor.getEducation()
        ));
    }

    public  static class DoctorResponse{


        private Integer doctor_Id;

        private String firstname;

        private String lastname ;


        private String phonenumber ;

        private String email;

        private String specilization ;

        private  String experience;

        private String education;

        public DoctorResponse(Integer doctor_Id, String firstname, String lastname, String phonenumber, String email, String specilization,String experience,String education) {
            this.doctor_Id = doctor_Id;
            this.firstname = firstname;
            this.lastname = lastname;
            this.phonenumber = phonenumber;
            this.email = email;
            this.specilization = specilization;
            this.experience=experience;
            this.education=education;
        }

        public Integer getDoctor_Id() {
            return doctor_Id;
        }

        public void setDoctor_Id(Integer doctor_Id) {
            this.doctor_Id = doctor_Id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSpecilization() {
            return specilization;
        }

        public void setSpecilization(String specilization) {
            this.specilization = specilization;
        }


        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }
    }
    @GetMapping("/getAll")
    public List<Doctor> getAllDoctors(){
        return  doctorService.getAllDoctors();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String>updateDoctor(@PathVariable Integer id,@RequestBody Doctor doctor){
        try {
            String result=doctorService.updateDoctor(id,doctor);
            return  new ResponseEntity<>(result, HttpStatus.OK);
        }

        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private  User user;
    @GetMapping("/ByUserId/{userId}")
    public ResponseEntity<DoctorDto> getDoctorByUserId(@PathVariable Integer userId){
        Doctor doctor=doctorRepositery.findByUserId(userId);
        if (doctor !=null){
            return  ResponseEntity.ok(new DoctorDto(doctor.getDoctor_Id(),doctor.getUserId()) );
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/ByDoc/{doctorId}")
    public  ResponseEntity<DoctorResponse> getDoctorDataByid(@PathVariable Integer doctorId){
        Optional<Doctor> doctor=doctorRepositery.findById(doctorId);
        if (doctor !=null){
            DoctorResponse doctorRes=new DoctorResponse(
                    doctor.get().getDoctor_Id(),
                    doctor.get().getFirstname(),
                    doctor.get().getLastname(),
                    doctor.get().getPhonenumber(),
                    doctor.get().getEmial(),
                    doctor.get().getSpecilization(),
                    doctor.get().getEducation(),
                    doctor.get().getExperience()

            );
            return ResponseEntity.ok(doctorRes);
        } else {
            return new ResponseEntity<>(new DoctorResponse(null, null, null, null, null, null,null,null), HttpStatus.NOT_FOUND);
        }



    }
    public  class DoctorDto {

        private  Integer doctor_Id;
        private  Integer userId;


        public DoctorDto(Integer doctor_Id, Integer userId) {
            this.doctor_Id = doctor_Id;
            this.userId = userId;
        }

        public Integer getDoctor_Id() {
            return doctor_Id;
        }

        public void setDoctor_Id(Integer doctor_Id) {
            this.doctor_Id = doctor_Id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }

        @DeleteMapping("/delete/{doctor_Id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Integer doctor_Id) {

        try {
            Optional<Doctor> existingpatient=doctorService.getDoctorById(doctor_Id);

            if (existingpatient.isPresent()){
                doctorService.deleteDoctor(doctor_Id);
                return ResponseEntity.ok("Succesfully deleted");
            }
            else{
                return ResponseEntity.ok("no doctor");
            }
        }
        catch (Exception ex){
            return ResponseEntity.ok(ex.getMessage());
        }


    }


    @GetMapping("/doccount")
    public  ResponseEntity<Long> getDocCount(){
        long count=doctorService.countDoctors();
        return ResponseEntity.ok(count);
    }


    @GetMapping("/report/{doctorId}")
    public ResponseEntity<ByteArrayResource> generateDoctorReport(@PathVariable Integer doctorId) {
        Optional<Doctor> doctorOptional = doctorService.getDoctorById(doctorId);
        if (doctorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Doctor doctor = doctorOptional.get();

        try {
            // Generate PDF report
            byte[] pdfBytes = generateDoctorPdfReport(doctor);

            // Prepare response with PDF
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=doctor_report_" + doctorId + ".pdf");
            headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            headers.add(HttpHeaders.PRAGMA, "no-cache");
            headers.add(HttpHeaders.EXPIRES, "0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfBytes.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Helper method to generate PDF
    private byte[] generateDoctorPdfReport(Doctor doctor) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Draw header background
            contentStream.setNonStrokingColor(0, 120, 215); // Blue background
            contentStream.addRect(0, 750, page.getMediaBox().getWidth(), 50);
            contentStream.fill();

            // Header Text
            contentStream.beginText();
            contentStream.setNonStrokingColor(Color.WHITE); // White text
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
            contentStream.newLineAtOffset(220, 765);
            contentStream.showText("Doctor Report");
            contentStream.endText();

            // Reset color for body
            contentStream.setNonStrokingColor(Color.BLACK);

            // Draw line below header
            contentStream.setStrokingColor(Color.DARK_GRAY);
            contentStream.moveTo(50, 740);
            contentStream.lineTo(550, 740);
            contentStream.stroke();

            // Body content
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(70, 700);

            contentStream.showText("Doctor ID: " + doctor.getDoctor_Id());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Name: " + doctor.getFirstname() + " " + doctor.getLastname());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Email: " + doctor.getEmial());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Phone: " + doctor.getPhonenumber());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Specialization: " + doctor.getSpecilization());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Experience: " + doctor.getExperience());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Education: " + doctor.getEducation());

            contentStream.endText();

            contentStream.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        }
    }

}
