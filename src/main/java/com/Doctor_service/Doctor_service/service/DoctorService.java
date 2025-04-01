package com.Doctor_service.Doctor_service.service;



import  com.Doctor_service.Doctor_service.model.UserResponse;
import  com.Doctor_service.Doctor_service.model.Doctor;
import com.Doctor_service.Doctor_service.repositery.DoctorRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {


    @Autowired
    private DoctorRepositery doctorRepositery;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private UserServiceClient userServiceClient;

    public Doctor createDoctor(Doctor doctor){
        if (doctor.getUserId()==null){
            throw new RuntimeException("userid is mising in Doctor request");
        }

        UserResponse  user=userServiceClient.validateUser(doctor.getUserId());

        if (user==null || !user.getRole().equalsIgnoreCase("Doctor")){
            throw  new RuntimeException("invalid user ! only doctors can register");
        }

        return doctorRepositery.save(doctor);
    }



    public List<Doctor> getAllDoctors(){
        return  doctorRepositery.findAll();
    }

    public Optional<Doctor> findDoctorById(Integer docid){
        return doctorRepositery.findById(docid);

    }

    public  Optional<Doctor>getDoctorById(@PathVariable Integer doctorId){
        return  doctorRepositery.findById(doctorId);
    }

    public  String updateDoctor(Integer id,Doctor doctor){
        Optional<Doctor> exisstingDoctor=doctorRepositery.findById(id);
        if (exisstingDoctor.isPresent()){
            Doctor updatedDoctor=exisstingDoctor.get();

           updatedDoctor.setFirstname(doctor.getFirstname());
           updatedDoctor.setLastname(doctor.getLastname());
           updatedDoctor.setEmial(doctor.getEmial());
           updatedDoctor.setPhonenumber(doctor.getPhonenumber());
           updatedDoctor.setSpecilization(doctor.getSpecilization());

           doctorRepositery.save(updatedDoctor);
           return "Doctor Updated Succesfully";
        }
        else {
            throw  new RuntimeException("Doctor not found");
        }
    }


    public String deleteDoctor(Integer docId){
        doctorRepositery.deleteById(docId);
        return "Doctor deleted succesfully";
    }

    public long countDoctors() {
        return doctorRepositery.count();
    }

}
